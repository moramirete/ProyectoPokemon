package controller;

import java.util.ArrayList;

import bd.PokemonBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Entrenador;
import model.Pokemon;

public class VerEstadisticasEquipoController {
	
    private Entrenador entrenador;
    private Stage stage;
    private EquipoController equipoController;
    
    public void init(Entrenador entrenador, Stage stage, EquipoController equipoController) {
        this.entrenador = entrenador;
        this.stage = stage;
        this.equipoController = equipoController;
        
        cargarEquipo();
    }

    @FXML
    private Button btnVolver;

    @FXML
    private Button btnEstadisticas;

    @FXML
    private TableColumn<Pokemon, String> colNombre;

    @FXML
    private TableColumn<Pokemon, Integer> colNivel;

    @FXML
    private TableColumn<Pokemon, String> colTipo;

    @FXML
    private TableColumn<Pokemon, Integer> colTipo2;
    
    @FXML
    private TableView<Pokemon> tabPokemon;

    @FXML
    private Label txtPokemonCaja;
    
    private void cargarEquipo() {
        
        ArrayList<Pokemon> equipo = PokemonBD.obtenerEquipo(entrenador.getIdEntrenador());
        ObservableList<Pokemon> listaEquipo = FXCollections.observableArrayList(equipo);
        
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre_pokemon"));
        colNivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo1"));
        colTipo2.setCellValueFactory(new PropertyValueFactory<>("tipo2"));
       
        tabPokemon.setItems(listaEquipo);
    }

    @FXML
    void volveratras(ActionEvent event) {
        // Cerrar la ventana emergente
        stage.close();
    }
    
    @FXML
    private void abrirVerEstadisticas() {
    	
    }

}
