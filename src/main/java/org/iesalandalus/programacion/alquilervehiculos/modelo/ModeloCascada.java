package org.iesalandalus.programacion.alquilervehiculos.modelo;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;

public class ModeloCascada extends Modelo {

	// Haz que el constructor de Modelo sea el que realice la asignación de la
	// fuente de datos y que desde ModeloCascada se llame a este constructor.
	public ModeloCascada(FactoriaFuenteDatos factoriaFuenteDatos) {
		super(factoriaFuenteDatos);
	}

	// creará la instancia de las clases de negocio anteriores
	@Override
	public void insertar(Cliente cliente) throws OperationNotSupportedException {
		clientes.insertar(new Cliente(cliente));
	}

	@Override
	public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {
		vehiculos.insertar(Vehiculo.copiar(vehiculo));
	}

	// en el caso de los alquileres, primero debe buscar el cliente y el turismo y
	// utilizar dichas instancias encontradas.
	@Override
	public void insertar(Alquiler alquiler) throws OperationNotSupportedException {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede realizar un alquiler nulo.");
		}
		// Creo un nuevo cliente y le digo que me busque el alquiler de ese cliente
		Cliente clienteNuevo = clientes.buscar(alquiler.getCliente());
		if (clienteNuevo == null) {
			throw new OperationNotSupportedException("ERROR: No existe el cliente del alquiler.");
		}
		Vehiculo vehiculoNuevo = vehiculos.buscar(alquiler.getVehiculo());
		if (vehiculoNuevo == null) {
			throw new OperationNotSupportedException("ERROR: No existe el turismo del alquiler.");
		}
		alquileres.insertar(new Alquiler(clienteNuevo, vehiculoNuevo, alquiler.getFechaAlquiler()));
	}

	// devolverá una nueva instancia del elemento encontrado si éste existe
	@Override
	public Cliente buscar(Cliente cliente) {
		// Uso método buscar de la clase Clientes que eso ya me busca si cliente existe
		return new Cliente(clientes.buscar(cliente));

	}

	@Override
	public Vehiculo buscar(Vehiculo vehiculo) {
		// No lo puedes inicializar como las otras porque es abstracta
		return Vehiculo.copiar(vehiculos.buscar(vehiculo));
	}

	@Override
	public Alquiler buscar(Alquiler alquiler) {
		return new Alquiler(alquileres.buscar(alquiler));
	}

	// invocará a su homólogo en la clase de negocio (modificar de clientes)
	@Override
	public void modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
		clientes.modificar(cliente, nombre, telefono);

	}

	// realizará la devolución, si es posible, del alquiler pasado

	public void devolver(Cliente cliente, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		alquileres.devolver(cliente, fechaDevolucion);
	}

	public void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		alquileres.devolver(vehiculo, fechaDevolucion);
	}

	// teniendo en cuenta que los borrados se realizarán en cascada, es decir, si
	// borramos un cliente también borraremos todos sus alquileres y lo mismo pasará
	// con los turismos.

	@Override
	public void borrar(Cliente cliente) throws OperationNotSupportedException {
		for (Alquiler alquiler : alquileres.get(cliente)) {
			alquileres.borrar(alquiler);
		}
		clientes.borrar(cliente);
	}

	@Override
	public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
		for (Alquiler alquiler : alquileres.get(vehiculo)) {
			alquileres.borrar(alquiler);
		}
		vehiculos.borrar(vehiculo);

	}

	@Override
	public void borrar(Alquiler alquiler) throws OperationNotSupportedException {
		alquileres.borrar(alquiler);
	}

	// deben devolver una nueva lista pero que contenga nuevas instancias no una
	// copia de los elementos.

	@Override
	public List<Alquiler> getListaAlquileres(Vehiculo vehiculo) {
		List<Alquiler> listaVehiculo = new ArrayList<>();
		for (Alquiler alquiler : alquileres.get(vehiculo)) {
			listaVehiculo.add(new Alquiler(alquiler));
		}
		return listaVehiculo;
	}

	@Override
	public List<Alquiler> getListaAlquileres(Cliente cliente) {
		List<Alquiler> listaClientes = new ArrayList<>();
		for (Alquiler alquiler : alquileres.get(cliente)) {
			listaClientes.add(new Alquiler(alquiler));
		}
		return listaClientes;
	}

	@Override
	public List<Alquiler> getListaAlquileres() {
		List<Alquiler> listaAlquileres = new ArrayList<>();
		for (Alquiler alquiler : alquileres.get()) {
			listaAlquileres.add(new Alquiler(alquiler));
		}
		return listaAlquileres;
	}

	@Override
	public List<Vehiculo> getListaVehiculos() {
		List<Vehiculo> listaVehiculos = new ArrayList<>();
		for (Vehiculo vehiculo : vehiculos.get()) {
			listaVehiculos.add(Vehiculo.copiar(vehiculo));

			// Hacerlo con el copia directamente
			/*
			 * if (vehiculo instanceof Autobus autobus) { listaVehiculos.add(new
			 * Autobus(autobus)); } else if (vehiculo instanceof Turismo turismo) {
			 * listaVehiculos.add(new Turismo(turismo)); } else if (vehiculo instanceof
			 * Furgoneta furgoneta) { listaVehiculos.add(new Furgoneta(furgoneta)); }
			 * 
			 */
		}

		return listaVehiculos;
	}

	@Override
	public List<Cliente> getListaClientes() {
		List<Cliente> listaClientes = new ArrayList<>();
		for (Cliente cliente : clientes.get()) {
			listaClientes.add(new Cliente(cliente));
		}
		return listaClientes;
	}

}
