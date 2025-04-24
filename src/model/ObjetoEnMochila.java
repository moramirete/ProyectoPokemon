package model;

public class ObjetoEnMochila {

	private String nombreObjeto;
	private String descripcion;
	private int cantidad;

	public ObjetoEnMochila(String nombreObjeto, String descripcion, int cantidad) {
		this.nombreObjeto = nombreObjeto;
		this.descripcion = descripcion;
		this.cantidad = cantidad;
	}

	public String getNombreObjeto() {
		return nombreObjeto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public int getCantidad() {
		return cantidad;
	}
}
