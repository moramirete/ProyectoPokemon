package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import bd.BDConecction;
import bd.MochilaBD;
import bd.PokemonBD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Entrenador;
import model.Pokemon;

public class CrianzaController {

	private Entrenador entrenador;
	private Stage stage;
	private MenuController menuController;
	
	
    @FXML
    private Button btnSalir;

    
    
    public void init(Entrenador ent, Stage stage, MenuController menuController) {
    	this.menuController = menuController;
    	this.stage = stage;
    	this.entrenador = ent;
    }
    
   

    @FXML
    void cerrarCrianza(ActionEvent event) {
    	System.out.println("Se ha accionado el boton de salir");
		menuController.show();
		this.stage.close();
		System.out.println("Se ha salido de captura correctamente");
    }


}