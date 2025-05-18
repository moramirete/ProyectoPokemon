package model;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import bd.PokemonBD;
import javafx.beans.property.SimpleIntegerProperty;
import bd.BDConecction;

/**
 * Clase que representa a un entrenador Pokémon.
 * Incluye atributos y métodos adicionales para hacerlo más "relleno".
 */
public class Entrenador {

    private String usuario;
    private String pass;
    private SimpleIntegerProperty pokedolares;
    private int idEntrenador;
    private Pokemon pokPrincipal;
    private LinkedList<Objeto> mochila = new LinkedList<Objeto>();
    private LinkedList<Pokemon> equipo = new LinkedList<Pokemon>();
    private LinkedList<Pokemon> caja = new LinkedList<Pokemon>();

    // Atributos adicionales para "relleno"
    private int pokemonsCapturados = 0;
    private int objetosUsados = 0;
    private String ultimoPokemonCapturado = "";
    private String ultimoObjetoUsado = "";

    public Entrenador(String usuario, String pass, int pokedolares, int idEntrenador, Pokemon pokPrincipal,
            LinkedList<Objeto> mochila, LinkedList<Pokemon> equipo, LinkedList<Pokemon> caja) {
        super();
        this.usuario = usuario;
        this.pass = pass;
        this.pokedolares = new SimpleIntegerProperty(pokedolares);
        this.idEntrenador = idEntrenador;
        this.pokPrincipal = pokPrincipal;
        this.mochila = mochila;
        this.equipo = equipo;
        this.caja = caja;
    }

    public Entrenador(String usuario, String pass) {
        this.idEntrenador = 0;
        this.usuario = usuario;
        this.pass = pass;
        this.pokedolares = new SimpleIntegerProperty(1000);
        this.mochila = new LinkedList<Objeto>();
        this.equipo = new LinkedList<Pokemon>();
        this.caja = new LinkedList<Pokemon>();
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getPokedolares() {
        return pokedolares.get();
    }

    public void setPokedolares(int pokedolares) {
        this.pokedolares.set(pokedolares);
    }

    public int getIdEntrenador() {
        return idEntrenador;
    }

    public void setIdEntrenador(int idEntrenador) {
        this.idEntrenador = idEntrenador;
    }

    public Pokemon getPokPrincipal() {
        return pokPrincipal;
    }

    public void setPokPrincipal(Pokemon pokPrincipal) {
        this.pokPrincipal = pokPrincipal;
    }

    public LinkedList<Objeto> getMochila() {
        return mochila;
    }

    public void setMochila(LinkedList<Objeto> mochila) {
        this.mochila = mochila;
    }

    public LinkedList<Pokemon> getEquipo() {
        return equipo;
    }

    public void setEquipo(LinkedList<Pokemon> equipo) {
        this.equipo = equipo;
    }

    public LinkedList<Pokemon> getCaja() {
        return caja;
    }

    public void setCaja(LinkedList<Pokemon> caja) {
        this.caja = caja;
    }

    public SimpleIntegerProperty pokedolaresProperty() {
        return pokedolares;
    }

   
}