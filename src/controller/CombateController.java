package controller;

import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Entrenador;

public class CombateController {

	private Entrenador entrenador;
	private Stage stage;
	private MenuController menuController;

	public void init(Entrenador ent, Stage stage, MenuController menuController) {
		this.menuController = menuController;
		this.stage = stage;
		this.entrenador = ent;

	}

	@FXML
	private Button btnDescansar;

	@FXML
	private Button btnHuir;

	@FXML
	private Button btnMov1;

	@FXML
	private Button btnMov2;

	@FXML
	private Button btnMov3;

	@FXML
	private Button btnMov4;

	@FXML
	private Button btnPokemon;

	@FXML
	private ProgressBar hpPokemon;

	@FXML
	private ProgressBar hpPokemonRival;

	@FXML
	private ImageView imagenFondo;

	@FXML
	private ImageView imgPoke;

	@FXML
	private ImageView imgPokeRival;

	@FXML
	private Label lblEntrenador;

	@FXML
	private Label lblNombrePoke;

	@FXML
	private Label lblNombrePokeRival;

	@FXML
	private Label lblRival;

	@FXML
	void cambiarPoke(ActionEvent event) {

	}

	@FXML
	void descansar(ActionEvent event) {

	}

	@FXML
	void hacerMov1(ActionEvent event) {

	}

	@FXML
	void hacerMov2(ActionEvent event) {

	}

	@FXML
	void hacerMov3(ActionEvent event) {

	}

	@FXML
	void hacerMov4(ActionEvent event) {

	}

	@FXML
	void huir(ActionEvent event) {
		menuController.show();
		this.stage.close();
	}

}
