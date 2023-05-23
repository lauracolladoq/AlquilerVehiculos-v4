package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

public enum Accion {
	SALIR("Salir") {
		@Override
		public void ejecutar() {
			vista.terminar();

		}
	},
	INSERTAR_CLIENTE("Insertar cliente") {
		@Override
		public void ejecutar() {
			vista.insertarCliente();

		}
	},
	INSERTAR_VEHICULO("Insertar vehiculo") {
		@Override
		public void ejecutar() {
			vista.insertarVehiculo();

		}
	},
	INSERTAR_ALQUILER("Insertar alquiler") {
		@Override
		public void ejecutar() {
			vista.insertarAlquiler();

		}
	},
	BUSCAR_CLIENTE("Buscar cliente") {
		@Override
		public void ejecutar() {
			vista.buscarCliente();

		}
	},
	BUSCAR_VEHICULO("Buscar vehiculo") {
		@Override
		public void ejecutar() {
			vista.buscarVehiculo();

		}
	},
	BUSCAR_ALQUILER("Buscar alquiler") {
		@Override
		public void ejecutar() {
			vista.buscarAlquiler();

		}
	},
	MODIFICAR_CLIENTE("Modificar cliente") {
		@Override
		public void ejecutar() {
			vista.modificarCliente();

		}
	},
	DEVOLVER_ALQUILER_CLIENTE("Devolver alquiler con el cliente") {
		@Override
		public void ejecutar() {
			vista.devolverAlquilerCliente();

		}
	},
	DEVOLVER_ALQUILER_VEHICULO("Devolver alquiler con el vehiculo") {
		@Override
		public void ejecutar() {
			vista.devolverAlquilerVehiculo();

		}
	},
	BORRAR_CLIENTE("Borrar cliente") {
		@Override
		public void ejecutar() {
			vista.borrarCliente();

		}
	},
	BORRAR_VEHICULO("Borrar vehiculo") {
		@Override
		public void ejecutar() {
			vista.borrarVehiculo();

		}
	},
	BORRAR_ALQUILER("Borrar alquiler") {
		@Override
		public void ejecutar() {
			vista.borrarAlquiler();

		}
	},
	LISTAR_CLIENTES("Listar clientes") {
		@Override
		public void ejecutar() {
			vista.listarClientes();

		}
	},
	LISTAR_VEHICULOS("Listar vehiculos") {
		@Override
		public void ejecutar() {
			vista.listarVehiculos();

		}
	},
	LISTAR_ALQUILERES("Listar alquileres") {
		@Override
		public void ejecutar() {
			vista.listarAlquileres();

		}
	},
	LISTAR_ALQUILERES_CLIENTE("Listar alquileres del cliente") {
		@Override
		public void ejecutar() {
			vista.listarAlquileresCliente();

		}
	},
	LISTAR_ALQUILERES_VEHICULO("Listar alquileres del vehiculo") {
		@Override
		public void ejecutar() {
			vista.listarAlquileresVehiculo();

		}
	},
	MOSTRAR_ESTADISTICAS_MENSUALES("Mostar estadísticas mensuales") {
		@Override
		public void ejecutar() {
			vista.mostrarEstadisticasMensualesTipoVehiculo();

		}
	};

	private static VistaTexto vista;
	private String texto;

	private Accion(String texto) {
		this.texto = texto;
	}

	static void setVista(VistaTexto vista) {
		if (vista == null) {
			throw new NullPointerException("La vista no puede ser nula");
		}
		Accion.vista = vista;
	}

	public abstract void ejecutar();

	private static boolean esOrdinalValido(int ordinal) {
		return (ordinal >= 0 && ordinal < Accion.values().length);
	}

	public static Accion get(int ordinal) {
		Accion aux = null;
		if (esOrdinalValido(ordinal)) {
			aux = Accion.values()[ordinal];
		} else {
			throw new NullPointerException("ERROR: El parámetro introducido no es adecuado");
		}
		return aux;

	}

	@Override
	public String toString() {
		return String.format("%d. %s", ordinal(), texto);
	}

}