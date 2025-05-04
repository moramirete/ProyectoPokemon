package controller;

import java.sql.Connection;

import bd.BDConecction;
import bd.MochilaBD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Entrenador;

public class CrianzaController {

	private Entrenador entrenador;
	private Stage stage;
	private MenuController menuController;
	
	private int huevo = 0;
	
    @FXML
    private Button btnAbrirHuevo;

    @FXML
    private Button btnIncubar;

    @FXML
    private Button btnSalir;

    @FXML
    private Button btnSeleccionarPokemon1;

    @FXML
    private Button btnSeleccionarPokemon2;

    
    @FXML
    private Label lblHuevos;

    @FXML
    private Label lblPokemon;
    
    public void init(Entrenador ent, Stage stage, MenuController menuController) {
    	this.menuController = menuController;
    	this.stage = stage;
    	this.entrenador = ent;
    }
    @FXML
    void abrirHuevo(ActionEvent event) {
    	
    }

    private void actualizarLblHuevos() {
		lblHuevos.setText(String.valueOf(huevo));
		lblHuevos.setStyle("-fx-font-size: 32px; -fx-text-fill: #ff0000;");
	}
	
	private void actualizarHuevosBD() {
		try (Connection conexion = BDConecction.getConnection()){
			MochilaBD.actualizarCantidad(entrenador.getIdEntrenador(), huevo);
		
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    @FXML
    void botonIncubar(ActionEvent event) {

    }

    @FXML
    void cerrarCrianza(ActionEvent event) {
    	System.out.println("Se ha accionado el boton de salir");
		menuController.show();
		this.stage.close();
		System.out.println("Se ha salido de captura correctamente");
    }

    @FXML
    void seleccionarPokemon1(ActionEvent event) {

    }

    @FXML
    void seleccionarPokemon2(ActionEvent event) {

    }

}