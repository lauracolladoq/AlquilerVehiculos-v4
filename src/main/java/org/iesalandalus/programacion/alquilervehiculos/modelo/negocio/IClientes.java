package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;

public interface IClientes {

	// devolverá una nueva lista con los mismos elementos (no debe crear nuevas
	// instancias)
	void comenzar();

	void terminar();

	List<Cliente> get();

	// añadirá un cliente a la lista si éste no es nulo y no existe aún en la lista.
	void insertar(Cliente cliente) throws OperationNotSupportedException;

	// devolverá el cliente si éste se encuentra en la lista y null en caso
	// contrario.
	Cliente buscar(Cliente cliente);

	// borrará el cliente si éste existe en la lista o lanzará una excepción en caso
	// contrario.
	void borrar(Cliente cliente) throws OperationNotSupportedException;

	// permitirá cambiar el nombre o el teléfono (si estos parámetros no son
	// nulos ni blancos) de un cliente existente y si no lanzará la correspondiente
	// excepción.
	void modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException;

}