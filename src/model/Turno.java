package model;

public class Turno {
	private int numCombate;
	private int numTurno;
	private String accionEntrenador;
	private String accionRival;

	public Turno(int numCombate, int numTurno, String accionEntrenador, String accionRival) {
		this.numCombate = numCombate;
		this.numTurno = numTurno;
		this.accionEntrenador = accionEntrenador;
		this.accionRival = accionRival;
	}

	@Override
    public String toString() {
        return "Turno " + numTurno + ":\n" +
               "Entrenador: " + accionEntrenador + "\n" +
               "Rival: " + accionRival + "\n";
        
    }

	public int getNumCombate() {
		return numCombate;
	}

	public void setNumCombate(int numCombate) {
		this.numCombate = numCombate;
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
	
	
}
