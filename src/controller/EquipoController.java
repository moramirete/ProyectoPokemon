package controller;

import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Entrenador;

public class EquipoController {

	private Entrenador entrenador;
	private Stage stage;
	private MenuController menuController;

	public void init(Entrenador ent, Stage stage, MenuController menuController) {
		this.menuController = menuController;
		this.stage = stage;
		this.entrenador = ent;

	}

	@FXML
	private Button btnSalir;

	@FXML
	private ImageView imgfondo;

	@FXML
	void cerrarEquipo(ActionEvent event) {
		menuController.show();
		this.stage.close();
	}

}
