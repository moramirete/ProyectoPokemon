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

public class MochilaController {

	private Entrenador entrenador;
	private Stage stage;
	private MenuController menuController;

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

	}

	@FXML
	void vender(ActionEvent event) {
	    // Obtener el objeto seleccionado de la tabla
	    ObjetoEnMochila objetoSeleccionado = tblListaMochila.getSelectionModel().getSelectedItem();

	    if (objetoSeleccionado == null) {
	        System.out.println("No has seleccionado ningún objeto para vender.");
	        return;
	    }

	    // Confirmar venta
	    System.out.println("Vas a vender: " + objetoSeleccionado.getNombreObjeto());

	    try {
	        // Obtener la cantidad actual
	        int cantidadActual = objetoSeleccionado.getCantidad();

	        if (cantidadActual <= 0) {
	            System.out.println("No tienes unidades de este objeto para vender.");
	            return;
	        }

	        // Obtener el ID del objeto
	        int idObjeto = MochilaBD.obtenerIdObjetoNombre(objetoSeleccionado.getNombreObjeto());

	        if (idObjeto == -1) {
	            System.out.println("No se pudo encontrar el ID del objeto.");
	            return;
	        }

	        // Obtener el precio del objeto
	        int precioObjeto = MochilaBD.obtenerPrecioObjetoId(idObjeto);

	        // Sumar dinero al entrenador
	        int dineroActual = entrenador.getPokedolares();
	        int dineroGanado = precioObjeto; // Por cada venta ganas el precio unitario
	        entrenador.setPokedolares(dineroActual + dineroGanado);

	        // Actualizar en base de datos
	        Connection con = BDConecction.getConnection();
	        EntrenadorBD.actualizarPokedolares(con, entrenador);

	        // Disminuir la cantidad de objeto o eliminarlo si ya no queda
	        MochilaBD.actualizarCantidad(entrenador.getIdEntrenador(), idObjeto, cantidadActual - 1);

	        // Actualizar la tabla
	        actualizarContentMochila();

	        System.out.println("Has vendido " + objetoSeleccionado.getNombreObjeto() + " por " + dineroGanado + " pokedólares.");

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

}
