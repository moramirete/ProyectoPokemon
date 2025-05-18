package controller;

import java.util.ArrayList;
import java.util.List;

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

/**
 * Controlador para la ventana de cambio de Pokémon durante entrenamiento o combate.
 * Permite seleccionar un Pokémon del equipo para cambiarlo por el principal.
 */
public class cambiarPokemonCombateController {

    @FXML
    private Button btnCambiarPor;

    @FXML
    private Button btnCambiarPor1;

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
    private CombateController combateController;
    private List<Pokemon> equipo;

    /**
     * Inicializa el controlador para el contexto de entrenamiento.
     */
    public void init(Entrenador entrenador, Pokemon actualPrincipal, EntrenamientoController controller) {
        this.entrenador = entrenador;
        this.actualPrincipal = actualPrincipal;
        this.entrenamientoController = controller;

        btnCambiarPor.setVisible(true);
        btnCambiarPor1.setVisible(false);

        cargarEquipo();
    }

    /**
     * Inicializa el controlador para el contexto de combate.
     */
    public void init(Entrenador entrenador, Pokemon actualPrincipal, CombateController controller, List<Pokemon> equipo) {
        this.entrenador = entrenador;
        this.actualPrincipal = actualPrincipal;
        this.combateController = controller;
        this.equipo = equipo;

        btnCambiarPor.setVisible(false);
        btnCambiarPor1.setVisible(true);

        cargarEquipo();
    }

    /**
     * Carga el equipo de Pokémon en la tabla, filtrando según el contexto.
     */
    private void cargarEquipo() {
        ArrayList<Pokemon> equipo = PokemonBD.obtenerEquipo(entrenador.getIdEntrenador());

        // FILTRO: solo si estamos en combate (btnCambiarPor1 está visible)
        if (btnCambiarPor1.isVisible()) {
            equipo.removeIf(p -> p.getVitalidadOBJ() <= 0 || p.getId_pokemon() == actualPrincipal.getId_pokemon());
        }

        ObservableList<Pokemon> listaEquipo = FXCollections.observableArrayList(equipo);

        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre_pokemon"));
        colNivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo1"));
        colTipo2.setCellValueFactory(new PropertyValueFactory<>("tipo2"));
        colPosicion.setCellValueFactory(new PropertyValueFactory<>("equipo"));

        tabPokemon.setItems(listaEquipo);
    }

    /**
     * Cambia el Pokémon principal por el seleccionado (entrenamiento).
     */
    @FXML
    void cambiarPor(ActionEvent event) {
        Pokemon seleccionado = tabPokemon.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            JOptionPane.showMessageDialog(null, "Selecciona un Pokémon.");
            return;
        }

        boolean cambio = PokemonBD.cambiarPrincipalConEquipo(entrenador.getIdEntrenador(), seleccionado, actualPrincipal);

        if (cambio) {
            entrenamientoController.recargarConNuevoPokemon(seleccionado.getId_pokemon());
            cerrarVentana(event);
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo cambiar el Pokémon.");
        }
    }

    /**
     * Cambia el Pokémon principal por el seleccionado (combate).
     */
    @FXML
    void cambiarPorCombate(ActionEvent event) {
        Pokemon seleccionado = tabPokemon.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            JOptionPane.showMessageDialog(null, "Selecciona un Pokémon.");
            return;
        }

        Pokemon anteriorPrincipal = actualPrincipal;
        actualPrincipal = seleccionado;

        equipo.remove(seleccionado);
        equipo.add(anteriorPrincipal);

        if (combateController != null) {
            combateController.recargarConNuevoPokemon(actualPrincipal.getId_pokemon());
        } else {
            System.out.println("El controlador de combate no está inicializado.");
        }

        cerrarVentana(event);
    }

    /**
     * Vuelve a la ventana anterior.
     */
    @FXML
    void volveratras(ActionEvent event) {
        cerrarVentana(event);
    }

    /**
     * Cierra la ventana actual.
     */
    @FXML
    void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) btnVolver.getScene().getWindow();
        stage.close();
    }
}