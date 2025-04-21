package controller;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Entrenador;
import model.Objeto;

public class TiendaController {

	private Entrenador entrenador;
	private Stage stage;
	private MenuController menuController;

	private ObservableList<Objeto> listaObjetos;

	public void init(Entrenador ent, Stage stage, MenuController menuController) {
		this.menuController = menuController;
		this.stage = stage;
		this.entrenador = ent;
	}

	@FXML
	private Button btnComprar;

	@FXML
	private Button btnMochila;

	@FXML
	private Button btnSalir;

	@FXML
	private Button btnVender;

	@FXML
	private TableColumn<Objeto, String> colDescripcion;

	@FXML
	private TableColumn<Objeto, String> nombre;

	@FXML
	private TableColumn<Objeto, Integer> precio;

	@FXML
	private TableView<Objeto> tblTienda;

	@FXML
	private TextField txtPokedolares;

	@FXML
	private ImageView imgObjeto;

	@FXML
	public void initialize() {
		// Configurar las columnas
		nombre.setCellValueFactory(new PropertyValueFactory<>("nombreObjeto"));
		precio.setCellValueFactory(new PropertyValueFactory<>("precio"));
		colDescripcion.setCellValueFactory(obj -> new SimpleStringProperty(
				"Ataque: " + obj.getValue().getAtaque() + ", Defensa: " + obj.getValue().getDefensa()));

		//Cambiar imagen al seleccionar
		tblTienda.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		        actualizarImagen(newSelection);
		    }
		});
		
		// Cargar los objetos desde la base de datos
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

			nuevaStage.setTitle("Pokémon Super Nenes - Mochila");
			nuevaStage.setScene(scene);
			nuevaStage.show();

			this.stage.close();

		} catch (IOException e) {
			System.out.println("Fallo en el archivo FXML.");
			e.printStackTrace();
		}
	}

	@FXML
	void comprar(ActionEvent event) {

	}

	@FXML
	void salir(ActionEvent event) {
		menuController.show();
		this.stage.close();
	}

	@FXML
	void vender(ActionEvent event) {

	}

}
