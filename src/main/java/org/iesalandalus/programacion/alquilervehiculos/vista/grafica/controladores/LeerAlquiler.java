package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.time.LocalDate;
import java.util.List;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public class LeerAlquiler extends Controlador {

	@FXML
	private DatePicker dtFechaAlquiler;

	private boolean cancelado;

	@FXML
	private ComboBox<Cliente> cbClientes;

	@FXML
	private ComboBox<Vehiculo> cbVehiculos;

	@FXML
	private void initialize() {

	}

//LISTA DE CLIENTES Y LISTA DE VEHICULO
	public Alquiler getAlquiler() {
		LocalDate fechaAlquiler = dtFechaAlquiler.getValue();
		Cliente cliente = cbClientes.getValue();
		Vehiculo vehiculo = cbVehiculos.getValue();
		// LocalDate fechaDevolucion = dtFechaDevolucion.getValue();

		return cancelado ? null : new Alquiler(cliente, vehiculo, fechaAlquiler);

	}

	public void actualizar(List<Cliente> clientes, List<Vehiculo> vehiculos) {
		cbClientes.setItems(FXCollections.observableArrayList(clientes));
		cbVehiculos.setItems(FXCollections.observableArrayList(vehiculos));

	}

	public void limpiar() {
		cbClientes.setValue(null);
		cbVehiculos.setValue(null);
		dtFechaAlquiler.setValue(LocalDate.now());
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
