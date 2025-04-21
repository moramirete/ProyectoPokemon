package controller;

import javafx.stage.Stage;
import model.Entrenador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
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
    
    	final String[] pokemon = new String[151];
    	for(int i = 0; i < 151; i++) {
   			pokemon[i] = (i + 1) + ".png";
   			
    	}
    	
    	int index = new Random().nextInt(pokemon.length);
    	String archivo = pokemon[index];
    	
    	String ruta = "multimedia/imagenes/delanteras/" +archivo;
    	
    	File file = new File(ruta);
    	
    	if (!file.exists()) {
    		System.out.println(" No se a encontrado la imagen: " + archivo);
    		lblPokemon.setText("No se pudo cargar " + archivo);
    		lblPokemon.setGraphic(null);
    		return;
    	}
    	
    	Image imagen = new Image(file.toURI().toString());
    	ImageView imageView = new ImageView(imagen);
    	imageView.setFitWidth(120);
    	imageView.setFitHeight(120);
    	imageView.setPreserveRatio(true);
    	
    	String nombre = archivo.replace(".png","");
    	lblPokemon.setText("");
    	lblPokemon.setGraphic(imageView);
    }

}