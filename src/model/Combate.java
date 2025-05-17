package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Combate {
	
	private int numeroCombate;
    private List<Turno> turnos;

    public Combate(int numeroCombate) {
        this.numeroCombate = numeroCombate;
        this.turnos = new ArrayList<>();
    }
    
    public void agregarTurno(Turno turno) {
        turnos.add(turno);
    }

    public void exportarTurnos(String rutaArchivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            writer.write("Combate " + numeroCombate + ":\n");
            for (Turno turno : turnos) {
                writer.write(turno.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public int getNumeroCombate() {
		return numeroCombate;
	}

	public void setNumeroCombate(int numeroCombate) {
		this.numeroCombate = numeroCombate;
	}

	public List<Turno> getTurnos() {
		return turnos;
	}

	public void setTurnos(List<Turno> turnos) {
		this.turnos = turnos;
	}
    
    

}
