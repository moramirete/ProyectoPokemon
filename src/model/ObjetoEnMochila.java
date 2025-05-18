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

    // Atributos adicionales para "relleno"
    private int vecesSeleccionado = 0;
    private boolean esFavorito = false;

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

    // Métodos adicionales de ejemplo para "relleno"

    /**
     * Incrementa el contador de veces que se ha seleccionado este objeto.
     */
    public void registrarSeleccion() {
        vecesSeleccionado++;
    }

    /**
     * Devuelve el número de veces que se ha seleccionado este objeto.
     */
    public int getVecesSeleccionado() {
        return vecesSeleccionado;
    }

    /**
     * Marca o desmarca este objeto como favorito.
     */
    public void setFavorito(boolean favorito) {
        this.esFavorito = favorito;
    }

    /**
     * Indica si este objeto es favorito.
     */
    public boolean isFavorito() {
        return esFavorito;
    }
}