package model;

public class Pokemon {

	
	//Atributos 
	private int id_pokemon;
	private int id_entrenador;
	private int num_pokedex;
	private int id_objeto;
	private int vitalidad;
	private int ataque;
	private int defensa;
	private int ataque_especial;
	private int defensa_especial;
	private int velocidad;
	private int nivel;
	private int fertilidad;
	private int equipo;
	
	private String nombre_pokemon;
	private String estado;
	private char sexo;
	
	//Getters and Setters
	public int getId_pokemon() {
		return id_pokemon;
	}
	public void setId_pokemon(int id_pokemon) {
		this.id_pokemon = id_pokemon;
	}
	public int getId_entrenador() {
		return id_entrenador;
	}
	public void setId_entrenador(int id_entrenador) {
		this.id_entrenador = id_entrenador;
	}
	public int getNum_pokedex() {
		return num_pokedex;
	}
	public void setNum_pokedex(int num_pokedex) {
		this.num_pokedex = num_pokedex;
	}
	public int getId_objeto() {
		return id_objeto;
	}
	public void setId_objeto(int id_objeto) {
		this.id_objeto = id_objeto;
	}
	public int getVitalidad() {
		return vitalidad;
	}
	public void setVitalidad(int vitalidad) {
		this.vitalidad = vitalidad;
	}
	public int getAtaque() {
		return ataque;
	}
	public void setAtaque(int ataque) {
		this.ataque = ataque;
	}
	public int getDefensa() {
		return defensa;
	}
	public void setDefensa(int defensa) {
		this.defensa = defensa;
	}
	public int getAtaque_especial() {
		return ataque_especial;
	}
	public void setAtaque_especial(int ataque_especial) {
		this.ataque_especial = ataque_especial;
	}
	public int getDefensa_especial() {
		return defensa_especial;
	}
	public void setDefensa_especial(int defensa_especial) {
		this.defensa_especial = defensa_especial;
	}
	public int getVelocidad() {
		return velocidad;
	}
	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	public int getFertilidad() {
		return fertilidad;
	}
	public void setFertilidad(int fertilidad) {
		this.fertilidad = fertilidad;
	}
	public int getEquipo() {
		return equipo;
	}
	public void setEquipo(int equipo) {
		this.equipo = equipo;
	}
	public String getNombre_pokemon() {
		return nombre_pokemon;
	}
	public void setNombre_pokemon(String nombre_pokemon) {
		this.nombre_pokemon = nombre_pokemon;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public char getSexo() {
		return sexo;
	}
	public void setSexo(char sexo) {
		this.sexo = sexo;
	}
	
	
	//Constructores
	
	public Pokemon(int id_pokemon, int id_entrenador, int num_pokedex, int id_objeto, int vitalidad, int ataque,
			int defensa, int ataque_especial, int defensa_especial, int velocidad, int nivel, int fertilidad,
			int equipo, String nombre_pokemon, String estado, char sexo) {
		
		super();
		this.id_pokemon = id_pokemon;
		this.id_entrenador = id_entrenador;
		this.num_pokedex = num_pokedex;
		this.id_objeto = id_objeto;
		this.vitalidad = vitalidad;
		this.ataque = ataque;
		this.defensa = defensa;
		this.ataque_especial = ataque_especial;
		this.defensa_especial = defensa_especial;
		this.velocidad = velocidad;
		this.nivel = nivel;
		this.fertilidad = fertilidad;
		this.equipo = equipo;
		this.nombre_pokemon = nombre_pokemon;
		this.estado = estado;
		this.sexo = sexo;
	}
	
	//Constructor sin parametros
	public Pokemon() {
		
		super();
		this.id_pokemon = 0;
		this.id_entrenador = 0;
		this.num_pokedex = 0;
		this.id_objeto = 0;
		this.vitalidad = 0;
		this.ataque = 0;
		this.defensa = 0;
		this.ataque_especial = 0;
		this.defensa_especial = 0;
		this.velocidad = 0;
		this.nivel = 0;
		this.fertilidad = 0;
		this.equipo = 0;
		this.nombre_pokemon = "";
		this.estado = "";
		this.sexo = 0;
	}
	
}
