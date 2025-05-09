package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Entrenador;
import model.Movimiento;
import model.Objeto;

public class EstadisticasController {
	
	private Entrenador entrenador;
	private Stage stage;
	private MenuController menuController;

	public void init(Entrenador ent, Stage stage, MenuController menuController) {
		this.menuController = menuController;
		this.stage = stage;
		this.entrenador = ent;


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
    private TableView<Objeto> tablaObjeto;

    @FXML
    private TableColumn<Objeto, Integer> colCantidadObj;

    @FXML
    private TableColumn<Objeto, String> colNombreMov;

    @FXML
    private TableColumn<Objeto, String> colNombreObj;

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

    }

    @FXML
    void quitarObjeto(ActionEvent event) {

    }

    @FXML
    void reproducirGrito(ActionEvent event) {

    }
    
    public void show() {
		stage.show();
	}

}
