package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BuscadorVehiculo extends Controlador {

	@FXML
	private Label lbMatricula;

	@FXML
	private Label lbMarca;

	@FXML
	private Label lbModelo;

	Vehiculo vehiculo;

	@FXML
	private void initialize() {
		System.out.println("Método initialize de BuscadorVehiculo");
	}

	public void setVehiculo(Vehiculo vehiculoBuscado) {
		lbMatricula.setText(vehiculoBuscado.getMatricula());
		lbModelo.setText(vehiculoBuscado.getModelo());
		lbMarca.setText(vehiculoBuscado.getMarca());
		this.vehiculo = vehiculoBuscado;
	}

	@FXML
	void devolverAlquilerVehiculo(ActionEvent event) {
		DevolverAlquilerVehiculo devolverAlquilerVehiculo = (DevolverAlquilerVehiculo) Controladores
				.get("vistas/DevolverAlquilerVehiculo.fxml", "Devolver alquiler", getEscenario());
		devolverAlquilerVehiculo.actualizar(VistaGrafica.getInstancia().getControlador().getAlquileres(vehiculo));
		devolverAlquilerVehiculo.setVehiculo(vehiculo);
		devolverAlquilerVehiculo.getEscenario().showAndWait();
	}

	@FXML
	void eliminarVehiculo(ActionEvent event) throws OperationNotSupportedException {

		try {
			VistaGrafica.getInstancia().getControlador().borrar(vehiculo);
			Dialogos.mostrarDialogoAdvertencia("Eliminar vehiculo", "Se ha eliminado correctamente el vehiculo",
					getEscenario());
			getEscenario().close();
		} catch (IllegalArgumentException | NullPointerException e) {
			Dialogos.mostrarDialogoError("Eliminar vehiculo", e.getMessage(), getEscenario());
		}

	}

	@FXML
	void mostrarAlquileresVehiculo(ActionEvent event) {
		MostrarAlquileres mostrarAlquileresVehiculo = (MostrarAlquileres) Controladores
				.get("vistas/MostrarAlquileres.fxml", "Mostrar alquileres", getEscenario());
		mostrarAlquileresVehiculo.actualizar(VistaGrafica.getInstancia().getControlador().getAlquileres(vehiculo));
		mostrarAlquileresVehiculo.getEscenario().showAndWait();
	}

	@FXML
	void cancelar(ActionEvent event) {
		if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Estás seguro de que quieres salir al menú principal?",
				getEscenario())) {
			getEscenario().close();
		}

	}

}
