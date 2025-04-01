package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Entrenador;

public class MenuController {

	private Entrenador entrenador;
	private Stage stage;
	private LoginController loginController;

	@FXML
	private Button btnCaptura;

	@FXML
	private Button btnCentroPokemon;

	@FXML
	private Button btnCombate;

	@FXML
	private Button btnCrianza;

	@FXML
	private Button btnEntrenamiento;

	@FXML
	private Button btnEquipo;

	@FXML
	private Button btnSalir;
	
    @FXML
    private ImageView btnSalir1;

	@FXML
	private ImageView imagenFondo;

	@FXML
	private ImageView imagenLogo;

	@FXML
	private Label lblJugador;

	@FXML
	private Label lblPokedollares;

	@FXML
	private Label plantillaPokeDolares;

	@FXML
	private Label plantillaUsuario;
	
	//Metodos para abrir cada una de las vistas

	@FXML
	void abrirCaptura(ActionEvent event) {
		
		try {
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/captura.fxml"));
	        Parent root = loader.load();
	        
	        CapturaController capturaController = loader.getController();
	        
	        Scene scene = new Scene(root);
    		Stage stage = new Stage();
    		
	        stage.setTitle("Pokémon Super Nenes - Captura");
	        stage.setScene(scene);
	        capturaController.init(entrenador, stage, this);
	        
	        stage.show();
	        
	        this.stage.close();
    		
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
		
	}

	@FXML
	void abrirCentro(ActionEvent event) {
		
		try {
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/centro.fxml"));
	        Parent root = loader.load();
	        
	        CentroController centroController = loader.getController();
	        
	        Scene scene = new Scene(root);
    		Stage stage = new Stage();
    		
	        stage.setTitle("Pokémon Super Nenes - Centro");
	        stage.setScene(scene);
	        centroController.init(entrenador, stage, this);
	        
	        stage.show();
	        
	        this.stage.close();
    		
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
		
	}

	@FXML
	void abrirCombate(ActionEvent event) {
		try {
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/combate.fxml"));
	        Parent root = loader.load();
	        
	        CombateController combateController = loader.getController();
	        
	        Scene scene = new Scene(root);
    		Stage stage = new Stage();
    		
	        stage.setTitle("Pokémon Super Nenes - Captura");
	        stage.setScene(scene);
	        combateController.init(entrenador, stage, this);
	        
	        stage.show();
	        
	        this.stage.close();
    		
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
	}

	@FXML
	void abrirCrianza(ActionEvent event) {
		try {
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/crianza.fxml"));
	        Parent root = loader.load();
	        
	        CrianzaController crianzaController = loader.getController();
	        
	        Scene scene = new Scene(root);
    		Stage stage = new Stage();
    		
	        stage.setTitle("Pokémon Super Nenes - Captura");
	        stage.setScene(scene);
	        crianzaController.init(entrenador, stage, this);
	        
	        stage.show();
	        
	        this.stage.close();
    		
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
	}

	@FXML
	void abrirEntrenamiento(ActionEvent event) {
		try {
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/entrenamiento.fxml"));
	        Parent root = loader.load();
	        
	        EntrenamientoController entrenamientoController = loader.getController();
	        
	        Scene scene = new Scene(root);
    		Stage stage = new Stage();
    		
	        stage.setTitle("Pokémon Super Nenes - Captura");
	        stage.setScene(scene);
	        entrenamientoController.init(entrenador, stage, this);
	        
	        stage.show();
	        
	        this.stage.close();
    		
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
	}

	@FXML
	void abrirEquipo(ActionEvent event) {
		try {
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/equipo.fxml"));
	        Parent root = loader.load();
	        
	        EquipoController equipoController = loader.getController();
	        
	        Scene scene = new Scene(root);
    		Stage stage = new Stage();
    		
	        stage.setTitle("Pokémon Super Nenes - Captura");
	        stage.setScene(scene);
	        equipoController.init(entrenador, stage, this);
	        
	        stage.show();
	        
	        this.stage.close();
    		
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
	}

	//Para cerrar el menu / cerrar sesion
	@FXML
	public void cerrarMenu(ActionEvent event) {
    	loginController.show();
    	this.stage.close();
	}
	
	//Inicializa el controlador con datos esenciales para que la nueva ventana funcione correctamente.
	public void init(Entrenador ent, Stage stage, LoginController loginController) {
		this.loginController = loginController;
		this.stage = stage;
		this.entrenador = ent;

		lblJugador.setText(entrenador.getUsuario());
		lblPokedollares.setText(Integer.toString(entrenador.getPokedolares()));

	}
	
	//Cerrar aplicacion
    @FXML
	public void cerrarAplicacion(MouseEvent event) {
		Stage stage = (Stage) btnSalir1.getScene().getWindow();
		stage.close();
	}
}