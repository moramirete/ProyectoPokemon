package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Combate {
	
	private String idCombate;
    private List<Turno> turnos;

    public Combate(String numeroCombate) {
        this.idCombate = numeroCombate;
        this.turnos = new ArrayList<>();
    }
    
    
    
    public Combate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        this.idCombate = LocalDateTime.now().format(formatter);
        this.turnos = new ArrayList<>();
    }

    public String getIdCombate() {
        return idCombate;
    }
    
    public void agregarTurno(Turno turno) {
        turnos.add(turno);
    }

    public void exportarTurnos(String rutaArchivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            writer.write("Combate " + idCombate + ":\n");
            for (Turno turno : turnos) {
                writer.write(turno.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public String getNumeroCombate() {
		return idCombate;
	}

	public void setNumeroCombate(String numeroCombate) {
		this.idCombate = numeroCombate;
	}

	public List<Turno> getTurnos() {
		return turnos;
	}

	public void setTurnos(List<Turno> turnos) {
		this.turnos = turnos;
	}
    
    

}
