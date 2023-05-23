package org.iesalandalus.programacion.alquilervehiculos.vista.grafica;

import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;

//crear una clase que se llama vista grafica que extiende de vista, implementa el patron singleton
//e implementa el comenzar y el terminar(get controlador terminar)

public class VistaGrafica extends Vista {
	
	private static VistaGrafica instancia;

	
	private VistaGrafica() {
		
	}

	public static VistaGrafica getInstancia() {
		if (instancia == null) {
			instancia = new VistaGrafica();
		}
		return instancia;

	}

	@Override

	public void comenzar() {
		LanzadorVentanaPrincipal.comenzar();
		getControlador().terminar();
	}

	@Override
	public void terminar() {

		System.out.printf("%nHasta luego Lucas!");
	}

}