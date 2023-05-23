package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controles;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class BuscarVehiculo extends Controlador {

	@FXML
	private TextField tfMatricula;

	private boolean cancelado;

	@FXML
	private void initialize() {
		System.out.println("Método initialize de BuscarVehiculo");
		tfMatricula.textProperty()
				.addListener((ob, ov, nv) -> Controles.validarCampoTexto(Vehiculo.ER_MATRICULA, tfMatricula));

	}

	public Vehiculo getVehiculo() {
		String matricula = tfMatricula.getText();

		return cancelado ? null : Vehiculo.getVehiculoConMatricula(matricula);
	}

	@FXML
	public void limpiar() {
		tfMatricula.setText("");
		cancelado = true;
	}

	@FXML
	void buscar(ActionEvent event) {
		cancelado = false;
		BuscadorVehiculo buscadorVehiculo = (BuscadorVehiculo) Controladores.get("vistas/BuscadorVehiculo.fxml",
				"Buscador vehículo", getEscenario());
		// El limpiar me sobra porque el vehiculo no puede modificarse
		// buscadorVehiculo.limpiar();

		try {
			Vehiculo vehiculoBuscado = getVehiculo();
			if (vehiculoBuscado != null) {
				vehiculoBuscado = VistaGrafica.getInstancia().getControlador().buscar(vehiculoBuscado);
				buscadorVehiculo.setVehiculo(vehiculoBuscado);
				buscadorVehiculo.getEscenario().showAndWait();
				getEscenario().close();
			}
		} catch (IllegalArgumentException | NullPointerException e) {
			Dialogos.mostrarDialogoError("Buscar vehiculo", "No existe ningún vehículo con esa matrícula.",
					getEscenario());
		}

	}

	@FXML
	void cancelar(ActionEvent event) {
		cancelado = true;
		if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Estás seguro de que quieres salir al menú principal?",
				getEscenario())) {
			getEscenario().close();
		}

	}

}
