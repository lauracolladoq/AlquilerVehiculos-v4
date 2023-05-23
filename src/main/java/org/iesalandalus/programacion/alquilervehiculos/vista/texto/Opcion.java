package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

public enum Opcion {
	/// Crea el enumerado Opcion que contendrá las diferentes opciones de nuestro
	/// menú de opciones y que será utilizado posteriormente para mostrar las
	/// posibles opciones a realizar. Cada instancia debe estar parametrizada con
	/// una cadena con el texto adecuado a mostrarnos.
	SALIR("Salir"), INSERTAR_CLIENTE("Insertar cliente"), INSERTAR_VEHICULO("Insertar vehiculo"),
	INSERTAR_ALQUILER("Insertar alquiler"), BUSCAR_CLIENTE("Buscar cliente"), BUSCAR_VEHICULO("Buscar vehiculo"),
	BUSCAR_ALQUILER("Buscar alquiler"), MODIFICAR_CLIENTE("Modificar cliente"),
	DEVOLVER_ALQUILER_CLIENTE("Devolver alquiler con cliente"),
	DEVOLVER_ALQUILER_VEHICULO("Devolver alquiler con vehiculo"), BORRAR_CLIENTE("Borrar cliente"),
	BORRAR_VEHICULO("Borrar vehiculo"), BORRAR_ALQUILER("Borrar alquiler"), LISTAR_CLIENTES("Listar clientes"),
	LISTAR_VEHICULOS("Listar vehiculos"), LISTAR_ALQUILERES("Listar alquileres"),
	LISTAR_ALQUILERES_CLIENTE("Listar alquileres del cliente"),
	LISTAR_ALQUILERES_TURISMO("Listar alquileres del turismo"), MOSTRAR_ESTADISTICAS_MENSUALES("Mostrar estadísticas mensuales");

	// Crea el atributo con el texto a mostrar que será asignado en el constructor
	// con parámetro, que también debes crear.
	private String texto;

	private Opcion(String texto) {
		this.texto = texto;
	}

	// Crea el método esOrdinalValido que comprobará si el ordinal pasado se
	// encuentra entre los ordinales válidos o no.
	private static boolean esOrdinalValido(int ordinal) {
		return (ordinal >= 0 && ordinal < Opcion.values().length);

	}

	// Crea el método get que devolverá la opción adecuada si el ordinal pasado es
	// correcto y lanzará una excepción en caso contrario.
	public static Opcion get(int ordinal) {
		Opcion comodin = null;
		if (esOrdinalValido(ordinal)) {
			comodin = Opcion.values()[ordinal];
		} else {
			throw new NullPointerException("ERROR: El parámetro introducido no es adecuado");
		}
		return comodin;

		// Crea el método toString que devuelva una cadena con el ordinal y el texto de
		// la opción que luego utilizaremos para mostrar las diferentes opciones del
		// menú.
	}

	@Override
	public String toString() {
		return String.format("%d. %s", ordinal(), texto);
	}

}
