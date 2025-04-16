package controller;

import javafx.stage.Stage;
import model.Entrenador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CajaController {

	private Entrenador entrenador;
	private Stage stage;
	private EquipoController equipoController;

	public void init(Entrenador entrenador, Stage stage, EquipoController equipoController) {
		this.entrenador = entrenador;
		this.stage = stage;
		this.equipoController = equipoController;

	}

	@FXML
	private Button btnBuscar;

	@FXML
	private Button btnVolver;

	@FXML
	private TableColumn<?, ?> colNombre;

	@FXML
	private TableColumn<?, ?> colTelefono;

	@FXML
	private TableColumn<?, ?> colTipo;

	@FXML
	private TableView<?> tabPokemon;

	@FXML
	private TextField txtNivel;

	@FXML
	private TextField txtNombre;

	@FXML
	private TextField txtTipo;

	@FXML
	private TextField txtVitalidad;

	@FXML
	void volveratras(ActionEvent event) {
		equipoController.show();
		this.stage.close();
	}
}
