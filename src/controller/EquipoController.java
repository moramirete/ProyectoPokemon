package controller;

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
	
}
