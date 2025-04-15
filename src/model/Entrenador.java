package model;

import java.util.LinkedList;

public class Entrenador {

	private String usuario;
	private String pass;
	private int pokedolares;
	private int idEntrenador;
	private LinkedList<Objeto> mochila = new LinkedList<Objeto>();
	
	public Entrenador(String usuario, String pass, int pokedolares, int idEntrenador, LinkedList<Objeto> mochila) {
		super();
		this.idEntrenador = idEntrenador;
		this.usuario = usuario;
		this.pass = pass;
		this.pokedolares = pokedolares;
		this.mochila = mochila;
	}
	
	public Entrenador(String usuario, String pass) {
		super();
		this.idEntrenador = 0;
		this.usuario = usuario;
		this.pass = pass;
		this.pokedolares = 1000;
		this.mochila = mochila;
	}

	public int getIdEntrenador() {
		return idEntrenador;
	}
	public void setIdEntrenador(int idEntrenador) {
		this.idEntrenador = idEntrenador;
	}
	public int getPokedolares() {
		return pokedolares;
	}
	public void setPokedolares(int pokedolares) {
		this.pokedolares = pokedolares;
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
	
	
}
