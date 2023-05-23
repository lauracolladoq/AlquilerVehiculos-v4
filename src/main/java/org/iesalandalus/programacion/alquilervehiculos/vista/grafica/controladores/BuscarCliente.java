package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controles;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class BuscarCliente extends Controlador {

	@FXML
	private TextField tfDni;

	private boolean cancelado;

	@FXML
	private void initialize() {
		System.out.println("Método initialize de BuscarClientes");
		tfDni.textProperty().addListener((ob, ov, nv) -> Controles.validarCampoTexto(Cliente.ER_DNI, tfDni));
	}

	public Cliente getCliente() {
		String dni = tfDni.getText();

		// SI CANCELADO ES FALSO DEVUELVE NULO Y SI ES VERDADERO DEVUELVE UN NUEVO
		// CLIENTE
		return cancelado ? null : Cliente.getClienteConDni(dni);
	}

	@FXML
	public void limpiar() {
		tfDni.setText("");
		cancelado = true;
	}

	@FXML
	void buscar(ActionEvent event) {
		cancelado = false;
		BuscadorCliente buscadorCliente = (BuscadorCliente) Controladores.get("vistas/BuscadorCliente.fxml",
				"BuscadorCliente", getEscenario());
		buscadorCliente.limpiar();

		try {
			Cliente clienteBuscado = getCliente();
			if (clienteBuscado != null) {
				clienteBuscado = VistaGrafica.getInstancia().getControlador().buscar(clienteBuscado);
				buscadorCliente.setCliente(clienteBuscado);
				buscadorCliente.getEscenario().showAndWait();
				getEscenario().close();
			}
		} catch (IllegalArgumentException | NullPointerException e) {
			Dialogos.mostrarDialogoError("Buscar Cliente", "No existe ningún cliente con ese DNI.", getEscenario());
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