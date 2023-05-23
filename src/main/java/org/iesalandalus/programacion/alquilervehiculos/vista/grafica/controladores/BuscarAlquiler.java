package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public class BuscarAlquiler extends Controlador {

	@FXML
	private DatePicker dtFechaAlquiler;

	@FXML
	private ComboBox<Cliente> cbClientes;

	@FXML
	private ComboBox<Vehiculo> cbVehiculos;

	private boolean cancelado;

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

	@FXML
	public void limpiar() {
		cbClientes.setValue(null);
		cbVehiculos.setValue(null);
		dtFechaAlquiler.setValue(LocalDate.now());
		// dtFechaDevolucion.setValue(LocalDate.now());
	}

	@FXML
	void buscar(ActionEvent event) {
		cancelado = false;
		AlquilerBuscado alquilerBuscado = (AlquilerBuscado) Controladores.get("vistas/AlquilerBuscado.fxml",
				"Alquileres buscados", getEscenario());

		try {
			Alquiler alquiler = getAlquiler();
			if (alquiler != null) {
				alquiler = VistaGrafica.getInstancia().getControlador().buscar(alquiler);
				ArrayList<Alquiler> listaAlquilerBuscado = new ArrayList<>();
				listaAlquilerBuscado.add(alquiler);
				alquilerBuscado.setAlquiler(alquiler);
				alquilerBuscado.actualizar(listaAlquilerBuscado);
				alquilerBuscado.getEscenario().showAndWait();
			}
		} catch (IllegalArgumentException | NullPointerException e) {
			Dialogos.mostrarDialogoError("Buscar alquiler", "No existe ningún alquiler con esos datos.",
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
