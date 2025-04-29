package controller;

import javafx.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import bd.PokemonBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Entrenador;
import model.Pokemon;

public class CambiarPorController {

    private Entrenador entrenador;
    private Stage stage;
    private EquipoController equipoController;
    private CajaController cajaController;
    private Pokemon pokemonSeleccionadoDeCaja;

    @FXML
    private Label txtPokemonCaja;

    @FXML
    private TableColumn<Pokemon, String> colNombre;

    @FXML
    private TableColumn<Pokemon, Integer> colNivel;

    @FXML
    private Button btnCambiarPor;

    @FXML
    private Button btnAgregarEquipo;

    @FXML
    private Button btnVolver;

    @FXML
    private TableColumn<Pokemon, String> colTipo;

    @FXML
    private TableColumn<Pokemon, Integer> colTipo2;

    @FXML
    private TableView<Pokemon> tabPokemon;


    public void init(Entrenador entrenador, Stage stage, EquipoController equipoController, CajaController cajaController, Pokemon pokemonSeleccionadoDeCaja) {
        this.entrenador = entrenador;
        this.stage = stage;
        this.equipoController = equipoController;
        this.cajaController = cajaController;
        this.pokemonSeleccionadoDeCaja = pokemonSeleccionadoDeCaja;

        
        txtPokemonCaja.setText("¿Qué quieres hacer con: " + pokemonSeleccionadoDeCaja.getNombre_pokemon() + "?");

        cargarEquipo();
        configurarBotones();
    }

    private void cargarEquipo() {
       
        ArrayList<Pokemon> equipo = PokemonBD.obtenerEquipo(entrenador.getIdEntrenador());
        ObservableList<Pokemon> listaEquipo = FXCollections.observableArrayList(equipo);

        
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre_pokemon"));
        colNivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));

       
        tabPokemon.setItems(listaEquipo);
    }

    private void configurarBotones() {
        if (tabPokemon.getItems().size() < 6) {
            btnAgregarEquipo.setVisible(true);
        } else {
            btnAgregarEquipo.setVisible(false);
        }
    }

    @FXML
    void cambiarPor(ActionEvent event) {
       
    	 Pokemon pokemonSeleccionadoDeEquipo = tabPokemon.getSelectionModel().getSelectedItem();

    	    if (pokemonSeleccionadoDeEquipo == null) {
    	        JOptionPane.showMessageDialog(null, "Selecciona un Pokémon del equipo para realizar el cambio.", "Error", JOptionPane.ERROR_MESSAGE);
    	        return;
    	    }

    	    // Verificar si el Pokémon seleccionado del equipo es el principal
    	    boolean esPrincipal = pokemonSeleccionadoDeEquipo.getEquipo() == 1;

    	    boolean cambiado;
    	    if (esPrincipal) {
    	        // Cambiar el Pokémon de la caja con el principal
    	        cambiado = PokemonBD.cambiarPokemonPrincipal(entrenador.getIdEntrenador(), pokemonSeleccionadoDeCaja, pokemonSeleccionadoDeEquipo);
    	    } else {
    	        // Cambiar el Pokémon de la caja con uno del equipo
    	        cambiado = PokemonBD.cambiarPokemonEquipo(entrenador.getIdEntrenador(), pokemonSeleccionadoDeCaja, pokemonSeleccionadoDeEquipo);
    	    }

    	    if (cambiado) {
    	        System.out.println("El Pokémon ha sido cambiado correctamente.");
    	        equipoController.inicializarEquipo(); // Actualizar el equipo en la interfaz principal
    	        stage.close(); // Cerrar la ventana emergente
    	        cajaController.volveratras(null); // Regresar a la pantalla de la caja
    	    } else {
    	        JOptionPane.showMessageDialog(null, "Error al cambiar el Pokémon en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
    	    }
    	
    }

    @FXML
    void agregarEquipo(ActionEvent event) {
        
    	boolean añadido = PokemonBD.añadirPokemonAlEquipo(entrenador.getIdEntrenador(), pokemonSeleccionadoDeCaja);

        if (añadido) {
        	JOptionPane.showMessageDialog(null, "El Pokémon ha sido añadido al equipo correctamente.", "Error", JOptionPane.INFORMATION_MESSAGE);
            equipoController.inicializarEquipo(); // Actualizar el equipo en la interfaz principal
            stage.close(); // Cerrar la ventana emergente
            cajaController.volveratras(null); // Regresar a la pantalla de la caja
        } else {
            System.err.println("Error al añadir el Pokémon al equipo en la base de datos.");
        }
    	
    }

    @FXML
    void volveratras(ActionEvent event) {
        // Cerrar la ventana emergente
        stage.close();
    }
}
