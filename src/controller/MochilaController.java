package controller;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Label;
import java.io.IOException;
import java.util.ArrayList;

import bd.MochilaBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Entrenador;
import model.Mochila;
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
	
	public void initialize() {
	    clmObjeto.setCellValueFactory(new PropertyValueFactory<>("nombreObjeto"));
	    clmDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
	    clmCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
	    
	    if (entrenador != null) {
	        actualizarContentMochila();
	    }
	}

	@FXML
	private Button btnEquipar;

	@FXML
	private Button btnQuitar;

	@FXML
	private Button btnSalir;

	@FXML
	private Button btnTienda;

	@FXML
	private TableColumn<ObjetoEnMochila, Integer> clmCantidad;

	@FXML
	private TableColumn<ObjetoEnMochila, String> clmDescripcion;

	@FXML
	private TableColumn<ObjetoEnMochila, String> clmObjeto;
	
	@FXML
	private TableView<ObjetoEnMochila> tblListaMochila;

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

			tiendaController.init(entrenador, nuevaStage, menuController); //Usar el controlador correcto

			nuevaStage.show();
			this.stage.close();

			System.out.println("Entrando a tienda");

		} catch (IOException e) {
			System.out.println("Falla en la carga del archivo FXML.");
			e.printStackTrace();
		}
	}
	
	public void actualizarContentMochila() {
	    ArrayList<ObjetoEnMochila> objetos = MochilaBD.obtenerContenidoMochila(entrenador.getIdEntrenador());
	    ObservableList<ObjetoEnMochila> lista = FXCollections.observableArrayList(objetos);
	    tblListaMochila.setItems(lista);
	}

	@FXML
	void equipar(ActionEvent event) {

	}

	@FXML
	void quitar(ActionEvent event) {

	}

	@FXML
	void salir(ActionEvent event) {
		menuController.show();
		this.stage.close();
	}

}
