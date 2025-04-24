package model;

public class Objeto {

	private int idObjeto;
	private String nombreObjeto;
	private int ataque;
	private int defensa;
	private int ataqueEspecial;
	private int defensaEspecial;
	private int velocidad;
	private int vitalidad;
	private int pp;
	private int precio;
	private String rutaImagen;
	private String descripcion;
	
	public Objeto(String rutaImagen) {
	   
	    this.rutaImagen = rutaImagen;
	    
	}

	public String getRutaImagen() {
		if (rutaImagen == null || rutaImagen.isEmpty()) {
	        
	        return "/imagenes/default.png";
	    }
	    return "/imagenes/" + rutaImagen;
	}

	public void setRutaImagen(String rutaImagen) {
	    this.rutaImagen = rutaImagen;
	}
	
	public Objeto(int idObjeto, String nombreObjeto, int ataque, int defensa, int ataqueEspecial, int defensaEspecial,
			int velocidad, int vitalidad, int pp, int precio, String rutaImagen, String descripcion) {
		super();
		this.idObjeto = idObjeto;
		this.nombreObjeto = nombreObjeto;
		this.ataque = ataque;
		this.defensa = defensa;
		this.ataqueEspecial = ataqueEspecial;
		this.defensaEspecial = defensaEspecial;
		this.velocidad = velocidad;
		this.vitalidad = vitalidad;
		this.pp = pp;
		this.precio = precio;
		this.rutaImagen = rutaImagen;
		this.descripcion = descripcion;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Objeto(String nombreObjeto, int precio) {
		super();
		this.nombreObjeto = nombreObjeto;
		this.precio = precio;
	}

	public int getIdObjeto() {
		return idObjeto;
	}

	public void setIdObjeto(int idObjeto) {
		this.idObjeto = idObjeto;
	}

	public String getNombreObjeto() {
		return nombreObjeto;
	}

	public void setNombreObjeto(String nombreObjeto) {
		this.nombreObjeto = nombreObjeto;
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

	public int getAtaqueEspecial() {
		return ataqueEspecial;
	}

	public void setAtaqueEspecial(int ataqueEspecial) {
		this.ataqueEspecial = ataqueEspecial;
	}

	public int getDefensaEspecial() {
		return defensaEspecial;
	}

	public void setDefensaEspecial(int defensaEspecial) {
		this.defensaEspecial = defensaEspecial;
	}

	public int getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}

	public int getVitalidad() {
		return vitalidad;
	}

	public void setVitalidad(int vitalidad) {
		this.vitalidad = vitalidad;
	}

	public int getPp() {
		return pp;
	}

	public void setPp(int pp) {
		this.pp = pp;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}
	
	
}
