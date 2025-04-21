package model;

public class Movimiento {

	//Atributos
	
	private int id_movimiento;
	private String nom_movimiento;
	private String descripcion;
	private int precision;
	private int pp_max;
	private int pp_actual;
	private String tipo;
	private String tipo_mov;
	private int potencia;
	private String estado;
	private int turnos;
	private int mejora;
	private int num_mov;
	private int cant_mejora;
	
	
	
	//CONTRUCTOR CON TODOS LOS PARAMETROS
	public Movimiento(int id_movimiento, String nom_movimiento, String descripcion, int precision, int pp_max, int pp_actual,
			String tipo, String tipo_mov, int potencia, String estado, int turnos, int mejora, int num_mov,
			int cant_mejora) {
		super();
		this.id_movimiento = id_movimiento;
		this.nom_movimiento = nom_movimiento;
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
	}

	//CONTRUCTOR SIN PARAMETROS

	public Movimiento() {
		super();
		this.id_movimiento = 0;
		this.nom_movimiento = "";
		this.descripcion = "";
		this.precision = 0;
		this.pp_max = 0;
		this.pp_actual = 0;
		this.tipo = "";
		this.tipo_mov = "";
		this.potencia = 0;
		this.estado = "";
		this.turnos = 0;
		this.mejora = 0;
		this.num_mov = 0;
		this.cant_mejora = 0;
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



	public int getMejora() {
		return mejora;
	}



	public void setMejora(int mejora) {
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
	
	
	
}
