package model;

public class ObjetoEnMochila {

	private String nombreObjeto;
	private String descripcion;
	private int cantidad;
	private String rutaImagen;

	public ObjetoEnMochila(String nombreObjeto, String descripcion, int cantidad, String rutaImgane) {
		this.nombreObjeto = nombreObjeto;
		this.descripcion = descripcion;
		this.cantidad = cantidad;
		this.rutaImagen = rutaImgane;
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

	public String getRutaImagen() {
		if (rutaImagen.isEmpty()) {
			return "/imagenes/default.png";
		}
		return "/imagenes/" + rutaImagen;
	}

	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}

}
