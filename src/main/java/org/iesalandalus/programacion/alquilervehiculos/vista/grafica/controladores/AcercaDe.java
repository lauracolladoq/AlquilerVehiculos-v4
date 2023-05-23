package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.awt.Desktop;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AcercaDe extends Controlador {

	@FXML
	private Hyperlink hlGit;

	@FXML
	private ImageView ivCorreo;

	@FXML
	private ImageView ivLogotipo;

	@FXML
	private ImageView ivTelefono;

	@FXML
	private ImageView ivWeb;

	@FXML
	private void initialize() throws IOException, URISyntaxException {
		System.out.println("Método initialize de AcercaDe");
		Image logotipo = new Image(getClass()
				.getResource(
						"/org/iesalandalus/programacion/alquilervehiculos/vista/grafica/recursos/imagenes/logotipo.png")
				.toExternalForm());
		ivLogotipo.setImage(logotipo);
		Image web = new Image(getClass()
				.getResource("/org/iesalandalus/programacion/alquilervehiculos/vista/grafica/recursos/imagenes/web.png")
				.toExternalForm());
		ivWeb.setImage(web);
		Image correo = new Image(getClass()
				.getResource(
						"/org/iesalandalus/programacion/alquilervehiculos/vista/grafica/recursos/imagenes/correo.png")
				.toExternalForm());
		ivCorreo.setImage(correo);
		Image telefono = new Image(getClass()
				.getResource(
						"/org/iesalandalus/programacion/alquilervehiculos/vista/grafica/recursos/imagenes/telefono.png")
				.toExternalForm());
		ivTelefono.setImage(telefono);

	}

	@FXML
	private void abrirEnlace(ActionEvent event) {
		try {
			Desktop desktop = Desktop.getDesktop();
			URI uri = new URI("https://github.com/lauracolladoq");
			desktop.browse(uri);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
