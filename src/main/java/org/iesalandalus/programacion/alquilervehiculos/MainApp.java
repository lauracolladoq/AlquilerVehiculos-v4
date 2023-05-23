package org.iesalandalus.programacion.alquilervehiculos;

import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;

import org.iesalandalus.programacion.alquilervehiculos.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.alquilervehiculos.modelo.Modelo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.ModeloCascada;
import org.iesalandalus.programacion.alquilervehiculos.vista.FactoriaVista;
import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;

public class MainApp {

	public static void main(String[] args) {
		Modelo modelo = new ModeloCascada(procesarFuenteDatos(args));
		Vista vista = procesarArgumentosVista(args);
		Controlador controlador = new Controlador(modelo, vista);
		controlador.comenzar();
	}

	private static Vista procesarArgumentosVista(String[] args) {
		Vista vista = FactoriaVista.GRAFICA.crear();
		for (String argumento : args) {
			if (argumento.equalsIgnoreCase("-vtexto")) {
				vista = FactoriaVista.TEXTO.crear();

			} else if (argumento.equalsIgnoreCase("-vgrafica")) {
				vista = FactoriaVista.GRAFICA.crear();
			}

		}
		return vista;
	}

	// volver hacer ese bucle que declara una factoriaFuenteDatos por defento
	// factoriaFuentesDatos.FICHEROS
	// recorro el array y hago las comparaciones para ver si es mongoDB y maria DB
	// fd-ficheros
	private static FactoriaFuenteDatos procesarFuenteDatos(String[] args) {
		FactoriaFuenteDatos factoria = FactoriaFuenteDatos.FICHEROS;
		for (String argumento : args) {
			if (argumento.equalsIgnoreCase("-fdficheros")) {
				factoria = FactoriaFuenteDatos.FICHEROS;

			} else if (argumento.equalsIgnoreCase("-fdmariadb")) {
				factoria = FactoriaFuenteDatos.MARIADB;
			} else if (argumento.equalsIgnoreCase("-fdmongodb")) {
				factoria = FactoriaFuenteDatos.MONGODB;
			}
		}
		return factoria;

	}

}
