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
 * Controlador del menú principal de la aplicación Pokémon Super Nenes. Gestiona
 * la navegación hacia las distintas secciones del juego como Captura, Combate,
 * Crianza, etc., así como el cierre de sesión y salida del programa.
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
	private Button btnMochila;
	@FXML
	private Button btnTienda;
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
	 * @throws IOException Si ocurre un error al cargar el archivo FXML.
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

			System.out.println("Entrando a captura");

		} catch (IOException e) {
			System.out.println("Falla en la carga del archivo FXML.");
			e.printStackTrace();
		}
	}

	/**
	 * Abre la vista del Centro Pokémon.
	 *
	 * @param event Evento de acción al presionar el botón del Centro Pokémon.
	 * @throws IOException Si ocurre un error al cargar el archivo FXML.
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

			System.out.println("Entrando a centro");

		} catch (IOException e) {
			System.out.println("Falla en la carga del archivo FXML.");
			e.printStackTrace();
		}
	}

	/**
	 * Abre la vista de Combate.
	 *
	 * @param event Evento de acción al presionar el botón de Combate.
	 * @throws IOException Si ocurre un error al cargar el archivo FXML.
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

			System.out.println("Entrando a combate");

		} catch (IOException e) {
			System.out.println("Falla en la carga del archivo FXML.");
			e.printStackTrace();
		}
	}

	/**
	 * Abre la vista de Crianza.
	 *
	 * @param event Evento de acción al presionar el botón de Crianza.
	 * @throws IOException Si ocurre un error al cargar el archivo FXML.
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

			System.out.println("Entrando a crianza");

		} catch (IOException e) {
			System.out.println("Falla en la carga del archivo FXML.");
			e.printStackTrace();
		}
	}

	/**
	 * Abre la vista de Entrenamiento.
	 *
	 * @param event Evento de acción al presionar el botón de Entrenamiento.
	 * @throws IOException Si ocurre un error al cargar el archivo FXML.
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

			System.out.println("Entrando a entrenamiento");

		} catch (IOException e) {
			System.out.println("Falla en la carga del archivo FXML.");
			e.printStackTrace();
		}
	}

	/**
	 * Abre la vista del Equipo Pokémon.
	 *
	 * @param event Evento de acción al presionar el botón de Equipo.
	 * @throws IOException Si ocurre un error al cargar el archivo FXML.
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

			System.out.println("Entrando a equipo");

		} catch (IOException e) {
			System.out.println("Falla en la carga del archivo FXML.");
			e.printStackTrace();
		}
	}

	@FXML
	void abrirMochila(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/mochila.fxml"));
			Parent root = loader.load();

			MochilaController mochilaController = loader.getController();
			Scene scene = new Scene(root);
			Stage stage = new Stage();

			stage.setTitle("Pokémon Super Nenes - Equipo");
			stage.setScene(scene);
			mochilaController.init(entrenador, stage, this);

			stage.show();
			this.stage.close();

			System.out.println("Entrando a mochila");

		} catch (IOException e) {
			System.out.println("Falla en la carga del archivo FXML.");
			e.printStackTrace();
		}
	}

	@FXML
	void abrirTienda(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/tienda.fxml"));
			Parent root = loader.load();

			TiendaController tiendaController = loader.getController();
			Scene scene = new Scene(root);
			Stage stage = new Stage();

			stage.setTitle("Pokémon Super Nenes - Equipo");
			stage.setScene(scene);
			tiendaController.init(entrenador, stage, this);

			stage.show();
			this.stage.close();

			System.out.println("Entrando a tienda");

		} catch (IOException e) {
			System.out.println("Falla en la carga del archivo FXML.");
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
		System.out.println("Cerrando sesion");
		System.out.println("Abriendo login");
	}

	/**
	 * Inicializa el controlador con el entrenador, el escenario actual y el
	 * controlador del login.
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
		lblPokedollares.textProperty().bind(entrenador.pokedolaresProperty().asString());
	}

	/**
	 * Cierra completamente la aplicación. Pero antes te pregunta si deseas de
	 * verdad cerrar la aplicación
	 *
	 * @param event Evento del mouse al hacer clic sobre el botón de cerrar (ícono).
	 */
	@FXML
	public void cerrarAplicacion(MouseEvent event) {
		int opcion = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres cerrar el proyectoPokemon?");

		if (opcion == JOptionPane.YES_OPTION) {
			System.out.println("Se ha elegido la opcion de si");
			Stage stage = (Stage) btnSalir.getScene().getWindow();
			stage.close();
			System.out.println("Se ha cerrado la aplicacion correctamente");
		} else {
			System.out.println("Se ha elegido la opcion de no");
		}
	}

	/**
	 * Muestra la ventana del menú principal.
	 */
	public void show() {
		stage.show();
	}
}
