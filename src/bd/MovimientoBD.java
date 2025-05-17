package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import model.Entrenador;
import model.Movimiento;
import model.Pokemon;

public class MovimientoBD {

	public static Movimiento otorgarPrimerMovimiento(Connection con, Entrenador entrenador, Pokemon pokemon) {

		Movimiento mov = null;

		try {

			String queryPlacaje = "SELECT * FROM MOVIMIENTO WHERE NOM_MOVIMIENTO = \"Placaje\";";
			PreparedStatement statementPlacaje = con.prepareStatement(queryPlacaje);
			ResultSet resultadoPlacaje = statementPlacaje.executeQuery();

			if (!resultadoPlacaje.next()) {
				throw new SQLException("No se encontró Placaje en la tabla MOVIMIENTO.");
			}

			// Creacion del movimiento

			mov = new Movimiento(

					resultadoPlacaje.getInt("ID_MOVIMIENTO"), resultadoPlacaje.getString("NOM_MOVIMIENTO"),
					pokemon.getId_pokemon(), resultadoPlacaje.getString("DESCRIPCION"),
					resultadoPlacaje.getInt("PRECI"), resultadoPlacaje.getInt("PP_MAX"),
					resultadoPlacaje.getInt("PP_MAX"), // Al principio los tiene al maximo
					resultadoPlacaje.getString("TIPO"), resultadoPlacaje.getString("TIPO_MOV"),
					resultadoPlacaje.getInt("POTENCIA"), resultadoPlacaje.getString("ESTADO"),
					resultadoPlacaje.getInt("TURNOS"), resultadoPlacaje.getString("MEJORA"),
					resultadoPlacaje.getInt("NUM_MOV"), resultadoPlacaje.getInt("CANT_MEJORA"),
					resultadoPlacaje.getInt(1));

			String queryInsertMov = "INSERT INTO movimiento_pokemon (ID_ENTRENADOR , ID_MOVIMIENTO, ID_POKEMON, PP_ACTUALES, POSICION)"
					+ "VALUES (?, ?, ?, ?, ?)";

			PreparedStatement st = con.prepareStatement(queryInsertMov);

			st.setInt(1, entrenador.getIdEntrenador());
			st.setInt(2, mov.getId_movimiento());
			st.setInt(3, pokemon.getId_pokemon());
			st.setInt(4, mov.getPp_actual());
			st.setInt(5, 1); // Al ser el primer movimiento, 100x100 al principio esta en la 1 posicion

			st.executeUpdate();
			st.close();

			System.out.println(
					"Se ha agregado correctamente el movimiento " + resultadoPlacaje.getString("NOM_MOVIMIENTO"));

		} catch (SQLException e) {
			System.err.println("Error al generar el Pokemon principal: " + e.getMessage());
			e.printStackTrace();
		}

		return mov;

	}

	public static Movimiento otorgarPrimerMovimientoRival(Pokemon pokemon) {

		Movimiento mov = null;

		try (Connection con = BDConecction.getConnection()) {

			String queryPlacaje = "SELECT * FROM MOVIMIENTO WHERE NOM_MOVIMIENTO = \"Placaje\";";
			PreparedStatement statementPlacaje = con.prepareStatement(queryPlacaje);
			ResultSet resultadoPlacaje = statementPlacaje.executeQuery();

			if (!resultadoPlacaje.next()) {
				throw new SQLException("No se encontró Placaje en la tabla MOVIMIENTO.");
			}

			// Creacion del movimiento

			mov = new Movimiento(

					resultadoPlacaje.getInt("ID_MOVIMIENTO"), resultadoPlacaje.getString("NOM_MOVIMIENTO"),
					pokemon.getId_pokemon(), resultadoPlacaje.getString("DESCRIPCION"),
					resultadoPlacaje.getInt("PRECI"), resultadoPlacaje.getInt("PP_MAX"),
					resultadoPlacaje.getInt("PP_MAX"), // Al principio los tiene al maximo
					resultadoPlacaje.getString("TIPO"), resultadoPlacaje.getString("TIPO_MOV"),
					resultadoPlacaje.getInt("POTENCIA"), resultadoPlacaje.getString("ESTADO"),
					resultadoPlacaje.getInt("TURNOS"), resultadoPlacaje.getString("MEJORA"),
					resultadoPlacaje.getInt("NUM_MOV"), resultadoPlacaje.getInt("CANT_MEJORA"),
					resultadoPlacaje.getInt(1));

		} catch (SQLException e) {
			System.err.println("Error al generar el Pokemon principal: " + e.getMessage());
			e.printStackTrace();
		}

		return mov;

	}

	public static LinkedList<Movimiento> obtenerMovimientosPorPokemon(int idPokemon, Connection con) {
		LinkedList<Movimiento> movimientos = new LinkedList<>();

		try {
			String sql = "					SELECT m.*, mp.POSICION\r\n"
					+ "					FROM MOVIMIENTO m\r\n"
					+ "					JOIN MOVIMIENTO_POKEMON mp ON m.ID_MOVIMIENTO = mp.ID_MOVIMIENTO\r\n"
					+ "					WHERE mp.ID_POKEMON = ?\r\n"
					+ "					ORDER BY mp.POSICION ASC";

			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, idPokemon);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Movimiento mov = new Movimiento(rs.getInt("ID_MOVIMIENTO"), rs.getString("NOM_MOVIMIENTO"), idPokemon,
						rs.getString("DESCRIPCION"), rs.getInt("PRECI"), rs.getInt("PP_MAX"), rs.getInt("PP_MAX"),
						rs.getString("TIPO"), rs.getString("TIPO_MOV"), rs.getInt("POTENCIA"), rs.getString("ESTADO"),
						rs.getInt("TURNOS"), rs.getString("MEJORA"), rs.getInt("NUM_MOV"), rs.getInt("CANT_MEJORA"),
						rs.getInt("POSICION"));

				movimientos.add(mov);
			}

			rs.close();
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return movimientos;
	}

	public static void asignarMovimientosAleatorios(Connection conexion, int idPokemon, String tipo1, String tipo2,
			int cantidadMovimientos, int idEntrenador) throws SQLException {
		// Consulta para obtener movimientos aleatorios del tipo del Pokémon
		String queryMovimientos = "				    SELECT ID_MOVIMIENTO, NOM_MOVIMIENTO, TIPO, PP_MAX\r\n"
				+ "				    FROM MOVIMIENTO\r\n"
				+ "				    WHERE TIPO_MOV IN (?, ?) -- Los tipos del Pokémon\r\n"
				+ "				    AND ID_MOVIMIENTO NOT IN (\r\n"
				+ "				        SELECT ID_MOVIMIENTO\r\n"
				+ "				        FROM MOVIMIENTO_POKEMON\r\n"
				+ "				        WHERE ID_POKEMON = ?\r\n"
				+ "				        AND ID_ENTRENADOR = ?\r\n"
				+ "				    )\r\n"
				+ "				    ORDER BY RAND()\r\n"
				+ "				    LIMIT ?";

		try (PreparedStatement st = conexion.prepareStatement(queryMovimientos)) {
			st.setString(1, tipo1);
			st.setString(2, tipo2 != null ? tipo2 : tipo1); // Si no hay TIPO2, usar TIPO1
			st.setInt(3, idPokemon);
			st.setInt(4, idEntrenador);
			st.setInt(5, cantidadMovimientos);

			ResultSet rs = st.executeQuery();

			int posicion = 2;

			while (rs.next()) {
				// Determinar la posición del movimiento
				if (posicion > 4) {
					posicion = 5; // Los movimientos adicionales van a la posición 5 (caja)
				}

				// Insertar el movimiento en la tabla MOVIMIENTO_POKEMON
				String insertMovimiento = "INSERT INTO MOVIMIENTO_POKEMON (ID_ENTRENADOR, ID_MOVIMIENTO, "
						+ "ID_POKEMON, PP_ACTUALES, POSICION)\r\n"
						+ "VALUES (?, ?, ?, ?, ?)";

				try (PreparedStatement insertSt = conexion.prepareStatement(insertMovimiento)) {
					insertSt.setInt(1, idEntrenador);
					insertSt.setInt(2, rs.getInt("ID_MOVIMIENTO"));
					insertSt.setInt(3, idPokemon);
					insertSt.setInt(4, rs.getInt("PP_MAX")); // Asignar PP máximos
					insertSt.setInt(5, posicion); // Asignar la posición
					insertSt.executeUpdate();
				}

				// Incrementar la posición para los primeros 3 movimientos
				if (posicion < 5) {
					posicion++;
				}
			}
		}
	}

	public static Movimiento asignarMovimientosAleatoriosRival(Pokemon pokemon, int numMov) {

		Movimiento pokemonMov = null;

		if (numMov == 0) {
			pokemonMov = otorgarPrimerMovimientoRival(pokemon);
		} else {

			try (Connection con = BDConecction.getConnection()) {

				String queryMovimientos = """
						    SELECT *
						    FROM MOVIMIENTO
						    WHERE TIPO_MOV IN (?, ?)
						    ORDER BY RAND()
						    LIMIT ?
						""";

				try (PreparedStatement st = con.prepareStatement(queryMovimientos)) {
					st.setString(1, pokemon.getTipo1());
					st.setString(2, pokemon.getTipo2() != null ? pokemon.getTipo2() : pokemon.getTipo1());
					st.setInt(3, 1);

					ResultSet rs = st.executeQuery();


					while (rs.next()) {

						Movimiento mov = new Movimiento(rs.getInt("ID_MOVIMIENTO"), rs.getString("NOM_MOVIMIENTO"),
								pokemon.getId_pokemon(), rs.getString("DESCRIPCION"), rs.getInt("PRECI"),
								rs.getInt("PP_MAX"), rs.getInt("PP_MAX"), rs.getString("TIPO"),
								rs.getString("TIPO_MOV"), rs.getInt("POTENCIA"), rs.getString("ESTADO"),
								rs.getInt("TURNOS"), rs.getString("MEJORA"), rs.getInt("NUM_MOV"),
								rs.getInt("CANT_MEJORA"), numMov + 1);

						pokemonMov = mov;

					}
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return pokemonMov;
	}

	public static void actualizarPPMovimiento(int idEntrenador, int idPokemon, int idMovimiento, int nuevosPP) {
	    String sql = "UPDATE MOVIMIENTO_POKEMON SET PP_ACTUALES = ? WHERE ID_ENTRENADOR = ? AND ID_POKEMON = ? AND ID_MOVIMIENTO = ?";
	    try (Connection conexion = BDConecction.getConnection();
	         PreparedStatement pstmt = conexion.prepareStatement(sql)) {
	        pstmt.setInt(1, nuevosPP);
	        pstmt.setInt(2, idEntrenador);
	        pstmt.setInt(3, idPokemon);
	        pstmt.setInt(4, idMovimiento);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        System.err.println("Error al actualizar los PP del movimiento: " + e.getMessage());
	    }
	}

}
