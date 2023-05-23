package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.util.List;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ListarVehiculos extends Controlador {
	@FXML
	void initialize() {
		System.out.println("Método initialize de ListarVehiculos");
		tcMarca.setCellValueFactory(fila -> new SimpleStringProperty(fila.getValue().getMarca()));
		tcMatricula.setCellValueFactory(fila -> new SimpleStringProperty(fila.getValue().getMatricula()));
		tcModelo.setCellValueFactory(fila -> new SimpleStringProperty(fila.getValue().getModelo()));

		tcPma.setCellValueFactory(fila -> new SimpleStringProperty(
				fila.getValue() instanceof Furgoneta furgoneta ? Integer.toString(furgoneta.getPma()) : ""));
		tcCilindrada.setCellValueFactory(fila -> new SimpleStringProperty(
				fila.getValue() instanceof Turismo turismo ? Integer.toString(turismo.getCilindrada()) : ""));
		// PLAZAS -> BUS Y FURGO
		tcPlazas.setCellValueFactory(fila -> {
			if (fila.getValue() instanceof Autobus autobus) {
				return new SimpleStringProperty(Integer.toString(autobus.getPlazas()));
			} else if (fila.getValue() instanceof Furgoneta furgoneta) {
				return new SimpleStringProperty(Integer.toString(furgoneta.getPlazas()));
			} else {
				return new SimpleStringProperty("");
			}
		});

	}

	public void actualizar(List<Vehiculo> vehiculos) {
		tvVehiculos.setItems(FXCollections.observableArrayList(vehiculos));
	}

	@FXML
	private Button btSalir;

	@FXML
	private TableColumn<Vehiculo, String> tcCilindrada;

	@FXML
	private TableColumn<Vehiculo, String> tcMarca;

	@FXML
	private TableColumn<Vehiculo, String> tcMatricula;

	@FXML
	private TableColumn<Vehiculo, String> tcModelo;

	@FXML
	private TableColumn<Vehiculo, String> tcPlazas;

	@FXML
	private TableColumn<Vehiculo, String> tcPma;

	@FXML
	private TableView<Vehiculo> tvVehiculos;

	@FXML
	void salir(ActionEvent event) {
		if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Estás seguro de que quieres salir al menú principal?",
				getEscenario())) {
			getEscenario().close();
		}
	}

}
