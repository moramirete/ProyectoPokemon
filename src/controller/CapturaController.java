package controller;

import javafx.stage.Stage;
import model.Entrenador;
import model.Pokemon;
import bd.BDConecction;
import bd.PokemonBD;
import bd.MochilaBD;
import model.Mochila;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import javax.swing.JOptionPane;

public class CapturaController {

	private Entrenador entrenador;
	private Stage stage;
	private MenuController menuController;
	private Pokemon pokemonCreado;
	
	private int pokebolas = 0;
	private final int ID_POKEBOLA = 8;

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

	@FXML
	private Label lblPokebolas;

	public void init(Entrenador ent, Stage stage, MenuController menuController) {
		this.menuController = menuController;
		this.stage = stage;
		this.entrenador = ent;
		
		cargarPokebolas();
	}

	private void cargarPokebolas() {
		try (Connection conexion = BDConecction.getConnection()) {
			ArrayList<Mochila> mochila = MochilaBD.obtenerMochila(entrenador.getIdEntrenador());
			for (Mochila objeto : mochila) {
				if(objeto.getIdObjeto() == ID_POKEBOLA) {
					pokebolas = objeto.getCantidad();
					break;
				}
			}
			actualizarLblPokebolas();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void actualizarLblPokebolas() {
		lblPokebolas.setText(String.valueOf(pokebolas));
		lblPokebolas.setStyle("-fx-font-size: 32px; -fx-text-fill: #ff0000;");
	}
	
	private void actualizarPokebolasBD() {
		try (Connection conexion = BDConecction.getConnection()){
			MochilaBD.actualizarCantidad(entrenador.getIdEntrenador(), ID_POKEBOLA, pokebolas);
		
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	void cerrarCaptura(ActionEvent event) {
		System.out.println("Se ha accionado el boton de salir");
		menuController.show();
		this.stage.close();
		System.out.println("Se ha salido de captura correctamente");
	}

	@FXML
	void generarPokemon(ActionEvent event) {
		System.out.println("Se ha accionado el boton de generar");

		BDConecction con = new BDConecction();

		Connection conexion = con.getConnection();

		try {

			pokemonCreado = PokemonBD.generarPokemonCaptura(entrenador.getIdEntrenador(), conexion);

			if (pokemonCreado != null) {
				String archivo = pokemonCreado.getNum_pokedex() + ".png";
				String ruta = "multimedia/imagenes/delanteras/" + archivo;
				File file = new File(ruta);

				if (!file.exists()) {
					System.out.println("No se encontró la imagen: " + archivo);
					lblPokemon.setText("No se pudo cargar" + archivo);
					lblPokemon.setGraphic(null);

				}

				Image imagen = new Image(file.toURI().toString());
				ImageView imageView = new ImageView(imagen);
				imageView.setFitWidth(120);
				imageView.setFitHeight(120);
				imageView.setPreserveRatio(true);

				lblPokemon.setText("");
				lblPokemon.setGraphic(imageView);

			} else {
				lblPokemon.setText("Error al generar el pokemon.");
			}

		} catch (Exception e) {
			System.err.println("Error generando Pokemon: " + e.getMessage());
			e.printStackTrace();
			lblPokemon.setText("Error al conectar con la Base de Datos");
		}

		/*
		 * final String[] pokemon = new String[151]; for(int i = 0; i < 151; i++) {
		 * pokemon[i] = (i + 1) + ".png";
		 * 
		 * }
		 * 
		 * int index = new Random().nextInt(pokemon.length); String archivo =
		 * pokemon[index];
		 * 
		 * String ruta = "multimedia/imagenes/delanteras/" +archivo;
		 * 
		 * File file = new File(ruta);
		 * 
		 * if (!file.exists()) { System.out.println(" No se a encontrado la imagen: " +
		 * archivo); lblPokemon.setText("No se pudo cargar " + archivo);
		 * lblPokemon.setGraphic(null); return; }
		 * 
		 * Image imagen = new Image(file.toURI().toString()); ImageView imageView = new
		 * ImageView(imagen); imageView.setFitWidth(120); imageView.setFitHeight(120);
		 * imageView.setPreserveRatio(true);
		 * 
		 * String nombre = archivo.replace(".png",""); lblPokemon.setText("");
		 * lblPokemon.setGraphic(imageView);
		 */

	}

	@FXML
	void capturarPokemon(ActionEvent event) {
		System.out.println("Se ha accionado el boton de capturar");
		
		
		
		
		if (pokebolas <= 0) {
			JOptionPane.showMessageDialog(null,"Te has quedado sin Pokeballs", "Sin Pokeballs", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		BDConecction con = new BDConecction();

		Connection conexion = con.getConnection();
		
		
		
		

		if (pokemonCreado != null) {

			Random rd = new Random();
			int prob = rd.nextInt(3);

			
			
			if (prob == 0) {
				JOptionPane.showMessageDialog(null, "El pokemon se ha salido de la bola", "Fallaste", 1);
			} else {
				
				TextInputDialog dialogo = new TextInputDialog(pokemonCreado.getNombre_pokemon());
				dialogo.setTitle("Captura Exitosa");
				dialogo.setHeaderText("Has capturado un " + pokemonCreado.getNombre_pokemon());
				dialogo.setTitle("¿Deseas cambiarle el nombre a tu pokemon?");
				Optional<String> nuevoNombre = dialogo.showAndWait();
				
				nuevoNombre.ifPresent(nombre -> pokemonCreado.setNombre_pokemon(nombre));
				
				try (Connection conexion1 = BDConecction.getConnection()){
					PokemonBD.guardarPokemonCaptura(pokemonCreado, conexion1);
					System.out.println("Pokemon guardado en la BBDD");
					lblPokemon.setText("Has capturado un : " + pokemonCreado.getNombre_pokemon());
					JOptionPane.showMessageDialog(null,"El pokemon ha sido capturado correctamente, esta situado en la caja", "Capturado", 1);
					lblPokemon.setGraphic(null);
					pokemonCreado = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			pokebolas--;
			actualizarPokebolasBD();
			actualizarLblPokebolas();
			
			if (pokebolas == 0) {
				JOptionPane.showMessageDialog(null,"Te has quedado sin pokebolas", "Sin pokebolas",JOptionPane.WARNING_MESSAGE);
			}
			
		} else {
			lblPokemon.setText("Primero tienes que generar un Pokemon.");
		}

		lblPokemon.setGraphic(null);
		lblPokemon.setText("");

	}

}