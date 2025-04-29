package controller;

import javafx.stage.Stage;
import model.Entrenador;
import model.Pokemon;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Optional;

import javax.swing.JOptionPane;

import bd.BDConecction;
import bd.PokemonBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

public class CajaController {

	private Entrenador entrenador;
	private Stage stage;
	private EquipoController equipoController;

	private ArrayList<Pokemon> equipo;
	private ArrayList<Pokemon> equipoFiltro;

	@FXML
	private Button btnCambiarNombre;

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
	private TableColumn<Pokemon, String> colTipo2;

	@FXML
	private TableView<Pokemon> tabPokemon;

	@FXML
	private TextField txtBuscador;

	public void init(Entrenador entrenador, Stage stage, EquipoController equipoController) {
		this.entrenador = entrenador;
		this.stage = stage;
		this.equipoController = equipoController;

		// Inicializa las listas después de que 'entrenador' esté configurado
		this.equipo = PokemonBD.obtenerCaja(entrenador.getIdEntrenador());
		this.equipoFiltro = new ArrayList<>(equipo);

		cargarCaja();
	}

	@FXML
	void volveratras(ActionEvent event) {
		equipoController.show();
		this.stage.close();
	}

	private void cargarCaja() {

		ObservableList<Pokemon> lista = FXCollections.observableArrayList(equipo);
		ObservableList<Pokemon> listaFiltro = FXCollections.observableArrayList(equipo);

		for (Pokemon pokemon : lista) {
			System.out.println("Pokemon: " + pokemon.getNombre_pokemon() + ", Vitalidad: " + pokemon.getVitalidad()
					+ ", Vitalidad Max: " + pokemon.getVitalidadMax());
		}

		tabPokemon.setItems(lista);
	}

	public void initialize() {
		colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre_pokemon"));
		colNivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));
		colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo1"));
		colTipo2.setCellValueFactory(new PropertyValueFactory<>("tipo2"));

		if (entrenador != null) {
			cargarCaja();
		}
	}

	@FXML
	void buscarPokemon(KeyEvent event) {

		String buscador = this.txtBuscador.getText();

		if (buscador.isEmpty()) {
			// Convierte 'equipo' a un ObservableList antes de asignarlo
			this.tabPokemon.setItems(FXCollections.observableArrayList(equipo));
		} else {
			this.equipoFiltro.clear();

			for (Pokemon p : this.equipo) {
				if (p.getNombre_pokemon().toUpperCase().contains(buscador.toUpperCase())) {
					this.equipoFiltro.add(p);
				}

				if (p.getTipo1().toUpperCase().contains(buscador.toUpperCase())) {
					this.equipoFiltro.add(p);
				}

				if (p.getTipo2() != null && p.getTipo2().toUpperCase().contains(buscador.toUpperCase())) {
					this.equipoFiltro.add(p);
				}

				if (String.valueOf(p.getNivel()).contains(buscador)) {
					this.equipoFiltro.add(p);
				}

			}

			// Convierte 'equipoFiltro' a un ObservableList antes de asignarlo
			this.tabPokemon.setItems(FXCollections.observableArrayList(equipoFiltro));
		}
	}

	@FXML
	void cambiarNombre(ActionEvent event) {
	    Pokemon pokSeleccionado = tabPokemon.getSelectionModel().getSelectedItem();

	    if (pokSeleccionado == null) {
	        JOptionPane.showMessageDialog(null, "Error: Primero selecciona el Pokémon de la caja");
	        return;
	    }

	    String nombreAntiguo = pokSeleccionado.getNombre_pokemon();

	    TextInputDialog dialogo = new TextInputDialog(nombreAntiguo);
	    dialogo.setTitle("Cambio de nombre");
	    dialogo.setHeaderText("Introduce el nuevo nombre:");
	    dialogo.setContentText("Nombre: ");

	    Optional<String> nombreNuevo = dialogo.showAndWait();

	    if (nombreNuevo.isPresent()) {
	        boolean actualizado = PokemonBD.cambiarNombre(pokSeleccionado, nombreNuevo);

	        if (actualizado) {
	            pokSeleccionado.setNombre_pokemon(nombreNuevo.get());
	            JOptionPane.showMessageDialog(null, "Cambio realizado correctamente al Pokémon " + nombreAntiguo, "Cambio Realizado", JOptionPane.INFORMATION_MESSAGE);
	            tabPokemon.refresh();
	        } else {
	            JOptionPane.showMessageDialog(null, "Error al actualizar el nombre en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }
	}

	@FXML
	void cambiarPor(ActionEvent event) {
	    try {
	        // Verificar que se haya seleccionado un Pokémon en la tabla
	        Pokemon pokemonSeleccionado = tabPokemon.getSelectionModel().getSelectedItem();
	        if (pokemonSeleccionado == null) {
	            JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún Pokémon de la caja.", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

	        // Cargar el archivo FXML de la ventana emergente
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/cambiarPor.fxml"));
	        Parent root = loader.load();

	        // Obtener el controlador de la ventana emergente
	        CambiarPorController cambiarPorController = loader.getController();

	        // Inicializar el controlador con los datos necesarios
	        Stage nuevaStage = new Stage();
	        cambiarPorController.init(entrenador, nuevaStage, equipoController, this, pokemonSeleccionado);

	        // Configurar la ventana emergente
	        Scene scene = new Scene(root);
	        nuevaStage.setTitle("Cambiar Pokémon");
	        nuevaStage.setScene(scene);
	        nuevaStage.initOwner(stage);
	        nuevaStage.show();

	    } catch (IOException e) {
	        System.err.println("Error al cargar la ventana emergente para cambiar Pokémon.");
	        e.printStackTrace();
	    }
	}

}
