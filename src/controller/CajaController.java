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

/**
 * Controlador para la gestión de la caja de Pokémon.
 * Permite visualizar, buscar, cambiar nombre y cambiar Pokémon entre la caja y el equipo.
 */
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
    private TextField txtBuscador;

    /**
     * Inicializa el controlador con el entrenador, la ventana y el controlador del equipo.
     */
    public void init(Entrenador entrenador, Stage stage, EquipoController equipoController) {
        this.entrenador = entrenador;
        this.stage = stage;
        this.equipoController = equipoController;

        // Inicializa las listas después de que 'entrenador' esté configurado
        this.equipo = PokemonBD.obtenerCaja(entrenador.getIdEntrenador());
        this.equipoFiltro = new ArrayList<>(equipo);

        cargarCaja();
    }

    /**
     * Vuelve a la ventana anterior (equipo).
     */
    @FXML
    void volveratras(ActionEvent event) {
        equipoController.show();
        this.stage.close();
    }

    /**
     * Carga la caja de Pokémon en la tabla.
     */
    private void cargarCaja() {
        ObservableList<Pokemon> lista = FXCollections.observableArrayList(equipo);
        ObservableList<Pokemon> listaFiltro = FXCollections.observableArrayList(equipo);

        for (Pokemon pokemon : lista) {
            System.out.println("Pokemon: " + pokemon.getNombre_pokemon() + ", Vitalidad: " + pokemon.getVitalidad()
                    + ", Vitalidad Max: " + pokemon.getVitalidadMax());
        }

        tabPokemon.setItems(lista);
    }

    /**
     * Inicializa las columnas de la tabla y carga la caja si el entrenador está definido.
     */
    public void initialize() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre_pokemon"));
        colNivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo1"));
        colTipo2.setCellValueFactory(new PropertyValueFactory<>("tipo2"));

        if (entrenador != null) {
            cargarCaja();
        }
    }

    /**
     * Filtra la lista de Pokémon de la caja según el texto introducido en el buscador.
     */
    @FXML
    void buscarPokemon(KeyEvent event) {
        String buscador = this.txtBuscador.getText();

        if (buscador.isEmpty()) {
            this.tabPokemon.setItems(FXCollections.observableArrayList(equipo));
        } else {
            this.equipoFiltro.clear();

            for (Pokemon p : this.equipo) {
                if (p.getNombre_pokemon().toUpperCase().contains(buscador.toUpperCase())) {
                    this.equipoFiltro.add(p);
                } else if (p.getTipo1().toUpperCase().contains(buscador.toUpperCase())) {
                    this.equipoFiltro.add(p);
                } else if (p.getTipo2() != null && p.getTipo2().toUpperCase().contains(buscador.toUpperCase())) {
                    this.equipoFiltro.add(p);
                } else if (String.valueOf(p.getNivel()).contains(buscador)) {
                    this.equipoFiltro.add(p);
                }
            }

            this.tabPokemon.setItems(FXCollections.observableArrayList(equipoFiltro));
        }
    }

    /**
     * Permite cambiar el nombre del Pokémon seleccionado en la caja.
     */
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
                JOptionPane.showMessageDialog(null, "Cambio realizado correctamente al Pokémon " + nombreAntiguo,
                        "Cambio Realizado", JOptionPane.INFORMATION_MESSAGE);
                tabPokemon.refresh();
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar el nombre en la base de datos.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Abre la ventana para cambiar el Pokémon seleccionado por otro del equipo.
     */
    @FXML
    void cambiarPor(ActionEvent event) {
        try {
            Pokemon pokemonSeleccionado = tabPokemon.getSelectionModel().getSelectedItem();
            if (pokemonSeleccionado == null) {
                JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún Pokémon de la caja.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/cambiarPor.fxml"));
            Parent root = loader.load();

            CambiarPorController cambiarPorController = loader.getController();

            Stage nuevaStage = new Stage();
            cambiarPorController.init(entrenador, nuevaStage, equipoController, this, pokemonSeleccionado);

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

    /**
     * Muestra la ventana de estadísticas del Pokémon seleccionado.
     */
    @FXML
    void verEstadisticas(ActionEvent event) {
        Pokemon pokSeleccionado = tabPokemon.getSelectionModel().getSelectedItem();

        if (pokSeleccionado == null) {
            JOptionPane.showMessageDialog(null, "Error: Primero selecciona el Pokémon de la caja");
            return;
        }

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
}