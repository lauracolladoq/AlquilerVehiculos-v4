package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.time.LocalDate;
import java.util.List;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public class DevolverAlquilerVehiculo extends Controlador {
	@FXML
	private ComboBox<Alquiler> cbAlquileres;

	@FXML
	private DatePicker dtFechaDevolucion;

	Vehiculo vehiculo;

	@FXML
	public void devolverAlquilerVehiculo() {

		LocalDate fechaDevolucion = dtFechaDevolucion.getValue();

		try {
			VistaGrafica.getInstancia().getControlador().devolver(vehiculo, fechaDevolucion);
			Dialogos.mostrarDialogoAdvertencia("Devolver alquiler vehiculo", "Alquiler devuelto correctamente",
					getEscenario());

		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Devolver alquiler ", e.getMessage(), getEscenario());
		}
	}

	public void actualizar(List<Alquiler> alquileres) {
		cbAlquileres.setItems(FXCollections.observableArrayList(alquileres));
	}

	@FXML
	public void limpiar() {
		cbAlquileres.setValue(null);
		dtFechaDevolucion.setValue(LocalDate.now());
	}

	@FXML
	void aceptar(ActionEvent event) {
		devolverAlquilerVehiculo();
		getEscenario().close();
	}

	@FXML
	void cancelar(ActionEvent event) {
		if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Estás seguro de que quieres cancelar la devolución del alquiler?",
				getEscenario())) {
			getEscenario().close();
		}
	}

	public void setVehiculo(Vehiculo vehiculoBuscado) {
		vehiculo = vehiculoBuscado;

	}

}
