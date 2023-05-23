package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.DocumentBuilder;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IAlquileres;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Alquileres implements IAlquileres {

	private static final File FICHERO_ALQUILERES = new File(String.format("datos%salquileres.xml", File.separator));
	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final String RAIZ = "alquileres";
	private static final String ALQUILER = "alquiler";
	private static final String CLIENTE = "cliente";
	private static final String VEHICULO = "vehiculo";
	private static final String FECHA_ALQUILER = "fechaAlquiler";
	private static final String FECHA_DEVOLUCION = "fechaDevolucion";
	private static Alquileres instancia;

	private List<Alquiler> coleccionAlquileres;

	// creará la lista
	public Alquileres() {
		coleccionAlquileres = new ArrayList<>();
	}

	static Alquileres getInstancia() {
		if (instancia == null) {
			instancia = new Alquileres();
		}
		return instancia;

	}

	public void comenzar() {
		Document documento = UtilidadesXml.leerXmlDeFichero(FICHERO_ALQUILERES);

		if (documento == null) {
			System.out.println("No se puede leer un documento nulo");
		} else {
			leerDom(documento);
			System.out.println("El documento se ha leído correctamente");
		}

	}

	private void leerDom(Document documentoXml) {
		NodeList alquileresNode = documentoXml.getElementsByTagName(ALQUILER);
		for (int i = 0; i < alquileresNode.getLength(); i++) {
			Node nAlquileres = alquileresNode.item(i);
			if (nAlquileres.getNodeType() == Node.ELEMENT_NODE) {
				try {
					insertar(getAlquiler((Element) nAlquileres));
				} catch (OperationNotSupportedException | NullPointerException | IllegalArgumentException e) {
					System.out.println("Error al procesar el alquiler número " + i + " ---> " + e.getMessage());
				}
			}
		}

	}

	private Alquiler getAlquiler(Element elemento) throws OperationNotSupportedException {
		String dni = elemento.getAttribute(CLIENTE);
		String matricula = elemento.getAttribute(VEHICULO);
		String fechaAlq = elemento.getAttribute(FECHA_ALQUILER);
		String fechaDev = null;

		Cliente cliente = Clientes.getInstancia().buscar(Cliente.getClienteConDni(dni));
		Vehiculo vehiculo = Vehiculos.getInstancia().buscar(Vehiculo.getVehiculoConMatricula(matricula));
		LocalDate fechaAlquiler = LocalDate.parse(fechaAlq, FORMATO_FECHA);

		Alquiler alquiler = new Alquiler(cliente, vehiculo, fechaAlquiler);

		if (elemento.hasAttribute(FECHA_DEVOLUCION)) {
			fechaDev = elemento.getAttribute(FECHA_DEVOLUCION);
			LocalDate fechaDevolucion = LocalDate.parse(fechaDev, FORMATO_FECHA);
			alquiler.devolver(fechaDevolucion);
		}

		return alquiler;

	}

	public void terminar() {
		Document documento = crearDom();
		UtilidadesXml.escribirXmlAFichero(documento, FICHERO_ALQUILERES);

	}

	public Document crearDom() {
		DocumentBuilder constructor = UtilidadesXml.crearConstructorDocumentoXml();
		Document documentoXml = null;
		if (constructor != null) {
			documentoXml = constructor.newDocument();
			documentoXml.appendChild(documentoXml.createElement(RAIZ));
			for (Alquiler alquiler1 : getInstancia().get()) {
				Element elementoAlquiler = getElemento(documentoXml, alquiler1);
				documentoXml.getDocumentElement().appendChild(elementoAlquiler);
			}
		}
		return documentoXml;
	}

	public Element getElemento(Document documentoXml, Alquiler alquiler) {
		Element elementoAlquiler = documentoXml.createElement(ALQUILER);
		elementoAlquiler.setAttribute(CLIENTE, String.format("%s", alquiler.getCliente().getDni()));
		elementoAlquiler.setAttribute(VEHICULO, String.format("%s", alquiler.getVehiculo().getMatricula()));
		elementoAlquiler.setAttribute(FECHA_ALQUILER, FORMATO_FECHA.format(alquiler.getFechaAlquiler()));

		if (alquiler.getFechaDevolucion() == null) {
			System.out.println("La fecha de devolución es nula");
		} else {
			elementoAlquiler.setAttribute(FECHA_DEVOLUCION, FORMATO_FECHA.format(alquiler.getFechaDevolucion()));
		}

		return elementoAlquiler;
	}

	// AQUÍ COMIENZA LA V1

	// devolverá una nueva lista con los mismos elementos (no debe crear nuevas
	// instancias)
	@Override
	public List<Alquiler> get() {
		return new ArrayList<>(coleccionAlquileres);
	}

	// para un cliente dado, que devolverá una nueva lista con los alquileres para
	// dicho cliente (no debe crear nuevas instancias).
	@Override
	public List<Alquiler> get(Cliente cliente) {
		List<Alquiler> alqCliente = new ArrayList<>();
		for (Alquiler alquiler : coleccionAlquileres) {
			if (alquiler.getCliente().equals(cliente)) {
				alqCliente.add(alquiler);
			}
		}
		return alqCliente;

	}

	// para un turismo dado, que devolverá una nueva lista con los alquileres para
	// dicho turismo (no debe crear nuevas instancias).
	@Override
	public List<Alquiler> get(Vehiculo vehiculo) {
		List<Alquiler> alqVehiculo = new ArrayList<>();
		for (Alquiler alquiler : coleccionAlquileres) {
			if (alquiler.getVehiculo().equals(vehiculo)) {
				alqVehiculo.add(alquiler);
			}
		}
		return alqVehiculo;
	}

	// comprobará que en la lista no existe ningún alquiler sin devolver ni para el
	// cliente ni para el turismo y que tampoco hay un alquiler devuelto, del
	// cliente o del turismo, con fecha de devolución posterior a la fecha en la que
	// se pretende alquiler.

	private void comprobarAlquiler(Cliente cliente, Vehiculo vehiculo, LocalDate fechaAlquiler)
			throws OperationNotSupportedException {
		for (Alquiler alquiler : coleccionAlquileres) {
			if (alquiler.getCliente().equals(cliente) && alquiler.getFechaDevolucion() == null) {
				throw new OperationNotSupportedException("ERROR: El cliente tiene otro alquiler sin devolver.");
			}
			if (alquiler.getVehiculo().equals(vehiculo) && alquiler.getFechaDevolucion() == null) {
				throw new OperationNotSupportedException("ERROR: El vehículo está actualmente alquilado.");
			}
			if (alquiler.getCliente().equals(cliente) && alquiler.getFechaDevolucion() != null
					&& (alquiler.getFechaDevolucion().isAfter(fechaAlquiler)
							|| alquiler.getFechaDevolucion().isEqual(fechaAlquiler))) {

				throw new OperationNotSupportedException("ERROR: El cliente tiene un alquiler posterior.");

			}
			if (alquiler.getVehiculo().equals(vehiculo) && alquiler.getFechaDevolucion() != null
					&& (alquiler.getFechaDevolucion().isAfter(fechaAlquiler)
							|| alquiler.getFechaDevolucion().isEqual(fechaAlquiler))) {

				throw new OperationNotSupportedException("ERROR: El vehículo tiene un alquiler posterior.");

			}
		}

	}

	@Override
	public void devolver(Cliente cliente, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede devolver un alquiler de un cliente nulo.");
		}
		Alquiler alquilerCliente = getAlquilerAbierto(cliente);
		if (alquilerCliente == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler abierto para ese cliente.");
		}
		alquilerCliente.devolver(fechaDevolucion);

	}

	// Estos métodos deben hacer uso de un iterador y una vez encontremos el
	// alquiler abierto, si es que existe, el método no debe continuar la búsqueda.
	private Alquiler getAlquilerAbierto(Cliente cliente) {
		Iterator<Alquiler> iterador = coleccionAlquileres.iterator();
		Alquiler alquilerEncontrado = null;
		while (iterador.hasNext() && (alquilerEncontrado == null)) {
			Alquiler alquilerCliente = iterador.next();
			if (alquilerCliente.getCliente().equals(cliente) && (alquilerCliente.getFechaDevolucion() == null)) {
				alquilerEncontrado = alquilerCliente;
			}

		}
		return alquilerEncontrado;

	}

	// añadirá un alquiler a la lista si éste no es nulo y pasa la comprobación
	// anterior
	@Override
	public void insertar(Alquiler alquiler) throws OperationNotSupportedException {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede insertar un alquiler nulo.");
		}

		comprobarAlquiler(alquiler.getCliente(), alquiler.getVehiculo(), alquiler.getFechaAlquiler());
		coleccionAlquileres.add(alquiler);
	}

	// devolverá (asignará la fecha de devolución) si éste existe en la lista o
	// lanzará la excepción en caso contrario.
	@Override
	public void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede devolver un alquiler de un vehículo nulo.");
		}
		Alquiler alquilerVehiculo = getAlquilerAbierto(vehiculo);
		if (alquilerVehiculo == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler abierto para ese vehículo.");
		}
		alquilerVehiculo.devolver(fechaDevolucion);

	}

	// devolverá el alquiler si éste se encuentra en la lista y null en caso
	// contrario

	private Alquiler getAlquilerAbierto(Vehiculo vehiculo) {
		Iterator<Alquiler> iterador = coleccionAlquileres.iterator();
		Alquiler alquilerEncontrado = null;
		while (iterador.hasNext() && (alquilerEncontrado == null)) {
			Alquiler alquilerVehiculo = iterador.next();
			if (alquilerVehiculo.getVehiculo().equals(vehiculo) && (alquilerVehiculo.getFechaDevolucion() == null)) {
				alquilerEncontrado = alquilerVehiculo;
			}

		}
		return alquilerEncontrado;

	}

	@Override
	public Alquiler buscar(Alquiler alquiler) {
		Alquiler comodinBuscar = null;
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede buscar un alquiler nulo.");
		}
		int alquilerAux = coleccionAlquileres.indexOf(alquiler);
		if (alquilerAux != -1) {
			comodinBuscar = coleccionAlquileres.get(alquilerAux);
		}
		return comodinBuscar;

	}

	// borrará el alquiler si éste existe en la lista o lanzará una excepción en
	// caso contrario.
	@Override
	public void borrar(Alquiler alquiler) throws OperationNotSupportedException {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede borrar un alquiler nulo.");
		}
		Alquiler alquilerAux = buscar(alquiler);
		if (alquilerAux == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler igual.");
		}
		coleccionAlquileres.remove(alquilerAux);
	}

}
