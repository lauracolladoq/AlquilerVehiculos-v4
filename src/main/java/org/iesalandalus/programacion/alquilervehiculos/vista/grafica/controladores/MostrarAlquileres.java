package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.time.LocalDate;
import java.util.List;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controles.FormateadorCeldaFecha;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MostrarAlquileres extends Controlador {

	@FXML
	void initialize() {
		System.out.println("Método initialize de ListarAlquileres");
		tcCliente.setCellValueFactory(fila -> new SimpleStringProperty(fila.getValue().getCliente().getDni()));
		tcFechaAlquiler.setCellValueFactory(fila -> new SimpleObjectProperty<>(fila.getValue().getFechaAlquiler()));
		tcFechaAlquiler.setCellFactory(columna -> new FormateadorCeldaFecha());
		tcFechaDevolucion.setCellValueFactory(fila -> new SimpleObjectProperty<>(fila.getValue().getFechaDevolucion()));
		tcFechaDevolucion.setCellFactory(columna -> new FormateadorCeldaFecha());
		tcVehiculo.setCellValueFactory(fila -> new SimpleStringProperty(fila.getValue().getVehiculo().getMatricula()));
		tcPrecio.setCellValueFactory(fila -> new SimpleStringProperty(String.valueOf(fila.getValue().getPrecio())));

	}

	public void actualizar(List<Alquiler> alquileres) {
		tvAlquileres.setItems(FXCollections.observableArrayList(alquileres));
	}

	@FXML
	private TableColumn<Alquiler, String> tcCliente;

	@FXML
	private TableColumn<Alquiler, LocalDate> tcFechaAlquiler;

	@FXML
	private TableColumn<Alquiler, LocalDate> tcFechaDevolucion;

	@FXML
	private TableColumn<Alquiler, String> tcVehiculo;

	@FXML
	private TableColumn<Alquiler, String> tcPrecio;

	@FXML
	private TableView<Alquiler> tvAlquileres;

	@FXML
	void salir(ActionEvent event) {
		if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Estás seguro de que quieres salir al menú principal?",
				getEscenario())) {
			getEscenario().close();
		}
	}

}
