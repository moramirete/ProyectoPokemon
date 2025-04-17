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
import model.Entrenador;

public class EquipoController {

	private Entrenador entrenador;
	private Stage stage;
	private MenuController menuController;

	public void init(Entrenador ent, Stage stage, MenuController menuController) {
		this.menuController = menuController;
		this.stage = stage;
		this.entrenador = ent;

		inicializarVidas();
	}

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

	@FXML
	void cerrarEquipo(ActionEvent event) {
		menuController.show();
		this.stage.close();
	}
	
	public void inicializarVidas() {
		actualizarBarraVida(barraVida1, lblVida1, 213, 218);
		actualizarBarraVida(barraVida2, lblVida2, 150, 218);
		actualizarBarraVida(barraVida3, lblVida3, 90, 218);
		actualizarBarraVida(barraVida4, lblVida4, 40, 218);
		actualizarBarraVida(barraVida5, lblVida5, 10, 218);
		actualizarBarraVida(barraVida6, lblVida6, 5, 218);
	}

	public void actualizarBarraVida(ProgressBar barra, Label label, double vidaActual, double vidaMaxima) {
	    double porcentaje = vidaActual / vidaMaxima;
	    barra.setProgress(porcentaje);

	    String color;
	    if (porcentaje > 0.5) {
	        color = "#228B22"; // Verde
	    } else if (porcentaje > 0.2) {
	        color = "yellow"; // Amarillo
	    } else {
	        color = "red"; // Rojo
	    }

	    barra.setStyle("-fx-accent: " + color + ";");
	    label.setText((int)vidaActual + "/" + (int)vidaMaxima);
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
