package org.iesalandalus.programacion.alquilervehiculos.vista.grafica;

import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LanzadorVentanaPrincipal extends Application {

	private static final String TITULO = "Alquiler de vehículos";

	@Override
	public void start(Stage escenarioPrincipal) {
		try {
			Controlador ventanaPrincipal = Controladores.get("vistas/VentanaPrincipal.fxml", TITULO, null);

			// ICONO
			// LocalizadorRecursos.class.getResourceAsStream("imagenes/icono.png");
			Image icono = new Image(LocalizadorRecursos.class.getResourceAsStream("imagenes/icono.png"));

			ventanaPrincipal.getEscenario().setOnCloseRequest(e -> confirmarSalida(ventanaPrincipal.getEscenario(), e));
			ventanaPrincipal.getEscenario().getIcons().add(icono);
			ventanaPrincipal.getEscenario().show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void confirmarSalida(Stage escenario, WindowEvent e) {
		if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Estás seguro de que quieres salir de la aplicación?",
				escenario)) {
			escenario.close();
		} else {
			e.consume();
		}
	}

	public static void comenzar() {
		launch(LanzadorVentanaPrincipal.class);
	}
}

/*

*/
