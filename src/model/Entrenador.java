package model;

import java.sql.Connection;
import java.util.LinkedList;

import bd.PokemonBD;
import bd.BDConecction;

public class Entrenador {

	private String usuario;
	private String pass;
	private int pokedolares;
	private int idEntrenador;
	private Pokemon pokPrincipal;
	private LinkedList<Objeto> mochila = new LinkedList<Objeto>();
	private LinkedList<Pokemon> equipo = new LinkedList<Pokemon>();
	private LinkedList<Pokemon> caja = new LinkedList<Pokemon>();
	
	
	public Entrenador(String usuario, String pass, int pokedolares, int idEntrenador, Pokemon pokPrincipal,
			LinkedList<Objeto> mochila, LinkedList<Pokemon> equipo, LinkedList<Pokemon> caja) {
		super();
		this.usuario = usuario;
		this.pass = pass;
		this.pokedolares = pokedolares;
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
	    this.pokedolares = 1000;
	    this.mochila = new LinkedList<Objeto>();
	    this.equipo = new LinkedList<Pokemon>();
	    this.caja = new LinkedList<Pokemon>();

	    Connection conexion = BDConecction.getConnection();
	    
	    // Generar mochila y asignar Pokémon aleatorio
	    this.pokPrincipal =  PokemonBD.generarPokemonPrincipal(idEntrenador, conexion); // Se asigna directamente al atributo pokPrincipal
	    this.equipo.add(pokPrincipal); // Añadir al equipo principal
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
		return pokedolares;
	}


	public void setPokedolares(int pokedolares) {
		this.pokedolares = pokedolares;
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
	
	
	
}
