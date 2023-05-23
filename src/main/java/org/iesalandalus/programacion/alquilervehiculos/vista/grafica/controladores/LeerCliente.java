package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controles;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LeerCliente extends Controlador {

	@FXML
	private TextField tfDni;
	@FXML
	private TextField tfNombre;
	@FXML
	private TextField tfTelefono;

	private boolean cancelado;

	@FXML
	private void initialize() {
		tfNombre.textProperty().addListener((ob, ov, nv) -> Controles.validarCampoTexto(Cliente.ER_NOMBRE, tfNombre));
		tfDni.textProperty().addListener((ob, ov, nv) -> Controles.validarCampoTexto(Cliente.ER_DNI, tfDni));
		tfTelefono.textProperty()
				.addListener((ob, ov, nv) -> Controles.validarCampoTexto(Cliente.ER_TELEFONO, tfTelefono));
	}

	public Cliente getCliente() {
		String dni = tfDni.getText();
		String nombre = tfNombre.getText();
		String telefono = tfTelefono.getText();

		// SI CANCELADO ES FALSO DEVUELVE NULO Y SI ES VERDADERO DEVUELVE UN NUEVO
		// CLIENTE
		return cancelado ? null : new Cliente(nombre, dni, telefono);
	}

	public void limpiar() {
		tfNombre.setText("");
		tfDni.setText("");
		tfTelefono.setText("");
		cancelado = true;
	}

	@FXML
	void aceptar(ActionEvent event) {
		cancelado = false;
		getEscenario().close();
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
