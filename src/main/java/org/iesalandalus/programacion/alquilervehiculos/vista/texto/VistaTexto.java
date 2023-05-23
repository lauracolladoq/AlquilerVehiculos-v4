package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import java.time.LocalDate;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;

public class VistaTexto extends Vista {

	public VistaTexto() {

	}

	public void comenzar() {
		Accion.setVista(this);
		Accion elegirAccion = null;
		do {
			Consola.mostrarMenuAcciones();
			elegirAccion = Consola.elegirAccion();
			elegirAccion.ejecutar();
		} while (elegirAccion != Accion.SALIR);
	}

	// mostrará un mensaje de despedida por consola
	public void terminar() {
		getControlador().terminar();
		System.out.printf("%nHasta luego Lucas!");
	}

	public void insertarCliente() {
		try {
			Consola.mostrarCabecera("Insertar cliente");
			getControlador().insertar(Consola.leerCliente());
			System.out.printf("%nCliente insertado correctamente%n%n");
		} catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}

	public void insertarVehiculo() {
		try {
			Consola.mostrarCabecera("Insertar vehiculo");
			getControlador().insertar(Consola.leerVehiculo());
			System.out.printf("%nVehiculo insertado correctamente%n%n");
		} catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}

	public void insertarAlquiler() {
		try {
			Consola.mostrarCabecera("Insertar alquiler");
			getControlador().insertar(Consola.leerAlquiler());
			System.out.printf("%nAlquiler insertado correctamente%n%n");
		} catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}

	public void buscarCliente() {
		try {
			Consola.mostrarCabecera("Buscar cliente");
			System.out.println(getControlador().buscar(Consola.leerClienteDni()));
			System.out.printf("%nCliente buscado correctamente%n%n");
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	public void buscarVehiculo() {
		try {
			Consola.mostrarCabecera("Buscar vehiculo");
			System.out.println(getControlador().buscar(Consola.leerVehiculoMatricula()));
			System.out.printf("%nBuscar vehiculo%n%n");
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	public void buscarAlquiler() {

		try {
			Consola.mostrarCabecera("Buscar alquiler");
			System.out.println(getControlador().buscar(Consola.leerAlquiler()));
			System.out.printf("%nAlquiler buscado correctamente%n%n");
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	public void modificarCliente() {
		try {
			Consola.mostrarCabecera("Modificar cliente");
			getControlador().modificar(Consola.leerClienteDni(), Consola.leerNombre(), Consola.leerTelefono());
			System.out.printf("%nCliente modificado correctamente%n%n");
		} catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}

	public void devolverAlquilerCliente() {
		try {
			Consola.mostrarCabecera("Devolver alquiler del cliente");
			getControlador().devolver(Consola.leerClienteDni(), Consola.leerFechaDevolucion());
			System.out.printf("%nAlquiler devuelto correctamente%n%n");
		} catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}

	public void devolverAlquilerVehiculo() {
		try {
			Consola.mostrarCabecera("Devolver alquiler del vehiculo");
			getControlador().devolver(Consola.leerVehiculoMatricula(), Consola.leerFechaDevolucion());
			System.out.printf("%nAlquiler devuelto correctamente%n%n");
		} catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}

	public void borrarCliente() {
		try {
			Consola.mostrarCabecera("Borrar cliente");
			getControlador().borrar(Consola.leerClienteDni());
			System.out.printf("%nCliente borrado correctamente%n%n");
		} catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}

	public void borrarVehiculo() {
		try {
			Consola.mostrarCabecera("Borrar vehiculo");
			getControlador().borrar(Consola.leerVehiculoMatricula());
			System.out.printf("%nVehiculo borrado correctamente%n%n");
		} catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}

	}

	public void borrarAlquiler() {
		try {
			Consola.mostrarCabecera("Borrar alquiler");
			getControlador().borrar(Consola.leerAlquiler());
			System.out.printf("%nAlquiler borrado correctamente%n%n");
		} catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarClientes() {
		try {
			Consola.mostrarCabecera("Listar clientes");
			List<Cliente> lista = getControlador().getClientes();
			// ordenados alfabéticamente por nombre y en caso de nombres iguales los ordene
			// por DNI.
			lista.sort(Comparator.comparing(Cliente::getNombre).thenComparing(Cliente::getDni));
			for (Cliente clienteLista : lista) {
				System.out.println(clienteLista);
			}
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("");
	}

	public void listarVehiculos() {
		try {
			Consola.mostrarCabecera("Listar vehiculos");
			List<Vehiculo> lista = getControlador().getVehiculos();
			// ordenados alfabéticamente por marca, modelo y matrícula
			lista.sort(Comparator.comparing(Vehiculo::getMarca).thenComparing(Vehiculo::getModelo)
					.thenComparing(Vehiculo::getMatricula));
			for (Vehiculo vehiculoLista : getControlador().getVehiculos()) {
				System.out.println(vehiculoLista);
			}
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("");

	}

	public void listarAlquileres() {
		try {
			Consola.mostrarCabecera("Listar alquileres");
			// ordenados por fecha de alquiler y por cliente
			List<Alquiler> lista = getControlador().getAlquileres();
			Comparator<Cliente> comparadorCliente = (Comparator.comparing(Cliente::getNombre)
					.thenComparing(Cliente::getDni));
			lista.sort(Comparator.comparing(Alquiler::getFechaAlquiler).thenComparing(Alquiler::getCliente,
					comparadorCliente));
			for (Alquiler alquilerLista : lista) {
				System.out.println(alquilerLista);
			}
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("");

	}

	public void listarAlquileresCliente() {
		try {
			Consola.mostrarCabecera("Listar alquileres por clientes");
			List<Alquiler> lista = getControlador().getAlquileres(Consola.leerClienteDni());
			Comparator<Cliente> comparadorCliente = (Comparator.comparing(Cliente::getNombre)
					.thenComparing(Cliente::getDni));
			lista.sort(Comparator.comparing(Alquiler::getFechaAlquiler).thenComparing(Alquiler::getCliente,
					comparadorCliente));
			for (Alquiler alquilerListaCliente : lista) {
				System.out.println(alquilerListaCliente);
			}
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}

	}

	public void listarAlquileresVehiculo() {
		try {
			Consola.mostrarCabecera("Listar alquileres por vehiculos");
			List<Alquiler> lista = getControlador().getAlquileres(Consola.leerVehiculoMatricula());
			lista.sort(Comparator.comparing(Alquiler::getFechaAlquiler));
			for (Alquiler alquilerListaVehiculo : lista) {
				System.out.println(alquilerListaVehiculo);
				System.out.println("");
			}
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("");

	}

	public void mostrarEstadisticasMensualesTipoVehiculo() {
		// inicializo mapa, pregunto un mes al usuario y segun el mes recorro los
		// alquileres y pregunto por el vehiculo
		// me pregunta cuantos tipos de vehiculos se alquilan ese mes

		// Hacer if con mismo mes y año porque pueden ser de otros años
		LocalDate mes;

		// CORREGIR: Al poner la fecha mal no te la vuelve a pedir
		do {
			Consola.mostrarCabecera("Introduce un mes para mostrar sus estadísticas: ");
			mes = Consola.leerMes();
		} while (mes == null);
		
		Map<TipoVehiculo, Integer> estadisticas = inicializarEstadisticas();
		for (Alquiler alquileres : getControlador().getAlquileres()) {
			if ((alquileres.getFechaAlquiler().getMonth().equals(mes.getMonth()))
					&& alquileres.getFechaAlquiler().getYear() == (mes.getYear())) {
				// Alquiler tiene cliente, vehiculo y fecha asi que puedo sacar un vehiculo pero
				// tengo que convertiro en TipoVehiculo para poder sumar las claves del mapa
				Vehiculo vehiculo = alquileres.getVehiculo();
				TipoVehiculo.get(vehiculo);
				// A estadisticas le añado la clave que ya tenía +1
				// Para no hacer TipoVehiculo. y el vehiculo lo hago con el .get(vehiculo)
				estadisticas.put(TipoVehiculo.get(vehiculo), estadisticas.get(TipoVehiculo.get(vehiculo)) + 1);
			}

		}
		System.out.println(estadisticas);

	}

	private Map<TipoVehiculo, Integer> inicializarEstadisticas() {
		// Crea mapa e inicializa las claves(tipos de vehiculos) con valor 0
		// Modificar to string de tipovehiculo si quiero cambiar como imprime el mapa

		Map<TipoVehiculo, Integer> estadisticas = new EnumMap<>(TipoVehiculo.class);

		for (TipoVehiculo tipoVehiculo : TipoVehiculo.values()) {
			estadisticas.put(tipoVehiculo, 0);
		}

		return estadisticas;

	}

}
