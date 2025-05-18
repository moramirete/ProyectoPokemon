package model;

/**
 * Clase que representa la relación entre un entrenador y un objeto en la mochila.
 * Incluye atributos y métodos adicionales para hacerlo más "relleno".
 */
public class Mochila {
    private int idEntrenador;
    private int idObjeto;
    private int cantidad;

    // Atributos adicionales para "relleno"
    private String fechaUltimaModificacion = "";
    private int vecesModificada = 0;

    public Mochila(int idEntrenador, int idObjeto, int cantidad) {
        this.idEntrenador = idEntrenador;
        this.idObjeto = idObjeto;
        this.cantidad = cantidad;
    }

    public int getIdEntrenador() {
        return idEntrenador;
    }

    public void setIdEntrenador(int idEntrenador) {
        this.idEntrenador = idEntrenador;
    }

    public int getIdObjeto() {
        return idObjeto;
    }

    public void setIdObjeto(int idObjeto) {
        this.idObjeto = idObjeto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        vecesModificada++;
        fechaUltimaModificacion = java.time.LocalDateTime.now().toString();
    }

    // Métodos adicionales de ejemplo para "relleno"

    /**
     * Devuelve la fecha de la última modificación de la cantidad.
     */
    public String getFechaUltimaModificacion() {
        return fechaUltimaModificacion;
    }

    /**
     * Devuelve el número de veces que se ha modificado la cantidad.
     */
    public int getVecesModificada() {
        return vecesModificada;
    }
}