package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.util.EnumMap;
import java.util.Map;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;
import org.iesalandalus.programacion.alquilervehiculos.vista.texto.TipoVehiculo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.DatePicker;

public class MostrarEstadisticas extends Controlador {

	@FXML
	private DatePicker dtFechaEstadisticas;

	@FXML
	private PieChart pcEstadisticas;

	@FXML
	void mostrar(ActionEvent event) {
		try {
			pcEstadisticas.getData().clear();
			Map<TipoVehiculo, Integer> estadisticas = mostrarEstadisticasMensualesTipoVehiculo();
			if (estadisticas.isEmpty()) {
				Dialogos.mostrarDialogoError("Mostrar estadísticas", "No hay estadísticas que mostrar", getEscenario());
			} else {
				for (Map.Entry<TipoVehiculo, Integer> entry : estadisticas.entrySet()) {
					TipoVehiculo tipoVehiculo = entry.getKey();
					Integer cantidad = entry.getValue();
					pcEstadisticas.getData().add(new PieChart.Data(tipoVehiculo.toString(), cantidad));

				}
				Dialogos.mostrarDialogoAdvertencia("Mostrar estadísticas",
						"Se han mostrado las estadísticas correctamente", getEscenario());
			}

		} catch (IllegalArgumentException | NullPointerException e) {
			Dialogos.mostrarDialogoError("Mostrar estadísticas", "No hay estadísticas que mostrar", getEscenario());
		}

	}

	public Map<TipoVehiculo, Integer> mostrarEstadisticasMensualesTipoVehiculo() {
		Map<TipoVehiculo, Integer> estadisticas = new EnumMap<>(TipoVehiculo.class);
		for (Alquiler alquileres : VistaGrafica.getInstancia().getControlador().getAlquileres()) {
			if ((alquileres.getFechaAlquiler().getMonth().equals(dtFechaEstadisticas.getValue().getMonth()))
					&& alquileres.getFechaAlquiler().getYear() == (dtFechaEstadisticas.getValue().getYear())) {
				Vehiculo vehiculo = alquileres.getVehiculo();
				TipoVehiculo.get(vehiculo);
				estadisticas.put(TipoVehiculo.get(vehiculo),
						estadisticas.getOrDefault(TipoVehiculo.get(vehiculo), 0) + 1);
			}

		}
		return estadisticas;

	}

	@FXML
	void cancelar(ActionEvent event) {
		if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Estás seguro de que quieres salir al menú principal?",
				getEscenario())) {
			getEscenario().close();
		}

	}
}
