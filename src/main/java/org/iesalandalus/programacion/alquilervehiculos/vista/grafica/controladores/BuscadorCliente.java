package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controles;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class BuscadorCliente extends Controlador {

	@FXML
	private TextField tfDni;

	@FXML
	private TextField tfNombre;

	@FXML
	private TextField tfTelefono;

	@FXML
	private Label lbDni;

	// private boolean cancelado;

	Cliente cliente;

	@FXML
	private void initialize() {
		System.out.println("Método initialize de BuscadorClientes");
		tfNombre.textProperty().addListener((ob, ov, nv) -> Controles.validarCampoTexto(Cliente.ER_NOMBRE, tfNombre));
		tfTelefono.textProperty()
				.addListener((ob, ov, nv) -> Controles.validarCampoTexto(Cliente.ER_TELEFONO, tfTelefono));

	}

	public void setCliente(Cliente clienteBuscado) {
		// cancelado = false;
		lbDni.setText(clienteBuscado.getDni());
		tfNombre.setText(clienteBuscado.getNombre());
		tfTelefono.setText(clienteBuscado.getTelefono());
		this.cliente = clienteBuscado;
	}

	@FXML
	void devolverAlquilerCliente(ActionEvent event) {
		// cancelado = false;
		DevolverAlquilerCliente devolverAlquilerCliente = (DevolverAlquilerCliente) Controladores
				.get("vistas/DevolverAlquilerCliente.fxml", "Devolver alquiler", getEscenario());
		devolverAlquilerCliente.actualizar(VistaGrafica.getInstancia().getControlador().getAlquileres(cliente));
		devolverAlquilerCliente.setCliente(cliente);
		devolverAlquilerCliente.getEscenario().showAndWait();

	}

	@FXML
	void eliminarCliente(ActionEvent event) throws OperationNotSupportedException {
		// cancelado = false;
		try {
			VistaGrafica.getInstancia().getControlador().borrar(cliente);
			Dialogos.mostrarDialogoAdvertencia("Eliminar cliente", "Se ha eliminado correctamente el cliente",
					getEscenario());
			getEscenario().close();
		} catch (IllegalArgumentException | NullPointerException e) {
			Dialogos.mostrarDialogoError("Borrar Cliente", e.getMessage(), getEscenario());
		}

	}

	@FXML
	void modificarCliente(ActionEvent event) throws OperationNotSupportedException {
		String nombre = tfNombre.getText();
		String telefono = tfTelefono.getText();
		try {
			cliente = VistaGrafica.getInstancia().getControlador().buscar(cliente);
			cliente.setNombre(nombre);
			cliente.setTelefono(telefono);
			VistaGrafica.getInstancia().getControlador().modificar(cliente, nombre, telefono);

			Dialogos.mostrarDialogoAdvertencia("Modificar cliente", "Se ha modificado correctamente el cliente",
					getEscenario());
		} catch (IllegalArgumentException | NullPointerException e) {
			Dialogos.mostrarDialogoError("Modificar Cliente", e.getMessage(), getEscenario());
		}
	}

	@FXML
	void mostrarAlquileresCliente(ActionEvent event) {
		MostrarAlquileres mostrarAlquileresCliente = (MostrarAlquileres) Controladores
				.get("vistas/MostrarAlquileres.fxml", "MostrarAlquileres", getEscenario());
		mostrarAlquileresCliente.actualizar(VistaGrafica.getInstancia().getControlador().getAlquileres(cliente));
		mostrarAlquileresCliente.getEscenario().showAndWait();

	}

	@FXML
	public void limpiar() {
		tfTelefono.setText("");
		tfNombre.setText("");
	}

	@FXML
	void cancelar(ActionEvent event) {
		if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Estás seguro de que quieres salir al menú principal?",
				getEscenario())) {
			getEscenario().close();
		}
	}

}
