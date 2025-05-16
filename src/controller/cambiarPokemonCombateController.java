package controller;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import bd.PokemonBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Entrenador;
import model.Pokemon;

public class cambiarPokemonCombateController {

	@FXML
	private Button btnCambiarPor;

	@FXML
	private Button btnVolver;

	@FXML
	private TableColumn<Pokemon, Integer> colNivel;

	@FXML
	private TableColumn<Pokemon, String> colNombre;

	@FXML
	private TableColumn<Pokemon, Integer> colPosicion;

	@FXML
	private TableColumn<Pokemon, String> colTipo;

	@FXML
	private TableColumn<Pokemon, String> colTipo2;

	@FXML
	private TableView<Pokemon> tabPokemon;

	@FXML
	private Label txtPokemonCaja;

	private Stage stage;
	private Entrenador entrenador;
	private Pokemon actualPrincipal;
	private EntrenamientoController entrenamientoController;

	public void init(Entrenador entrenador, Pokemon actualPrincipal, EntrenamientoController controller) {
		this.entrenador = entrenador;
		this.actualPrincipal = actualPrincipal;
		this.entrenamientoController = controller;

		cargarEquipo();
	}

	private void cargarEquipo() {

		ArrayList<Pokemon> equipo = PokemonBD.obtenerEquipo(entrenador.getIdEntrenador());
		ObservableList<Pokemon> listaEquipo = FXCollections.observableArrayList(equipo);

		colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre_pokemon"));
		colNivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));
		colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo1"));
		colTipo2.setCellValueFactory(new PropertyValueFactory<>("tipo2"));
		colPosicion.setCellValueFactory(new PropertyValueFactory<>("equipo"));

		tabPokemon.setItems(listaEquipo);
	}

	@FXML
	void cambiarPor(ActionEvent event) {
		Pokemon seleccionado = tabPokemon.getSelectionModel().getSelectedItem();

		if (seleccionado == null) {
			JOptionPane.showMessageDialog(null, "Selecciona un Pokémon.");
			return;
		}

		boolean cambio = PokemonBD.cambiarPrincipalConEquipo(entrenador.getIdEntrenador(), seleccionado,
				actualPrincipal);

		if (cambio) {
			entrenamientoController.recargarConNuevoPokemon(seleccionado.getId_pokemon());
			cerrarVentana(event);
		} else {
			JOptionPane.showMessageDialog(null, "No se pudo cambiar el Pokémon.");
		}
	}

	@FXML
	void volveratras(ActionEvent event) {
		cerrarVentana(event);
	}
	
	@FXML
    void cerrarVentana(ActionEvent event) {
        // Cerrar la ventana emergente
		Stage stage = (Stage) btnVolver.getScene().getWindow();
	    stage.close();
    }
}
