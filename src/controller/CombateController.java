package controller;

import javafx.scene.control.Label;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import bd.EntrenadorBD;
import bd.MovimientoBD;
import bd.PokemonBD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Combate;
import model.Entrenador;
import model.Movimiento;
import model.Pokemon;
import model.Turno;

/**
 * Controlador de la vista de combate Pokémon. Gestiona el flujo del combate,
 * acciones del jugador y del rival, y la experiencia. Incluye atributos y
 * métodos adicionales para hacerlo más "relleno".
 */
public class CombateController {

	@FXML
	private Button btnDescansar;

	@FXML
	private Button btnHuir;

	@FXML
	private Button btnMov1;

	@FXML
	private Button btnMov2;

	@FXML
	private Button btnMov3;

	@FXML
	private Button btnMov4;

	@FXML
	private Button btnPokemon;

	@FXML
	private ImageView imagenFondo;

	@FXML
	private ImageView imgParteAbajo;

	@FXML
	private ImageView imgPoke;

	@FXML
	private ImageView imgPokeRival;

	@FXML
	private Label lblNivel;

	@FXML
	private Label lblNivelRival;

	@FXML
	private Label lblNombrePoke;

	@FXML
	private Label lblNombrePokeRival;

	@FXML
	private Label lblVitalidad;

	@FXML
	private Label lblVitalidadMax;

	@FXML
	private ProgressBar pbPokemonExp;

	@FXML
	private ProgressBar pbPokemonRival;

	@FXML
	private ProgressBar pbPokemonVida;

	@FXML
	private TextArea logCombate;

	private Entrenador entrenador;
	private Stage stage;
	private MenuController menuController;

	private List<Pokemon> rival;
	private List<Pokemon> equipo;
	public Pokemon pokEquipo;
	public Pokemon pokRival;
	private Combate combate;

	private Turno turnoActual;
	private int contadorTurno = 1;

	/**
	 * Inicializa el controlador con el entrenador, la ventana y el menú principal.
	 * Configura los equipos y comienza el combate.
	 */
	public void init(Entrenador ent, Stage stage, MenuController menuController) {
		this.menuController = menuController;
		this.stage = stage;
		this.entrenador = ent;

		equipo = PokemonBD.obtenerEquipo(entrenador.getIdEntrenador());
		pokEquipo = equipo.get(0);
		rival = PokemonBD.equipoRival(equipo);
		pokRival = rival.get(0);

		if (equipo.isEmpty()) {
			System.out.println("El equipo del usuario está vacío.");
			return;
		}

		if (rival.isEmpty()) {
			System.out.println("El equipo rival está vacío.");
			return;
		}
		cargarDatos(pokEquipo, pokRival);

		combate = new Combate(); // Inicializa el combate
		iniciarCombate(); // Inicia el combate

	}

	/**
	 * Carga los datos de los Pokémon en la interfaz gráfica. Actualiza imágenes,
	 * barras de vida y experiencia, nombres, niveles y botones.
	 */
	public void cargarDatos(Pokemon equipo, Pokemon rival) {

		// Cargar imagenes

		String rutaImagen = PokemonBD.obtenerRutaImagenTrasera(equipo);
		Image imagen = new Image(rutaImagen);
		imgPoke.setImage(imagen);

		String rutaImagen1 = PokemonBD.obtenerRutaImagen(rival);
		Image imagen1 = new Image(rutaImagen1);
		imgPokeRival.setImage(imagen1);

		// Cargar pb

		actualizarBarraVida(pbPokemonVida, equipo.getVitalidadOBJ(), equipo.getVitalidadMaxOBJ());
		actualizarBarraVida(pbPokemonRival, rival.getVitalidadOBJ(), rival.getVitalidadMaxOBJ());
		int limiteExperiencia = (equipo.getNivel() * 10);
		pbPokemonExp.setProgress(equipo.getExperiencia() / limiteExperiencia);

		// Cargar txt

		lblNivel.setText(String.valueOf(equipo.getNivel()));
		lblNivelRival.setText(String.valueOf(rival.getNivel()));
		lblNombrePoke.setText(equipo.getNombre_pokemon());
		lblNombrePokeRival.setText(rival.getNombre_pokemon());
		lblVitalidad.setText(String.valueOf(equipo.getVitalidadOBJ()));
		lblVitalidadMax.setText(String.valueOf(equipo.getVitalidadMaxOBJ()));

		// Cargar movimientos

		equipo = PokemonBD.obtenerPokemonPorIdConMovimientos(equipo.getId_pokemon());

		List<Movimiento> movimientos = equipo.getMovPrincipales();

		btnMov1.setText(
				movimientos.size() > 0
						? movimientos.get(0).getNom_movimiento() + " " + movimientos.get(0).getPp_actual() + "/"
								+ movimientos.get(0).getPp_max()
						: "Movimiento 1");
		btnMov1.setVisible(movimientos.size() > 0);

		btnMov2.setText(
				movimientos.size() > 1
						? movimientos.get(1).getNom_movimiento() + " " + movimientos.get(1).getPp_actual() + "/"
								+ movimientos.get(1).getPp_max()
						: "Movimiento 2");
		btnMov2.setVisible(movimientos.size() > 1);

		btnMov3.setText(
				movimientos.size() > 2
						? movimientos.get(2).getNom_movimiento() + " " + movimientos.get(2).getPp_actual() + "/"
								+ movimientos.get(2).getPp_max()
						: "Movimiento 3");
		btnMov3.setVisible(movimientos.size() > 2);

		btnMov4.setText(
				movimientos.size() > 3
						? movimientos.get(3).getNom_movimiento() + " " + movimientos.get(3).getPp_actual() + "/"
								+ movimientos.get(3).getPp_max()
						: "Movimiento 4");
		btnMov4.setVisible(movimientos.size() > 3);

	}

	/**
	 * Actualiza la barra de vida del Pokémon jugador con el color correspondiente.
	 */
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

	/**
	 * Determina quién comienza el combate en base a la velocidad de los Pokémon.
	 */
	private boolean determinarQuienEmpieza(Pokemon jugador, Pokemon rival) {
		return jugador.getVelocidadOBJ() >= rival.getVelocidadOBJ();
	}

	/**
	 * Inicia el combate mostrando quién empieza y ejecutando el primer turno.
	 */
	public void iniciarCombate() {
		if (combate == null) {
			combate = new Combate(); // Asegúrate de inicializar el objeto combate
		}

		if (combate.getTurnos() == null) {
			combate.setTurnos(new ArrayList<>()); // Inicializa la lista turnos si es null
		}

		boolean jugadorEmpieza = determinarQuienEmpieza(pokEquipo, pokRival);

		if (jugadorEmpieza) {
			actualizarLogCombate("¡Empiezas tú!");
		} else {
			actualizarLogCombate("¡El rival empieza!");
			turnoRival(pokRival, pokEquipo); // El rival toma el primer turno
		}
	}

	/**
	 * Selecciona un movimiento aleatorio del Pokémon rival para su turno.
	 * 
	 * @return Movimiento seleccionado
	 */
	private Movimiento elegirMovimientoAleatorio(Pokemon pokemon) {
		List<Movimiento> movimientos = pokemon.getMovPrincipales();
		if (movimientos == null || movimientos.isEmpty()) {
			return null; // No hay movimientos disponibles
		}

		Random rd = new Random();
		return movimientos.get(rd.nextInt(movimientos.size()));
	}

	/**
	 * Acción de descansar que recupera un porcentaje de vida.
	 */
	@FXML
	void descansar(ActionEvent event) {

		int vitalidadRecuperada = pokEquipo.getVitalidadMaxOBJ() / 10; // Recupera el 10% de la vitalidad máxima
		int nuevaVitalidad = Math.min(pokEquipo.getVitalidadOBJ() + vitalidadRecuperada,
				pokEquipo.getVitalidadMaxOBJ());
		pokEquipo.setVitalidadOBJ(nuevaVitalidad);

		boolean actualizado = PokemonBD.actualizarVitalidad(pokEquipo, nuevaVitalidad);
		if (!actualizado) {
			System.out.println("Error al actualizar la vitalidad en la base de datos.");
		}

		actualizarBarraVida(pbPokemonVida, pokEquipo.getVitalidadOBJ(), pokEquipo.getVitalidadMaxOBJ());
		lblVitalidad.setText(String.valueOf(pokEquipo.getVitalidadOBJ()));

		guardarAccionEntrenador(
				pokEquipo.getNombre_pokemon() + " descanso y recupero la cantidad de PS de " + vitalidadRecuperada);

		turnoRival(pokRival, pokEquipo);

	}

	/**
	 * Permite cambiar de Pokémon durante el combate.
	 */
	@FXML
	void cambiarPokemon(ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/cambiarPokemonCombate.fxml"));
			Parent root = loader.load();

			cambiarPokemonCombateController controlador = loader.getController();
			controlador.init(entrenador, pokEquipo, this, equipo);

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
	 * Recarga la vista con un nuevo Pokémon tras cambiar.
	 */	
	public void recargarConNuevoPokemon(int idPokemon) {
		Pokemon nuevoPrincipal = equipo.stream().filter(pokemon -> pokemon.getId_pokemon() == idPokemon).findFirst()
				.orElse(null);

		if (nuevoPrincipal != null) {
			pokEquipo = nuevoPrincipal;
			cargarDatos(nuevoPrincipal, pokRival);
		}
	}

	// Ejecuta el turno del jugador usando el primer movimiento y asi con los
	// siguientes movimientos que se ven
	@FXML
	void hacerMov1(ActionEvent event) {

		pokEquipo = PokemonBD.obtenerPokemonPorIdConMovimientos(pokEquipo.getId_pokemon());
		List<Movimiento> movimientos = pokEquipo.getMovPrincipales();
		turnoJugador(pokEquipo, pokRival, movimientos.get(0));
		actualizarBotonesConMovimientos();
	}

	@FXML
	void hacerMov2(ActionEvent event) {

		pokEquipo = PokemonBD.obtenerPokemonPorIdConMovimientos(pokEquipo.getId_pokemon());
		List<Movimiento> movimientos = pokEquipo.getMovPrincipales();
		turnoJugador(pokEquipo, pokRival, movimientos.get(1));
		actualizarBotonesConMovimientos();
	}

	@FXML
	void hacerMov3(ActionEvent event) {

		pokEquipo = PokemonBD.obtenerPokemonPorIdConMovimientos(pokEquipo.getId_pokemon());
		List<Movimiento> movimientos = pokEquipo.getMovPrincipales();
		turnoJugador(pokEquipo, pokRival, movimientos.get(2));
		actualizarBotonesConMovimientos();
	}

	@FXML
	void hacerMov4(ActionEvent event) {

		pokEquipo = PokemonBD.obtenerPokemonPorIdConMovimientos(pokEquipo.getId_pokemon());
		List<Movimiento> movimientos = pokEquipo.getMovPrincipales();
		turnoJugador(pokEquipo, pokRival, movimientos.get(3));
		actualizarBotonesConMovimientos();
	}

	/**
	 * Cambia el Pokémon rival cuando se debilita.
	 * Si no quedan Pokémon rivales, termina el combate.
	 */
	private void cambiarPokemonRival() {
		for (Pokemon pokemon : rival) {
			if (pokemon.getVitalidadOBJ() > 0) {
				guardarAccionRival(
						pokRival.getNombre_pokemon() + " cambio el puesto al pokemon a " + pokemon.getNombre_pokemon());
				cargarDatos(pokEquipo, pokemon); // Actualiza los datos del combate
				pokRival = pokemon;
				return;
			}
		}

		// Si no hay más Pokémon disponibles, el jugador gana
		finalizarCombate(true);
	}

	/*Ejecuta el turno del rival, realiza daño y actualiza estado
	 */
	private void turnoRival(Pokemon rival, Pokemon jugador) {
		if (rival.getVitalidadOBJ() <= 0) {
			otorgarExperiencia(pokEquipo, pokRival);
			cambiarPokemonRival();
			return;
		}

		Movimiento movimiento = elegirMovimientoAleatorio(rival);
		if (movimiento == null) {
			System.out.println("No hay movimientos disponibles.");
			return;
		}

		int daño = calcularDaño(rival, jugador, movimiento);
		pokEquipo.setVitalidadOBJ(pokEquipo.getVitalidadOBJ() - daño);

		PokemonBD.actualizarVida(pokEquipo);

		actualizarBarraVida(pbPokemonVida, pokEquipo.getVitalidadOBJ(), pokEquipo.getVitalidadMaxOBJ());
		actualizarBarraVida(pbPokemonRival, pokRival.getVitalidadOBJ(), pokRival.getVitalidadMaxOBJ());

		lblVitalidad.setText(String.valueOf(pokEquipo.getVitalidadOBJ()));

		guardarAccionRival(pokRival.getNombre_pokemon() + " usó " + movimiento.getNom_movimiento() + " e hizo " + daño
				+ " de daño.");
	}

	/*Ejecuta el turno del jugador, realiza daño y gestiona cambio de Pokémon
	 * 
	 */
	private void turnoJugador(Pokemon jugador, Pokemon rival, Movimiento movimiento) {
		if (jugador.getVitalidadOBJ() <= 0) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/cambiarPokemonCombate.fxml"));
				Parent root = loader.load();

				cambiarPokemonCombateController controlador = loader.getController();
				controlador.init(entrenador, pokEquipo, this, equipo);

				Stage nuevaVentana = new Stage();
				nuevaVentana.setTitle("Cambiar Pokémon del equipo");
				nuevaVentana.setScene(new Scene(root));
				nuevaVentana.initOwner(this.stage);
				nuevaVentana.show();
			} catch (IOException e) {
				e.printStackTrace();
				mostrarAlerta("Error al abrir la ventana de cambio de Pokémon.");
			}
			;

			turnoRival(rival, pokEquipo);

			return;
		}

		if (movimiento.getPp_actual() <= 0) {
			actualizarLogCombate("No te quedan pp en ese movimiento, prueba con otro");
			return;
		}

		int idEntrenador = pokEquipo.getId_entrenador();
		int idPokemon = pokEquipo.getId_pokemon();
		int idMovimiento = movimiento.getId_movimiento();
		int ppActuales = movimiento.getPp_actual() - 1; // o como lo estés gestionando

		movimiento.setPp_actual(ppActuales); // Actualiza en el objeto (opcional)
		MovimientoBD.actualizarPPMovimiento(idEntrenador, idPokemon, idMovimiento, ppActuales);

		int daño = calcularDaño(jugador, rival, movimiento);
		rival.setVitalidadOBJ(pokRival.getVitalidadOBJ() - daño);

		actualizarBarraVida(pbPokemonVida, pokEquipo.getVitalidadOBJ(), pokEquipo.getVitalidadMaxOBJ());
		actualizarBarraVida(pbPokemonRival, pokRival.getVitalidadOBJ(), pokRival.getVitalidadMaxOBJ());

		lblVitalidad.setText(String.valueOf(pokEquipo.getVitalidadOBJ()));

		guardarAccionEntrenador(pokEquipo.getNombre_pokemon() + " uso " + movimiento.getNom_movimiento() + " e hizo "
				+ daño + " de daño.");

		if (pokRival.getVitalidadOBJ() <= 0) {
			otorgarExperiencia(pokEquipo, pokRival);
			subirNivel(pokEquipo);
			cambiarPokemonRival();
			return;
		}

		turnoRival(jugador, rival);
	}

	/*Actualiza los textos de los botones con los movimientos y PP actuales
	 * 
	 */
	private void actualizarBotonesConMovimientos() {
		List<Movimiento> movimientos = pokEquipo.getMovPrincipales();

		if (movimientos.size() > 0)
			btnMov1.setText(movimientos.get(0).getNom_movimiento() + " (" + movimientos.get(0).getPp_actual() + "/"
					+ movimientos.get(0).getPp_max() + ")");
		if (movimientos.size() > 1)
			btnMov2.setText(movimientos.get(1).getNom_movimiento() + " (" + movimientos.get(1).getPp_actual() + "/"
					+ movimientos.get(1).getPp_max() + ")");
		if (movimientos.size() > 2)
			btnMov3.setText(movimientos.get(2).getNom_movimiento() + " (" + movimientos.get(2).getPp_actual() + "/"
					+ movimientos.get(2).getPp_max() + ")");
		if (movimientos.size() > 3)
			btnMov4.setText(movimientos.get(3).getNom_movimiento() + " (" + movimientos.get(3).getPp_actual() + "/"
					+ movimientos.get(3).getPp_max() + ")");
	}

	/*Calcula el daño que hace un movimiento del atacante al defensor
	 * 
	 */
	private int calcularDaño(Pokemon atacante, Pokemon defensor, Movimiento movimiento) {
		double multiplicadorTipo = calcularMultiplicadorTipo(movimiento.getTipo(), defensor.getTipo1(),
				defensor.getTipo2());
		double dañoBase = (((2 * atacante.getNivel()) / 5.0 + 2) * movimiento.getPotencia()
				* (atacante.getAtaqueOBJ() / (double) defensor.getDefensaOBJ()) / 250.0 + 2);
		return (int) (dañoBase * multiplicadorTipo);
	}

	/*Guarda la acción realizada por el entrenador en el turno actual
	 * 
	 */
	private void guardarAccionEntrenador(String accion) {
		if (turnoActual == null) {
			turnoActual = new Turno(combate.getNumeroCombate(), contadorTurno, accion, "");
		} else {
			turnoActual.setAccionEntrenador(accion);
		}

	}

	/*Guarda la acción realizada por el rival en el turno actual y actualiza el log
	 * 
	 */
	private void guardarAccionRival(String accion) {
		if (turnoActual == null) {
			turnoActual = new Turno(combate.getNumeroCombate(), contadorTurno, "", accion);
		} else {
			turnoActual.setAccionRival(accion);
		}

		actualizarLogCombate(accion);

		if (turnoActual.getAccionEntrenador() != null && !turnoActual.getAccionEntrenador().isEmpty()
				&& turnoActual.getAccionRival() != null && !turnoActual.getAccionRival().isEmpty()) {
//Mostramos Log de ambas acciones
			String textoTurno = "Turno " + contadorTurno + ":\n" + "Jugador: " + turnoActual.getAccionEntrenador()
					+ "\n" + "Rival: " + turnoActual.getAccionRival() + "\n";

			actualizarLogCombate(textoTurno);

			guardarTurnoEnArchivo(turnoActual);
			contadorTurno++;
			turnoActual = null;
		}
	}

	/*Calcula el multiplicador de daño según tipos de ataque y defensa
	 * 
	 */
	private double calcularMultiplicadorTipo(String tipoAtaque, String tipoDefensor1, String tipoDefensor2) {
		double multiplicador = 1.0;

		// Ataques que hacen el DOBLE de daño (x2)
		if (tipoAtaque.equals("AGUA") && (tipoDefensor1.equals("FUEGO") || tipoDefensor1.equals("ROCA")
				|| tipoDefensor1.equals("TIERRA") || tipoDefensor2.equals("FUEGO") || tipoDefensor2.equals("ROCA")
				|| tipoDefensor2.equals("TIERRA"))) {
			multiplicador *= 2.0;
		}
		if (tipoAtaque.equals("BICHO") && (tipoDefensor1.equals("PLANTA") || tipoDefensor1.equals("PSÍQUICO")
				|| tipoDefensor1.equals("SINIESTRO") || tipoDefensor2.equals("PLANTA")
				|| tipoDefensor2.equals("PSÍQUICO") || tipoDefensor2.equals("SINIESTRO"))) {
			multiplicador *= 2.0;
		}
		if (tipoAtaque.equals("DRAGÓN") && (tipoDefensor1.equals("DRAGÓN") || tipoDefensor2.equals("DRAGÓN"))) {
			multiplicador *= 2.0;
		}
		if (tipoAtaque.equals("ELÉCTRICO") && (tipoDefensor1.equals("AGUA") || tipoDefensor1.equals("VOLADOR")
				|| tipoDefensor2.equals("AGUA") || tipoDefensor2.equals("VOLADOR"))) {
			multiplicador *= 2.0;
		}
		if (tipoAtaque.equals("FANTASMA") && (tipoDefensor1.equals("FANTASMA") || tipoDefensor1.equals("PSÍQUICO")
				|| tipoDefensor2.equals("FANTASMA") || tipoDefensor2.equals("PSÍQUICO"))) {
			multiplicador *= 2.0;
		}
		if (tipoAtaque.equals("FUEGO") && (tipoDefensor1.equals("BICHO") || tipoDefensor1.equals("PLANTA")
				|| tipoDefensor1.equals("HIELO") || tipoDefensor1.equals("ACERO") || tipoDefensor2.equals("BICHO")
				|| tipoDefensor2.equals("PLANTA") || tipoDefensor2.equals("HIELO") || tipoDefensor2.equals("ACERO"))) {
			multiplicador *= 2.0;
		}
		if (tipoAtaque.equals("HIELO") && (tipoDefensor1.equals("DRAGÓN") || tipoDefensor1.equals("PLANTA")
				|| tipoDefensor1.equals("TIERRA") || tipoDefensor1.equals("VOLADOR") || tipoDefensor2.equals("DRAGÓN")
				|| tipoDefensor2.equals("PLANTA") || tipoDefensor2.equals("TIERRA")
				|| tipoDefensor2.equals("VOLADOR"))) {
			multiplicador *= 2.0;
		}
		if (tipoAtaque.equals("LUCHA") && (tipoDefensor1.equals("NORMAL") || tipoDefensor1.equals("HIELO")
				|| tipoDefensor1.equals("ROCA") || tipoDefensor1.equals("SINIESTRO") || tipoDefensor1.equals("ACERO")
				|| tipoDefensor2.equals("NORMAL") || tipoDefensor2.equals("HIELO") || tipoDefensor2.equals("ROCA")
				|| tipoDefensor2.equals("SINIESTRO") || tipoDefensor2.equals("ACERO"))) {
			multiplicador *= 2.0;
		}
		if (tipoAtaque.equals("PLANTA") && (tipoDefensor1.equals("AGUA") || tipoDefensor1.equals("TIERRA")
				|| tipoDefensor1.equals("ROCA") || tipoDefensor2.equals("AGUA") || tipoDefensor2.equals("TIERRA")
				|| tipoDefensor2.equals("ROCA"))) {
			multiplicador *= 2.0;
		}
		if (tipoAtaque.equals("PSÍQUICO") && (tipoDefensor1.equals("LUCHA") || tipoDefensor1.equals("VENENO")
				|| tipoDefensor2.equals("LUCHA") || tipoDefensor2.equals("VENENO"))) {
			multiplicador *= 2.0;
		}
		if (tipoAtaque.equals("ROCA") && (tipoDefensor1.equals("BICHO") || tipoDefensor1.equals("FUEGO")
				|| tipoDefensor1.equals("HIELO") || tipoDefensor1.equals("VOLADOR") || tipoDefensor2.equals("BICHO")
				|| tipoDefensor2.equals("FUEGO") || tipoDefensor2.equals("HIELO") || tipoDefensor2.equals("VOLADOR"))) {
			multiplicador *= 2.0;
		}
		if (tipoAtaque.equals("TIERRA") && (tipoDefensor1.equals("ELÉCTRICO") || tipoDefensor1.equals("FUEGO")
				|| tipoDefensor1.equals("ROCA") || tipoDefensor1.equals("ACERO") || tipoDefensor1.equals("VENENO")
				|| tipoDefensor2.equals("ELÉCTRICO") || tipoDefensor2.equals("FUEGO") || tipoDefensor2.equals("ROCA")
				|| tipoDefensor2.equals("ACERO") || tipoDefensor2.equals("VENENO"))) {
			multiplicador *= 2.0;
		}
		if (tipoAtaque.equals("VENENO") && (tipoDefensor1.equals("PLANTA") || tipoDefensor1.equals("HADA")
				|| tipoDefensor2.equals("PLANTA") || tipoDefensor2.equals("HADA"))) {
			multiplicador *= 2.0;
		}
		if (tipoAtaque.equals("VOLADOR") && (tipoDefensor1.equals("BICHO") || tipoDefensor1.equals("LUCHA")
				|| tipoDefensor1.equals("PLANTA") || tipoDefensor2.equals("BICHO") || tipoDefensor2.equals("LUCHA")
				|| tipoDefensor2.equals("PLANTA"))) {
			multiplicador *= 2.0;
		}

		if (tipoAtaque.equals("AGUA") && (tipoDefensor1.equals("AGUA") || tipoDefensor1.equals("PLANTA")
				|| tipoDefensor1.equals("DRAGÓN") || tipoDefensor2.equals("AGUA") || tipoDefensor2.equals("PLANTA")
				|| tipoDefensor2.equals("DRAGÓN"))) {
			multiplicador *= 0.5;
		}
		if (tipoAtaque.equals("BICHO") && (tipoDefensor1.equals("FUEGO") || tipoDefensor1.equals("LUCHA")
				|| tipoDefensor1.equals("VOLADOR") || tipoDefensor1.equals("FANTASMA") || tipoDefensor1.equals("HADA")
				|| tipoDefensor1.equals("VENENO") || tipoDefensor1.equals("ACERO") || tipoDefensor2.equals("FUEGO")
				|| tipoDefensor2.equals("LUCHA") || tipoDefensor2.equals("VOLADOR") || tipoDefensor2.equals("FANTASMA")
				|| tipoDefensor2.equals("HADA") || tipoDefensor2.equals("VENENO") || tipoDefensor2.equals("ACERO"))) {
			multiplicador *= 0.5;
		}
		if (tipoAtaque.equals("DRAGÓN") && (tipoDefensor1.equals("ACERO") || tipoDefensor2.equals("ACERO"))) {
			multiplicador *= 0.5;
		}
		if (tipoAtaque.equals("ELÉCTRICO") && (tipoDefensor1.equals("PLANTA") || tipoDefensor1.equals("DRAGÓN")
				|| tipoDefensor1.equals("ELÉCTRICO") || tipoDefensor2.equals("PLANTA") || tipoDefensor2.equals("DRAGÓN")
				|| tipoDefensor2.equals("ELÉCTRICO"))) {
			multiplicador *= 0.5;
		}
		if (tipoAtaque.equals("FANTASMA") && (tipoDefensor1.equals("SINIESTRO") || tipoDefensor2.equals("SINIESTRO"))) {
			multiplicador *= 0.5;
		}
		if (tipoAtaque.equals("FUEGO") && (tipoDefensor1.equals("AGUA") || tipoDefensor1.equals("FUEGO")
				|| tipoDefensor1.equals("ROCA") || tipoDefensor1.equals("DRAGÓN") || tipoDefensor2.equals("AGUA")
				|| tipoDefensor2.equals("FUEGO") || tipoDefensor2.equals("ROCA") || tipoDefensor2.equals("DRAGÓN"))) {
			multiplicador *= 0.5;
		}
		if (tipoAtaque.equals("HIELO") && (tipoDefensor1.equals("AGUA") || tipoDefensor1.equals("FUEGO")
				|| tipoDefensor1.equals("HIELO") || tipoDefensor1.equals("ACERO") || tipoDefensor2.equals("AGUA")
				|| tipoDefensor2.equals("FUEGO") || tipoDefensor2.equals("HIELO") || tipoDefensor2.equals("ACERO"))) {
			multiplicador *= 0.5;
		}
		if (tipoAtaque.equals("LUCHA") && (tipoDefensor1.equals("BICHO") || tipoDefensor1.equals("HADA")
				|| tipoDefensor1.equals("PSÍQUICO") || tipoDefensor1.equals("VOLADOR") || tipoDefensor1.equals("VENENO")
				|| tipoDefensor2.equals("BICHO") || tipoDefensor2.equals("HADA") || tipoDefensor2.equals("PSÍQUICO")
				|| tipoDefensor2.equals("VOLADOR") || tipoDefensor2.equals("VENENO"))) {
			multiplicador *= 0.5;
		}
		if (tipoAtaque.equals("NORMAL") && (tipoDefensor1.equals("ROCA") || tipoDefensor1.equals("ACERO")
				|| tipoDefensor2.equals("ROCA") || tipoDefensor2.equals("ACERO"))) {
			multiplicador *= 0.5;
		}
		if (tipoAtaque.equals("PLANTA") && (tipoDefensor1.equals("FUEGO") || tipoDefensor1.equals("PLANTA")
				|| tipoDefensor1.equals("DRAGÓN") || tipoDefensor1.equals("BICHO") || tipoDefensor1.equals("VENENO")
				|| tipoDefensor1.equals("VOLADOR") || tipoDefensor1.equals("ACERO") || tipoDefensor2.equals("FUEGO")
				|| tipoDefensor2.equals("PLANTA") || tipoDefensor2.equals("DRAGÓN") || tipoDefensor2.equals("BICHO")
				|| tipoDefensor2.equals("VENENO") || tipoDefensor2.equals("VOLADOR")
				|| tipoDefensor2.equals("ACERO"))) {
			multiplicador *= 0.5;
		}
		if (tipoAtaque.equals("PSÍQUICO") && (tipoDefensor1.equals("ACERO") || tipoDefensor1.equals("PSÍQUICO")
				|| tipoDefensor2.equals("ACERO") || tipoDefensor2.equals("PSÍQUICO"))) {
			multiplicador *= 0.5;
		}
		if (tipoAtaque.equals("ROCA") && (tipoDefensor1.equals("LUCHA") || tipoDefensor1.equals("ACERO")
				|| tipoDefensor1.equals("TIERRA") || tipoDefensor2.equals("LUCHA") || tipoDefensor2.equals("ACERO")
				|| tipoDefensor2.equals("TIERRA"))) {
			multiplicador *= 0.5;
		}
		if (tipoAtaque.equals("TIERRA") && (tipoDefensor1.equals("PLANTA") || tipoDefensor1.equals("BICHO")
				|| tipoDefensor2.equals("PLANTA") || tipoDefensor2.equals("BICHO"))) {
			multiplicador *= 0.5;
		}
		if (tipoAtaque.equals("VENENO") && (tipoDefensor1.equals("FANTASMA") || tipoDefensor1.equals("TIERRA")
				|| tipoDefensor1.equals("ROCA") || tipoDefensor1.equals("VENENO") || tipoDefensor2.equals("FANTASMA")
				|| tipoDefensor2.equals("TIERRA") || tipoDefensor2.equals("ROCA") || tipoDefensor2.equals("VENENO"))) {
			multiplicador *= 0.5;
		}
		if (tipoAtaque.equals("VOLADOR") && (tipoDefensor1.equals("ELÉCTRICO") || tipoDefensor1.equals("ROCA")
				|| tipoDefensor1.equals("ACERO") || tipoDefensor2.equals("ELÉCTRICO") || tipoDefensor2.equals("ROCA")
				|| tipoDefensor2.equals("ACERO"))) {
			multiplicador *= 0.5;
		}

		if (tipoAtaque.equals("FANTASMA") && (tipoDefensor1.equals("NORMAL") || tipoDefensor2.equals("NORMAL"))) {
			multiplicador = 0.0;
		}
		if (tipoAtaque.equals("LUCHA") && (tipoDefensor1.equals("FANTASMA") || tipoDefensor2.equals("FANTASMA"))) {
			multiplicador = 0.0;
		}
		if (tipoAtaque.equals("NORMAL") && (tipoDefensor1.equals("FANTASMA") || tipoDefensor2.equals("FANTASMA"))) {
			multiplicador = 0.0;
		}
		if (tipoAtaque.equals("ELÉCTRICO") && (tipoDefensor1.equals("TIERRA") || tipoDefensor2.equals("TIERRA"))) {
			multiplicador = 0.0;
		}
		if (tipoAtaque.equals("VENENO") && (tipoDefensor1.equals("ACERO") || tipoDefensor2.equals("ACERO"))) {
			multiplicador = 0.0;
		}
		if (tipoAtaque.equals("TIERRA") && (tipoDefensor1.equals("VOLADOR") || tipoDefensor2.equals("VOLADOR"))) {
			multiplicador = 0.0;
		}
		if (tipoAtaque.equals("PSÍQUICO") && (tipoDefensor1.equals("SINIESTRO") || tipoDefensor2.equals("SINIESTRO"))) {
			multiplicador = 0.0;
		}
		if (tipoAtaque.equals("DRAGÓN") && (tipoDefensor1.equals("HADA") || tipoDefensor2.equals("HADA"))) {
			multiplicador = 0.0;
		}

		return multiplicador;
	}

	private void finalizarCombate(boolean entrenadorGana) {
		try {
			// Obtiene el entrenador rival desde la BD usando el id_entrenador de pokRival
			Entrenador entrenadorRival = EntrenadorBD.obtenerEntrenadorPorId(pokRival.getId_entrenador());

			if (entrenadorGana) {
				if (entrenadorRival != null) {
					int pokedolaresRival = entrenadorRival.getPokedolares();
					int recompensa = pokedolaresRival / 3;

					int pokedolaresEntrenador = entrenador.getPokedolares();
					entrenador.setPokedolares(pokedolaresEntrenador + recompensa);

					// Actualiza pokedolares del entrenador en BD
					EntrenadorBD.actualizarPokedolares(entrenador);

					actualizarLogCombate("¡Has ganado! Obtienes " + recompensa + " pokédolares como recompensa.");
				} else {
					actualizarLogCombate("No se pudo obtener al entrenador rival. No recibes recompensa.");
				}
			} else {
				int pokedolaresEntrenador = entrenador.getPokedolares();
				int perdida = pokedolaresEntrenador / 3;

				entrenador.setPokedolares(pokedolaresEntrenador - perdida);

				// Actualiza pokedolares del entrenador en BD
				EntrenadorBD.actualizarPokedolares(entrenador);

				actualizarLogCombate("Has perdido el combate. Pierdes " + perdida + " pokédolares.");
			}

		} finally {

		}

		mostrarAlerta("El combate ha terminado.");
		menuController.show();
		stage.close();
	}

	private void subirNivel(Pokemon pokemon) {
		// Incrementa el nivel del Pokémon en 1
		pokemon.setNivel(pokemon.getNivel() + 1);
		// Resetea experiencia a cero
		pokemon.setExperiencia(0);
		// Aumenta ataque, defensa y vitalidad máxima
		pokemon.setAtaqueOBJ(pokemon.getAtaqueOBJ() + 2);
		pokemon.setDefensaOBJ(pokemon.getDefensaOBJ() + 2);
		pokemon.setVitalidadMaxOBJ(pokemon.getVitalidadMaxOBJ() + 5);

		// Cada 3 niveles asigna un movimiento nuevo aleatorio
		if (pokemon.getNivel() % 3 == 0) {
			MovimientoBD.asignarMovimientoAleatorio(pokemon);
			mostrarAlerta("Has obtenido un nuevo movimiento para el pokemon " + pokemon.getNombre_pokemon()
					+ " miralo en estadisticas de este pokemon");
		}

		// Evoluciona el Pokémon en niveles 25 o 50
		if (pokemon.getNivel() == 25 || pokemon.getNivel() == 50) {
			evolucionarPokemon(pokemon);
		}
	}

	/*Otorga experiencia al Pokémon del entrenador según nivel del rival y sube de
	 * nivel si corresponde
	 */
	

	private void otorgarExperiencia(Pokemon pokEq, Pokemon pokRiv) {
		int experienciaGanada = (pokRiv.getNivel() * 10) / pokEq.getNivel();
		pokEq.setExperiencia(pokEq.getExperiencia() + experienciaGanada);
		PokemonBD.actualizarPokemonExperiencia(pokEq);

		if (pokEq.getExperiencia() >= pokEq.getNivel() * 10) {
			subirNivel(pokEq);
			PokemonBD.actualizarNivelPokemon(pokEq, pokEq.getNivel());
		}
	}

	/*
	 * Verifica si el Pokémon puede evolucionar y actualiza su forma en caso
	 * afirmativo
	 */
	

	private void evolucionarPokemon(Pokemon pokemon) {
		int nuevoNumPokedex = PokemonBD.obtenerEvolucion(pokemon);
		if (nuevoNumPokedex != pokemon.getNum_pokedex()) {
			pokemon.setNum_pokedex(nuevoNumPokedex);
			mostrarAlerta("El pokemon " + pokemon.getNombre_pokemon() + " ha evolucionado");
		}
	}
	/*Actualiza el área de texto del log de combate con una nueva línea descriptiva
	 * 
	 */

	private void actualizarLogCombate(String descripcion) {
		logCombate.appendText(descripcion + "\n");
	}

	/*Muestra una ventana de alerta informativa con el mensaje proporcionado
	 * 
	 */

	private void mostrarAlerta(String mensaje) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Entrenamiento");
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		alert.showAndWait();
	}

	/*Acción para que el entrenador huya del combate, registrando la acción y finalizando el combate
	 * 
	 */
	

	@FXML
	void huir(ActionEvent event) {
		guardarAccionEntrenador(entrenador.getUsuario() + " huyó del combate.");
		finalizarCombate(false);
	}

	/* Guarda en un archivo de texto el detalle de cada turno del combate para histórico o registro
	 * 
	 */
	

	private void guardarTurnoEnArchivo(Turno turno) {
		try (FileWriter writer = new FileWriter("log_combate.txt", true);
				BufferedWriter bufferedWriter = new BufferedWriter(writer)) {

			if (turno.getNumTurno() == 1) {
				bufferedWriter.write("\n" + "Combate " + turno.getNumCombate() + ":" + "\n");
			}

			bufferedWriter.write(turno.toString());
			bufferedWriter.newLine();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}