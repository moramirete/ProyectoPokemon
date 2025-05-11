package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import bd.MochilaBD;
import bd.ObjetoBD;
import bd.PokemonBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.Entrenador;
import model.Movimiento;
import model.Objeto;
import model.ObjetoEnMochila;
import model.Pokemon;

public class EstadisticasController {

	private Entrenador entrenador;
	private Stage stage;
	private MenuController menuController;
	private Pokemon pokemon;

	public void init(Entrenador ent, Stage stage, MenuController menuController, Pokemon pokemon) {
		this.menuController = menuController;
		this.stage = stage;
		this.entrenador = ent;
		this.pokemon = pokemon;

		inicializarEstadisticas();
	}

	@FXML
	private Button btnCambiarMov1;

	@FXML
	private Button btnCambiarMov2;

	@FXML
	private Button btnCambiarMov3;

	@FXML
	private Button btnCambiarMov4;

	@FXML
	private Button btnCambiarObj;

	@FXML
	private Button btnQuitarObjeto;

	@FXML
	private Button btnReproducirGrito;

	@FXML
	private Button btnReproducirGrito2;

	@FXML
	private Button btnReproducirGrito21;

	@FXML
	private TableView<Movimiento> tablaMovimiento;

	@FXML
	private TableView<ObjetoEnMochila> tablaObjeto;

	@FXML
	private TableColumn<ObjetoEnMochila, Integer> colCantidadObj;

	@FXML
	private TableColumn<ObjetoEnMochila, String> colNombreMov;

	@FXML
	private TableColumn<ObjetoEnMochila, String> colNombreObj;

	@FXML
	private TableColumn<Movimiento, String> colTipoMov;

	@FXML
	private TableColumn<Movimiento, String> colTipoMovMov;

	@FXML
	private ImageView imgFondo;

	@FXML
	private ImageView imgFondo1;

	@FXML
	private ImageView imgFondo2;

	@FXML
	private ImageView imgPokemon;

	@FXML
	private ImageView imgPokemon1;

	@FXML
	private ImageView imgPokemon11;

	@FXML
	private ImageView imgSexo;

	@FXML
	private ImageView imgTipo1;

	@FXML
	private ImageView imgTipo11;

	@FXML
	private ImageView imgTipo111;

	@FXML
	private ImageView imgTipo2;

	@FXML
	private ImageView imgTipo21;

	@FXML
	private ImageView imgTipo211;

	@FXML
	private ProgressBar pbVitalidad;

	@FXML
	private ProgressBar pbVitalidad1;

	@FXML
	private ProgressBar pbVitalidad11;

	@FXML
	private Label txtAtaque;

	@FXML
	private Label txtAtaqueEspecial;

	@FXML
	private Label txtDefensa;

	@FXML
	private Label txtDefensaEspecial;

	@FXML
	private Label txtFertilidad;

	@FXML
	private Label txtMovimiento1;

	@FXML
	private Label txtMovimiento2;

	@FXML
	private Label txtMovimiento3;

	@FXML
	private Label txtMovimiento4;

	@FXML
	private Label txtNivel;

	@FXML
	private Label txtNivel1;

	@FXML
	private Label txtNivel11;

	@FXML
	private Label txtNombre;

	@FXML
	private Label txtNombre1;

	@FXML
	private Label txtNombre11;

	@FXML
	private Label txtNumPokedex;

	@FXML
	private Label txtNumPokedex1;

	@FXML
	private Label txtNumPokedex11;

	@FXML
	private Label txtObjetoEquipado;

	@FXML
	private Label txtVelocidad;

	@FXML
	private Label txtVitalidad;

	@FXML
	private Label txtVitalidad1;

	@FXML
	private Label txtVitalidad11;

	public void inicializarEstadisticas() {
		if (pokemon != null) {
			
			//Primera Pantalla - Informacion
			String fondo = PokemonBD.obtenerRutaImagenFondo(pokemon);
			Image imgfondo = new Image(fondo);
			imgFondo.setImage(imgfondo);
			
			txtNombre.setText(pokemon.getNombre_pokemon());
			txtNivel.setText(String.valueOf(pokemon.getNivel()));
			
			String tipo1 = PokemonBD.obtenerRutaImagenTipo1(pokemon);
			Image imgtipo1 = new Image(tipo1);
			imgTipo1.setImage(imgtipo1);
			
			if (pokemon.getTipo2() == null || pokemon.getTipo2().isEmpty()) {
	            imgTipo2.setImage(null);
	        } else {
	        	String tipo2 = PokemonBD.obtenerRutaImagenTipo2(pokemon);
				Image imgtipo2 = new Image(tipo2);
				imgTipo2.setImage(imgtipo2);
	        }
			
			txtNumPokedex.setText(String.valueOf(pokemon.getNum_pokedex()));
			String rutaImagen = PokemonBD.obtenerRutaImagen(pokemon);
			Image imagen = new Image(rutaImagen);
			imgPokemon.setImage(imagen);
			
			txtVitalidad.setText(String.valueOf(pokemon.getVitalidad()) + "/" +  String.valueOf(pokemon.getVitalidadMax()));
			actualizarBarraVida(pbVitalidad, pokemon.getVitalidad(), pokemon.getVitalidadMax());
			
			txtAtaque.setText(String.valueOf(pokemon.getAtaque()));
			txtDefensa.setText(String.valueOf(pokemon.getDefensa()));
			txtAtaqueEspecial.setText(String.valueOf(pokemon.getAtaque_especial()));
			txtDefensaEspecial.setText(String.valueOf(pokemon.getDefensa_especial()));
			txtVelocidad.setText(String.valueOf(pokemon.getVelocidad()));
			txtFertilidad.setText(String.valueOf(pokemon.getFertilidad()));
			
			//Segunda Pantalla - Movimientos
			String fondo2 = PokemonBD.obtenerRutaImagenFondo(pokemon);
			Image imgfondo2 = new Image(fondo);
			imgFondo2.setImage(imgfondo);
			
			txtNombre1.setText(pokemon.getNombre_pokemon());
			txtNivel1.setText(String.valueOf(pokemon.getNivel()));
			
			String tipo11 = PokemonBD.obtenerRutaImagenTipo1(pokemon);
			Image imgtipo11 = new Image(tipo11);
			imgTipo11.setImage(imgtipo11);
			
			if (pokemon.getTipo2() == null || pokemon.getTipo2().isEmpty()) {
	            imgTipo2.setImage(null);
	        } else {
	        	String tipo22 = PokemonBD.obtenerRutaImagenTipo2(pokemon);
				Image imgtipo22 = new Image(tipo22);
				imgTipo21.setImage(imgtipo22);
	        }
			
			txtNumPokedex1.setText(String.valueOf(pokemon.getNum_pokedex()));
			String rutaImagen1 = PokemonBD.obtenerRutaImagen(pokemon);
			Image imagen1 = new Image(rutaImagen1);
			imgPokemon1.setImage(imagen);
			
			txtVitalidad1.setText(String.valueOf(pokemon.getVitalidad()) + "/" +  String.valueOf(pokemon.getVitalidadMax()));
			actualizarBarraVida(pbVitalidad1, pokemon.getVitalidad(), pokemon.getVitalidadMax());
			
			
			
			//Tercera Pantalla - Objetos
			String fondo1 = PokemonBD.obtenerRutaImagenFondo(pokemon);
			Image imgfondo1 = new Image(fondo);
			imgFondo1.setImage(imgfondo);
			
			txtNombre11.setText(pokemon.getNombre_pokemon());
			txtNivel11.setText(String.valueOf(pokemon.getNivel()));
			
			String tipo111 = PokemonBD.obtenerRutaImagenTipo1(pokemon);
			Image imgtipo111 = new Image(tipo111);
			imgTipo111.setImage(imgtipo111);
			
			if (pokemon.getTipo2() == null || pokemon.getTipo2().isEmpty()) {
	            imgTipo2.setImage(null);
	        } else {
	        	String tipo222 = PokemonBD.obtenerRutaImagenTipo2(pokemon);
				Image imgtipo222 = new Image(tipo222);
				imgTipo211.setImage(imgtipo222);
	        }
			
			txtNumPokedex11.setText(String.valueOf(pokemon.getNum_pokedex()));
			String rutaImagen11 = PokemonBD.obtenerRutaImagen(pokemon);
			Image imagen11 = new Image(rutaImagen1);
			imgPokemon11.setImage(imagen);
			
			txtVitalidad11.setText(String.valueOf(pokemon.getVitalidad()) + "/" +  String.valueOf(pokemon.getVitalidadMax()));
			actualizarBarraVida(pbVitalidad11, pokemon.getVitalidad(), pokemon.getVitalidadMax());
			
			txtObjetoEquipado.setText(ObjetoBD.obtenerNombreObjetoPorId(pokemon.getId_objeto()));
			
			actualizarTablaObjetos();
			colNombreObj.setCellValueFactory(new PropertyValueFactory<>("nombreObjeto"));
			colCantidadObj.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
			
		}
	}
	
	public void actualizarTablaObjetos() {
		ArrayList<ObjetoEnMochila> objetos = MochilaBD.obtenerContenidoTablaObjetos(entrenador.getIdEntrenador());
		ObservableList<ObjetoEnMochila> lista = FXCollections.observableArrayList(objetos);
		tablaObjeto.setItems(lista);
	}
	
	public void actualizarBarraVida(ProgressBar barra, double vidaActual, double vidaMaxima) {
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
	}

	@FXML
	void cambiarMov1(ActionEvent event) {

		
		
	}

	@FXML
	void cambiarMov2(ActionEvent event) {

		
		
	}

	@FXML
	void cambiarMov3(ActionEvent event) {

		
		
	}

	@FXML
	void cambiarMov4(ActionEvent event) {

		
		
	}

	@FXML
	void cambiarObj(ActionEvent event) {

		ObjetoEnMochila objetoSeleccionado = tablaObjeto.getSelectionModel().getSelectedItem();
		
		if (objetoSeleccionado == null) {
			JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún Objeto.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		
		
	}

	@FXML
	void quitarObjeto(ActionEvent event) {

		
		
	}

	@FXML
	void reproducirGrito(ActionEvent event) {
		
		// Construir la ruta del archivo de sonido
	    String numPokedex = String.valueOf(pokemon.getNum_pokedex());
	    
	    String numPokedexEditado;
	    
	    if (pokemon.getNum_pokedex() < 10) {
	        numPokedexEditado = "00" + numPokedex;
	    } else if (pokemon.getNum_pokedex() < 100) {
	        numPokedexEditado = "0" + numPokedex;
	    } else {
	        numPokedexEditado = numPokedex;
	    }
	    
	    String rutaSonido = "multimedia/sonidos/Pokemon/" + numPokedexEditado + ".wav";

	    File archivoSonido = new File(rutaSonido);

	    if (!archivoSonido.exists()) {
	        System.err.println("El archivo de sonido no existe: " + rutaSonido);
	        return;
	    }

	    try {
	        // Crear el objeto Media y MediaPlayer
	        Media media = new Media(archivoSonido.toURI().toString());
	        MediaPlayer mediaPlayer = new MediaPlayer(media);

	        // Reproducir el sonido
	        mediaPlayer.play();
	        System.out.println("Reproduciendo grito del Pokémon: " + pokemon.getNombre_pokemon());
	    } catch (Exception e) {
	        System.err.println("Error al reproducir el sonido: " + e.getMessage());
	        e.printStackTrace();
	    }
		
	}

	public void show() {
		stage.show();
	}

}
