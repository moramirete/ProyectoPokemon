package controller;

import java.io.IOException;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Entrenador;

/**
 * Controlador del menú principal de la aplicación Pokémon Super Nenes.
 * Gestiona la navegación hacia las distintas secciones del juego como Captura,
 * Combate, Crianza, etc., así como el cierre de sesión y salida del programa.
 * Incluye atributos y métodos adicionales para hacerlo más "relleno".
 */
public class MenuController {

    private Entrenador entrenador;
    private Stage stage;
    private LoginController loginController;

    // Atributos adicionales para "relleno"
    private int vecesMenuAbierto = 0;
    private String mensajeBienvenida = "¡Bienvenido al menú principal de Pokémon Super Nenes!";
    private String ultimaSeccionAbierta = "";

    @FXML
    private Button btnCaptura;
    @FXML
    private Button btnCentroPokemon;
    @FXML
    private Button btnCombate;
    @FXML
    private Button btnCrianza;
    @FXML
    private Button btnEntrenamiento;
    @FXML
    private Button btnEquipo;
    @FXML
    private Button btnMochila;
    @FXML
    private Button btnTienda;
    @FXML
    private Button btnSalir;

    @FXML
    private ImageView btnSalir1;
    @FXML
    private ImageView imagenFondo;
    @FXML
    private ImageView imagenLogo;

    @FXML
    private ImageView imgBocadillo;

    @FXML
    private Label lblJugador;
    @FXML
    private Label lblPokedollares;
    @FXML
    private Label plantillaPokeDolares;
    @FXML
    private Label plantillaUsuario;

    @FXML
    void abrirCaptura(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/captura.fxml"));
            Parent root = loader.load();

            CapturaController capturaController = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setTitle("Pokémon Super Nenes - Captura");
            stage.setScene(scene);
            stage.getIcons().add(new Image("/imagenes/lossupernenes.png"));
            capturaController.init(entrenador, stage, this);

            stage.show();
            this.stage.close();

            ultimaSeccionAbierta = "Captura";
            System.out.println("Entrando a captura");

        } catch (IOException e) {
            System.out.println("Falla en la carga del archivo FXML.");
            e.printStackTrace();
        }
    }

    @FXML
    void abrirCentro(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/centro.fxml"));
            Parent root = loader.load();

            CentroController centroController = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setTitle("Pokémon Super Nenes - Centro Pokemon");
            stage.setScene(scene);
            stage.getIcons().add(new Image("/imagenes/lossupernenes.png"));
            centroController.init(entrenador, stage, this);

            stage.show();
            this.stage.close();

            ultimaSeccionAbierta = "Centro Pokémon";
            System.out.println("Entrando a centro");

        } catch (IOException e) {
            System.out.println("Falla en la carga del archivo FXML.");
            e.printStackTrace();
        }
    }

    @FXML
    void abrirCombate(ActionEvent event) {
        if (entrenador.getEquipo().size() == 1) {
            JOptionPane.showMessageDialog(null,
                    "Para combatir, primero tienes que tener en tu equipo al menos a 2 pokemons", "Equipo incompleto",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/combate.fxml"));
                Parent root = loader.load();

                CombateController combateController = loader.getController();
                Scene scene = new Scene(root);
                Stage stage = new Stage();

                stage.setTitle("Pokémon Super Nenes - Combate");
                stage.setScene(scene);
                stage.getIcons().add(new Image("/imagenes/lossupernenes.png"));
                combateController.init(entrenador, stage, this);

                stage.show();
                this.stage.close();

                ultimaSeccionAbierta = "Combate";
                System.out.println("Entrando a combate");

            } catch (IOException e) {
                System.out.println("Falla en la carga del archivo FXML.");
                e.printStackTrace();
            }
        }
    }

    @FXML
    void abrirCrianza(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/crianza.fxml"));
            Parent root = loader.load();

            CrianzaController crianzaController = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setTitle("Pokémon Super Nenes - Captura");
            stage.setScene(scene);
            stage.getIcons().add(new Image("/imagenes/lossupernenes.png"));
            crianzaController.init(entrenador, stage, this);

            stage.show();
            this.stage.close();

            ultimaSeccionAbierta = "Crianza";
            System.out.println("Entrando a crianza");

        } catch (IOException e) {
            System.out.println("Falla en la carga del archivo FXML.");
            e.printStackTrace();
        }
    }

    @FXML
    void abrirEntrenamiento(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/entrenamiento.fxml"));
            Parent root = loader.load();

            EntrenamientoController entrenamientoController = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setTitle("Pokémon Super Nenes - Entrenamiento");
            stage.setScene(scene);
            stage.getIcons().add(new Image("/imagenes/lossupernenes.png"));
            entrenamientoController.init(entrenador, stage, this);

            stage.show();
            this.stage.close();

            ultimaSeccionAbierta = "Entrenamiento";
            System.out.println("Entrando a entrenamiento");

        } catch (IOException e) {
            System.out.println("Falla en la carga del archivo FXML.");
            e.printStackTrace();
        }
    }

    @FXML
    void abrirEquipo(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/equipo.fxml"));
            Parent root = loader.load();

            EquipoController equipoController = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setTitle("Pokémon Super Nenes - Equipo");
            stage.setScene(scene);
            stage.getIcons().add(new Image("/imagenes/lossupernenes.png"));
            equipoController.init(entrenador, stage, this);

            stage.show();
            this.stage.close();

            ultimaSeccionAbierta = "Equipo";
            System.out.println("Entrando a equipo");

        } catch (IOException e) {
            System.out.println("Falla en la carga del archivo FXML.");
            e.printStackTrace();
        }
    }

    @FXML
    void abrirMochila(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/mochila.fxml"));
            Parent root = loader.load();

            MochilaController mochilaController = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setTitle("Pokémon Super Nenes - Equipo");
            stage.setScene(scene);
            stage.getIcons().add(new Image("/imagenes/lossupernenes.png"));
            mochilaController.init(entrenador, stage, this);

            stage.show();
            this.stage.close();

            ultimaSeccionAbierta = "Mochila";
            System.out.println("Entrando a mochila");

        } catch (IOException e) {
            System.out.println("Falla en la carga del archivo FXML.");
            e.printStackTrace();
        }
    }

    @FXML
    void abrirTienda(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/tienda.fxml"));
            Parent root = loader.load();

            TiendaController tiendaController = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setTitle("Pokémon Super Nenes - Equipo");
            stage.setScene(scene);
            stage.getIcons().add(new Image("/imagenes/lossupernenes.png"));
            tiendaController.init(entrenador, stage, this);

            stage.show();
            this.stage.close();

            ultimaSeccionAbierta = "Tienda";
            System.out.println("Entrando a tienda");

        } catch (IOException e) {
            System.out.println("Falla en la carga del archivo FXML.");
            e.printStackTrace();
        }
    }

    @FXML
    public void cerrarMenu(ActionEvent event) {
        loginController.show();
        this.stage.close();
        System.out.println("Cerrando sesion");
        System.out.println("Abriendo login");
    }

    public void init(Entrenador ent, Stage stage, LoginController loginController) {
        this.loginController = loginController;
        this.stage = stage;
        this.entrenador = ent;

        lblJugador.setText(entrenador.getUsuario());
        lblPokedollares.textProperty().bind(entrenador.pokedolaresProperty().asString());

        vecesMenuAbierto++;
        System.out.println(mensajeBienvenida + " (Menú abierto " + vecesMenuAbierto + " veces)");
    }

    @FXML
    public void cerrarAplicacion(MouseEvent event) {
        int opcion = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres cerrar el proyectoPokemon?");

        if (opcion == JOptionPane.YES_OPTION) {
            System.out.println("Se ha elegido la opcion de si");
            Stage stage = (Stage) btnSalir.getScene().getWindow();
            stage.close();
            System.out.println("Se ha cerrado la aplicacion correctamente");
        } else {
            System.out.println("Se ha elegido la opcion de no");
        }
    }

    public void show() {
        stage.show();
    }

    // Métodos adicionales de ejemplo para "relleno"

    /**
     * Devuelve el número de veces que se ha abierto el menú.
     */
    public int getVecesMenuAbierto() {
        return vecesMenuAbierto;
    }

    /**
     * Devuelve la última sección abierta desde el menú.
     */
    public String getUltimaSeccionAbierta() {
        return ultimaSeccionAbierta;
    }

    /**
     * Cambia el mensaje de bienvenida mostrado en consola.
     */
    public void setMensajeBienvenida(String mensaje) {
        this.mensajeBienvenida = mensaje;
    }
}