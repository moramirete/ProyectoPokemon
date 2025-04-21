package controller;

import javafx.stage.Stage;
import model.Entrenador;
import model.Pokemon;

import java.sql.Connection;

import bd.BDConecction;
import bd.PokemonBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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
	private TableColumn<Pokemon, String> colNombre;

	@FXML
	private TableColumn<Pokemon, Integer> colNivel;

	@FXML
	private TableColumn<Pokemon, String> colTipo;
	
	@FXML
	private TableColumn<Pokemon, String> colTipo2;

	@FXML
	private TableView<Pokemon> tabPokemon;

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
