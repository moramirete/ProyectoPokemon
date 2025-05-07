package controller;

import java.sql.Connection;
import java.util.LinkedList;

import bd.BDConecction;
import bd.EntrenadorBD;
import bd.MovimientoBD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
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
	private ImageView imgPokeRival;

	@FXML
	private ImageView imgPokemon;

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

	@FXML
	void cambiarPoke(ActionEvent event) {
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
}
