package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un combate Pokémon.
 * Permite agregar turnos, exportar el registro y consultar información adicional.
 * Incluye atributos y métodos extra para hacerlo más "relleno".
 */
public class Combate {

    private String idCombate;
    private List<Turno> turnos;

    // Atributos adicionales para "relleno"
    private int totalTurnosExportados = 0;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    public Combate(String numeroCombate) {
        this.idCombate = numeroCombate;
        this.turnos = new ArrayList<>();
        this.fechaInicio = LocalDateTime.now();
    }

    public Combate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        this.idCombate = LocalDateTime.now().format(formatter);
        this.turnos = new ArrayList<>();
        this.fechaInicio = LocalDateTime.now();
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
                writer.write(turno.toString() + "\n\n");
                totalTurnosExportados++;
            }
            fechaFin = LocalDateTime.now();
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