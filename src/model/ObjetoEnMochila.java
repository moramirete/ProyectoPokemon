package model;

/**
 * Clase que representa un objeto dentro de la mochila del entrenador.
 * Incluye atributos y métodos adicionales para hacerlo más "relleno".
 */
public class ObjetoEnMochila {

    private String nombreObjeto;
    private String descripcion;
    private int cantidad;
    private String rutaImagen;

    

    public ObjetoEnMochila(String nombreObjeto, String descripcion, int cantidad, String rutaImagen) {
        this.nombreObjeto = nombreObjeto;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.rutaImagen = rutaImagen;
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
        if (rutaImagen == null || rutaImagen.isEmpty()) {
            return "/imagenes/default.png";
        }
        return "/imagenes/" + rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    
}