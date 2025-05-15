package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import bd.PokemonBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    	
    	Pokemon pokSeleccionado = tabPokemon.getSelectionModel().getSelectedItem();

		if (pokSeleccionado == null) {
			JOptionPane.showMessageDialog(null, "Error: Primero selecciona el Pokémon de la caja");
			return;
		}

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/estadisticas.fxml"));
			Parent root = loader.load();

			EstadisticasController estadisticasController = loader.getController();

			Stage nuevaStage = new Stage();

			estadisticasController.init(entrenador, nuevaStage, null, pokSeleccionado);

			// Mostrar la nueva escena
			Scene scene = new Scene(root);
			nuevaStage.setScene(scene);
			nuevaStage.setTitle("Estadísticas de " + pokSeleccionado.getNombre_pokemon());
			nuevaStage.show();

		} catch (IOException e) {
			System.err.println("Error al cargar la ventana de estadísticas.");
			e.printStackTrace();
		}
	
    }

}
