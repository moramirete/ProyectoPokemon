package controller;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Label;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Entrenador;

public class TiendaController {
	
	private Entrenador entrenador;
	private Stage stage;
	private MenuController menuController;

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
    private TableColumn<?, ?> nombre;

    @FXML
    private TableColumn<?, ?> precio;

    @FXML
    private TableView<?> tblTienda;

    @FXML
    private TextArea txtDescripcion;

    @FXML
    private TextField txtPokedolares;

    @FXML
    void abrirMochila(ActionEvent event) {
    	
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/mochila.fxml"));
            Parent root = loader.load();

            MochilaController mochilaController = loader.getController(); // ✅ AQUÍ está el cambio
            Stage nuevaStage = new Stage();
            Scene scene = new Scene(root);

            mochilaController.init(entrenador, nuevaStage, menuController); // ✅ Pasas el menuController real

            nuevaStage.setTitle("Pokémon Super Nenes - Mochila");
            nuevaStage.setScene(scene);
            nuevaStage.show();

            this.stage.close(); // cerramos la tienda

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
