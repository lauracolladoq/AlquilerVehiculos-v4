package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio;

import java.time.LocalDate;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;

public interface IAlquileres {
	void comenzar();

	void terminar();
	// devolverá una nueva lista con los mismos elementos (no debe crear nuevas
	// instancias)
	List<Alquiler> get();

	// para un cliente dado, que devolverá una nueva lista con los alquileres para
	// dicho cliente (no debe crear nuevas instancias).
	List<Alquiler> get(Cliente cliente);

	// para un turismo dado, que devolverá una nueva lista con los alquileres para
	// dicho turismo (no debe crear nuevas instancias).
	List<Alquiler> get(Vehiculo vehiculo);

	void devolver(Cliente cliente, LocalDate fechaDevolucion) throws OperationNotSupportedException;

	// añadirá un alquiler a la lista si éste no es nulo y pasa la comprobación
	// anterior
	void insertar(Alquiler alquiler) throws OperationNotSupportedException;

	// devolverá (asignará la fecha de devolución) si éste existe en la lista o
	// lanzará la excepción en caso contrario.
	void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws OperationNotSupportedException;

	Alquiler buscar(Alquiler alquiler);

	// borrará el alquiler si éste existe en la lista o lanzará una excepción en
	// caso contrario.
	void borrar(Alquiler alquiler) throws OperationNotSupportedException;

}