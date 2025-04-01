package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Entrenador;

public class MenuController {

	private Entrenador entrenador;
	private Stage stage;
	private LoginController loginController;

	@FXML
	private Button btnCaptura;

	@FXML
	private Button btnCentroPokemon;

	@FXML
	private Button btnCombate;

	@FXML
	private Button btnCrianza;

	@FXML
	private Button btnEntrenamiento;

	@FXML
	private Button btnEquipo;

	@FXML
	private Button btnSalir;

	@FXML
	private ImageView imagenFondo;

	@FXML
	private ImageView imagenLogo;

	@FXML
	private Label lblJugador;

	@FXML
	private Label lblPokedollares;

	@FXML
	private Label plantillaPokeDolares;

	@FXML
	private Label plantillaUsuario;

	@FXML
	void abrirCaptura(ActionEvent event) {

	}

	@FXML
	void abrirCentro(ActionEvent event) {

	}

	@FXML
	void abrirCombate(ActionEvent event) {

	}

	@FXML
	void abrirCrianza(ActionEvent event) {

	}

	@FXML
	void abrirEntrenamiento(ActionEvent event) {

	}

	@FXML
	void abrirEquipo(ActionEvent event) {

	}

	@FXML
	public void cerrarMenu(ActionEvent event) {
		Stage stage = (Stage) btnSalir.getScene().getWindow();
		stage.close();
	}

	public void init(Entrenador ent, Stage stage, LoginController loginController) {
		this.loginController = loginController;
		this.stage = stage;
		this.entrenador = ent;

		lblJugador.setText(entrenador.getUsuario());
		lblPokedollares.setText(Integer.toString(entrenador.getPokedolares()));

	}
}
