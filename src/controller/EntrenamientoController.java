package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import model.Entrenador;
import model.Movimiento;
import model.Pokemon;

import java.io.IOException;
import java.util.List;
import bd.PokemonBD;
import bd.MovimientoBD;
import controller.cambiarPokemonCombateController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class EntrenamientoController {

	@FXML
	private ImageView imgFondo;
	@FXML
	private ImageView imgPokemon;
	@FXML
	private ImageView imgPokeRival;
	@FXML
	private Button btnSalir;
	@FXML
	private Button btnPokemon;
	@FXML
	private Button btnMov1;
	@FXML
	private Button btnMov2;
	@FXML
	private Button btnMov3;
	@FXML
	private Button btnMov4;
	@FXML
	private ProgressBar hpPokemon;
	@FXML
	private ProgressBar hpPokemonRival;

	@FXML
	private ProgressBar expPokemon;

	private Pokemon miPokemon;
	private Pokemon pokemonRival;
	private Entrenador entrenador;
	private Stage stage;
	private MenuController menuController;

	@FXML
	public void init(Entrenador entrenador, Stage stage, MenuController menuController) {
		this.entrenador = entrenador;
		this.stage = stage;
		this.menuController = menuController;

		// Cargamos el Pokémon principal del entrenador y un rival fijo (id=2)
		miPokemon = PokemonBD.obtenerPokemonPorIdConMovimientos(PokemonBD.obtenerPokemonPrincipal(entrenador.getIdEntrenador()).getId_pokemon());
		pokemonRival = PokemonBD.generarPokemonRivalAleatorio();

		cargarDatos();
	}

	/**
	 * Carga los movimientos del Pokémon en los botones
	 */
	private void cargarMovimientos() {

		List<Movimiento> movimientos = miPokemon.getMovPrincipales();

		// Usamos operador ternario para evitar errores si hay menos de 4 movimientos
		btnMov1.setText(movimientos.size() > 0 ? movimientos.get(0).getNom_movimiento() : "Vacío");
		btnMov2.setText(movimientos.size() > 1 ? movimientos.get(1).getNom_movimiento() : "Vacío");
		btnMov3.setText(movimientos.size() > 2 ? movimientos.get(2).getNom_movimiento() : "Vacío");
		btnMov4.setText(movimientos.size() > 3 ? movimientos.get(3).getNom_movimiento() : "Vacío");
	}

	/**
	 * Carga los datos de los Pokémon y actualiza la interfaz
	 */
	private void cargarDatos() {
		if (miPokemon == null || pokemonRival == null) {
			mostrarAlerta("No se pudo cargar uno de los Pokémon.");
			return;
		}

		// Obtenemos las rutas de las imágenes para mostrar
		String rutaMiPoke = PokemonBD.obtenerRutaImagen(miPokemon);
		String rutaRival = PokemonBD.obtenerRutaImagen(pokemonRival);

		// Mostramos las imágenes si existen las rutas
		if (rutaMiPoke != null)
			imgPokemon.setImage(new Image(getClass().getResourceAsStream(rutaMiPoke)));

		if (rutaRival != null)
			imgPokeRival.setImage(new Image(getClass().getResourceAsStream(rutaRival)));

		actualizarHP(); // Actualiza las barras de vida
		cargarMovimientos(); // Carga los nombres de los movimientos en los botones
	}

	/**
	 * Actualiza el progreso de las barras de vida de ambos Pokémon
	 */
	private void actualizarHP() {
		double progresoMiPoke = (miPokemon.getVitalidadMaxOBJ() != 0)
				? (miPokemon.getVitalidadOBJ() * 1.0 / miPokemon.getVitalidadMaxOBJ())
				: 0.0;

		double progresoRival = (pokemonRival.getVitalidadMaxOBJ() != 0)
				? (pokemonRival.getVitalidadOBJ() * 1.0 / pokemonRival.getVitalidadMaxOBJ())
				: 0.0;

		hpPokemon.setProgress(progresoMiPoke);
		hpPokemonRival.setProgress(progresoRival);
	}

	// Métodos que se llaman al pulsar cada botón de movimiento
	@FXML
	public void hacerMov1(ActionEvent event) {
		usarMovimiento(0);
	}

	@FXML
	public void hacerMov2(ActionEvent event) {
		usarMovimiento(1);
	}

	@FXML
	public void hacerMov3(ActionEvent event) {
		usarMovimiento(2);
	}

	@FXML
	public void hacerMov4(ActionEvent event) {
		usarMovimiento(3);
	}

	/**
	 * Método que ejecuta el movimiento seleccionado, causando daño al rival
	 * 
	 * @param index índice del movimiento seleccionado
	 */
	private void usarMovimiento(int index) {
		List<Movimiento> movimientos = miPokemon.getMovPrincipales();
		if (index >= movimientos.size())
			return; // Si no existe ese movimiento, no hacer nada

		Movimiento mov = movimientos.get(index);

		// Si el movimiento no tiene potencia, damos daño base 10
		int danio = mov.getPotencia() != 0 ? mov.getPotencia() : 10;

		// Restamos vida al Pokémon rival sin que baje de 0
		pokemonRival.setVitalidad(Math.max(0, pokemonRival.getVitalidad() - danio));
		actualizarHP(); // Actualizamos barras

		// mostramos mensaje de victoria
		if (pokemonRival.getVitalidad() <= 0) {
			mostrarAlerta("¡Has ganado el entrenamiento!");
		}
	}

	/**
	 * Método para cambiar Pokémon (no implementado)
	 */
	@FXML
	public void cambiarPoke(ActionEvent event) {
		try {
			// Cargamos el FXML de cambiar Pokémon
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/cambiarPokemonCombate.fxml"));
			Parent root = loader.load();

			// Obtenemos el controlador y le pasamos datos necesarios
			cambiarPokemonCombateController controlador = loader.getController();
			controlador.init(entrenador, miPokemon, this);

			// Creamos una nueva ventana para cambiar Pokémon
			Stage nuevaVentana = new Stage();
			nuevaVentana.setTitle("Cambiar Pokémon del equipo");
			nuevaVentana.setScene(new Scene(root));
			nuevaVentana.initOwner(this.stage);
			nuevaVentana.show();

		} catch (IOException e) {
			e.printStackTrace();
			mostrarAlerta("Error al abrir la ventana de cambio de Pokémon.");
		}
	}

	/**
	 * Recarga los datos con el nuevo Pokémon seleccionado tras cambiar
	 * 
	 * @param idNuevo ID del nuevo Pokémon principal
	 */
	public void recargarConNuevoPokemon(int idNuevo) {
		miPokemon = PokemonBD.obtenerPokemonPorIdConMovimientos(idNuevo);
		// vuelve a mostrar imagen, vida y movimientos
		cargarDatos();
	}

	/**
	 * Método para salir de la aplicación (cerrar)
	 */
	@FXML
	public void salir(ActionEvent event) {
		System.out.println("Se ha accionado el boton de salir");
		menuController.show();
		this.stage.close();
		System.out.println("Se ha salido de captura correctamente");
	}

	/**
	 * Método auxiliar para mostrar alertas informativas al usuario
	 * 
	 * @param mensaje texto a mostrar en la alerta
	 */
	private void mostrarAlerta(String mensaje) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Entrenamiento");
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		alert.showAndWait();
	}
}
