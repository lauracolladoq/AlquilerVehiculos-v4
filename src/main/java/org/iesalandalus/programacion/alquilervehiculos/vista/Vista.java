package org.iesalandalus.programacion.alquilervehiculos.vista;

import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;

public abstract class Vista {
	private Controlador controlador;

	public Controlador getControlador() {
		return controlador;
	}

	// asignará el controlador pasado al atributo si éste no es nulo.
	public void setControlador(Controlador controlador) {
		if (controlador == null) {
			throw new NullPointerException("No puedes asignar un controlador nulo");
		}
		this.controlador = controlador;
	}

	// que mostrará el menú, leerá una opción de consola y la ejecutará. Repetirá
	// este proceso mientras la opción elegida no sea la correspondiente a salir.
	// Utilizará los correspondientes métodos de la clase Consola y llamará al
	// método ejecutar de esta clase que describiré a continuación.
	public abstract void comenzar();

	// mostrará un mensaje de despedida por consola
	public abstract void terminar();

}

//poner get controlador publico