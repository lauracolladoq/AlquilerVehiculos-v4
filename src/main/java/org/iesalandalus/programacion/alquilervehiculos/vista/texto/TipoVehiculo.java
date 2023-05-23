package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;

public enum TipoVehiculo {
	TURISMO("Turismo"), AUTOBUS("Autobus"), FURGONETA("Furgoneta");

	private String nombre;

	private TipoVehiculo(String nombre) {
		this.nombre = nombre;
	}

	private static boolean esOrdinalValido(int ordinal) {
		return (ordinal >= 0 && ordinal < TipoVehiculo.values().length);

	}

	public static TipoVehiculo get(int ordinal) {
		TipoVehiculo aux = null;
		if (esOrdinalValido(ordinal)) {
			aux = TipoVehiculo.values()[ordinal];
		} else {
			throw new NullPointerException("ERROR: El parámetro introducido no es adecuado");
		}
		return aux;
	}

	// Instance of
	public static TipoVehiculo get(Vehiculo vehiculo) {
		TipoVehiculo tipoVehiculo = null;
		if (vehiculo instanceof Autobus) {
			tipoVehiculo = TipoVehiculo.AUTOBUS;
		} else if (vehiculo instanceof Furgoneta) {
			tipoVehiculo = TipoVehiculo.FURGONETA;
		} else if (vehiculo instanceof Turismo) {
			tipoVehiculo = TipoVehiculo.TURISMO;
		}

		return tipoVehiculo;

	}

	@Override
	public String toString() {
		return String.format(" %s", nombre);
		// return String.format("%d. %s", ordinal(), nombre);
	}

}
