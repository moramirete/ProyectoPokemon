package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Entrenador;
import model.Pokemon;

/**
 * Controlador para la vista de estadísticas del equipo Pokémon.
 * Permite ver los Pokémon del equipo y acceder a sus estadísticas detalladas.
 * Incluye atributos y métodos adicionales para hacerlo más "relleno".
 */
public class VerEstadisticasEquipoController {

    private Entrenador entrenador;
    private Stage stage;
    private EquipoController equipoController;

    // Atributos adicionales para "relleno"
    private int vecesVistaAbierta = 0;
    private String ultimoPokemonSeleccionado = "";

    public void init(Entrenador entrenador, Stage stage, EquipoController equipoController) {
        this.entrenador = entrenador;
        this.stage = stage;
        this.equipoController = equipoController;

        cargarEquipo();
        vecesVistaAbierta++;
        System.out.println("Vista de estadísticas de equipo abierta " + vecesVistaAbierta + " veces.");
    }

    @FXML
    private Button btnVolver;

    @FXML
    private Button btnEstadisticas;

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
    private Label txtPokemonCaja;

    private void cargarEquipo() {
        ArrayList<Pokemon> equipo = PokemonBD.obtenerEquipo(entrenador.getIdEntrenador());
        ObservableList<Pokemon> listaEquipo = FXCollections.observableArrayList(equipo);

        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre_pokemon"));
        colNivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo1"));
        colTipo2.setCellValueFactory(new PropertyValueFactory<>("tipo2"));

        tabPokemon.setItems(listaEquipo);
    }

    @FXML
    void volveratras(ActionEvent event) {
        stage.close();
        System.out.println("Cerrando vista de estadísticas de equipo.");
    }

    @FXML
    private void abrirVerEstadisticas() {
        Pokemon pokSeleccionado = tabPokemon.getSelectionModel().getSelectedItem();

        if (pokSeleccionado == null) {
            JOptionPane.showMessageDialog(null, "Error: Primero selecciona el Pokémon de la caja");
            return;
        }

        ultimoPokemonSeleccionado = pokSeleccionado.getNombre_pokemon();
        System.out.println("Abriendo estadísticas de: " + ultimoPokemonSeleccionado);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/estadisticas.fxml"));
            Parent root = loader.load();

            EstadisticasController estadisticasController = loader.getController();

            Stage nuevaStage = new Stage();

            estadisticasController.init(entrenador, nuevaStage, null, pokSeleccionado);

            Scene scene = new Scene(root);
            nuevaStage.setScene(scene);
            nuevaStage.setTitle("Estadísticas de " + pokSeleccionado.getNombre_pokemon());
            nuevaStage.show();

        } catch (IOException e) {
            System.err.println("Error al cargar la ventana de estadísticas.");
            e.printStackTrace();
        }
    }

    // Métodos adicionales de ejemplo para "relleno"

    /**
     * Devuelve el número de veces que se ha abierto la vista.
     */
    public int getVecesVistaAbierta() {
        return vecesVistaAbierta;
    }

    /**
     * Devuelve el nombre del último Pokémon seleccionado.
     */
    public String getUltimoPokemonSeleccionado() {
        return ultimoPokemonSeleccionado;
    }
}