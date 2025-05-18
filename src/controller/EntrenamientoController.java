package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

import model.Combate;
import model.Turno;

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

	@FXML private Label lblVidaMiPoke;
	
    @FXML private Label lblVidaRival;

	private Pokemon miPokemon;
	private Pokemon pokemonRival;
	private Entrenador entrenador;
	private Stage stage;
	private MenuController menuController;
	
	private Combate combate;
	private int numeroTurno = 1;

	@FXML
	public void init(Entrenador entrenador, Stage stage, MenuController menuController) {
		this.entrenador = entrenador;
		this.stage = stage;
		this.menuController = menuController;

		// Cargamos el Pokémon principal del entrenador y un rival fijo (id=2)
		miPokemon = PokemonBD.obtenerPokemonPorIdConMovimientos(
				PokemonBD.obtenerPokemonPrincipal(entrenador.getIdEntrenador()).getId_pokemon());
		pokemonRival = PokemonBD.generarPokemonRivalAleatorio(miPokemon);
		combate = new Combate();

		cargarDatos();
	}

	/**
	 * Carga los movimientos del Pokémon en los botones
	 */
	private void cargarMovimientos() {

		List<Movimiento> movimientos = miPokemon.getMovPrincipales();

		// Usamos operador ternario para evitar errores si hay menos de 4 movimientos
		btnMov1.setText(movimientos.get(0).getNom_movimiento() + " " + movimientos.get(0).getPp_actual()
				+ "/" + movimientos.get(0).getPp_max());
		
		if (movimientos.size() > 1) {
			btnMov2.setText(movimientos.get(1).getNom_movimiento() + " " + movimientos.get(1).getPp_actual()
					+ "/" + movimientos.get(1).getPp_max());
		} else {
			btnMov2.setVisible(false);
		}

		if (movimientos.size() > 2) {
			btnMov3.setText(movimientos.get(2).getNom_movimiento() + " " + movimientos.get(2).getPp_actual() 
					+ "/" + movimientos.get(2).getPp_max());
		} else {
			btnMov3.setVisible(false);
		}

		if (movimientos.size() > 3) {
			btnMov4.setText(movimientos.get(3).getNom_movimiento() + " " + movimientos.get(3).getPp_actual()
					+ "/" + movimientos.get(3).getPp_max());
		} else {
			btnMov4.setVisible(false);
		}
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
		double vidaMiPoke = (double) miPokemon.getVitalidad() / miPokemon.getVitalidadMaxOBJ();
        double vidaRival = (double) pokemonRival.getVitalidad() / pokemonRival.getVitalidadMaxOBJ();

        hpPokemon.setProgress(vidaMiPoke);
        hpPokemonRival.setProgress(vidaRival);

        lblVidaMiPoke.setText(miPokemon.getVitalidad() + " / " + miPokemon.getVitalidadMaxOBJ());
        lblVidaRival.setText(pokemonRival.getVitalidad() + " / " + pokemonRival.getVitalidadMaxOBJ());
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
	    if (index >= movimientos.size()) return;

	    Movimiento mov = movimientos.get(index);

	    if (mov.getPp_actual() <= 0) {
	        mostrarAlerta("¡" + mov.getNom_movimiento() + " no tiene PP disponibles!");
	        return;
	    }

	    // Reducir PP en 1 y actualizar en BD
	    mov.setPp_actual(mov.getPp_actual() - 1);
	    MovimientoBD.actualizarPPMovimiento(miPokemon.getId_entrenador(), miPokemon.getId_pokemon(), mov.getId_movimiento(), mov.getPp_actual());

	    // Daño que hace el jugador al rival
	    int danioJugador = mov.getPotencia() != 0 ? mov.getPotencia() : 10;
	    int nuevaVidaRival = Math.max(0, pokemonRival.getVitalidad() - danioJugador);
	    pokemonRival.setVitalidad(nuevaVidaRival);

	    System.out.println(miPokemon.getNombre_pokemon() + " usa " + mov.getNom_movimiento());

	    // --- Turno rival atacando ---
	    Movimiento movRival = elegirMovimientoRival();
	    int danioRival = movRival.getPotencia() != 0 ? movRival.getPotencia() : 10;
	    int nuevaVidaJugador = Math.max(0, miPokemon.getVitalidad() - danioRival);
	    miPokemon.setVitalidad(nuevaVidaJugador);

	    System.out.println(pokemonRival.getNombre_pokemon() + " usa " + movRival.getNom_movimiento());

	    // Guardar turnos de ambos ataques en combate
	    String accionEntrenador = miPokemon.getNombre_pokemon() + " usa " + mov.getNom_movimiento() + ".";
	    String accionRival = pokemonRival.getNombre_pokemon() + " usa " + movRival.getNom_movimiento() + ".";
	    Turno turno = new Turno(combate.getNumeroCombate(), numeroTurno, accionEntrenador, accionRival);
	    combate.agregarTurno(turno);
	    numeroTurno++;

	    // Actualizar barras de vida
	    actualizarHP();

	    cargarMovimientos();

	    // Comprobar si alguno ha sido derrotado
	    if (nuevaVidaRival <= 0) {
	        mostrarAlerta("¡Has derrotado a " + pokemonRival.getNombre_pokemon() + "!");
	        pokemonRival = PokemonBD.generarPokemonRivalAleatorio(miPokemon);
	        cargarDatos();
	    }

	    if (nuevaVidaJugador <= 0) {
	        mostrarAlerta("¡Tu Pokémon ha sido derrotado!");
	        // Aquí puedes implementar la lógica de qué pasa cuando pierdes
	    }
	}

	/**
	 * Método para elegir un movimiento válido del rival (aleatorio y con PP)
	 */
	private Movimiento elegirMovimientoRival() {
	    List<Movimiento> movimientosRival = pokemonRival.getMovPrincipales();

	    // Filtrar movimientos con PP > 0
	    movimientosRival.removeIf(mov -> mov.getPp_actual() <= 0);

	    if (movimientosRival.isEmpty()) {
	        // Si no tiene movimientos con PP, crear uno por defecto
	        return new Movimiento(numeroTurno, "Ataque débil", 10, null, Integer.MAX_VALUE, numeroTurno, numeroTurno, null, null, numeroTurno, null, numeroTurno, null, numeroTurno, numeroTurno, numeroTurno); 
	    }

	    int index = (int) (Math.random() * movimientosRival.size());
	    Movimiento movElegido = movimientosRival.get(index);

	    // Reducir PP del movimiento rival usado
	    movElegido.setPp_actual(movElegido.getPp_actual() - 1);
	    MovimientoBD.actualizarPPMovimiento(pokemonRival.getId_entrenador(), pokemonRival.getId_pokemon(), movElegido.getId_movimiento(), movElegido.getPp_actual());

	    return movElegido;
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
	 * Método auxiliar para mostrar alertas informativas
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