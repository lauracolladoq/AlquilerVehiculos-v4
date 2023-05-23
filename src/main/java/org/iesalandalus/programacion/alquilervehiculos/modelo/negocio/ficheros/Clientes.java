package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.DocumentBuilder;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IClientes;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Clientes implements IClientes {

	private static final File FICHERO_CLIENTES = new File(String.format("datos%sclientes.xml", File.separator));
	private static final String RAIZ = "clientes";
	private static final String CLIENTE = "cliente";
	private static final String NOMBRE = "nombre";
	private static final String DNI = "dni";
	private static final String TELEFONO = "telefono";
	private static Clientes instancia;

	private List<Cliente> coleccionClientes;

	public Clientes() {
		coleccionClientes = new ArrayList<>();
	}
	// al comenzar lea el fichero XML de clientes, lo almacene en un una lista y al
	// terminar lo vuelva a almacenar en dicho fichero. El fichero debe estar
	// situado en la carpeta datos de la raíz del proyecto y se debe llamar
	// clientes.xml

	// Mira si la isntancia es nula, si es nula le asigna un new de la clase y
	// finalmente vuelve la instanica, si no es nula la devuelve
	static Clientes getInstancia() {
		if (instancia == null) {
			instancia = new Clientes();
		}
		return instancia;

	}

	public void comenzar() {
		// Llamar a la clase de utlidades y lea un xml, eso te devuelve un documento que
		// se lo pasas a leer dom
		Document documento = UtilidadesXml.leerXmlDeFichero(FICHERO_CLIENTES);

		if (documento == null) {
			System.out.println("No se puede leer un documento nulo");
		} else {
			leerDom(documento);
			System.out.println("El documento se ha leído correctamente");
		}

	}

	private void leerDom(Document documentoXml) {

		// Genera el documento, procesarlo/recorrerlo para quedarse con todos los
		// elementos con la etiqueta cliente y añadir un nuevo cliente a la lista
		// Voy recorriendo el documento y si lee cliente, lo guardo en la lista y uso
		// getCliente
		NodeList clientesNode = documentoXml.getElementsByTagName(CLIENTE);
		for (int i = 0; i < clientesNode.getLength(); i++) {
			Node nClientes = clientesNode.item(i);
			if (nClientes.getNodeType() == Node.ELEMENT_NODE) {
				// Le hago un casting al nodo para que sea elemento porque getCliente recibe
				// elementos
				// Falta añadir a la lista, hacerlo con insertar y hacer try catch "Error al
				// procesar el cliente"
				try {
					insertar(getCliente((Element) nClientes));
				} catch (OperationNotSupportedException | NullPointerException | IllegalArgumentException e) {
					System.out.println("Error al procesar el cliente número " + i + " ---> " + e.getMessage());
				}
			}
		}

	}

	private Cliente getCliente(Element elemento) {
		// elemento.getatribute y el atributo
		// le paso un elemento que he leido del dom y le digo que me lo pase aun cliente
		// para añadiro a la lista
		String nombre = elemento.getAttribute(NOMBRE);
		String dni = elemento.getAttribute(DNI);
		String telefono = elemento.getAttribute(TELEFONO);
		return new Cliente(nombre, dni, telefono);
	}

	public void terminar() {
		// Llama a crear dom, eso te da un documento y llama a escribirFicjero xml
		Document documento = crearDom();
		UtilidadesXml.escribirXmlAFichero(documento, FICHERO_CLIENTES);

	}

	public Document crearDom() {
		DocumentBuilder constructor = UtilidadesXml.crearConstructorDocumentoXml();
		Document documentoXml = null;
		if (constructor != null) {
			documentoXml = constructor.newDocument();
			documentoXml.appendChild(documentoXml.createElement(RAIZ));
			for (Cliente cliente1 : getInstancia().get()) {
				Element elementoCliente = getElemento(documentoXml, cliente1);
				documentoXml.getDocumentElement().appendChild(elementoCliente);
			}
		}
		return documentoXml;
	}

	public Element getElemento(Document documentoXml, Cliente cliente) {
		// Crea un elemento del tipo cliente y le va añadiendo los atributos y lo
		// devuelve
		Element elementoCliente = documentoXml.createElement(CLIENTE);

		elementoCliente.setAttribute(NOMBRE, cliente.getNombre());
		elementoCliente.setAttribute(DNI, cliente.getDni());
		elementoCliente.setAttribute(TELEFONO, cliente.getTelefono());

		return elementoCliente;
	}

	// A PARTIR DE AQUÍ EMPIEZA LA PARTE DE LA V1
	// devolverá una nueva lista con los mismos elementos (no debe crear nuevas
	// instancias)
	@Override
	public List<Cliente> get() {
		return new ArrayList<>(coleccionClientes);
		// Devuelve un list pero en verdad de vuelve un ArrayList
	}

	// añadirá un cliente a la lista si éste no es nulo y no existe aún en la lista.
	@Override
	public void insertar(Cliente cliente) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede insertar un cliente nulo.");
		}
		if (coleccionClientes.contains(cliente)) {
			throw new OperationNotSupportedException("ERROR: Ya existe un cliente con ese DNI.");
		}
		coleccionClientes.add(cliente);

	}

	// devolverá el cliente si éste se encuentra en la lista y null en caso
	// contrario.
	@Override
	public Cliente buscar(Cliente cliente) {

		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede buscar un cliente nulo.");
		}
		Cliente clienteComodin = null;
		int coleccionAux = coleccionClientes.indexOf(cliente);
		if (coleccionAux != -1) {
			clienteComodin = coleccionClientes.get(coleccionAux);
		}
		return clienteComodin;

	}

	// borrará el cliente si éste existe en la lista o lanzará una excepción en caso
	// contrario.
	@Override
	public void borrar(Cliente cliente) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede borrar un cliente nulo.");
		}
		if (buscar(cliente) == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
		}
		coleccionClientes.remove(cliente);

	}

	// permitirá cambiar el nombre o el teléfono (si estos parámetros no son
	// nulos ni blancos) de un cliente existente y si no lanzará la correspondiente
	// excepción.
	@Override
	public void modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede modificar un cliente nulo.");
		}
		Cliente clienteBuscado = buscar(cliente);
		if (clienteBuscado == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
		} else {

			// Si el nombre no es nulo ni blanco, me deja cambiar el nombre
			if (nombre != null && !nombre.isBlank()) {
				clienteBuscado.setNombre(nombre);
			}
			if (telefono != null && !telefono.isBlank()) {
				clienteBuscado.setTelefono(telefono);

			}
//			// PARA QUE SE ACTUALICE LA LISTA CUANDO MODIFICO
//			int aux = coleccionClientes.indexOf(cliente);
//			coleccionClientes.set(aux, clienteBuscado);
		}

	}
}
