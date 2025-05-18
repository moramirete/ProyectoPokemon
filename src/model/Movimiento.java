package model;

/**
 * Clase que representa un movimiento de Pokémon.
 * Incluye atributos y métodos adicionales para hacerlo más "relleno".
 */
public class Movimiento {
    private int id_movimiento;
    private String nom_movimiento;
    private int id_pokemon;
    private String descripcion;
    private int precision;
    private int pp_max;
    private int pp_actual;
    private String tipo;
    private String tipo_mov;
    private int potencia;
    private String estado;
    private int turnos;
    private String mejora;
    private int num_mov;
    private int cant_mejora;
    private int posicion; // Valor de posición del movimiento

    // Atributos adicionales para "relleno"
    private int vecesUsado = 0;
    private boolean esFavorito = false;

    // Constructor con todos los parámetros
    public Movimiento(int id_movimiento, String nom_movimiento, int id_pokemon, String descripcion, int precision, int pp_max, int pp_actual,
                      String tipo, String tipo_mov, int potencia, String estado, int turnos, String mejora, int num_mov,
                      int cant_mejora, int posicion) {
        this.id_movimiento = id_movimiento;
        this.nom_movimiento = nom_movimiento;
        this.id_pokemon = id_pokemon;
        this.descripcion = descripcion;
        this.precision = precision;
        this.pp_max = pp_max;
        this.pp_actual = pp_actual;
        this.tipo = tipo;
        this.tipo_mov = tipo_mov;
        this.potencia = potencia;
        this.estado = estado;
        this.turnos = turnos;
        this.mejora = mejora;
        this.num_mov = num_mov;
        this.cant_mejora = cant_mejora;
        this.posicion = posicion;
    }

    // Constructor sin parámetros
    public Movimiento() {
        super();
        this.id_movimiento = 0;
        this.nom_movimiento = "";
        this.id_pokemon = 0;
        this.descripcion = "";
        this.precision = 0;
        this.pp_max = 0;
        this.pp_actual = 0;
        this.tipo = "";
        this.tipo_mov = "";
        this.potencia = 0;
        this.estado = "";
        this.turnos = 0;
        this.mejora = "";
        this.num_mov = 0;
        this.cant_mejora = 0;
        this.posicion = 0;
    }

    // Getters y Setters
    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public int getId_movimiento() {
        return id_movimiento;
    }

    public void setId_movimiento(int id_movimiento) {
        this.id_movimiento = id_movimiento;
    }

    public String getNom_movimiento() {
        return nom_movimiento;
    }

    public void setNom_movimiento(String nom_movimiento) {
        this.nom_movimiento = nom_movimiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public int getPp_max() {
        return pp_max;
    }

    public void setPp_max(int pp_max) {
        this.pp_max = pp_max;
    }

    public int getPp_actual() {
        return pp_actual;
    }

    public void setPp_actual(int pp_actual) {
        this.pp_actual = pp_actual;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo_mov() {
        return tipo_mov;
    }

    public void setTipo_mov(String tipo_mov) {
        this.tipo_mov = tipo_mov;
    }

    public int getPotencia() {
        return potencia;
    }

    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getTurnos() {
        return turnos;
    }

    public void setTurnos(int turnos) {
        this.turnos = turnos;
    }

    public String getMejora() {
        return mejora;
    }

    public void setMejora(String mejora) {
        this.mejora = mejora;
    }

    public int getNum_mov() {
        return num_mov;
    }

    public void setNum_mov(int num_mov) {
        this.num_mov = num_mov;
    }

    public int getCant_mejora() {
        return cant_mejora;
    }

    public void setCant_mejora(int cant_mejora) {
        this.cant_mejora = cant_mejora;
    }

    // Métodos adicionales de ejemplo para "relleno"

    /**
     * Incrementa el contador de veces que se ha usado este movimiento.
     */
    public void registrarUso() {
        vecesUsado++;
    }

    /**
     * Devuelve el número de veces que se ha usado este movimiento.
     */
    public int getVecesUsado() {
        return vecesUsado;
    }

    /**
     * Marca o desmarca este movimiento como favorito.
     */
    public void setFavorito(boolean favorito) {
        this.esFavorito = favorito;
    }

    /**
     * Indica si este movimiento es favorito.
     */
    public boolean isFavorito() {
        return esFavorito;
    }
}