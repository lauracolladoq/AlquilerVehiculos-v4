package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.util.List;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListarClientes extends Controlador {
	@FXML
	void initialize() {
		System.out.println("Método initialize de ListarClientes");
		this.actualizar(VistaGrafica.getInstancia().getControlador().getClientes());
		tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		// OTRA FORMA DE HACERLO
		tcDNI.setCellValueFactory(fila -> new SimpleStringProperty(fila.getValue().getDni()));
		tcTelef.setCellValueFactory(new PropertyValueFactory<>("telefono"));
	}

	public void actualizar(List<Cliente> clientes) {
		tvClientes.setItems(FXCollections.observableArrayList(clientes));
		// REFRESH SIRVE PARA ACTUALIZAR LA TABLA, ESTO CORRIGE EL ERROR DE MODIFICAR
		tvClientes.refresh();
	}

	@FXML
	private TableColumn<Cliente, String> tcDNI;

	@FXML
	private TableColumn<Cliente, String> tcNombre;

	@FXML
	private TableColumn<Cliente, String> tcTelef;

	@FXML
	private TableView<Cliente> tvClientes;

	@FXML
	void salir(ActionEvent event) {
		if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Estás seguro de que quieres salir al menú principal?",
				getEscenario())) {
			getEscenario().close();
		}
	}

}
