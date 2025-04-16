package controller;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Entrenador;

public class EquipoController {

	private Entrenador entrenador;
	private Stage stage;
	private MenuController menuController;

	public void init(Entrenador ent, Stage stage, MenuController menuController) {
		this.menuController = menuController;
		this.stage = stage;
		this.entrenador = ent;

	}

	@FXML
	private Button btnCambiar;

	@FXML
	private Button btnSalir;

	@FXML
	private ImageView imgfondo;

	@FXML
	void cerrarEquipo(ActionEvent event) {
		menuController.show();
		this.stage.close();
	}

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
			nuevaStage.show();

			stage.show();

			this.stage.close();

		} catch (IOException e) {
			System.out.println("Fallo en el archivo FXML.");
			e.printStackTrace();
		}

	}

	public void show() {
		stage.show();
	}

}
