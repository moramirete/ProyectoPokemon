package model;

import java.util.LinkedList;
import java.util.Random;

public class Pokemon {

	
	//Atributos 
	private int id_pokemon;
	private int id_entrenador;
	private int num_pokedex;
	private int id_objeto;
	private String tipo1;
	private String tipo2;
	private int vitalidad;
	private int vitalidadMax;
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
	private LinkedList<Movimiento> movPrincipales = new LinkedList<Movimiento>();
	private LinkedList<Movimiento> movSecundarios = new LinkedList<Movimiento>();
	private int vitalidadOBJ;
	private int ataqueOBJ;
	private int defensaOBJ;
	private int ataque_especialOBJ;
	private int defensa_especialOBJ;
	private int velocidadOBJ;
	
	
	public Pokemon(int id_pokemon, int id_entrenador, int num_pokedex, int id_objeto, String tipo1, String tipo2, 
			int vitalidad, int ataque, int defensa, int ataque_especial, int defensa_especial, int velocidad, 
			int nivel, int fertilidad,int equipo, String nombre_pokemon, String estado, char sexo, int vitalidadMax, 
			LinkedList<Movimiento> movPrincipales, LinkedList<Movimiento> movSecundarios,int vitalidadOBJ, int ataqueOBJ, int defensaOBJ, 
			int ataque_especialOBJ, int defensa_especialOBJ, int velocidadOBJ) {
		super();
		this.id_pokemon = id_pokemon;
		this.id_entrenador = id_entrenador;
		this.num_pokedex = num_pokedex;
		this.id_objeto = id_objeto;
		this.tipo1 = tipo1;
		this.tipo2 = tipo2;
		this.vitalidad = vitalidad;
		this.vitalidadMax = vitalidadMax;
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
		this.movPrincipales = movPrincipales;
		this.movSecundarios = movSecundarios;
		this.vitalidadOBJ = vitalidadOBJ;
		this.ataqueOBJ = ataqueOBJ;
		this.defensaOBJ = defensaOBJ;
		this.ataque_especialOBJ = ataque_especialOBJ;
		this.defensa_especialOBJ = defensa_especialOBJ;
		this.velocidadOBJ = velocidadOBJ;
	}
	
	//Constructor sin las linkedList
	
	public Pokemon(int id_pokemon, int id_entrenador, int num_pokedex, int id_objeto, String tipo1, String tipo2, 
			int vitalidad, int ataque, int defensa, int ataque_especial, int defensa_especial, int velocidad, int nivel, 
			int fertilidad, int equipo, String nombre_pokemon, String estado, char sexo, int vitalidadMax, int vitalidadOBJ, int ataqueOBJ, 
			int defensaOBJ, int ataque_especialOBJ, int defensa_especialOBJ, int velocidadOBJ) {
		
		super();
		this.id_pokemon = id_pokemon;
		this.id_entrenador = id_entrenador;
		this.num_pokedex = num_pokedex;
		this.id_objeto = id_objeto;
		this.tipo1 = tipo1;
		this.tipo2 = tipo2;
		this.vitalidad = vitalidad;
		this.vitalidadMax = vitalidadMax;
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
		this.vitalidadOBJ = vitalidadOBJ;
		this.ataqueOBJ = ataqueOBJ;
		this.defensaOBJ = defensaOBJ;
		this.ataque_especialOBJ = ataque_especialOBJ;
		this.defensa_especialOBJ = defensa_especialOBJ;
		this.velocidadOBJ = velocidadOBJ;
		this.movPrincipales = new LinkedList<Movimiento>();
		this.movSecundarios = new LinkedList<Movimiento>();
	}
	
	//Copia
	
	public Pokemon(Pokemon c) {
		
		super();
		this.id_pokemon = c.id_pokemon;
		this.id_entrenador = c.id_entrenador;
		this.num_pokedex = c.num_pokedex;
		this.id_objeto = c.id_objeto;
		this.vitalidad = c.vitalidad;
		this.vitalidadMax = c.vitalidadMax;
		this.ataque = c.ataque;
		this.defensa = c.defensa;
		this.ataque_especial = c.ataque_especial;
		this.defensa_especial = c.defensa_especial;
		this.velocidad = c.velocidad;
		this.nivel = c.nivel;
		this.fertilidad = c.fertilidad;
		this.equipo = c.equipo;
		this.nombre_pokemon = c.nombre_pokemon;
		this.estado = c.estado;
		this.sexo = c.sexo;
		this.movPrincipales = c.movPrincipales;
		this.movSecundarios = c.movSecundarios;
		this.vitalidadOBJ = c.vitalidadOBJ;
		this.ataqueOBJ = c.ataqueOBJ;
		this.defensaOBJ = c.defensaOBJ;
		this.ataque_especialOBJ = c.ataque_especialOBJ;
		this.defensa_especialOBJ = c.defensa_especialOBJ;
		this.velocidadOBJ = c.velocidadOBJ;
	}
	
	public int getVitalidadOBJ() {
		return vitalidadOBJ;
	}

	public void setVitalidadOBJ(int vitalidadOBJ) {
		this.vitalidadOBJ = vitalidadOBJ;
	}

	public int getAtaqueOBJ() {
		return ataqueOBJ;
	}

	public void setAtaqueOBJ(int ataqueOBJ) {
		this.ataqueOBJ = ataqueOBJ;
	}

	public int getDefensaOBJ() {
		return defensaOBJ;
	}

	public void setDefensaOBJ(int defensaOBJ) {
		this.defensaOBJ = defensaOBJ;
	}

	public int getAtaque_especialOBJ() {
		return ataque_especialOBJ;
	}

	public void setAtaque_especialOBJ(int ataque_especialOBJ) {
		this.ataque_especialOBJ = ataque_especialOBJ;
	}

	public int getDefensa_especialOBJ() {
		return defensa_especialOBJ;
	}

	public void setDefensa_especialOBJ(int defensa_especialOBJ) {
		this.defensa_especialOBJ = defensa_especialOBJ;
	}

	public int getVelocidadOBJ() {
		return velocidadOBJ;
	}

	public void setVelocidadOBJ(int velocidadOBJ) {
		this.velocidadOBJ = velocidadOBJ;
	}

	public LinkedList<Movimiento> getMovPrincipales() {
		return movPrincipales;
	}

	public void setMovPrincipales(LinkedList<Movimiento> movPrincipales) {
		this.movPrincipales = movPrincipales;
	}

	public LinkedList<Movimiento> getMovSecundarios() {
		return movSecundarios;
	}

	public void setMovSecundarios(LinkedList<Movimiento> movSecundarios) {
		this.movSecundarios = movSecundarios;
	}

	//Constructor sin parametros
	public Pokemon() {
		
		super();
		this.id_pokemon = 0;
		this.id_entrenador = 0;
		this.num_pokedex = 0;
		this.id_objeto = 0;
		this.vitalidad = 0;
		this.vitalidadMax = 0;
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
		this.movPrincipales = new LinkedList<Movimiento>();
		this.movSecundarios = new LinkedList<Movimiento>();
	}
	
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
	public String getTipo1() {
		return tipo1;
	}
	public void setTipo1(String tipo1) {
		this.tipo1 = tipo1;
	}
	public String getTipo2() {
		return tipo2;
	}
	public void setTipo2(String tipo2) {
		this.tipo2 = tipo2;
	}

	public int getVitalidadMax() {
		return vitalidadMax;
	}

	public void setVitalidadMax(int vitalidadMax) {
		this.vitalidadMax = vitalidadMax;
	}
	

	
}
