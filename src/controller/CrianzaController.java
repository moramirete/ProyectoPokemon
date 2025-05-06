package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import bd.BDConecction;
import bd.MochilaBD;
import bd.PokemonBD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Entrenador;
import model.Pokemon;

public class CrianzaController {

	private Entrenador entrenador;
	private Stage stage;
	private MenuController menuController;
	
	
    @FXML
    private Button btnAbrirHuevo;

    @FXML
    private Button btnIncubar;

    @FXML
    private Button btnSalir;

    @FXML
    private Button btnSeleccionarPokemon1;

    @FXML
    private Button btnSeleccionarPokemon2;

    @FXML
    private ImageView imgHuevo;
    
    @FXML
    private Label lblHuevos;

    @FXML
    private Label lblPokemon;
    
    public void init(Entrenador ent, Stage stage, MenuController menuController) {
    	this.menuController = menuController;
    	this.stage = stage;
    	this.entrenador = ent;
    }
    
    @FXML
    void abrirHuevo(ActionEvent event) {
    	
    }

    
    @FXML
    void botonIncubar(ActionEvent event) {

    }

    @FXML
    void cerrarCrianza(ActionEvent event) {
    	System.out.println("Se ha accionado el boton de salir");
		menuController.show();
		this.stage.close();
		System.out.println("Se ha salido de captura correctamente");
    }

    @FXML
    void seleccionarPokemon1(ActionEvent event) {
    	
    	List<Pokemon> pokemonMacho = obtenerPokemonMacho(entrenador.getIdEntrenador());

    	if (!pokemonMacho.isEmpty()) {
    		Pokemon pokemonSeleccionado = pokemonMacho.get(0);
    		
    		lblPokemon.setText(pokemonSeleccionado.getNombre_pokemon());
    		
    		String rutaImagen = PokemonBD.obtenerRutaImagen(pokemonSeleccionado);
    		
    		cargarImagenVista(rutaImagen);

    	}else {
    		
    		lblPokemon.setText("No tienes pokemon machos disponibles.");
    	}
    }

	@FXML
    void seleccionarPokemon2(ActionEvent event) {

    }

	private List<Pokemon> obtenerPokemonMacho(int idEntrenador) {
		List<Pokemon> pokemonList = PokemonBD.obtenerEquipo(idEntrenador);
		
		
		return pokemonList.stream().filter(pokemon -> pokemon.getSexo() == 'M').collect(Collectors.toList());
	}
	
	
	
	private void cargarImagenVista(String rutaImagen) {
		
		
	}

}