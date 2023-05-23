package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.time.LocalDate;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
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

public class AlquilerBuscado extends Controlador {
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

	Alquiler alquiler;
	
	private Alquiler aux;

	@FXML
	void initialize() {
		System.out.println("Método initialize de AlquilerBuscado");

	}

	public void setAlquiler(Alquiler alquilerBuscado) {
		this.alquiler = alquilerBuscado;

		tcCliente.setCellValueFactory(fila -> new SimpleStringProperty(alquilerBuscado.getCliente().getDni()));
		tcFechaAlquiler.setCellValueFactory(fila -> new SimpleObjectProperty<>(alquilerBuscado.getFechaAlquiler()));
		tcFechaAlquiler.setCellFactory(columna -> new FormateadorCeldaFecha());
		tcFechaDevolucion.setCellValueFactory(fila -> new SimpleObjectProperty<>(alquilerBuscado.getFechaDevolucion()));
		tcFechaDevolucion.setCellFactory(columna -> new FormateadorCeldaFecha());
		tcVehiculo.setCellValueFactory(fila -> new SimpleStringProperty(alquilerBuscado.getVehiculo().getMatricula()));
		tcPrecio.setCellValueFactory(fila -> new SimpleStringProperty(String.valueOf(alquilerBuscado.getPrecio())));

	}

	public void actualizar(List<Alquiler> alquileres) {
		tvAlquileres.setItems(FXCollections.observableArrayList(alquileres));
		for (Alquiler alquiler : alquileres) {
			aux = alquiler;
		}
	}

	@FXML
	void eliminarAlquiler(ActionEvent event) throws OperationNotSupportedException {
		Alquiler alquilerSeleccionado = aux;
		System.out.println(alquilerSeleccionado);
		try {
			if (alquilerSeleccionado != null) {
				VistaGrafica.getInstancia().getControlador().borrar(alquilerSeleccionado);
				Dialogos.mostrarDialogoAdvertencia("Eliminar alquiler", "Se ha eliminado correctamente el alquiler",
						getEscenario());
				tvAlquileres.getItems().remove(alquilerSeleccionado);
				getEscenario().close();
			}
		} catch (IllegalArgumentException | NullPointerException e) {
			Dialogos.mostrarDialogoError("Borrar alquiler", e.getMessage(), getEscenario());
		}
	}

	@FXML
	void salir(ActionEvent event) {
		if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Estás seguro de que quieres salir al menú principal?",
				getEscenario())) {
			getEscenario().close();
		}
	}

}
