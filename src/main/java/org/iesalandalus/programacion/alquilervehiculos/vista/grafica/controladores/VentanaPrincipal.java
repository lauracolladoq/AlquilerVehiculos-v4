package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;

import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class VentanaPrincipal extends Controlador {
	@FXML
	private ImageView ivAlquileres;

	@FXML
	private ImageView ivClientes;

	@FXML
	private ImageView ivVehiculos;

	@FXML
	private void initialize() {
		System.out.println("Método initialize de VentanaPrincipal");
		Image clientes = new Image(getClass()
				.getResource(
						"/org/iesalandalus/programacion/alquilervehiculos/vista/grafica/recursos/imagenes/clientes.png")
				.toExternalForm());
		ivClientes.setImage(clientes);

		Image vehiculos = new Image(getClass().getResource(
				"/org/iesalandalus/programacion/alquilervehiculos/vista/grafica/recursos/imagenes/vehiculos.png")
				.toExternalForm());
		ivVehiculos.setImage(vehiculos);
		Image alquileres = new Image(getClass().getResource(
				"/org/iesalandalus/programacion/alquilervehiculos/vista/grafica/recursos/imagenes/alquileres.png")
				.toExternalForm());
		ivAlquileres.setImage(alquileres);

	}

	@FXML
	void listarClientes(ActionEvent event) {
		ListarClientes listarClientes = (ListarClientes) Controladores.get("vistas/ListarClientes.fxml",
				"Listar clientes", getEscenario());
		listarClientes.actualizar(VistaGrafica.getInstancia().getControlador().getClientes());
		listarClientes.getEscenario().setResizable(false);
		listarClientes.getEscenario().showAndWait();
	}

	@FXML
	void listarVehiculos(ActionEvent event) {
		ListarVehiculos listarVehiculos = (ListarVehiculos) Controladores.get("vistas/ListarVehiculos.fxml",
				"Listar vehículos", getEscenario());
		listarVehiculos.actualizar(VistaGrafica.getInstancia().getControlador().getVehiculos());

		listarVehiculos.getEscenario().showAndWait();
	}

	@FXML
	void listarAlquileres(ActionEvent event) {
		ListarAlquileres listarAlquileres = (ListarAlquileres) Controladores.get("vistas/ListarAlquileres.fxml",
				"Listar alquileres", getEscenario());
		listarAlquileres.actualizar(VistaGrafica.getInstancia().getControlador().getAlquileres());

		listarAlquileres.getEscenario().showAndWait();
	}

	@FXML
	private void leerCliente(ActionEvent event) {
		LeerCliente leerCliente = (LeerCliente) Controladores.get("vistas/LeerCliente.fxml", "Insertar cliente",
				getEscenario());
		leerCliente.limpiar();
		leerCliente.getEscenario().showAndWait();
		try {
			Cliente cliente = leerCliente.getCliente();
			if (cliente != null) {
				VistaGrafica.getInstancia().getControlador().insertar(cliente);
				Dialogos.mostrarDialogoAdvertencia("Insertar cliente", "Cliente insertado correctamente",
						getEscenario());
			}
		} catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
			Dialogos.mostrarDialogoError("Insertar cliente", e.getMessage(), getEscenario());
		}

	}

	@FXML
	private void leerVehiculo(ActionEvent event) {
		LeerVehiculo leerVehiculo = (LeerVehiculo) Controladores.get("vistas/LeerVehiculo.fxml", "Insertar vehículo",
				getEscenario());
		leerVehiculo.limpiar();
		leerVehiculo.getEscenario().showAndWait();
		try {
			Vehiculo vehiculo = leerVehiculo.getVehiculo();
			VistaGrafica.getInstancia().getControlador().insertar(vehiculo);
			Dialogos.mostrarDialogoAdvertencia("Insertar vehiculo", "Vehiculo insertado correctamente", getEscenario());

		} catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
			Dialogos.mostrarDialogoError("Insertar vehiculo", e.getMessage(), getEscenario());
		}
	}

	@FXML
	private void leerAlquiler(ActionEvent event) {
		LeerAlquiler leerAlquiler = (LeerAlquiler) Controladores.get("vistas/LeerAlquiler.fxml", "Insertar alquiler",
				getEscenario());
		leerAlquiler.limpiar();
		leerAlquiler.actualizar(VistaGrafica.getInstancia().getControlador().getClientes(),
				(VistaGrafica.getInstancia().getControlador().getVehiculos()));
		leerAlquiler.getEscenario().showAndWait();
		try {
			Alquiler alquiler = leerAlquiler.getAlquiler();
			if (alquiler != null) {
				VistaGrafica.getInstancia().getControlador().insertar(alquiler);
				Dialogos.mostrarDialogoAdvertencia("Insertar alquiler", "Alquiler insertado correctamente",
						getEscenario());
			}
		} catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
			Dialogos.mostrarDialogoError("Insertar alquiler", e.getMessage(), getEscenario());
		}
	}

	@FXML
	private void buscarCliente() {
		BuscarCliente buscarCliente = (BuscarCliente) Controladores.get("vistas/BuscarCliente.fxml", "Buscar cliente",
				getEscenario());
		buscarCliente.limpiar();
		buscarCliente.getEscenario().showAndWait();
		try {
			Cliente cliente = buscarCliente.getCliente();
			if (cliente != null) {
				VistaGrafica.getInstancia().getControlador().buscar(cliente);
				Dialogos.mostrarDialogoAdvertencia("Buscar cliente", "Cliente buscado correctamente", getEscenario());
			}
		} catch (IllegalArgumentException | NullPointerException e) {
			Dialogos.mostrarDialogoError("Buscar Cliente", e.getMessage(), getEscenario());
		}
	}

	@FXML
	private void buscarVehiculo() {
		BuscarVehiculo buscarVehiculo = (BuscarVehiculo) Controladores.get("vistas/BuscarVehiculo.fxml",
				"Buscar vehiculo", getEscenario());
		buscarVehiculo.limpiar();
		buscarVehiculo.getEscenario().showAndWait();
		try {
			Vehiculo vehiculo = buscarVehiculo.getVehiculo();
			if (vehiculo != null) {
				VistaGrafica.getInstancia().getControlador().buscar(vehiculo);
				Dialogos.mostrarDialogoAdvertencia("Buscar vehiculo", "Vehiculo buscado correctamente", getEscenario());
			}
		} catch (IllegalArgumentException | NullPointerException e) {
			Dialogos.mostrarDialogoError("Buscar vehiculo", e.getMessage(), getEscenario());
		}
	}

	@FXML
	private void buscarAlquiler() {
		BuscarAlquiler buscarAlquiler = (BuscarAlquiler) Controladores.get("vistas/BuscarAlquiler.fxml",
				"Buscar alquiler", getEscenario());
		buscarAlquiler.limpiar();
		buscarAlquiler.actualizar(VistaGrafica.getInstancia().getControlador().getClientes(),
				(VistaGrafica.getInstancia().getControlador().getVehiculos()));
		buscarAlquiler.getEscenario().showAndWait();
		try {
			Alquiler alquiler = buscarAlquiler.getAlquiler();
			if (alquiler != null) {
				VistaGrafica.getInstancia().getControlador().buscar(alquiler);
				Dialogos.mostrarDialogoAdvertencia("Buscar alquiler", "Alquiler buscado correctamente", getEscenario());
			}
		} catch (IllegalArgumentException | NullPointerException e) {
			Dialogos.mostrarDialogoError("Buscar alquiler", e.getMessage(), getEscenario());
		}
	}

	@FXML
	void acercaDe(ActionEvent event) {
		AcercaDe acercaDe = (AcercaDe) Controladores.get("vistas/AcercaDe.fxml", "Acerca de", getEscenario());
		acercaDe.getEscenario().showAndWait();
	}

	@FXML
	void mostrarEstadisticas(ActionEvent event) {
		MostrarEstadisticas mostrarEstadisticas = (MostrarEstadisticas) Controladores
				.get("vistas/MostrarEstadisticas.fxml", "Mostrar estadísticas", getEscenario());
		mostrarEstadisticas.getEscenario().showAndWait();
	}

	@FXML
	private void confirmarSalida() {
		if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Estás seguro de que quieres salir de la aplicación?",
				getEscenario())) {
			getEscenario().close();
		}
	}

}
