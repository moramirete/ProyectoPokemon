package controller;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import bd.BDConecction;
import bd.MochilaBD;
import bd.ObjetoBD;
import bd.PokemonBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.Entrenador;
import model.Movimiento;
import model.ObjetoEnMochila;
import model.Pokemon;

/**
 * Controlador para la ventana de estadísticas de un Pokémon.
 * Permite ver información, movimientos y objetos equipados, así como cambiar movimientos y reproducir el grito.
 * Además, muestra información adicional y ejemplos de funcionalidades ampliadas.
 */
public class EstadisticasController {

    private Entrenador entrenador;
    private Stage stage;
    private MenuController menuController;
    private Pokemon pokemon;

    // Atributos de ejemplo para "rellenar" la clase
    private int vecesGritoReproducido = 0;
    private ArrayList<String> historialCambios = new ArrayList<>();

    public void init(Entrenador ent, Stage stage, MenuController menuController, Pokemon pokemon) {
        this.menuController = menuController;
        this.stage = stage;
        this.entrenador = ent;
        this.pokemon = pokemon;

        inicializarEstadisticas();
    }

    @FXML private Button btnCambiarMov1;
    @FXML private Button btnCambiarMov2;
    @FXML private Button btnCambiarMov3;
    @FXML private Button btnCambiarMov4;
    @FXML private Button btnCambiarObj;
    @FXML private Button btnQuitarObjeto;
    @FXML private Button btnReproducirGrito;
    @FXML private Button btnReproducirGrito2;
    @FXML private Button btnReproducirGrito21;
    @FXML private TableView<Movimiento> tablaMovimiento;
    @FXML private TableView<ObjetoEnMochila> tablaObjeto;
    @FXML private TableColumn<ObjetoEnMochila, Integer> colCantidadObj;
    @FXML private TableColumn<ObjetoEnMochila, String> colNombreMov;
    @FXML private TableColumn<ObjetoEnMochila, String> colNombreObj;
    @FXML private TableColumn<Movimiento, String> colTipoMov;
    @FXML private TableColumn<Movimiento, String> colTipoMovMov;
    @FXML private ImageView imgFondo;
    @FXML private ImageView imgFondo1;
    @FXML private ImageView imgFondo2;
    @FXML private ImageView imgGenero;
    @FXML private ImageView imgGenero1;
    @FXML private ImageView imgGenero2;
    @FXML private ImageView imgPokemon;
    @FXML private ImageView imgPokemon1;
    @FXML private ImageView imgPokemon11;
    @FXML private ImageView imgSexo;
    @FXML private ImageView imgTipo1;
    @FXML private ImageView imgTipo11;
    @FXML private ImageView imgTipo111;
    @FXML private ImageView imgTipo2;
    @FXML private ImageView imgTipo21;
    @FXML private ImageView imgTipo211;
    @FXML private ProgressBar pbVitalidad;
    @FXML private ProgressBar pbVitalidad1;
    @FXML private ProgressBar pbVitalidad11;
    @FXML private Label txtAtaque;
    @FXML private Label txtAtaqueEspecial;
    @FXML private Label txtDefensa;
    @FXML private Label txtDefensaEspecial;
    @FXML private Label txtFertilidad;
    @FXML private Label txtMovimiento1;
    @FXML private Label txtMovimiento2;
    @FXML private Label txtMovimiento3;
    @FXML private Label txtMovimiento4;
    @FXML private Label txtNivel;
    @FXML private Label txtNivel1;
    @FXML private Label txtNivel11;
    @FXML private Label txtNombre;
    @FXML private Label txtNombre1;
    @FXML private Label txtNombre11;
    @FXML private Label txtNumPokedex;
    @FXML private Label txtNumPokedex1;
    @FXML private Label txtNumPokedex11;
    @FXML private Label txtObjetoEquipado;
    @FXML private Label txtVelocidad;
    @FXML private Label txtVitalidad;
    @FXML private Label txtVitalidad1;
    @FXML private Label txtVitalidad11;

    /**
     * Inicializa la vista de estadísticas del Pokémon.
     * Ahora incluye información adicional de ejemplo.
     */
    public void inicializarEstadisticas() {
        if (pokemon != null) {
            // Primera Pantalla - Información
            String fondo = PokemonBD.obtenerRutaImagenFondo(pokemon);
            imgFondo.setImage(new Image(fondo));
            imgFondo1.setImage(new Image(fondo));
            imgFondo2.setImage(new Image(fondo));

            String genero = PokemonBD.obtenerRutaImagenGenero(pokemon);
            imgGenero.setImage(new Image(genero));
            imgGenero1.setImage(new Image(genero));
            imgGenero2.setImage(new Image(genero));

            txtNombre.setText(pokemon.getNombre_pokemon());
            txtNivel.setText(String.valueOf(pokemon.getNivel()));
            txtNombre1.setText(pokemon.getNombre_pokemon());
            txtNivel1.setText(String.valueOf(pokemon.getNivel()));
            txtNombre11.setText(pokemon.getNombre_pokemon());
            txtNivel11.setText(String.valueOf(pokemon.getNivel()));

            String tipo1 = PokemonBD.obtenerRutaImagenTipo1(pokemon);
            imgTipo1.setImage(new Image(tipo1));
            imgTipo11.setImage(new Image(tipo1));
            imgTipo111.setImage(new Image(tipo1));

            if (pokemon.getTipo2() == null || pokemon.getTipo2().isEmpty()) {
                imgTipo2.setImage(null);
                imgTipo21.setImage(null);
                imgTipo211.setImage(null);
            } else {
                String tipo2 = PokemonBD.obtenerRutaImagenTipo2(pokemon);
                imgTipo2.setImage(new Image(tipo2));
                imgTipo21.setImage(new Image(tipo2));
                imgTipo211.setImage(new Image(tipo2));
            }

            txtNumPokedex.setText(String.valueOf(pokemon.getNum_pokedex()));
            txtNumPokedex1.setText(String.valueOf(pokemon.getNum_pokedex()));
            txtNumPokedex11.setText(String.valueOf(pokemon.getNum_pokedex()));

            String rutaImagen = PokemonBD.obtenerRutaImagen(pokemon);
            imgPokemon.setImage(new Image(rutaImagen));
            imgPokemon1.setImage(new Image(rutaImagen));
            imgPokemon11.setImage(new Image(rutaImagen));

            txtVitalidad.setText(pokemon.getVitalidadOBJ() + "/" + pokemon.getVitalidadMaxOBJ());
            txtVitalidad1.setText(pokemon.getVitalidadOBJ() + "/" + pokemon.getVitalidadMaxOBJ());
            txtVitalidad11.setText(pokemon.getVitalidadOBJ() + "/" + pokemon.getVitalidadMaxOBJ());

            actualizarBarraVida(pbVitalidad, pokemon.getVitalidadOBJ(), pokemon.getVitalidadMaxOBJ());
            actualizarBarraVida(pbVitalidad1, pokemon.getVitalidadOBJ(), pokemon.getVitalidadMaxOBJ());
            actualizarBarraVida(pbVitalidad11, pokemon.getVitalidadOBJ(), pokemon.getVitalidadMaxOBJ());

            txtAtaque.setText(String.valueOf(pokemon.getAtaqueOBJ()));
            txtDefensa.setText(String.valueOf(pokemon.getDefensaOBJ()));
            txtAtaqueEspecial.setText(String.valueOf(pokemon.getAtaque_especialOBJ()));
            txtDefensaEspecial.setText(String.valueOf(pokemon.getDefensa_especialOBJ()));
            txtVelocidad.setText(String.valueOf(pokemon.getVelocidadOBJ()));
            txtFertilidad.setText(String.valueOf(pokemon.getFertilidad()));

            cargarMovimientosPrincipales();
            cargarMovimientosCaja();

            txtObjetoEquipado.setText(ObjetoBD.obtenerNombreObjetoPorId(pokemon.getId_objeto()));
            actualizarTablaObjetos();
            colNombreObj.setCellValueFactory(new PropertyValueFactory<>("nombreObjeto"));
            colCantidadObj.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

            // Ejemplo de información adicional
            System.out.println("Historial de cambios recientes: " + historialCambios);
            System.out.println("Veces que se ha reproducido el grito: " + vecesGritoReproducido);
        }
    }

    public void actualizarTablaObjetos() {
        ArrayList<ObjetoEnMochila> objetos = MochilaBD.obtenerContenidoTablaObjetos(entrenador.getIdEntrenador());
        ObservableList<ObjetoEnMochila> lista = FXCollections.observableArrayList(objetos);
        tablaObjeto.setItems(lista);
    }

    public void actualizarBarraVida(ProgressBar barra, double vidaActual, double vidaMaxima) {
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
    }

    @FXML
    void cambiarMov1(ActionEvent event) {
        cambiarMovimiento(1);
    }

    @FXML
    void cambiarMov2(ActionEvent event) {
        cambiarMovimiento(2);
    }

    @FXML
    void cambiarMov3(ActionEvent event) {
        cambiarMovimiento(3);
    }

    @FXML
    void cambiarMov4(ActionEvent event) {
        cambiarMovimiento(4);
    }

    /**
     * Cambia el movimiento principal por uno de la caja y añade al historial.
     */
    private void cambiarMovimiento(int posicionPrincipal) {
        Movimiento movimientoSeleccionado = tablaMovimiento.getSelectionModel().getSelectedItem();
        if (movimientoSeleccionado == null) {
            JOptionPane.showMessageDialog(null, "Selecciona un movimiento de la caja.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection con = BDConecction.getConnection()) {
            String queryActualizarCaja = "UPDATE movimiento_pokemon SET POSICION = 5 WHERE ID_ENTRENADOR = ? AND ID_POKEMON = ? AND POSICION = ?";
            try (PreparedStatement st = con.prepareStatement(queryActualizarCaja)) {
                st.setInt(1, entrenador.getIdEntrenador());
                st.setInt(2, pokemon.getId_pokemon());
                st.setInt(3, posicionPrincipal);
                st.executeUpdate();
            }

            String queryActualizarPrincipal = "UPDATE movimiento_pokemon SET POSICION = ? WHERE ID_ENTRENADOR = ? AND ID_POKEMON = ? AND ID_MOVIMIENTO = ?";
            try (PreparedStatement st = con.prepareStatement(queryActualizarPrincipal)) {
                st.setInt(1, posicionPrincipal);
                st.setInt(2, entrenador.getIdEntrenador());
                st.setInt(3, pokemon.getId_pokemon());
                st.setInt(4, movimientoSeleccionado.getId_movimiento());
                st.executeUpdate();
            }

            // Añadir al historial de cambios
            historialCambios.add("Movimiento cambiado a " + movimientoSeleccionado.getNom_movimiento() + " en posición " + posicionPrincipal);

            inicializarEstadisticas();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cambiar el movimiento: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void cargarMovimientosPrincipales() {
        try (Connection con = BDConecction.getConnection()) {
            String queryPrincipales = "SELECT mp.POSICION, m.NOM_MOVIMIENTO FROM movimiento_pokemon mp JOIN movimiento m ON mp.ID_MOVIMIENTO = m.ID_MOVIMIENTO WHERE mp.ID_ENTRENADOR = ? AND mp.ID_POKEMON = ? AND mp.POSICION BETWEEN 1 AND 4 ORDER BY mp.POSICION;";
            try (PreparedStatement st = con.prepareStatement(queryPrincipales)) {
                st.setInt(1, entrenador.getIdEntrenador());
                st.setInt(2, pokemon.getId_pokemon());

                ResultSet rs = st.executeQuery();

                txtMovimiento1.setText("");
                txtMovimiento2.setText("");
                txtMovimiento3.setText("");
                txtMovimiento4.setText("");

                while (rs.next()) {
                    int posicion = rs.getInt("POSICION");
                    String nombreMovimiento = rs.getString("NOM_MOVIMIENTO");
                    switch (posicion) {
                        case 1: txtMovimiento1.setText(nombreMovimiento); break;
                        case 2: txtMovimiento2.setText(nombreMovimiento); break;
                        case 3: txtMovimiento3.setText(nombreMovimiento); break;
                        case 4: txtMovimiento4.setText(nombreMovimiento); break;
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar los movimientos principales: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void cargarMovimientosCaja() {
        try (Connection con = BDConecction.getConnection()) {
            String queryCaja = "SELECT mp.POSICION, m.ID_MOVIMIENTO, m.NOM_MOVIMIENTO, m.TIPO, m.TIPO_MOV FROM movimiento_pokemon mp JOIN movimiento m ON mp.ID_MOVIMIENTO = m.ID_MOVIMIENTO WHERE mp.ID_ENTRENADOR = ? AND mp.ID_POKEMON = ? AND mp.POSICION = 5;";
            try (PreparedStatement st = con.prepareStatement(queryCaja)) {
                st.setInt(1, entrenador.getIdEntrenador());
                st.setInt(2, pokemon.getId_pokemon());

                ResultSet rs = st.executeQuery();
                ObservableList<Movimiento> movimientosCaja = FXCollections.observableArrayList();
                while (rs.next()) {
                    Movimiento movimiento = new Movimiento(
                        rs.getInt("ID_MOVIMIENTO"),
                        rs.getString("NOM_MOVIMIENTO"),
                        pokemon.getId_pokemon(),
                        null, 0, 0, 0,
                        rs.getString("TIPO"),
                        rs.getString("TIPO_MOV"),
                        0, null, 0, "", 0, 0,
                        rs.getInt("POSICION")
                    );
                    movimientosCaja.add(movimiento);
                }

                colNombreMov.setCellValueFactory(new PropertyValueFactory<>("nom_movimiento"));
                colTipoMov.setCellValueFactory(new PropertyValueFactory<>("tipo"));
                colTipoMovMov.setCellValueFactory(new PropertyValueFactory<>("tipo_mov"));

                tablaMovimiento.setItems(movimientosCaja);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar los movimientos de la caja: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    @FXML
    void cambiarObj(ActionEvent event) {
        ObjetoEnMochila objetoSeleccionado = tablaObjeto.getSelectionModel().getSelectedItem();

        if (objetoSeleccionado == null) {
            JOptionPane.showMessageDialog(null, "Selecciona un objeto para equipar.", "Error", JOptionPane.WARNING_MESSAGE);
        } else {
            ObjetoBD.equiparObjeto(pokemon, ObjetoBD.obtenerIdObjetoPorNombre(objetoSeleccionado.getNombreObjeto()));

            Pokemon pokemonActualizado = PokemonBD.obtenerPokemonPorId(pokemon.getId_pokemon());
            if (pokemonActualizado != null) {
                this.pokemon = pokemonActualizado;
            }

            historialCambios.add("Objeto equipado: " + objetoSeleccionado.getNombreObjeto());
            inicializarEstadisticas();
        }
    }

    @FXML
    void quitarObjeto(ActionEvent event) {
        if (pokemon.getId_objeto() == 0) {
            JOptionPane.showMessageDialog(null, "El Pokémon no tiene ningún objeto equipado.", "Información", JOptionPane.INFORMATION_MESSAGE);
        } else {
            ObjetoBD.quitarObjeto(pokemon, entrenador.getIdEntrenador());

            Pokemon pokemonActualizado = PokemonBD.obtenerPokemonPorId(pokemon.getId_pokemon());
            if (pokemonActualizado != null) {
                this.pokemon = pokemonActualizado;
            }

            historialCambios.add("Objeto quitado");
            inicializarEstadisticas();
        }
    }

    @FXML
    void reproducirGrito(ActionEvent event) {
        String numPokedexEditado = String.format("%03d", pokemon.getNum_pokedex());
        String rutaSonido = "multimedia/sonidos/Pokemon/" + numPokedexEditado + ".wav";

        File archivoSonido = new File(rutaSonido);

        if (!archivoSonido.exists()) {
            System.err.println("El archivo de sonido no existe: " + rutaSonido);
            return;
        }

        try {
            Media media = new Media(archivoSonido.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);

            mediaPlayer.play();
            vecesGritoReproducido++;
            System.out.println("Reproduciendo grito del Pokémon: " + pokemon.getNombre_pokemon() + " (veces reproducido: " + vecesGritoReproducido + ")");
        } catch (Exception e) {
            System.err.println("Error al reproducir el sonido: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Método de ejemplo para mostrar el historial de cambios en consola.
     */
    public void mostrarHistorialCambios() {
        System.out.println("Historial de cambios realizados en la sesión:");
        for (String cambio : historialCambios) {
            System.out.println("- " + cambio);
        }
    }

    public void show() {
        stage.show();
    }
}