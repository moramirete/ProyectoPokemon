// filepath: c:\ProyectoPokemon\src\controller\CambiarPokemonPrincipalController.java
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

public class CambiarPokemonPrincipalController {

	@FXML
	private Button btnCambiarPor;

	@FXML
	private Button btnVolver;

	@FXML
	private TableColumn<Pokemon, String> colNombre;

	@FXML
	private TableColumn<Pokemon, Integer> colNivel;

	@FXML
	private TableColumn<Pokemon, String> colTipo;

	@FXML
	private TableColumn<Pokemon, Integer> colTipo2;

	@FXML
	private TableView<Pokemon> tabPokemon;

	@FXML
	private Label txtPokemonCaja;

	@FXML
	private Label txtPokemonCaja1;

	private Entrenador entrenador;
	private Stage stage;
	private EquipoController equipoController;

	public void init(Entrenador entrenador, Stage stage, EquipoController equipoController) {
		this.entrenador = entrenador;
		this.stage = stage;
		this.equipoController = equipoController;

		cargarEquipo();
	}

	private void cargarEquipo() {
		ArrayList<Pokemon> equipo = PokemonBD.obtenerEquipoSinPrincipal(entrenador.getIdEntrenador());
		ObservableList<Pokemon> listaEquipo = FXCollections.observableArrayList(equipo);

		colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre_pokemon"));
		colNivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));
		colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo1"));
		colTipo2.setCellValueFactory(new PropertyValueFactory<>("tipo2"));

		tabPokemon.setItems(listaEquipo);
	}

	@FXML
	void cambiarPor(ActionEvent event) {
		Pokemon pokemonSeleccionadoDeEquipo = tabPokemon.getSelectionModel().getSelectedItem();

		if (pokemonSeleccionadoDeEquipo == null) {
			JOptionPane.showMessageDialog(null, "Selecciona un Pokémon del equipo para realizar el cambio.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		boolean cambiado = PokemonBD.cambiarPokemonPrincipalAEquipo(entrenador.getIdEntrenador(),
				 pokemonSeleccionadoDeEquipo, PokemonBD.obtenerPokemonPrincipal(entrenador.getIdEntrenador()));

		if (cambiado) {
			System.out.println("El Pokémon ha sido cambiado correctamente.");
			equipoController.inicializarEquipo(); // Actualizar el equipo en la interfaz principal
			stage.close(); // Cerrar la ventana emergente
		} else {
			JOptionPane.showMessageDialog(null, "Error al cambiar el Pokémon en la base de datos.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@FXML
	void volveratras(ActionEvent event) {
		if (equipoController != null) {
			equipoController.show(); // Mostrar la ventana del equipo
		}
		stage.close(); // Cerrar la ventana actual
	}
}