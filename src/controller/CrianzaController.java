package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Entrenador;
import model.Pokemon;

/**
 * Controlador para la ventana de crianza de Pokémon.
 * Permite seleccionar dos Pokémon compatibles y obtener un nuevo Pokémon hijo.
 */
public class CrianzaController {

    @FXML
    private Button btnCerrarCrianza;

    @FXML
    private Button btnEstadisticas;

    @FXML
    private Button btnSeleccionarPokemon;

    @FXML
    private Button btnSeleccionarPokemon2;

    @FXML
    private TableColumn<Pokemon, Integer> colFertilidad;

    @FXML
    private TableColumn<Pokemon, String> colNombre;

    @FXML
    private TableColumn<Pokemon, Character> colSexo;

    @FXML
    private ImageView imgPokemon1;

    @FXML
    private ImageView imgPokemon2;

    @FXML
    private TableView<Pokemon> tabCaja;

    @FXML
    private ImageView imgHuevo;

    @FXML
    private Button btnAbrir;

    private Entrenador entrenador;
    private Stage stage;
    private MenuController menuController;
    private Pokemon pokemonPadre1 = null;
    private Pokemon pokemonPadre2 = null;

    @FXML
    private Button btnSalir;

    /**
     * Inicializa el controlador con el entrenador, la ventana y el menú principal.
     */
    public void init(Entrenador ent, Stage stage, MenuController menuController) {
        this.menuController = menuController;
        this.stage = stage;
        this.entrenador = ent;
        cargarTablaCaja();
    }

    /**
     * Carga la tabla con todos los Pokémon del entrenador que pueden criar.
     */
    private void cargarTablaCaja() {
        ArrayList<Pokemon> equipo = PokemonBD.obtenerTodosLosPokemons(entrenador.getIdEntrenador());
        ObservableList<Pokemon> listaEquipo = FXCollections.observableArrayList(equipo);

        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre_pokemon"));
        colFertilidad.setCellValueFactory(new PropertyValueFactory<>("fertilidad"));
        colSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));

        tabCaja.setItems(listaEquipo);
    }

    /**
     * Cierra la ventana de crianza y vuelve al menú principal.
     */
    @FXML
    void cerrarCrianza(ActionEvent event) {
        System.out.println("Se ha accionado el boton de salir");
        menuController.show();
        this.stage.close();
        System.out.println("Se ha salido de crianza correctamente");
    }

    /**
     * Selecciona el primer Pokémon padre y filtra los compatibles.
     */
    @FXML
    void seleccionarPokemon(ActionEvent event) {
        pokemonPadre1 = tabCaja.getSelectionModel().getSelectedItem();
        if (pokemonPadre1 == null) {
            JOptionPane.showMessageDialog(null, "Selecciona un Pokémon de la caja.");
            return;
        }

        // Imagen del primer Pokémon
        String rutaImagen = PokemonBD.obtenerRutaImagen(pokemonPadre1);
        imgPokemon1.setImage(new Image(rutaImagen));

        // Filtra la tabla para mostrar solo los compatibles
        ArrayList<Pokemon> compatibles;
        if (pokemonPadre1.getSexo() == 'M') {
            compatibles = PokemonBD.obtenerFemeninos(entrenador.getIdEntrenador(), pokemonPadre1);
        } else {
            compatibles = PokemonBD.obtenerMasculinos(entrenador.getIdEntrenador(), pokemonPadre1);
        }

        ObservableList<Pokemon> listaCompatibles = FXCollections.observableArrayList(compatibles);
        tabCaja.setItems(listaCompatibles);

        if (tabCaja.getItems().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay Pokémons compatibles", "Error Crianza",
                    JOptionPane.ERROR_MESSAGE);
            btnAbrir.setVisible(false);
            imgHuevo.setVisible(false);
            imgPokemon1.setImage(null);
            imgPokemon2.setImage(null);
            pokemonPadre1 = null;
            pokemonPadre2 = null;
            tabCaja.setVisible(true);
            cargarTablaCaja();
            btnSeleccionarPokemon.setVisible(true);
            return;
        }

        btnSeleccionarPokemon.setVisible(false);
        btnSeleccionarPokemon2.setVisible(true);
    }

    /**
     * Selecciona el segundo Pokémon padre y muestra el huevo.
     */
    @FXML
    void seleccionarPokemon2(ActionEvent event) {
        pokemonPadre2 = tabCaja.getSelectionModel().getSelectedItem();

        if (pokemonPadre2 == null) {
            JOptionPane.showMessageDialog(null, "Selecciona el segundo Pokémon compatible.");
            return;
        }

        // Imagen del segundo Pokémon
        String rutaImagen2 = PokemonBD.obtenerRutaImagen(pokemonPadre2);
        imgPokemon2.setImage(new Image(rutaImagen2));

        // Oculta la tabla y muestra el huevo y el botón de abrir
        tabCaja.setVisible(false);
        btnSeleccionarPokemon2.setVisible(false);
        btnAbrir.setVisible(true);
        imgHuevo.setVisible(true);
    }

    /**
     * Abre el huevo y genera el Pokémon hijo, restando fertilidad a los padres.
     */
    @FXML
    void abrirHuevo(ActionEvent event) throws SQLException {
        if (pokemonPadre1 == null || pokemonPadre2 == null) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar dos Pokémon compatibles.");
            return;
        }

        // Resta uno a la fertilidad de cada padre
        pokemonPadre1.setFertilidad(pokemonPadre1.getFertilidad() - 1);
        pokemonPadre2.setFertilidad(pokemonPadre2.getFertilidad() - 1);

        // Crea el hijo con las mejores características
        Pokemon hijo = PokemonBD.criarPokemonHijo(pokemonPadre1, pokemonPadre2);

        // Guarda el hijo en la caja (EQUIPO = 3)
        try (Connection con = BDConecction.getConnection()) {
            PokemonBD.guardarPokemonCaptura(hijo, con);
            PokemonBD.restarFertilidad(pokemonPadre1, con);
            PokemonBD.restarFertilidad(pokemonPadre2, con);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar el Pokémon hijo.");
            e.printStackTrace();
            return;
        }

        JOptionPane.showMessageDialog(null, "¡Ha nacido un nuevo Pokémon!\n" +
                "Nombre: " + hijo.getNombre_pokemon() +
                "\nNivel: 1\nTipo: " + hijo.getTipo1() +
                (hijo.getTipo2() != null ? " / " + hijo.getTipo2() : "") +
                "\nAtaque: " + hijo.getAtaque() +
                "\nDefensa: " + hijo.getDefensa() +
                "\nAtaque Esp: " + hijo.getAtaque_especial() +
                "\nDefensa Esp: " + hijo.getDefensa_especial() +
                "\nVelocidad: " + hijo.getVelocidad() +
                "\nEl hijo tiene el ataque Placaje por defecto."
        );

        // Reinicia la vista
        btnAbrir.setVisible(false);
        imgHuevo.setVisible(false);
        imgPokemon1.setImage(null);
        imgPokemon2.setImage(null);
        pokemonPadre1 = null;
        pokemonPadre2 = null;
        tabCaja.setVisible(true);
        cargarTablaCaja();
        btnSeleccionarPokemon.setVisible(true);
    }

    /**
     * Muestra la ventana de estadísticas del Pokémon seleccionado.
     */
    @FXML
    void verEstadisticas(ActionEvent event) {
        Pokemon pokSeleccionado = tabCaja.getSelectionModel().getSelectedItem();

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