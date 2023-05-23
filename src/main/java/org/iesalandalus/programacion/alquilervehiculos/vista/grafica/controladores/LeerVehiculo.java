package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controles;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;
import org.iesalandalus.programacion.alquilervehiculos.vista.texto.TipoVehiculo;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class LeerVehiculo extends Controlador {

	@FXML
	private ComboBox<TipoVehiculo> cbTipo;

	@FXML
	private TextField tfCilindrada;

	@FXML
	private TextField tfMarca;

	@FXML
	private TextField tfMatricula;

	@FXML
	private TextField tfModelo;

	@FXML
	private TextField tfPlazas;

	@FXML
	private TextField tfPma;

	private boolean cancelado;

	@FXML
	private void initialize() {
		// NO LE HAGO ESCUCHADOR A MODELO PORQUE NO TIENE EXPRESIÓN REGULAR ASI QUE NO
		// HAY NADA QUE COMPROBAR
		tfMarca.textProperty().addListener((ob, ov, nv) -> Controles.validarCampoTexto(Vehiculo.ER_MARCA, tfMarca));
		tfMatricula.textProperty()
				.addListener((ob, ov, nv) -> Controles.validarCampoTexto(Vehiculo.ER_MATRICULA, tfMatricula));
		cbTipo.setItems(FXCollections.observableArrayList(TipoVehiculo.values()));
	}

	public Vehiculo getVehiculo() {
		String marca = tfMarca.getText();
		String modelo = tfModelo.getText();
		String matricula = tfMatricula.getText();

		TipoVehiculo tipoVehiculo = cbTipo.getValue();
		Vehiculo nuevoVehiculo = null;

		// ERROR AL CERRAR LA VENTANA -> HACER QUE SI EL TIPO DE VEHICULO NO ES NULO ME
		// CREE EL VEHICULO
		if (tipoVehiculo == null) {
			getEscenario().close();
		} else {
			if (tipoVehiculo.equals(TipoVehiculo.AUTOBUS)) {
				int plazas = Integer.parseInt(tfPlazas.getText());
				// ERROR DEL NEW ES PORQUE PLAZAS, ETC ES INT ASI QUE HACER PARSE
				nuevoVehiculo = new Autobus(marca, modelo, plazas, matricula);
			} else if (tipoVehiculo.equals(TipoVehiculo.TURISMO)) {
				int cilindrada = Integer.parseInt(tfCilindrada.getText());
				nuevoVehiculo = new Turismo(marca, modelo, cilindrada, matricula);
			} else if (tipoVehiculo.equals(TipoVehiculo.FURGONETA)) {
				int plazas = Integer.parseInt(tfPlazas.getText());
				int pma = Integer.parseInt(tfPma.getText());
				nuevoVehiculo = new Furgoneta(marca, modelo, pma, plazas, matricula);
			}

		}
		return cancelado ? null : nuevoVehiculo;

	}

	// ERROR NO SE ME VUELVEN A HABILITAR -> PONER TODOS LOS VALORES Y JUGAR CON
	// TRUE O FALSE(TRUE NO SE VE, FALSE SI SE VE)

	public void elegirTipo(ActionEvent event) {
		if (cbTipo.getValue().equals(TipoVehiculo.AUTOBUS)) {
			tfCilindrada.setDisable(true);
			tfPma.setDisable(true);
			tfPlazas.setDisable(false);
		} else if (cbTipo.getValue().equals(TipoVehiculo.TURISMO)) {
			tfCilindrada.setDisable(false);
			tfPma.setDisable(true);
			tfPlazas.setDisable(true);
		} else if (cbTipo.getValue().equals(TipoVehiculo.FURGONETA)) {
			tfCilindrada.setDisable(true);
			tfPma.setDisable(false);
			tfPlazas.setDisable(false);
		}
	}

	public void limpiar() {
		tfMarca.setText("");
		tfModelo.setText("");
		tfMatricula.setText("");
		tfCilindrada.setText("");
		tfPlazas.setText("");
		tfPma.setText("");
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