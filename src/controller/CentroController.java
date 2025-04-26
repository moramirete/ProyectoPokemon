package controller;

import javafx.stage.Stage;
import model.Entrenador;
import model.Mochila;
import model.Objeto;
import model.ObjetoEnMochila;
import model.Pokemon;
import javafx.scene.control.ProgressBar;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import bd.MochilaBD;
import bd.ObjetoBD;
import bd.PokemonBD;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class CentroController {
	private Entrenador entrenador;
	private Stage stage;
	private MenuController menuController;

	@FXML
	private Button btnRecuperar;

	@FXML
	private Button btnSalir;

	@FXML
	private TableColumn<Pokemon, String> colPokemon;

	@FXML
	private TableColumn<Pokemon, Integer> colVida;

	@FXML
	private ImageView imagenFondo;

	@FXML
	private Label lblPokedollares;

	@FXML
	private TableView<Pokemon> tableCentro;

	@FXML
	private TextField txtPokedolares;

	public void init(Entrenador ent, Stage stage, MenuController menuController) {
		this.menuController = menuController;
		this.stage = stage;
		this.entrenador = ent;

		lblPokedollares.textProperty().bind(entrenador.pokedolaresProperty().asString());

		cargarEquipo();
	}

	public void initialize() {
		colPokemon.setCellValueFactory(new PropertyValueFactory<>("nombre_pokemon"));
		colVida.setCellValueFactory(new PropertyValueFactory<>("vitalidad"));

		colVida.setCellFactory(tc -> new TableCell<Pokemon, Integer>() {
			private final ProgressBar progressBar = new ProgressBar();
			private final Label label = new Label();

			@Override
			protected void updateItem(Integer s, boolean empty) {
				super.updateItem(s, empty);
				if (empty || s == null) {
					setGraphic(null);
				} else {
					Pokemon pokemon = getTableView().getItems().get(getIndex());
					int vidaActual = pokemon.getVitalidad();
					int vidaMaxima = pokemon.getVitalidadMax();
			
					actualizarColorBarraVida(progressBar, label, vidaActual, vidaMaxima);
			
					HBox hbox = new HBox(10, progressBar, label);
					hbox.setSpacing(10);
					setGraphic(hbox);
				}
			}
		});

		if (entrenador != null) {
			cargarEquipo();
		}
	}

	private void actualizarColorBarraVida(ProgressBar barra, Label label, double vidaActual, double vidaMaxima) {
		double porcentaje = vidaActual / vidaMaxima;
		barra.setProgress(porcentaje);
		barra.setPrefWidth(200); // Establece el ancho de la barra

		String color;

		if (porcentaje > 0.5) {
			color = "#228B22";
		} else if (porcentaje > 0.2) {
			color = "yellow";
		} else {
			color = "red";
		}

		barra.setStyle("-fx-accent: " + color + ";"); // Establece el color de la barra
		label.setText((int) vidaActual + "/" + (int) vidaMaxima); // Muestra la vida actual y máxima
	}

	private void cargarEquipo() {
		ArrayList<Pokemon> equipo = PokemonBD.obtenerEquipo(entrenador.getIdEntrenador());
		ObservableList<Pokemon> lista = FXCollections.observableArrayList(equipo);

		for (Pokemon pokemon : lista) {
			System.out.println("Pokemon: " + pokemon.getNombre_pokemon() +
							   ", Vitalidad: " + pokemon.getVitalidad() +
							   ", Vitalidad Max: " + pokemon.getVitalidadMax());
		}

		tableCentro.setItems(lista);
	}

	@FXML
	void recuperarPokemon(ActionEvent event) {
		// Obtener el objeto seleccionado en la tienda
		Pokemon pokSeleccionado = tableCentro.getSelectionModel().getSelectedItem();

		if (pokSeleccionado == null) {
			JOptionPane.showMessageDialog(null, "Selecciona primero un pokemon", "Error", 0);
			return;
		}

		if (pokSeleccionado.getVitalidad() == pokSeleccionado.getVitalidadMax()) {
			JOptionPane.showMessageDialog(null, "El pokemon ya tiene la vida maxima", "Error", 0);
			return;
		}

		 // Intentar curar el Pokémon en la base de datos
		 if (PokemonBD.curarPokemon(entrenador.getIdEntrenador(), pokSeleccionado.getId_pokemon())) {
			 
			// Actualizar la vitalidad del Pokémon en la lista observable
			pokSeleccionado.setVitalidad(pokSeleccionado.getVitalidadMax());
	
			// Refrescar la tabla para reflejar los cambios y lanzar el mensaje
			tableCentro.refresh();
			JOptionPane.showMessageDialog(null, "El pokemon " + pokSeleccionado.getNombre_pokemon() + " se ha curado correctamente", 
					 "Curación", 1);
			
		} else {
			JOptionPane.showMessageDialog(null, "No se pudo curar al pokemon", "Error", 0);
		}
	}

	@FXML
	void volverMenu(ActionEvent event) {
		menuController.show();
		this.stage.close();
	}
}
