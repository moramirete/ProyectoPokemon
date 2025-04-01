package model;

public class Entrenador {

	private String usuario;
	private String pass;
	private int pokedolares;
	
	public Entrenador(String usuario, String pass, int pokedolares) {
		super();
		this.usuario = usuario;
		this.pass = pass;
		this.pokedolares = pokedolares;
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
