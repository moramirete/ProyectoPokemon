package controller;

import java.io.IOException;

import javax.swing.JOptionPane;

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

/**
 * Controlador del menú principal de la aplicación Pokémon Super Nenes.
 * Gestiona la navegación hacia las distintas secciones del juego como Captura,
 * Combate, Crianza, etc., así como el cierre de sesión y salida del programa.
 */
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
    private ImageView imgBocadillo;

	@FXML
	private Label lblJugador;
	@FXML
	private Label lblPokedollares;
	@FXML
	private Label plantillaPokeDolares;
	@FXML
	private Label plantillaUsuario;

	/**
	 * Abre la vista de Captura.
	 *
	 * @param event Evento de acción al presionar el botón de Captura.
	 */
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

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Abre la vista del Centro Pokémon.
	 *
	 * @param event Evento de acción al presionar el botón del Centro Pokémon.
	 */
	@FXML
	void abrirCentro(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/centro.fxml"));
			Parent root = loader.load();

			CentroController centroController = loader.getController();
			Scene scene = new Scene(root);
			Stage stage = new Stage();

			stage.setTitle("Pokémon Super Nenes - Centro Pokemon");
			stage.setScene(scene);
			centroController.init(entrenador, stage, this);

			stage.show();
			this.stage.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Abre la vista de Combate.
	 *
	 * @param event Evento de acción al presionar el botón de Combate.
	 */
	@FXML
	void abrirCombate(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/combate.fxml"));
			Parent root = loader.load();

			CombateController combateController = loader.getController();
			Scene scene = new Scene(root);
			Stage stage = new Stage();

			stage.setTitle("Pokémon Super Nenes - Combate");
			stage.setScene(scene);
			combateController.init(entrenador, stage, this);

			stage.show();
			this.stage.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Abre la vista de Crianza.
	 *
	 * @param event Evento de acción al presionar el botón de Crianza.
	 */
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

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Abre la vista de Entrenamiento.
	 *
	 * @param event Evento de acción al presionar el botón de Entrenamiento.
	 */
	@FXML
	void abrirEntrenamiento(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/entrenamiento.fxml"));
			Parent root = loader.load();

			EntrenamientoController entrenamientoController = loader.getController();
			Scene scene = new Scene(root);
			Stage stage = new Stage();

			stage.setTitle("Pokémon Super Nenes - Entrenamiento");
			stage.setScene(scene);
			entrenamientoController.init(entrenador, stage, this);

			stage.show();
			this.stage.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Abre la vista del Equipo Pokémon.
	 *
	 * @param event Evento de acción al presionar el botón de Equipo.
	 */
	@FXML
	void abrirEquipo(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/equipo.fxml"));
			Parent root = loader.load();

			EquipoController equipoController = loader.getController();
			Scene scene = new Scene(root);
			Stage stage = new Stage();

			stage.setTitle("Pokémon Super Nenes - Equipo");
			stage.setScene(scene);
			equipoController.init(entrenador, stage, this);

			stage.show();
			this.stage.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cierra el menú actual y retorna a la pantalla de inicio de sesión.
	 *
	 * @param event Evento de acción al presionar el botón de Salir (cerrar sesión).
	 */
	@FXML
	public void cerrarMenu(ActionEvent event) {
		loginController.show();
		this.stage.close();
	}

	/**
	 * Inicializa el controlador con el entrenador, el escenario actual y el controlador del login.
	 *
	 * @param ent             Entrenador actual.
	 * @param stage           Ventana principal del menú.
	 * @param loginController Controlador de la vista de login.
	 */
	public void init(Entrenador ent, Stage stage, LoginController loginController) {
		this.loginController = loginController;
		this.stage = stage;
		this.entrenador = ent;

		lblJugador.setText(entrenador.getUsuario());
		lblPokedollares.setText(Integer.toString(entrenador.getPokedolares()));
	}

	/**
	 * Cierra completamente la aplicación. Pero antes te pregunta si deseas de verdad cerrar la aplicación
	 *
	 * @param event Evento del mouse al hacer clic sobre el botón de cerrar (ícono).
	 */
	@FXML
	public void cerrarAplicacion(MouseEvent event) {
		int opcion = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres cerrar el proyectoPokemon?");
		
		if(opcion == JOptionPane.YES_OPTION) {
			Stage stage = (Stage) btnSalir.getScene().getWindow();
			stage.close();
		}
	}

	/**
	 * Muestra la ventana del menú principal.
	 */
	public void show() {
		stage.show();
	}
}
