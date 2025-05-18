package controller;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import bd.BDConecction;
import bd.EntrenadorBD;
import bd.MochilaBD;
import bd.ObjetoBD;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Entrenador;
import model.Mochila;
import model.Objeto;

/**
 * Controlador de la vista de Tienda. Permite comprar objetos, ver detalles,
 * acceder a la mochila y regresar al menú. Incluye atributos y métodos
 * adicionales para hacerlo más "relleno".
 */
public class TiendaController {

    private Entrenador entrenador;
    private Stage stage;
    private MenuController menuController;

    private ObservableList<Objeto> listaObjetos;

    // Atributos adicionales para "relleno"
    private int objetosComprados = 0;
    private String ultimoObjetoComprado = "";

    public void init(Entrenador ent, Stage stage, MenuController menuController) {
        this.menuController = menuController;
        this.stage = stage;
        this.entrenador = ent;

        lblPokedollares.textProperty().bind(entrenador.pokedolaresProperty().asString());
    }

    @FXML
    private Button btnComprar;

    @FXML
    private Button btnMochila;

    @FXML
    private Button btnSalir;

    @FXML
    private TableColumn<Objeto, String> nombre;

    @FXML
    private TableColumn<Objeto, Integer> precio;

    @FXML
    private TableView<Objeto> tblTienda;

    @FXML
    private TextArea txtDescripcion;

    @FXML
    private TextField txtPokedolares;

    @FXML
    private Label lblPokedollares;

    @FXML
    private TextField txtTituloDescripcion;

    @FXML
    private Label lblDescripcion;

    @FXML
    private ImageView imgObjeto;

    @FXML
    public void initialize() {
        nombre.setCellValueFactory(new PropertyValueFactory<>("nombreObjeto"));
        precio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        tblTienda.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                actualizarImagen(newSelection);
                txtDescripcion.setText(newSelection.getDescripcion());
                txtTituloDescripcion.setText(newSelection.getNombreObjeto());
            }
        });

        cargarObjetos();
    }

    private void cargarObjetos() {
        ArrayList<Objeto> objetos = ObjetoBD.obtenerTodosLosObjetos();
        if (objetos == null) {
            objetos = new ArrayList<>();
        }
        listaObjetos = FXCollections.observableArrayList(objetos);
        tblTienda.setItems(listaObjetos);
    }

    private void actualizarImagen(Objeto objeto) {
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

    @FXML
    void abrirMochila(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/mochila.fxml"));
            Parent root = loader.load();

            MochilaController mochilaController = loader.getController();
            Stage nuevaStage = new Stage();
            Scene scene = new Scene(root);

            mochilaController.init(entrenador, nuevaStage, menuController);
            mochilaController.actualizarContentMochila();
            this.setMochilaController(mochilaController);

            nuevaStage.setTitle("Pokémon Super Nenes - Mochila");
            nuevaStage.getIcons().add(new Image("/imagenes/lossupernenes.png"));
            nuevaStage.setScene(scene);
            nuevaStage.show();

            this.stage.close();

        } catch (IOException e) {
            System.out.println("Fallo en el archivo FXML.");
            e.printStackTrace();
        }
    }

    @FXML
    void comprar(ActionEvent event) throws SQLException {
        Objeto objetoSeleccionado = tblTienda.getSelectionModel().getSelectedItem();

        if (objetoSeleccionado == null) {
            System.out.println("No se ha seleccionado ningún objeto.");
            return;
        }

        int precio = objetoSeleccionado.getPrecio();
        if (entrenador.getPokedolares() < precio) {
            System.out.println("No tienes suficiente dinero para comprar este objeto.");
            return;
        }

        ArrayList<Mochila> mochila = MochilaBD.obtenerMochila(entrenador.getIdEntrenador());
        boolean objetoExistente = false;
        for (Mochila item : mochila) {
            if (item.getIdObjeto() == objetoSeleccionado.getIdObjeto()) {
                int nuevaCantidad = item.getCantidad() + 1;
                if (MochilaBD.actualizarCantidad(entrenador.getIdEntrenador(), objetoSeleccionado.getIdObjeto(),
                        nuevaCantidad)) {
                    System.out.println("Se ha incrementado la cantidad de " + objetoSeleccionado.getNombreObjeto());
                    objetoExistente = true;
                    break;
                }
            }
        }

        if (!objetoExistente) {
            Mochila nuevoObjeto = new Mochila(entrenador.getIdEntrenador(), objetoSeleccionado.getIdObjeto(), 1);
            boolean agregado = MochilaBD.agregarObjeto(nuevoObjeto);

            if (agregado) {
                System.out.println("Has comprado: " + objetoSeleccionado.getNombreObjeto());
            } else {
                System.out.println("No se pudo agregar el objeto a la mochila.");
                return;
            }
        }

        int precioActualizado = entrenador.getPokedolares() - precio;
        entrenador.setPokedolares(precioActualizado);

        Connection con = BDConecction.getConnection();
        EntrenadorBD.actualizarPokedolares(con, entrenador);

        // Actualizar la mochila en la interfaz
        if (mochilaController != null) {
            mochilaController.actualizarContentMochila();
        }

        // Relleno: actualizar atributos de compra
        objetosComprados++;
        ultimoObjetoComprado = objetoSeleccionado.getNombreObjeto();
        System.out.println("Total objetos comprados en la sesión: " + objetosComprados);
    }

    // El controlador de la tienda se comunica con el de mochila
    private MochilaController mochilaController;

    public void setMochilaController(MochilaController mochilaController) {
        this.mochilaController = mochilaController;
    }

    @FXML
    void salir(ActionEvent event) {
        menuController.show();
        this.stage.close();
    }

    // Métodos adicionales de ejemplo para "relleno"

    /**
     * Devuelve el número de objetos comprados en la sesión.
     */
    public int getObjetosComprados() {
        return objetosComprados;
    }

    /**
     * Devuelve el nombre del último objeto comprado.
     */
    public String getUltimoObjetoComprado() {
        return ultimoObjetoComprado;
    }
}