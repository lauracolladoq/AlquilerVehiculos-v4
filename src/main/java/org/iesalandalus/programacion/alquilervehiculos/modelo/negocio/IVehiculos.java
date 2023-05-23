package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;

public interface IVehiculos {
	void comenzar();

	void terminar();
	// devolverá una nueva lista con los mismos elementos (no debe crear nuevas
	// instancias).
	List<Vehiculo> get();

	// añadirá un turismo a la lista si éste no es nulo y no existe aún en la lista.
	void insertar(Vehiculo vehiculo) throws OperationNotSupportedException;

	// devolverá el turismo si éste se encuentra en la lista y null en caso
	// contrario.
	Vehiculo buscar(Vehiculo vehiculo);

	// borrará el turismo si éste existe en la lista o lanzará una excepción en caso
	// contrario
	void borrar(Vehiculo vehiculo) throws OperationNotSupportedException;

}