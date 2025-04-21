package controller;

import javafx.stage.Stage;
import model.Entrenador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.util.Random;

public class CapturaController {

	private Entrenador entrenador;
	private Stage stage;
	private MenuController menuController;
	
	 @FXML
	 private Button btnCapturar;

	 @FXML
	 private Button btnGenerar;

	 @FXML
	 private Button btnSalir;

	 @FXML
	 private ImageView imgFondo;
	 

	 @FXML
	 private Label lblPokemon;

	 
	 
	
	
	public void init(Entrenador ent, Stage stage, MenuController menuController) {
		this.menuController = menuController;
		this.stage = stage;
		this.entrenador = ent;
	}
	
	@FXML
	void cerrarCaptura(ActionEvent event) {
		System.out.println("Se ha accionado el boton de salir");
		menuController.show();
		this.stage.close();
		System.out.println("Se ha salido de captura correctamente");
	}
	
    @FXML
    void capturarPokemon(ActionEvent event) {
    	System.out.println("Se ha accionado el boton de capturar");
    }
    
    @FXML
    void generarPokemon(ActionEvent event) {
    	System.out.println("Se ha accionado el boton de generar");
    
    	final String[] pokemon = {
   			 "1.png",
   			 "2.png",
   			 "3.png",
   			 "4.png"
   			
   	 };
    	int index = (int)(Math.random() * pokemon.length);
    	String archivo = pokemon[index];
    	
    	InputStream is = getClass().getResourceAsStream("/multimedia/imagenes/delanteras/" + archivo);
    	
    	if (is == null) {
    		System.out.println(" No se a encontrado la imagen: " + archivo);
    		lblPokemon.setText("No se pudo cargar " + archivo);
    		lblPokemon.setGraphic(null);
    		return;
    	}
    	
    	Image imagen = new Image(is);
    	ImageView imageView = new ImageView(imagen);
    	imageView.setFitWidth(120);
    	imageView.setFitHeight(120);
    	imageView.setPreserveRatio(true);
    	
    	String nombre = archivo.replace(".png", "");
    	lblPokemon.setText(nombre.substring(0, 1).toUpperCase() + nombre.substring(1));
    	lblPokemon.setGraphic(imageView);
    	
    }

}
