package controller;

import javafx.stage.Stage;
import model.Entrenador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class CapturaController {

	private Entrenador entrenador;
	private Stage stage;
	private MenuController menuController;
	
	 @FXML
	 private Button btnCapturar;

	 @FXML
	 private Button btnGenerar;

	 @FXML
	 private Button btnSalir;

	 @FXML
	 private ImageView imgFondo;

	
	public void init(Entrenador ent, Stage stage, MenuController menuController) {
		this.menuController = menuController;
		this.stage = stage;
		this.entrenador = ent;
	}
	
	@FXML
	void cerrarCaptura(ActionEvent event) {
		System.out.println("Se ha accionado el boton de salir");
		menuController.show();
		this.stage.close();
		System.out.println("Se ha salido de captura correctamente");
	}
	
    @FXML
    void capturarPokemon(ActionEvent event) {
    	System.out.println("Se ha accionado el boton de capturar");
    }
    
    @FXML
    void generarPokemon(ActionEvent event) {
    	System.out.println("Se ha accionado el boton de generar");
    }

}
