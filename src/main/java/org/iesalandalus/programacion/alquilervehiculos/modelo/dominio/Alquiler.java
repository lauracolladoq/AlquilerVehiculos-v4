package org.iesalandalus.programacion.alquilervehiculos.modelo.dominio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import javax.naming.OperationNotSupportedException;

public class Alquiler {
	// es posible registrar un alquiler pasado (nuestro cliente a veces apunta los
	// alquileres y luego los pasa a la aplicación). La fecha de alquiler no puede
	// ser posterior a hoy. La fecha de devolución no puede ser igual o anterior a
	// la fecha de alquiler y tampoco puede ser posterior a hoy.
	static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final int PRECIO_DIA = 20;
	private LocalDate fechaAlquiler;
	private LocalDate fechaDevolucion;

	// No salen en el diagrama pero se añaden para conectar las dos clases
	private Cliente cliente;
	private Vehiculo vehiculo;

	public Alquiler(Cliente cliente, Vehiculo vehiculo, LocalDate fechaAlquiler) {
		setCliente(cliente);
		setVehiculo(vehiculo);
		setFechaAlquiler(fechaAlquiler);
	}

	public Alquiler(Alquiler alquiler) {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No es posible copiar un alquiler nulo.");
		}
		this.cliente = new Cliente(alquiler.getCliente());
		this.vehiculo = Vehiculo.copiar(alquiler.getVehiculo());
		this.fechaAlquiler = alquiler.getFechaAlquiler();
		this.fechaDevolucion = alquiler.getFechaDevolucion();

	}

	public Cliente getCliente() {
		return cliente;
	}

	private void setCliente(Cliente cliente) {
		if (cliente == null) {
			throw new NullPointerException("ERROR: El cliente no puede ser nulo.");
		}
		this.cliente = cliente;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	private void setVehiculo(Vehiculo vehiculo) {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: El vehículo no puede ser nulo.");
		}

		this.vehiculo = vehiculo;
	}

	public LocalDate getFechaAlquiler() {
		return fechaAlquiler;
	}

	private void setFechaAlquiler(LocalDate fechaAlquiler) {
		if (fechaAlquiler == null) {
			throw new NullPointerException("ERROR: La fecha de alquiler no puede ser nula.");
		}
		if (fechaAlquiler.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("ERROR: La fecha de alquiler no puede ser futura.");
		}
		this.fechaAlquiler = fechaAlquiler;
	}

	public LocalDate getFechaDevolucion() {
		return fechaDevolucion;
	}

	private void setFechaDevolucion(LocalDate fechaDevolucion) {
		if (fechaDevolucion == null) {
			throw new NullPointerException("ERROR: La fecha de devolución no puede ser nula.");
		}
		if (fechaDevolucion.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("ERROR: La fecha de devolución no puede ser futura.");
		}
		// ERROR AQUI
		if (fechaDevolucion.compareTo(getFechaAlquiler()) <= 0) {
			throw new IllegalArgumentException(
					"ERROR: La fecha de devolución debe ser posterior a la fecha de alquiler.");
		}

		this.fechaDevolucion = fechaDevolucion;
	}

	// se encargará de asignar la fecha de devoluión si ésta es correcta
	public void devolver(LocalDate fechaDevolucion) throws OperationNotSupportedException {
		if (getFechaDevolucion() != null) {
			throw new OperationNotSupportedException("ERROR: La devolución ya estaba registrada.");
		}
		setFechaDevolucion(fechaDevolucion);

	}

	// devolverá el precio del alquiler conforme a la fórmula establecida por
	// nuestro cliente y explicada anteriormente
	public int getPrecio() {
		long dias;

		if (getFechaDevolucion() == null) {
			dias = 0;
		} else {
			// Saco los días entre la fechaAlquiler y la fechaDevolucion
			dias = (int) ChronoUnit.DAYS.between(getFechaAlquiler(), getFechaDevolucion());
		}
		return (int) ((PRECIO_DIA + vehiculo.getFactorPrecio()) * dias);

	}

	// Un alquiler será igual a otro si es el mismo cliente, el mismo turismo y la
	// fecha de alquiler
	@Override
	public int hashCode() {
		return Objects.hash(cliente, fechaAlquiler, vehiculo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Alquiler other = (Alquiler) obj;
		return Objects.equals(cliente, other.cliente) && Objects.equals(fechaAlquiler, other.fechaAlquiler)
				&& Objects.equals(vehiculo, other.vehiculo);
	}

	@Override
	public String toString() {
		String cadena = null;
		if (getFechaDevolucion() == null) {
			cadena = String.format("%s <---> %s, %s - Aún no devuelto (%s€)", cliente, vehiculo,
					getFechaAlquiler().format(FORMATO_FECHA), getPrecio());
		} else {
			cadena = String.format("%s <---> %s, %s - %s (%s€)", cliente, vehiculo,
					getFechaAlquiler().format(FORMATO_FECHA), getFechaDevolucion().format(FORMATO_FECHA), getPrecio());
		}
		return cadena;

	}
}
