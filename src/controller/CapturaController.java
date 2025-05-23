package controller;

import javafx.stage.Stage;
import model.Entrenador;
import model.Pokemon;
import bd.BDConecction;
import bd.PokemonBD;
import bd.MochilaBD;
import bd.MovimientoBD;
import model.Mochila;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 * Controlador para la ventana de captura de Pokémon.
 * Permite generar un Pokémon salvaje, intentar capturarlo y gestionar el número de Pokébolas.
 */
public class CapturaController {

    private Entrenador entrenador;
    private Stage stage;
    private MenuController menuController;
    private Pokemon pokemonCreado;

    private int pokebolas = 0;
    private final int ID_POKEBOLA = 8;

    @FXML
    private Button btnCapturar;

    @FXML
    private Button btnGenerar;

    @FXML
    private Button btnSalir;

    @FXML
    private ImageView imgFondo;

    @FXML
    private Label lblPokemon;

    @FXML
    private Label lblPokebolas;

    /**
     * Inicializa el controlador con el entrenador, la ventana y el menú principal.
     */
    public void init(Entrenador ent, Stage stage, MenuController menuController) {
        this.menuController = menuController;
        this.stage = stage;
        this.entrenador = ent;

        cargarPokebolas();
    }

    /**
     * Carga la cantidad de Pokébolas del entrenador desde la base de datos.
     */
    private void cargarPokebolas() {
        try (Connection conexion = BDConecction.getConnection()) {
            ArrayList<Mochila> mochila = MochilaBD.obtenerMochila(entrenador.getIdEntrenador());
            for (Mochila objeto : mochila) {
                if (objeto.getIdObjeto() == ID_POKEBOLA) {
                    pokebolas = objeto.getCantidad();
                    break;
                }
            }
            actualizarLblPokebolas();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Actualiza la etiqueta de Pokébolas en la interfaz.
     */
    private void actualizarLblPokebolas() {
        lblPokebolas.setText(String.valueOf(pokebolas));
        lblPokebolas.setStyle("-fx-font-size: 32px; -fx-text-fill: #ff0000;");
    }

    /**
     * Actualiza la cantidad de Pokébolas en la base de datos.
     */
    private void actualizarPokebolasBD() {
        try (Connection conexion = BDConecction.getConnection()) {
            MochilaBD.actualizarCantidad(entrenador.getIdEntrenador(), ID_POKEBOLA, pokebolas);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Cierra la ventana de captura y vuelve al menú principal.
     */
    @FXML
    void cerrarCaptura(ActionEvent event) {
        System.out.println("Se ha accionado el boton de salir");
        menuController.show();
        this.stage.close();
        System.out.println("Se ha salido de captura correctamente");
    }

    /**
     * Genera un Pokémon salvaje para intentar capturarlo.
     */
    @FXML
    void generarPokemon(ActionEvent event) {
        System.out.println("Se ha accionado el boton de generar");

        try (Connection conexion = BDConecction.getConnection()) {
            pokemonCreado = PokemonBD.generarPokemonCaptura(entrenador.getIdEntrenador(), conexion);

            if (pokemonCreado != null) {
                String archivo = pokemonCreado.getNum_pokedex() + ".png";
                String ruta = "multimedia/imagenes/delanteras/" + archivo;
                File file = new File(ruta);

                if (!file.exists()) {
                    System.out.println("No se encontró la imagen: " + archivo);
                    lblPokemon.setText("No se pudo cargar " + archivo);
                    lblPokemon.setGraphic(null);
                } else {
                    Image imagen = new Image(file.toURI().toString());
                    ImageView imageView = new ImageView(imagen);
                    imageView.setFitWidth(120);
                    imageView.setFitHeight(120);
                    imageView.setPreserveRatio(true);

                    lblPokemon.setText("");
                    lblPokemon.setGraphic(imageView);
                }
            } else {
                lblPokemon.setText("Error al generar el pokemon.");
            }

        } catch (Exception e) {
            System.err.println("Error generando Pokemon: " + e.getMessage());
            e.printStackTrace();
            lblPokemon.setText("Error al conectar con la Base de Datos");
        }
    }

    /**
     * Intenta capturar el Pokémon generado, consume una Pokébola y lo guarda si tiene éxito.
     */
    @FXML
    void capturarPokemon(ActionEvent event) {
        System.out.println("Se ha accionado el boton de capturar");

        if (pokebolas <= 0) {
            JOptionPane.showMessageDialog(null, "Te has quedado sin Pokeballs", "Sin Pokeballs",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (pokemonCreado != null) {

            Random rd = new Random();
            int prob = rd.nextInt(3);

            if (prob == 0) {
                JOptionPane.showMessageDialog(null, "El pokemon se ha salido de la bola", "Fallaste", 1);
            } else {

                int respuesta = JOptionPane.showConfirmDialog(null, "¿Deseas ponerle un mote a tu pokemon?",
                        "Cambio de nombre", JOptionPane.YES_NO_OPTION);

                if (respuesta == JOptionPane.YES_OPTION) {

                    TextInputDialog dialogo = new TextInputDialog(pokemonCreado.getNombre_pokemon());
                    dialogo.setTitle("Captura Exitosa");
                    dialogo.setHeaderText("Has capturado un " + pokemonCreado.getNombre_pokemon());
                    dialogo.setContentText("¿Deseas cambiarle el nombre a tu pokemon?");
                    Optional<String> nuevoNombre = dialogo.showAndWait();

                    nuevoNombre.ifPresent(nombre -> pokemonCreado.setNombre_pokemon(nombre));
                }

                try (Connection conexion1 = BDConecction.getConnection()) {
                    PokemonBD.guardarPokemonCaptura(pokemonCreado, conexion1);
                    MovimientoBD.otorgarPrimerMovimiento(conexion1, entrenador, pokemonCreado);

                    if (pokemonCreado.getNivel() == 25) {
                        MovimientoBD.asignarMovimientosAleatorios(conexion1, pokemonCreado.getId_pokemon(),
                                pokemonCreado.getTipo1(), pokemonCreado.getTipo2(), 6, entrenador.getIdEntrenador());
                    } else if (pokemonCreado.getNivel() == 50) {
                        MovimientoBD.asignarMovimientosAleatorios(conexion1, pokemonCreado.getId_pokemon(),
                                pokemonCreado.getTipo1(), pokemonCreado.getTipo2(), 15, entrenador.getIdEntrenador());
                    }

                    System.out.println("Pokemon guardado en la BBDD");
                    lblPokemon.setText("Has capturado un : " + pokemonCreado.getNombre_pokemon());
                    JOptionPane.showMessageDialog(null,
                            "El pokemon ha sido capturado correctamente, está situado en la caja", "Capturado", 1);
                    lblPokemon.setGraphic(null);
                    pokemonCreado = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            pokebolas--;
            actualizarPokebolasBD();
            actualizarLblPokebolas();

            if (pokebolas == 0) {
                JOptionPane.showMessageDialog(null, "Te has quedado sin pokebolas", "Sin pokebolas",
                        JOptionPane.WARNING_MESSAGE);
            }

        } else {
            lblPokemon.setText("Primero tienes que generar un Pokemon.");
        }

        lblPokemon.setGraphic(null);
        lblPokemon.setText("");
    }
}