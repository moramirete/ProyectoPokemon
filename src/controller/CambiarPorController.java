package controller;

import javafx.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import bd.PokemonBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Entrenador;
import model.Pokemon;

/**
 * Controlador para la ventana de cambio de Pokémon entre la caja y el equipo.
 * Permite cambiar un Pokémon de la caja por uno del equipo o añadirlo directamente si hay espacio.
 */
public class CambiarPorController {

    private Entrenador entrenador;
    private Stage stage;
    private EquipoController equipoController;
    private CajaController cajaController;
    private Pokemon pokemonSeleccionadoDeCaja;

    @FXML
    private Label txtPokemonCaja;

    @FXML
    private TableColumn<Pokemon, String> colNombre;

    @FXML
    private TableColumn<Pokemon, Integer> colNivel;

    @FXML
    private Button btnCambiarPor;

    @FXML
    private Button btnAgregarEquipo;

    @FXML
    private Button btnVolver;

    @FXML
    private TableColumn<Pokemon, String> colTipo;

    @FXML
    private TableColumn<Pokemon, Integer> colTipo2;

    @FXML
    private TableColumn<Pokemon, Integer> colPosicion;

    @FXML
    private TableView<Pokemon> tabPokemon;

    /**
     * Inicializa el controlador con los datos necesarios.
     */
    public void init(Entrenador entrenador, Stage stage, EquipoController equipoController, CajaController cajaController, Pokemon pokemonSeleccionadoDeCaja) {
        this.entrenador = entrenador;
        this.stage = stage;
        this.equipoController = equipoController;
        this.cajaController = cajaController;
        this.pokemonSeleccionadoDeCaja = pokemonSeleccionadoDeCaja;

        txtPokemonCaja.setText("¿Qué quieres hacer con: " + pokemonSeleccionadoDeCaja.getNombre_pokemon() + "?");

        cargarEquipo();
        configurarBotones();
    }

    /**
     * Carga el equipo en la tabla.
     */
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

    /**
     * Configura la visibilidad de los botones según el tamaño del equipo.
     */
    private void configurarBotones() {
        if (tabPokemon.getItems().size() < 6) {
            btnAgregarEquipo.setVisible(true);
        } else {
            btnAgregarEquipo.setVisible(false);
        }
    }

    /**
     * Cambia el Pokémon de la caja por uno del equipo o por el principal.
     */
    @FXML
    void cambiarPor(ActionEvent event) {
        Pokemon pokemonSeleccionadoDeEquipo = tabPokemon.getSelectionModel().getSelectedItem();

        if (pokemonSeleccionadoDeEquipo == null) {
            JOptionPane.showMessageDialog(null, "Selecciona un Pokémon del equipo para realizar el cambio.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean esPrincipal = pokemonSeleccionadoDeEquipo.getEquipo() == 1;

        boolean cambiado;
        if (esPrincipal) {
            cambiado = PokemonBD.cambiarPokemonPrincipal(entrenador.getIdEntrenador(), pokemonSeleccionadoDeCaja, pokemonSeleccionadoDeEquipo);
        } else {
            cambiado = PokemonBD.cambiarPokemonEquipo(entrenador.getIdEntrenador(), pokemonSeleccionadoDeCaja, pokemonSeleccionadoDeEquipo);
        }

        if (cambiado) {
            System.out.println("El Pokémon ha sido cambiado correctamente.");
            equipoController.inicializarEquipo();
            stage.close();
            cajaController.volveratras(null);
        } else {
            JOptionPane.showMessageDialog(null, "Error al cambiar el Pokémon en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Añade el Pokémon de la caja al equipo si hay espacio.
     */
    @FXML
    void agregarEquipo(ActionEvent event) {
        boolean añadido = PokemonBD.añadirPokemonAlEquipo(entrenador.getIdEntrenador(), pokemonSeleccionadoDeCaja);

        if (añadido) {
            JOptionPane.showMessageDialog(null, "El Pokémon ha sido añadido al equipo correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            equipoController.inicializarEquipo();
            stage.close();
            cajaController.volveratras(null);
        } else {
            System.err.println("Error al añadir el Pokémon al equipo en la base de datos.");
        }
    }

    /**
     * Cierra la ventana actual.
     */
    @FXML
    void volveratras(ActionEvent event) {
        stage.close();
    }
}