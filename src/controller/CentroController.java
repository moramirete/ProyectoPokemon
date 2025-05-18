package controller;

import javafx.stage.Stage;
import model.Entrenador;
import model.Pokemon;
import javafx.scene.control.ProgressBar;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import bd.PokemonBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

/**
 * Controlador para la ventana del Centro Pokémon.
 * Permite visualizar el equipo, curar Pokémon y volver al menú principal.
 */
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
    private TableView<Pokemon> tableCentro;

    /**
     * Inicializa el controlador con el entrenador, la ventana y el menú principal.
     */
    public void init(Entrenador ent, Stage stage, MenuController menuController) {
        this.menuController = menuController;
        this.stage = stage;
        this.entrenador = ent;

        cargarEquipo();
    }

    /**
     * Inicializa las columnas de la tabla y carga el equipo si el entrenador está definido.
     */
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
                    int vidaActual = pokemon.getVitalidadOBJ();
                    int vidaMaxima = pokemon.getVitalidadMaxOBJ();

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

    /**
     * Actualiza el color y valor de la barra de vida.
     */
    private void actualizarColorBarraVida(ProgressBar barra, Label label, double vidaActual, double vidaMaxima) {
        double porcentaje = vidaActual / vidaMaxima;
        barra.setProgress(porcentaje);
        barra.setPrefWidth(200);

        String color;
        if (porcentaje > 0.5) {
            color = "#228B22";
        } else if (porcentaje > 0.2) {
            color = "yellow";
        } else {
            color = "red";
        }

        barra.setStyle("-fx-accent: " + color + ";");
        label.setText((int) vidaActual + "/" + (int) vidaMaxima);
    }

    /**
     * Carga el equipo del entrenador en la tabla.
     */
    private void cargarEquipo() {
        ArrayList<Pokemon> equipo = PokemonBD.obtenerEquipo(entrenador.getIdEntrenador());
        ObservableList<Pokemon> lista = FXCollections.observableArrayList(equipo);

        for (Pokemon pokemon : lista) {
            System.out.println("Pokemon: " + pokemon.getNombre_pokemon() + ", Vitalidad: " + pokemon.getVitalidadOBJ()
                    + ", Vitalidad Max: " + pokemon.getVitalidadMaxOBJ());
        }

        tableCentro.setItems(lista);
    }

    /**
     * Cura el Pokémon seleccionado y recupera sus PP.
     */
    @FXML
    void recuperarPokemon(ActionEvent event) {
        Pokemon pokSeleccionado = tableCentro.getSelectionModel().getSelectedItem();

        if (pokSeleccionado == null) {
            JOptionPane.showMessageDialog(null, "Selecciona primero un pokemon", "Error", 0);
            return;
        }

        if (pokSeleccionado.getVitalidadOBJ() == pokSeleccionado.getVitalidadMaxOBJ()) {
            JOptionPane.showMessageDialog(null, "El pokemon ya tiene la vida maxima", "Error", 0);
            return;
        }

        boolean vidaCurada = PokemonBD.curarPokemon(entrenador.getIdEntrenador(), pokSeleccionado.getId_pokemon());
        boolean ppRecuperados = PokemonBD.recuperarPPMovimientos(entrenador.getIdEntrenador(), pokSeleccionado.getId_pokemon());

        if (vidaCurada && ppRecuperados) {
            pokSeleccionado.setVitalidadOBJ(pokSeleccionado.getVitalidadMaxOBJ());
            pokSeleccionado.recuperarTodosLosPP();

            tableCentro.refresh();
            JOptionPane.showMessageDialog(null,
                    "El pokemon " + pokSeleccionado.getNombre_pokemon() + " se ha curado correctamente", "Curación", 1);

        } else {
            JOptionPane.showMessageDialog(null, "No se pudo curar al pokemon", "Error", 0);
        }
    }

    /**
     * Vuelve al menú principal y cierra la ventana actual.
     */
    @FXML
    void volverMenu(ActionEvent event) {
        menuController.show();
        this.stage.close();
    }
}