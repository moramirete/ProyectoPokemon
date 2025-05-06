package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Entrenador;

public class EntrenamientoController {

	private Entrenador entrenador;
	private Stage stage;
	private MenuController menuController;
	
	@FXML
    private ImageView imgFondo;
	
	@FXML
	private Button btnSalir;

	public void init(Entrenador ent, Stage stage, MenuController menuController) {
		this.menuController = menuController;
		this.stage = stage;
		this.entrenador = ent;
	}

	
	@FXML
	void salir(ActionEvent event) {
		menuController.show();
		this.stage.close();
	}

}
