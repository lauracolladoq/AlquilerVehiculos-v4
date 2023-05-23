package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {
	private static final String PATRON_FECHA = "dd/MM/yyyy";
	private static final String PATRON_MES = "MM/yyyy";
	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern(PATRON_FECHA);

	private Consola() {

	}

	// mostrará por pantalla el mensaje pasado por parámetro y luego mostrará un
	// subrayado compuesto de guiones con su misma longitud.
	public static void mostrarCabecera(String mensaje) {
		System.out.println(mensaje);
		System.out.println("-".repeat(mensaje.length()));

	}

	// mostrará una cabecera informando del cometido de la aplicación y mostrará las
	// diferentes opciones del menú.
	public static void mostrarMenuAcciones() {
		mostrarCabecera("     MENÚ DE OPCIONES     ");

		for (int i = 0; i < (Opcion.values().length); i++) {
			System.out.println(Opcion.values()[i]);
		}
		// Para que selecciona quede con salto de línea
		System.out.println("");
	}

	public static Accion elegirAccion() {
		Accion accion = null;
		do {
			try {
				int num = leerEntero("Selecciona una opción: ");
				// Me comprueba el num en la clase opcion
				accion = Accion.get(num);
			} catch (NullPointerException e) {
				System.out.println(e.getMessage());
			}
		} while (accion == null);
		return accion;

	}

	// mostrará el mensaje pasado por parámetro y devolverá la cadena que lea por
	// consola.
	private static String leerCadena(String mensaje) {
		System.out.print(mensaje);
		return Entrada.cadena();

	}

	private static Integer leerEntero(String mensaje) {
		System.out.printf("%s", mensaje);
		return Entrada.entero();

	}
	// hará lo mismo, pero con una fecha. Deberá repetir la lectura mientras la
	// fecha no se haya podido crear correctamente

	// tengo patron_fecha = "dd/MM/yyyy" y patron_mes = "MM/yyyy" este metodo recibe
	// un mensaje y un patron, si el patron es el patron del mes a la cadena le
	// añado 01/ , si es patron fecha no se hace nada
	private static LocalDate leerFecha(String mensaje, String patron) {
		LocalDate fecha = null;
		// Patron_fecha no se pone con if porque ya tiene el formato de la fecha
		// entonces
		try {
			String cadena = leerCadena(mensaje);
			if (patron.equals(PATRON_MES)) {
				cadena = "01/" + cadena;
			}
			fecha = LocalDate.parse(cadena, FORMATO_FECHA);

		} catch (DateTimeParseException e) {
			System.out.print(e.getMessage());
		}
		return fecha;
	}

	// leerá un entero (utilizando el método anteriormente creado) asociado a la
	// opción y devolverá la opción correspondiente. Si el entero introducido no se
	// corresponde con ninguna opción deberá volver a leerlo hasta que éste sea
	// válido.

	// harán uso de los métodos privados anteriormente creados y que son
	// autodescriptivos

	public static Cliente leerCliente() {

		return new Cliente(leerCadena("Introduce el nombre del cliente: "),
				leerCadena("Introduce el DNI del cliente: "), leerCadena("Introduce el teléfono del cliente: "));

	}

	public static Cliente leerClienteDni() {
		return Cliente.getClienteConDni(leerCadena("Introduce el DNI del cliente: "));

	}

	public static String leerNombre() {
		return leerCadena("Introduce el nombre: ");

	}

	public static String leerTelefono() {
		return leerCadena("Introduce el teléfono: ");

	}

	public static Vehiculo leerVehiculo() {
		// Mostrar menu del tipo de vehiculos, llamo a leer vehiculo priv y lo que le
		// paso qu es tipoVehiculo se lo paso con el elegir
		mostrarMenuTipoVehiculos();
		return leerVehiculo(elegirTipoVehiculo());
	}

	private static void mostrarMenuTipoVehiculos() {
		System.out.println("Tipos de vehículos a elegir:");
		// Me recorro los valores de TipoVehiculo y los muestro segun el to string de
		// TipoVehiculo
		for (TipoVehiculo tipo : TipoVehiculo.values()) {
			String mensaje = tipo.ordinal() + ". " + tipo;
			System.out.println(mensaje);
		}
	}

	// Elegir opcion
	private static TipoVehiculo elegirTipoVehiculo() {
		// Pido al usuaro un num y según ese num saco el tipo de vehículo con el get de
		// TipoVehiculo
		TipoVehiculo tipoVehiculo = null;
		do {
			try {
				int num = leerEntero("Selecciona una opción: ");
				tipoVehiculo = TipoVehiculo.get(num);
			} catch (NullPointerException e) {
				System.out.println(e.getMessage());
			}
		} while (tipoVehiculo == null);
		return tipoVehiculo;
	}

	// me pasan un tipo de vehiculo, si este es igual a autobus por ejemplo, creo un
	// nuevo vehiculo que sea un autobus
	private static Vehiculo leerVehiculo(TipoVehiculo tipoVehiculo) {
		Vehiculo vehiculo = null;
		// Cambiar en vez de pedir marca, modelo y matricula para cada uno hacerlos
		// generales para evitar error
		String marca = leerCadena("Introduce la marca del vehiculo: ");
		String modelo = leerCadena("Introduce la modelo del vehiculo: ");
		String matricula = leerCadena("Introduce la matrícula del vehiculo: ");

		if (tipoVehiculo.equals(TipoVehiculo.AUTOBUS)) {
			vehiculo = new Autobus(marca, modelo, (leerEntero("Introduce las plazas  del vehiculo: ")), matricula);
		} else if (tipoVehiculo.equals(TipoVehiculo.FURGONETA)) {
			vehiculo = new Furgoneta(marca, modelo,
					(leerEntero("Introduce el peso máximo autorizado(pma) del vehiculo: ")),
					(leerEntero("Introduce las plazas  del vehiculo: ")), matricula);
		} else if (tipoVehiculo.equals(TipoVehiculo.TURISMO)) {
			vehiculo = new Turismo(marca, modelo, (leerEntero("Introduce la cilindrada  del vehiculo: ")), matricula);
		}
		return vehiculo;

	}

	public static Vehiculo leerVehiculoMatricula() {
		return Vehiculo.getVehiculoConMatricula(leerCadena("Introduce la matrícula del vehiculo: "));

	}

	public static Alquiler leerAlquiler() {
		return new Alquiler(leerClienteDni(), leerVehiculoMatricula(),
				leerFecha("Introduce la fecha del alquiler: ", PATRON_FECHA));

	}

	public static LocalDate leerFechaDevolucion() {
		return leerFecha(("Introduce la fecha de devolución: "), PATRON_FECHA);

	}

	public static LocalDate leerMes() {
		return leerFecha(("Introduce la fecha con mes y año: "), PATRON_MES);
	}
}
