package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Entrenador;

public class CrianzaController {


	private Entrenador entrenador;
	private Stage stage;
	private MenuController menuController;
	
	@FXML
    private Button btnAbrirHuevo;

    @FXML
    private Button btnIncubar;

    @FXML
    private Button btnSeleccionarPokemon1;

    @FXML
    private Button btnSeleccionarPokemon2;

    public void init(Entrenador ent, Stage stage, MenuController menuController) {
    	this.menuController = menuController;
    	this.stage = stage;
    	this.entrenador = ent;
    	}
    
    @FXML
    void abrirHuevo(ActionEvent event) {

    }

    @FXML
    void botonIncubar(ActionEvent event) {

    }

    @FXML
    void seleccionarPokemon1(ActionEvent event) {

    }

    @FXML
    void seleccionarPokemon2(ActionEvent event) {

    }

    @FXML
    void cerrarCrianza(ActionEvent event) {
    	System.out.println("Se ha accionado el boton de salir");
		menuController.show();
		this.stage.close();
		System.out.println("Se ha salido de captura correctamente");
    }
    
    
}
