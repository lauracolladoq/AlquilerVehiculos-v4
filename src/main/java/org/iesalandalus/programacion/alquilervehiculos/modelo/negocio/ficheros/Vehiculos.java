package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.DocumentBuilder;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IVehiculos;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Vehiculos implements IVehiculos {
	private static final File FICHERO_VEHICULOS = new File(String.format("datos%svehiculos.xml", File.separator));
	private static final String RAIZ = "vehiculos";
	private static final String VEHICULO = "vehiculo";
	private static final String MARCA = "marca";
	private static final String MODELO = "modelo";
	private static final String MATRICULA = "matricula";
	private static final String CILINDRADA = "cilindrada";
	private static final String PLAZAS = "plazas";
	private static final String PMA = "pma";
	private static final String TIPO = "tipo";
	private static final String TURISMO = "turismo";
	private static final String AUTOBUS = "autobus";
	private static final String FURGONETA = "furgoneta";
	private static Vehiculos instancia;

	private List<Vehiculo> coleccionVehiculos;

	// creará la lista
	public Vehiculos() {
		coleccionVehiculos = new ArrayList<>();
	}

	static Vehiculos getInstancia() {
		if (instancia == null) {
			instancia = new Vehiculos();
		}
		return instancia;

	}

	public void comenzar() {
		Document documento = UtilidadesXml.leerXmlDeFichero(FICHERO_VEHICULOS);

		if (documento == null) {
			System.out.println("No se puede leer un documento nulo");
		} else {
			leerDom(documento);
			System.out.println("El documento se ha leído correctamente");
		}

	}

	private void leerDom(Document documentoXml) {
		NodeList vehiculosNode = documentoXml.getElementsByTagName(VEHICULO);
		for (int i = 0; i < vehiculosNode.getLength(); i++) {
			Node nVehiculos = vehiculosNode.item(i);
			if (nVehiculos.getNodeType() == Node.ELEMENT_NODE) {
				try {
					insertar(getVehiculo((Element) nVehiculos));
				} catch (OperationNotSupportedException | NullPointerException | IllegalArgumentException e) {
					System.out.println("Error al procesar el vehículo número " + i + " ---> " + e.getMessage());
				}
			}
		}

	}

	private Vehiculo getVehiculo(Element elemento) {
		String marca = elemento.getAttribute(MARCA);
		String modelo = elemento.getAttribute(MODELO);
		String matricula = elemento.getAttribute(MATRICULA);
		String tipo = elemento.getAttribute(TIPO);
		Vehiculo vehiculo = null;

		if (tipo.equals(AUTOBUS)) {
			int plazas = Integer.parseInt(elemento.getAttribute(PLAZAS));
			vehiculo = new Autobus(marca, modelo, plazas, matricula);
		}
		if (tipo.equals(TURISMO)) {
			int cilindrada = Integer.parseInt(elemento.getAttribute(CILINDRADA));
			vehiculo = new Turismo(marca, modelo, cilindrada, matricula);
		}
		if (tipo.equals(FURGONETA)) {
			int plazas = Integer.parseInt(elemento.getAttribute(PLAZAS));
			int pma = Integer.parseInt(elemento.getAttribute(PMA));
			vehiculo = new Furgoneta(marca, modelo, pma, plazas, matricula);
		}

		return vehiculo;
	}

	public void terminar() {
		Document documento = crearDom();
		UtilidadesXml.escribirXmlAFichero(documento, FICHERO_VEHICULOS);

	}

	public Document crearDom() {
		DocumentBuilder constructor = UtilidadesXml.crearConstructorDocumentoXml();
		Document documentoXml = null;
		if (constructor != null) {
			documentoXml = constructor.newDocument();
			documentoXml.appendChild(documentoXml.createElement(RAIZ));
			for (Vehiculo vehiculo1 : getInstancia().get()) {
				Element elementoVehiculo = getElemento(documentoXml, vehiculo1);
				documentoXml.getDocumentElement().appendChild(elementoVehiculo);
			}
		}
		return documentoXml;
	}

	public Element getElemento(Document documentoXml, Vehiculo vehiculo) {
		Element elementoVehiculo = documentoXml.createElement(VEHICULO);

		elementoVehiculo.setAttribute(MARCA, vehiculo.getMarca());
		elementoVehiculo.setAttribute(MODELO, vehiculo.getModelo());
		elementoVehiculo.setAttribute(MATRICULA, vehiculo.getMatricula());

		if (vehiculo instanceof Autobus autobus) {
			elementoVehiculo.setAttribute(PLAZAS, String.format("%d", autobus.getPlazas()));
			elementoVehiculo.setAttribute(TIPO, AUTOBUS);
		}
		if (vehiculo instanceof Turismo turismo) {
			elementoVehiculo.setAttribute(CILINDRADA, String.format("%d", turismo.getCilindrada()));
			elementoVehiculo.setAttribute(TIPO, TURISMO);
		}
		if (vehiculo instanceof Furgoneta furgoneta) {
			elementoVehiculo.setAttribute(PLAZAS, String.format("%d", furgoneta.getPlazas()));
			elementoVehiculo.setAttribute(TIPO, FURGONETA);
			elementoVehiculo.setAttribute(PMA, String.format("%d", furgoneta.getPma()));

		}

		return elementoVehiculo;
	}

	// AQUÍ EMPIEZA LA V1

	// devolverá una nueva lista con los mismos elementos (no debe crear nuevas
	// instancias).
	@Override
	public List<Vehiculo> get() {
		return new ArrayList<>(coleccionVehiculos);
	}

	// añadirá un turismo a la lista si éste no es nulo y no existe aún en la lista.
	@Override
	public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede insertar un vehículo nulo.");
		}
		if (coleccionVehiculos.contains(vehiculo)) {
			throw new OperationNotSupportedException("ERROR: Ya existe un vehículo con esa matrícula.");
		}
		coleccionVehiculos.add(vehiculo);

	}

	// devolverá el turismo si éste se encuentra en la lista y null en caso
	// contrario.
	@Override
	public Vehiculo buscar(Vehiculo vehiculo) {
		Vehiculo vehiculoBuscado = null;
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede buscar un vehículo nulo.");
		}
		int coleccionVehiculosAux = coleccionVehiculos.indexOf(vehiculo);
		if (coleccionVehiculosAux != -1) {
			vehiculoBuscado = coleccionVehiculos.get(coleccionVehiculosAux);
		}
		return vehiculoBuscado;
	}

	// borrará el turismo si éste existe en la lista o lanzará una excepción en caso
	// contrario
	@Override
	public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede borrar un vehículo nulo.");
		}
		if (buscar(vehiculo) == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún vehículo con esa matrícula.");
		}
		coleccionVehiculos.remove(vehiculo);

	}

}
