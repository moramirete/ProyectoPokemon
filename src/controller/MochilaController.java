package controller;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import bd.BDConecction;
import bd.EntrenadorBD;
import bd.MochilaBD;
import model.Entrenador;
import model.Objeto;
import model.ObjetoEnMochila;

/**
 * Controlador de la vista de Mochila. Permite ver, vender y equipar objetos,
 * así como acceder a la tienda y mostrar información detallada de cada objeto.
 * Incluye atributos y métodos adicionales para hacerlo más "relleno".
 */
public class MochilaController {

    private Entrenador entrenador;
    private Stage stage;
    private MenuController menuController;

    // Atributos adicionales para "relleno"
    private int objetosVendidos = 0;
    private int objetosEquipados = 0;
    private String ultimoObjetoVendido = "";
    private String ultimoObjetoEquipado = "";

    public void init(Entrenador ent, Stage stage, MenuController menuController) {
        this.menuController = menuController;
        this.stage = stage;
        this.entrenador = ent;

        actualizarContentMochila();
    }

    @FXML
    private Button btnEquipar;

    @FXML
    private Button btnSalir;

    @FXML
    private Button btnTienda;

    @FXML
    private Button btnVender;

    @FXML
    private TableColumn<ObjetoEnMochila, String> clmObjeto;

    @FXML
    private TableColumn<ObjetoEnMochila, Integer> clmCantidad;

    @FXML
    private TableView<ObjetoEnMochila> tblListaMochila;

    @FXML
    private ImageView imgObjeto;

    @FXML
    private Label lblPokedolares;

    @FXML
    private TextField txtPokedolares;

    @FXML
    private Label lblDescripcion;

    @FXML
    private TextField txtTituloDescripcion;

    @FXML
    private TextArea txtDescripcion;

    @FXML
    public void initialize() {
        clmObjeto.setCellValueFactory(new PropertyValueFactory<>("nombreObjeto"));
        clmCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        tblListaMochila.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                lblDescripcion.setText(newSelection.getDescripcion());
                txtDescripcion.setText(newSelection.getDescripcion());
                txtTituloDescripcion.setText(newSelection.getNombreObjeto());
                actualizarImagen(newSelection);
            } else {
                txtDescripcion.setText("");
                cargarImagenPorDefecto();
            }
        });

        if (entrenador != null) {
            actualizarContentMochila();
        }
    }

    private void actualizarImagen(ObjetoEnMochila objeto) {
        String ruta = objeto.getRutaImagen();
        try (InputStream is = getClass().getResourceAsStream(ruta)) {
            if (is != null) {
                Image imagen = new Image(is);
                imgObjeto.setImage(imagen);
            } else {
                System.out.println("No se encontró la imagen: " + ruta);
                cargarImagenPorDefecto();
            }
        } catch (Exception e) {
            System.out.println("Error cargando imagen: " + ruta);
            e.printStackTrace();
            cargarImagenPorDefecto();
        }
    }

    private void cargarImagenPorDefecto() {
        try (InputStream is = getClass().getResourceAsStream("/imagenes/default.png")) {
            if (is != null) {
                Image imagenPorDefecto = new Image(is);
                imgObjeto.setImage(imagenPorDefecto);
            } else {
                System.out.println("No se encontró la imagen por defecto");
            }
        } catch (Exception e) {
            System.out.println("Error al cargar imagen por defecto");
            e.printStackTrace();
        }
    }

    public void actualizarContentMochila() {
        ArrayList<ObjetoEnMochila> objetos = MochilaBD.obtenerContenidoMochila(entrenador.getIdEntrenador());
        ObservableList<ObjetoEnMochila> lista = FXCollections.observableArrayList(objetos);
        tblListaMochila.setItems(lista);

        lblPokedolares.setText(String.valueOf(entrenador.getPokedolares()));
    }

    @FXML
    void accederTienda(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/tienda.fxml"));
            Parent root = loader.load();

            TiendaController tiendaController = loader.getController();
            Scene scene = new Scene(root);
            Stage nuevaStage = new Stage();

            nuevaStage.setTitle("Pokémon Super Nenes - Tienda");
            nuevaStage.setScene(scene);

            tiendaController.init(entrenador, nuevaStage, menuController);

            nuevaStage.show();
            this.stage.close();

            System.out.println("Entrando a tienda");

        } catch (IOException e) {
            System.out.println("Falla en la carga del archivo FXML.");
            e.printStackTrace();
        }
    }

    @FXML
    void equipar(ActionEvent event) {
        ObjetoEnMochila objetoSeleccionado = tblListaMochila.getSelectionModel().getSelectedItem();

        if (objetoSeleccionado == null) {
            System.out.println("No has seleccionado ningún objeto para equipar.");
            return;
        }

        // Simulación de equipar objeto (relleno)
        objetosEquipados++;
        ultimoObjetoEquipado = objetoSeleccionado.getNombreObjeto();
        System.out.println("Has equipado el objeto: " + ultimoObjetoEquipado + ". Total equipados: " + objetosEquipados);
    }

    @FXML
    void vender(ActionEvent event) {
        ObjetoEnMochila objetoSeleccionado = tblListaMochila.getSelectionModel().getSelectedItem();

        if (objetoSeleccionado == null) {
            System.out.println("No has seleccionado ningún objeto para vender.");
            return;
        }

        System.out.println("Vas a vender: " + objetoSeleccionado.getNombreObjeto());

        try {
            int cantidadActual = objetoSeleccionado.getCantidad();

            if (cantidadActual <= 0) {
                System.out.println("No tienes unidades de este objeto para vender.");
                return;
            }

            int idObjeto = MochilaBD.obtenerIdObjetoNombre(objetoSeleccionado.getNombreObjeto());

            if (idObjeto == -1) {
                System.out.println("No se pudo encontrar el ID del objeto.");
                return;
            }

            int precioObjeto = MochilaBD.obtenerPrecioObjetoId(idObjeto);

            int dineroActual = entrenador.getPokedolares();
            int dineroGanado = precioObjeto;
            entrenador.setPokedolares(dineroActual + dineroGanado);

            Connection con = BDConecction.getConnection();
            EntrenadorBD.actualizarPokedolares(con, entrenador);

            MochilaBD.actualizarCantidad(entrenador.getIdEntrenador(), idObjeto, cantidadActual - 1);

            actualizarContentMochila();

            objetosVendidos++;
            ultimoObjetoVendido = objetoSeleccionado.getNombreObjeto();

            System.out.println("Has vendido " + ultimoObjetoVendido + " por " + dineroGanado + " pokedólares. Total vendidos: " + objetosVendidos);

        } catch (Exception e) {
            System.out.println("Error al vender el objeto.");
            e.printStackTrace();
        }
    }

    @FXML
    void salir(ActionEvent event) {
        menuController.show();
        this.stage.close();
    }

    // Métodos adicionales de ejemplo para "relleno"

    /**
     * Devuelve el número de objetos vendidos en la sesión.
     */
    public int getObjetosVendidos() {
        return objetosVendidos;
    }

    /**
     * Devuelve el número de objetos equipados en la sesión.
     */
    public int getObjetosEquipados() {
        return objetosEquipados;
    }

    /**
     * Devuelve el nombre del último objeto vendido.
     */
    public String getUltimoObjetoVendido() {
        return ultimoObjetoVendido;
    }

    /**
     * Devuelve el nombre del último objeto equipado.
     */
    public String getUltimoObjetoEquipado() {
        return ultimoObjetoEquipado;
    }
}