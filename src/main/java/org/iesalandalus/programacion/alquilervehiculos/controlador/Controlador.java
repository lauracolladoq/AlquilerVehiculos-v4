package org.iesalandalus.programacion.alquilervehiculos.controlador;

import java.time.LocalDate;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.Modelo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;

public class Controlador {
	private Modelo modelo;
	private Vista vista;

	// Crea el constructor con parámetros que comprobará que no son nulos y los
	// asignará a los atributos. Además debe llamar al método setVista de la vista
	// con una instancia suya.
	public Controlador(Modelo modelo, Vista vista) {
		if (modelo == null) {
			throw new NullPointerException("No puedes crear un controlador con un modelo nulo");
		}

		if (vista == null) {
			throw new NullPointerException("No puedes crear un controlador con una vista nula");
		}
		this.modelo = modelo;
		this.vista = vista;

		vista.setControlador(this);
	}

	// Crea los métodos comenzar y terminar, que llamarán a los correspondientes
	// métodos en el modelo y en la vista
	public void comenzar() {
		modelo.comenzar();
		vista.comenzar();
	}

	public void terminar() {
		modelo.terminar();
		//vista.terminar();
	}

	// Crea los demás métodos que simplemente harán una llamada al correspondiente
	// método del modelo.

	public void insertar(Cliente cliente) throws OperationNotSupportedException {
		modelo.insertar(cliente);
	}

	public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {
		modelo.insertar(vehiculo);
	}

	public void insertar(Alquiler alquiler) throws OperationNotSupportedException {
		modelo.insertar(alquiler);
	}

	public Cliente buscar(Cliente cliente) {
		return modelo.buscar(cliente);
	}

	public Vehiculo buscar(Vehiculo vehiculo) {
		return modelo.buscar(vehiculo);
	}

	public Alquiler buscar(Alquiler alquiler) {
		return modelo.buscar(alquiler);
	}

	public void modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
		modelo.modificar(cliente, nombre, telefono);
	}

	public void devolver(Cliente cliente, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		modelo.devolver(cliente, fechaDevolucion);
	}

	public void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		modelo.devolver(vehiculo, fechaDevolucion);
	}

	public void borrar(Cliente cliente) throws OperationNotSupportedException {
		modelo.borrar(cliente);
	}

	public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
		modelo.borrar(vehiculo);
	}

	public void borrar(Alquiler alquiler) throws OperationNotSupportedException {
		modelo.borrar(alquiler);
	}

	public List<Cliente> getClientes() {
		return modelo.getListaClientes();

	}

	public List<Vehiculo> getVehiculos() {
		return modelo.getListaVehiculos();
	}

	public List<Alquiler> getAlquileres() {
		return modelo.getListaAlquileres();

	}

	public List<Alquiler> getAlquileres(Cliente cliente) {
		return modelo.getListaAlquileres(cliente);

	}

	public List<Alquiler> getAlquileres(Vehiculo vehiculo) {
		return modelo.getListaAlquileres(vehiculo);

	}

}
