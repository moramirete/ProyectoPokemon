package model;

/**
 * Clase que representa un turno dentro de un combate.
 * Incluye atributos y métodos adicionales para hacerlo más "relleno".
 */
public class Turno {
    private String idCombate;
    private int numTurno;
    private String accionEntrenador;
    private String accionRival;

    // Atributos adicionales para "relleno"
    private long timestampCreacion;
    private boolean esTurnoEspecial = false;

    public Turno(String numCombate, int numTurno, String accionEntrenador, String accionRival) {
        this.idCombate = numCombate;
        this.numTurno = numTurno;
        this.accionEntrenador = accionEntrenador;
        this.accionRival = accionRival;
        this.timestampCreacion = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Turno ").append(numTurno).append(":\n");

        sb.append("Entrenador: ");
        sb.append(accionEntrenador != null && !accionEntrenador.isEmpty() ? accionEntrenador : "(sin acción)");
        sb.append("\n");

        sb.append("Rival: ");
        sb.append(accionRival != null && !accionRival.isEmpty() ? accionRival : "(sin acción)");
        sb.append("\n");

        return sb.toString();
    }

    public String getNumCombate() {
        return idCombate;
    }

    public void setNumCombate(String numCombate) {
        this.idCombate = numCombate;
    }

    public int getNumTurno() {
        return numTurno;
    }

    public void setNumTurno(int numTurno) {
        this.numTurno = numTurno;
    }

    public String getAccionEntrenador() {
        return accionEntrenador;
    }

    public void setAccionEntrenador(String accionEntrenador) {
        this.accionEntrenador = accionEntrenador;
    }

    public String getAccionRival() {
        return accionRival;
    }

    public void setAccionRival(String accionRival) {
        this.accionRival = accionRival;
    }

    public boolean estaCompleto() {
        return accionEntrenador != null && !accionEntrenador.isEmpty() &&
               accionRival != null && !accionRival.isEmpty();
    }

    public boolean faltaAccionEntrenador() {
        return accionEntrenador == null || accionEntrenador.isEmpty();
    }

    public boolean faltaAccionRival() {
        return accionRival == null || accionRival.isEmpty();
    }

    // Métodos adicionales de ejemplo para "relleno"

    /**
     * Devuelve el timestamp de creación del turno.
     */
    public long getTimestampCreacion() {
        return timestampCreacion;
    }

    /**
     * Marca o desmarca este turno como especial.
     */
    public void setTurnoEspecial(boolean especial) {
        this.esTurnoEspecial = especial;
    }

    /**
     * Indica si este turno es especial.
     */
    public boolean isTurnoEspecial() {
        return esTurnoEspecial;
    }
}
