package controller;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Label;
import java.io.IOException;
import java.util.List;

import bd.PokemonBD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Entrenador;
import model.Pokemon;

/**
 * Controlador para la ventana de gestión del equipo Pokémon.
 * Permite visualizar el equipo, cambiar el principal, abrir la caja y ver estadísticas.
 */
public class EquipoController {

    private Entrenador entrenador;
    private Stage stage;
    private MenuController menuController;

    public void init(Entrenador ent, Stage stage, MenuController menuController) {
        this.menuController = menuController;
        this.stage = stage;
        this.entrenador = ent;

        inicializarEquipo();
    }

    @FXML
    private ImageView Imgpoke1;
    @FXML
    private ImageView Imgpoke2;
    @FXML
    private ImageView Imgpoke3;
    @FXML
    private ImageView Imgpoke4;
    @FXML
    private ImageView Imgpoke5;
    @FXML
    private ImageView Imgpoke6;

    @FXML
    private Label NivelPoke1;
    @FXML
    private Label NivelPoke2;
    @FXML
    private Label NivelPoke3;
    @FXML
    private Label NivelPoke4;
    @FXML
    private Label NivelPoke5;
    @FXML
    private Label NivelPoke6;

    @FXML
    private Label NombrePoke1;
    @FXML
    private Label NombrePoke2;
    @FXML
    private Label NombrePoke3;
    @FXML
    private Label NombrePoke4;
    @FXML
    private Label NombrePoke5;
    @FXML
    private Label NombrePoke6;

    @FXML
    private ProgressBar barraVida1;
    @FXML
    private ProgressBar barraVida2;
    @FXML
    private ProgressBar barraVida3;
    @FXML
    private ProgressBar barraVida4;
    @FXML
    private ProgressBar barraVida5;
    @FXML
    private ProgressBar barraVida6;

    @FXML
    private Button btnCambiar;
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnEstadisticas;
    @FXML
    private Button btnPokemonPrincipal;

    @FXML
    private ImageView imgfondo;

    @FXML
    private Label lblVida1;
    @FXML
    private Label lblVida2;
    @FXML
    private Label lblVida3;
    @FXML
    private Label lblVida4;
    @FXML
    private Label lblVida5;
    @FXML
    private Label lblVida6;

    /**
     * Cierra la ventana del equipo y vuelve al menú principal.
     */
    @FXML
    void cerrarEquipo(ActionEvent event) {
        menuController.show();
        this.stage.close();
    }

    /**
     * Inicializa y muestra los datos del equipo Pokémon en la interfaz.
     */
    public void inicializarEquipo() {
        List<Pokemon> equipo = PokemonBD.obtenerEquipo(entrenador.getIdEntrenador());

        // Ordena para que el principal esté primero
        equipo.sort((p1, p2) -> {
            if (p1.getEquipo() == 1) return -1;
            if (p2.getEquipo() == 1) return 1;
            return 0;
        });

        ImageView[] imagenes = { Imgpoke1, Imgpoke2, Imgpoke3, Imgpoke4, Imgpoke5, Imgpoke6 };
        Label[] nombres = { NombrePoke1, NombrePoke2, NombrePoke3, NombrePoke4, NombrePoke5, NombrePoke6 };
        Label[] niveles = { NivelPoke1, NivelPoke2, NivelPoke3, NivelPoke4, NivelPoke5, NivelPoke6 };
        ProgressBar[] barras = { barraVida1, barraVida2, barraVida3, barraVida4, barraVida5, barraVida6 };
        Label[] etiquetasVida = { lblVida1, lblVida2, lblVida3, lblVida4, lblVida5, lblVida6 };

        for (int i = 0; i < imagenes.length; i++) {
            if (i < equipo.size()) {
                Pokemon p = equipo.get(i);
                String rutaImagen = PokemonBD.obtenerRutaImagen(p);

                try {
                    Image imagen = new Image(rutaImagen);
                    imagenes[i].setImage(imagen);
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid image URL: " + rutaImagen);
                    imagenes[i].setImage(new Image("file:/imagenes/placeholder.png"));
                }

                nombres[i].setText(p.getNombre_pokemon());
                niveles[i].setText("Nivel: " + p.getNivel());
                actualizarBarraVida(barras[i], etiquetasVida[i], p.getVitalidadOBJ(), p.getVitalidadMaxOBJ());
            } else {
                imagenes[i].setImage(null);
                nombres[i].setText("");
                niveles[i].setText("");
                barras[i].setProgress(0);
                etiquetasVida[i].setText("");
            }
        }
    }

    /**
     * Actualiza la barra de vida y la etiqueta correspondiente.
     */
    public void actualizarBarraVida(ProgressBar barra, Label label, double vidaActual, double vidaMaxima) {
        double porcentaje = vidaActual / vidaMaxima;
        barra.setProgress(porcentaje);

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
     * Abre la ventana de la caja de Pokémon.
     */
    @FXML
    void abrirCaja(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/caja.fxml"));
            Parent root = loader.load();

            CajaController cajaController = loader.getController();
            Stage nuevaStage = new Stage();
            Scene scene = new Scene(root);

            cajaController.init(entrenador, nuevaStage, this);

            nuevaStage.setTitle("Pokémon Super Nenes - Caja");
            nuevaStage.setScene(scene);
            nuevaStage.getIcons().add(new Image("/imagenes/lossupernenes.png"));
            nuevaStage.show();

            stage.show();

        } catch (IOException e) {
            System.out.println("Fallo en el archivo FXML.");
            e.printStackTrace();
        }
    }

    /**
     * Abre la ventana para cambiar el Pokémon principal.
     */
    @FXML
    void abrirEdicion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/cambiarPokemonPrincipal.fxml"));
            Parent root = loader.load();

            CambiarPokemonPrincipalController cambiarPok = loader.getController();
            Stage nuevaStage = new Stage();
            Scene scene = new Scene(root);

            cambiarPok.init(entrenador, nuevaStage, this);

            nuevaStage.setTitle("Pokémon Super Nenes - Cambio Pokémon Principal");
            nuevaStage.setScene(scene);
            nuevaStage.show();
        } catch (IOException e) {
            System.out.println("Fallo en el archivo FXML.");
            e.printStackTrace();
        }
    }

    /**
     * Abre la ventana para ver las estadísticas del equipo.
     */
    @FXML
    void abrirEstadisticas(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/verEstadisticasEquipo.fxml"));
            Parent root = loader.load();

            VerEstadisticasEquipoController cambiarPok = loader.getController();
            Stage nuevaStage = new Stage();
            Scene scene = new Scene(root);

            cambiarPok.init(entrenador, nuevaStage, this);

            nuevaStage.setTitle("Pokémon Super Nenes - Ver Estadisticas Equipo");
            nuevaStage.setScene(scene);
            nuevaStage.show();
        } catch (IOException e) {
            System.out.println("Fallo en el archivo FXML.");
            e.printStackTrace();
        }
    }

    /**
     * Muestra la ventana del equipo.
     */
    public void show() {
        stage.show();
    }
}