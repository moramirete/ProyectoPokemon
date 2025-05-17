package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import model.Movimiento;
import model.Pokemon;

public class PokemonBD {
	public static Pokemon generarPokemonPrincipal(int idEntrenador, Connection conexion) {
		Pokemon nuevoPokemon = null;

		try {
			Random rd = new Random();

			String queryPokedex = "SELECT * FROM POKEDEX WHERE NIVEL_EVOLUCION = 1 ORDER BY RAND() LIMIT 1;";
			PreparedStatement statementPokedex = conexion.prepareStatement(queryPokedex);
			ResultSet resultadoPokedex = statementPokedex.executeQuery();

			if (!resultadoPokedex.next()) {
				throw new SQLException("No se encontró ningún Pokémon en la tabla POKEDEX.");
			}

			int nuevoIdPokemon = generarIdUnico(conexion);
			int vitalidad = 15 + rd.nextInt(16);
			int ataque = 5 + rd.nextInt(6);
			int defensa = 5 + rd.nextInt(6);
			int ataqueEspecial = 5 + rd.nextInt(6);
			int defensaEspecial = 5 + rd.nextInt(6);
			int velocidad = 5 + rd.nextInt(11);
			int fertilidad = 1 + rd.nextInt(5);
			char sexo = rd.nextBoolean() ? 'M' : 'F';
			String estado = "NORMAL";
			String nombrePokemon = resultadoPokedex.getString("NOM_POKEMON");

			nuevoPokemon = new Pokemon(nuevoIdPokemon, idEntrenador, resultadoPokedex.getInt("NUM_POKEDEX"), 0,
					resultadoPokedex.getString("TIPO1"), resultadoPokedex.getString("TIPO2"), vitalidad, ataque,
					defensa, ataqueEspecial, defensaEspecial, velocidad, 1, fertilidad, 1, nombrePokemon, estado, sexo,
					vitalidad, vitalidad, vitalidad, ataque, defensa, ataqueEspecial, defensaEspecial, velocidad, 0);

			String queryInsertPokemon = "INSERT INTO POKEMON (ID_POKEMON, ID_ENTRENADOR, NUM_POKEDEX, ID_OBJETO, "
					+ "NOM_POKEMON, VITALIDAD, ATAQUE, DEFENSA, AT_ESPECIAL, DEF_ESPECIAL, VELOCIDAD, NIVEL, FERTILIDAD, "
					+ "SEXO, ESTADO, EQUIPO, TIPO1, TIPO2, VITALIDADMAX, VITALIDADMAX_OBJ, VITALIDAD_OBJ, ATAQUE_OBJ, "
					+ "DEFENSA_OBJ, AT_ESPECIAL_OBJ, DEF_ESPECIAL_OBJ, VELOCIDAD_OBJ, EXPERIENCIA) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement st = conexion.prepareStatement(queryInsertPokemon);

			st.setInt(1, nuevoIdPokemon);
			st.setInt(2, idEntrenador);
			st.setInt(3, resultadoPokedex.getInt("NUM_POKEDEX"));
			st.setInt(4, 0);
			st.setString(5, nombrePokemon);
			st.setInt(6, vitalidad);
			st.setInt(7, ataque);
			st.setInt(8, defensa);
			st.setInt(9, ataqueEspecial);
			st.setInt(10, defensaEspecial);
			st.setInt(11, velocidad);
			st.setInt(12, 1);
			st.setInt(13, fertilidad);
			st.setString(14, String.valueOf(sexo));
			st.setString(15, estado);
			st.setInt(16, 1);
			st.setString(17, resultadoPokedex.getString("TIPO1"));
			st.setString(18, resultadoPokedex.getString("TIPO2"));
			st.setInt(19, vitalidad);
			st.setInt(20, vitalidad);
			st.setInt(21, vitalidad);
			st.setInt(22, ataque);
			st.setInt(23, defensa);
			st.setInt(24, ataqueEspecial);
			st.setInt(25, defensaEspecial);
			st.setInt(26, velocidad);
			st.setInt(27, 0); // EXPERIENCIA

			st.executeUpdate();

			System.out.println("Se ha generado un Pokémon principal: " + nombrePokemon);

		} catch (SQLException e) {
			System.err.println("Error al generar el Pokémon principal: " + e.getMessage());
			e.printStackTrace();
		}

		return nuevoPokemon;
	}

	public static Pokemon generarPokemonCaptura(int idEntrenador, Connection conexion) {

		Pokemon nuevoPokemon = null;

		try {
			Random rd = new Random();

			String queryPokedex = "SELECT * FROM POKEDEX ORDER BY RAND() LIMIT 1";
			PreparedStatement st = conexion.prepareStatement(queryPokedex);
			ResultSet rs = st.executeQuery();

			if (!rs.next()) {
				throw new SQLException("No se encontró ningún Pokémon en la tabla POKEDEX.");
			}

			int nuevoIdPokemon = generarIdUnico(conexion);
			String estado = "NORMAL";
			char sexo = rd.nextBoolean() ? 'M' : 'F';
			int fertilidad = 1 + rd.nextInt(5);

			int vitalidad, ataque, defensa, ataqueEspecial, defensaEspecial, velocidad, nivel;

			if (rs.getInt("NIVEL_EVOLUCION") == 1) {
				vitalidad = 15 + rd.nextInt(16);
				ataque = 5 + rd.nextInt(6);
				defensa = 5 + rd.nextInt(6);
				ataqueEspecial = 5 + rd.nextInt(6);
				defensaEspecial = 5 + rd.nextInt(6);
				velocidad = 5 + rd.nextInt(11);
				nivel = 1;
			} else if (rs.getInt("NIVEL_EVOLUCION") == 2) {
				vitalidad = 39 + rd.nextInt(1, 120);
				ataque = 29 + rd.nextInt(1, 120);
				defensa = 29 + rd.nextInt(1, 120);
				ataqueEspecial = 29 + rd.nextInt(1, 120);
				defensaEspecial = 29 + rd.nextInt(1, 120);
				velocidad = 29 + rd.nextInt(1, 120);
				nivel = 25;
			} else {
				vitalidad = 63 + rd.nextInt(1, 120);
				ataque = 53 + rd.nextInt(1, 120);
				defensa = 53 + rd.nextInt(1, 120);
				ataqueEspecial = 53 + rd.nextInt(1, 120);
				defensaEspecial = 53 + rd.nextInt(1, 120);
				velocidad = 53 + rd.nextInt(1, 120);
				nivel = 50;
			}
			;

			nuevoPokemon = new Pokemon(nuevoIdPokemon, idEntrenador, rs.getInt("NUM_POKEDEX"), 0, rs.getString("TIPO1"),
					rs.getString("TIPO2"), vitalidad, ataque, defensa, ataqueEspecial, defensaEspecial, velocidad,
					nivel, fertilidad, 3, rs.getString("NOM_POKEMON"), estado, sexo, vitalidad, vitalidad, vitalidad,
					ataque, defensa, ataqueEspecial, defensaEspecial, velocidad, 0);

		} catch (SQLException e) {
			System.err.println("Error al generar el Pokemon principal: " + e.getMessage());
			e.printStackTrace();
		}

		return nuevoPokemon;

	}

	public static void guardarPokemonCaptura(Pokemon pok, Connection conexion) {
		try {

			String queryInsertPokemon = "INSERT INTO POKEMON (ID_POKEMON, ID_ENTRENADOR, NUM_POKEDEX, ID_OBJETO, "
					+ "NOM_POKEMON, VITALIDAD, ATAQUE, DEFENSA, AT_ESPECIAL, DEF_ESPECIAL, VELOCIDAD, NIVEL, FERTILIDAD, "
					+ "SEXO, ESTADO, EQUIPO, TIPO1, TIPO2, VITALIDADMAX,  VITALIDAD_OBJ, ATAQUE_OBJ, DEFENSA_OBJ, AT_ESPECIAL_OBJ, "
					+ "DEF_ESPECIAL_OBJ, VELOCIDAD_OBJ, VITALIDADMAX_OBJ, EXPERIENCIA) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement st = conexion.prepareStatement(queryInsertPokemon);

			st.setInt(1, pok.getId_pokemon());
			st.setInt(2, pok.getId_entrenador());
			st.setInt(3, pok.getNum_pokedex());
			st.setInt(4, pok.getId_objeto());
			st.setString(5, pok.getNombre_pokemon());
			st.setInt(6, pok.getVitalidad());
			st.setInt(7, pok.getAtaque());
			st.setInt(8, pok.getDefensa());
			st.setInt(9, pok.getAtaque_especial());
			st.setInt(10, pok.getDefensa_especial());
			st.setInt(11, pok.getVelocidad());
			st.setInt(12, pok.getNivel());
			st.setInt(13, pok.getFertilidad());
			st.setString(14, String.valueOf(pok.getSexo()));
			st.setString(15, pok.getEstado());
			st.setInt(16, pok.getEquipo());
			st.setString(17, pok.getTipo1());
			st.setString(18, pok.getTipo2());
			st.setInt(19, pok.getVitalidad());
			st.setInt(20, pok.getVitalidad());
			st.setInt(21, pok.getAtaque());
			st.setInt(22, pok.getDefensa());
			st.setInt(23, pok.getAtaque_especial());
			st.setInt(24, pok.getDefensa_especial());
			st.setInt(25, pok.getVelocidad());
			st.setInt(26, pok.getVitalidadMaxOBJ());
			st.setInt(27, pok.getExperiencia());

			st.executeUpdate();
			st.close();

		} catch (SQLException e) {
			System.err.println("Error al generar el Pokemon: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static int generarIdUnico(Connection conexion) throws SQLException {
		int nuevoId = 0;

		// Obtener el ID máximo actual en la tabla POKEMON
		String query = "SELECT MAX(ID_POKEMON) AS MAX_ID FROM POKEMON";
		PreparedStatement statement = conexion.prepareStatement(query);
		ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {
			nuevoId = resultSet.getInt("MAX_ID") + 1; // Incrementar el ID máximo en 1
		} else {
			nuevoId = 1; // Si no hay registros, empezar desde 1
		}

		resultSet.close();
		statement.close();

		return nuevoId;
	}

	public static ArrayList<Pokemon> obtenerEquipo(int idEntrenador) {
		ArrayList<Pokemon> equipo = new ArrayList<>();

		try (Connection con = BDConecction.getConnection()) {
			String sql = "SELECT * \r\n" + "FROM POKEMON p \r\n"
					+ "JOIN ENTRENADOR e ON p.ID_ENTRENADOR = e.ID_ENTRENADOR \r\n"
					+ "WHERE e.ID_ENTRENADOR = ? AND p.EQUIPO > 0 AND p.EQUIPO < 3 " + "ORDER BY EQUIPO";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, idEntrenador);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				equipo.add(new Pokemon(rs.getInt("ID_POKEMON"), rs.getInt("ID_ENTRENADOR"), rs.getInt("NUM_POKEDEX"),
						rs.getInt("ID_OBJETO"), rs.getString("TIPO1"), rs.getString("TIPO2"), rs.getInt("VITALIDAD"),
						rs.getInt("ATAQUE"), rs.getInt("DEFENSA"), rs.getInt("AT_ESPECIAL"), rs.getInt("DEF_ESPECIAL"),
						rs.getInt("VELOCIDAD"), rs.getInt("NIVEL"), rs.getInt("FERTILIDAD"), rs.getInt("EQUIPO"),
						rs.getString("NOM_POKEMON"), rs.getString("ESTADO"), rs.getString("SEXO").charAt(0),
						rs.getInt("VITALIDADMAX"), rs.getInt("VITALIDADMAX_OBJ"), rs.getInt("VITALIDAD_OBJ"),
						rs.getInt("ATAQUE_OBJ"), rs.getInt("DEFENSA_OBJ"), rs.getInt("AT_ESPECIAL_OBJ"),
						rs.getInt("DEF_ESPECIAL_OBJ"), rs.getInt("VELOCIDAD_OBJ"), rs.getInt("EXPERIENCIA")));
			}
			;

			System.out.println("Se ha realizado el metodo obtenerEquipo a la perfeccion");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return equipo;
	}

	public static ArrayList<Pokemon> obtenerEquipoSinPrincipal(int idEntrenador) {
		ArrayList<Pokemon> equipo = new ArrayList<>();

		try (Connection con = BDConecction.getConnection()) {
			String sql = "SELECT * \r\n" + "FROM POKEMON p \r\n"
					+ "JOIN ENTRENADOR e ON p.ID_ENTRENADOR = e.ID_ENTRENADOR \r\n"
					+ "WHERE e.ID_ENTRENADOR = ? AND p.EQUIPO = 2 ";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, idEntrenador);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				equipo.add(new Pokemon(rs.getInt("ID_POKEMON"), rs.getInt("ID_ENTRENADOR"), rs.getInt("NUM_POKEDEX"),
						rs.getInt("ID_OBJETO"), rs.getString("TIPO1"), rs.getString("TIPO2"), rs.getInt("VITALIDAD"),
						rs.getInt("ATAQUE"), rs.getInt("DEFENSA"), rs.getInt("AT_ESPECIAL"), rs.getInt("DEF_ESPECIAL"),
						rs.getInt("VELOCIDAD"), rs.getInt("NIVEL"), rs.getInt("FERTILIDAD"), rs.getInt("EQUIPO"),
						rs.getString("NOM_POKEMON"), rs.getString("ESTADO"), rs.getString("SEXO").charAt(0),
						rs.getInt("VITALIDADMAX"), rs.getInt("VITALIDADMAX_OBJ"), rs.getInt("VITALIDAD_OBJ"),
						rs.getInt("ATAQUE_OBJ"), rs.getInt("DEFENSA_OBJ"), rs.getInt("AT_ESPECIAL_OBJ"),
						rs.getInt("DEF_ESPECIAL_OBJ"), rs.getInt("VELOCIDAD_OBJ"), rs.getInt("EXPERIENCIA")));
			}
			;

			System.out.println("Se ha realizado el metodo obtenerEquipo a la perfeccion");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return equipo;
	}

	public static ArrayList<Pokemon> obtenerCaja(int idEntrenador) {
		ArrayList<Pokemon> equipo = new ArrayList<>();

		try (Connection con = BDConecction.getConnection()) {
			String sql = "SELECT * \r\n" + "FROM POKEMON p \r\n"
					+ "JOIN ENTRENADOR e ON p.ID_ENTRENADOR = e.ID_ENTRENADOR \r\n"
					+ "WHERE e.ID_ENTRENADOR = ? AND p.EQUIPO = 3 ";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, idEntrenador);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				equipo.add(new Pokemon(rs.getInt("ID_POKEMON"), rs.getInt("ID_ENTRENADOR"), rs.getInt("NUM_POKEDEX"),
						rs.getInt("ID_OBJETO"), rs.getString("TIPO1"), rs.getString("TIPO2"), rs.getInt("VITALIDAD"),
						rs.getInt("ATAQUE"), rs.getInt("DEFENSA"), rs.getInt("AT_ESPECIAL"), rs.getInt("DEF_ESPECIAL"),
						rs.getInt("VELOCIDAD"), rs.getInt("NIVEL"), rs.getInt("FERTILIDAD"), rs.getInt("EQUIPO"),
						rs.getString("NOM_POKEMON"), rs.getString("ESTADO"), rs.getString("SEXO").charAt(0),
						rs.getInt("VITALIDADMAX"), rs.getInt("VITALIDADMAX_OBJ"), rs.getInt("VITALIDAD_OBJ"),
						rs.getInt("ATAQUE_OBJ"), rs.getInt("DEFENSA_OBJ"), rs.getInt("AT_ESPECIAL_OBJ"),
						rs.getInt("DEF_ESPECIAL_OBJ"), rs.getInt("VELOCIDAD_OBJ"), rs.getInt("EXPERIENCIA")));
			}
			;

			System.out.println("Se ha realizado el metodo obtenerEquipo a la perfeccion");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return equipo;
	}

	public static ArrayList<Pokemon> obtenerTodosLosPokemons(int idEntrenador) {
		ArrayList<Pokemon> equipo = new ArrayList<>();

		try (Connection con = BDConecction.getConnection()) {
			String sql = "SELECT * \r\n" + "FROM POKEMON p \r\n"
					+ "JOIN ENTRENADOR e ON p.ID_ENTRENADOR = e.ID_ENTRENADOR \r\n"
					+ "WHERE e.ID_ENTRENADOR = ? AND FERTILIDAD > 0";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, idEntrenador);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				equipo.add(new Pokemon(rs.getInt("ID_POKEMON"), rs.getInt("ID_ENTRENADOR"), rs.getInt("NUM_POKEDEX"),
						rs.getInt("ID_OBJETO"), rs.getString("TIPO1"), rs.getString("TIPO2"), rs.getInt("VITALIDAD"),
						rs.getInt("ATAQUE"), rs.getInt("DEFENSA"), rs.getInt("AT_ESPECIAL"), rs.getInt("DEF_ESPECIAL"),
						rs.getInt("VELOCIDAD"), rs.getInt("NIVEL"), rs.getInt("FERTILIDAD"), rs.getInt("EQUIPO"),
						rs.getString("NOM_POKEMON"), rs.getString("ESTADO"), rs.getString("SEXO").charAt(0),
						rs.getInt("VITALIDADMAX"), rs.getInt("VITALIDADMAX_OBJ"), rs.getInt("VITALIDAD_OBJ"),
						rs.getInt("ATAQUE_OBJ"), rs.getInt("DEFENSA_OBJ"), rs.getInt("AT_ESPECIAL_OBJ"),
						rs.getInt("DEF_ESPECIAL_OBJ"), rs.getInt("VELOCIDAD_OBJ"), rs.getInt("EXPERIENCIA")));
			}
			;

			System.out.println("Se ha realizado el metodo obtenerEquipo a la perfeccion");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return equipo;
	}

	public static ArrayList<Pokemon> obtenerMasculinos(int idEntrenador, Pokemon pokemon1) {
		ArrayList<Pokemon> equipo = new ArrayList<>();

		try (Connection con = BDConecction.getConnection()) {
			String sql = "SELECT * \r\n" + "FROM POKEMON p \r\n"
					+ "JOIN ENTRENADOR e ON p.ID_ENTRENADOR = e.ID_ENTRENADOR \r\n"
					+ "WHERE e.ID_ENTRENADOR = ? AND p.SEXO = 'M' AND p.NUM_POKEDEX = ? AND FERTILIDAD > 0";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, idEntrenador);
			stmt.setInt(2, pokemon1.getNum_pokedex());
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				equipo.add(new Pokemon(rs.getInt("ID_POKEMON"), rs.getInt("ID_ENTRENADOR"), rs.getInt("NUM_POKEDEX"),
						rs.getInt("ID_OBJETO"), rs.getString("TIPO1"), rs.getString("TIPO2"), rs.getInt("VITALIDAD"),
						rs.getInt("ATAQUE"), rs.getInt("DEFENSA"), rs.getInt("AT_ESPECIAL"), rs.getInt("DEF_ESPECIAL"),
						rs.getInt("VELOCIDAD"), rs.getInt("NIVEL"), rs.getInt("FERTILIDAD"), rs.getInt("EQUIPO"),
						rs.getString("NOM_POKEMON"), rs.getString("ESTADO"), rs.getString("SEXO").charAt(0),
						rs.getInt("VITALIDADMAX"), rs.getInt("VITALIDADMAX_OBJ"), rs.getInt("VITALIDAD_OBJ"),
						rs.getInt("ATAQUE_OBJ"), rs.getInt("DEFENSA_OBJ"), rs.getInt("AT_ESPECIAL_OBJ"),
						rs.getInt("DEF_ESPECIAL_OBJ"), rs.getInt("VELOCIDAD_OBJ"), rs.getInt("EXPERIENCIA")));
			}
			;

			System.out.println("Se ha realizado el metodo obtenerEquipo a la perfeccion");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return equipo;
	}

	public static ArrayList<Pokemon> obtenerFemeninos(int idEntrenador, Pokemon pokemon1) {
		ArrayList<Pokemon> equipo = new ArrayList<>();

		try (Connection con = BDConecction.getConnection()) {
			String sql = "SELECT * \r\n" + "FROM POKEMON p \r\n"
					+ "JOIN ENTRENADOR e ON p.ID_ENTRENADOR = e.ID_ENTRENADOR \r\n"
					+ "WHERE e.ID_ENTRENADOR = ? AND p.SEXO = 'F' AND p.NUM_POKEDEX = ? AND FERTILIDAD > 0";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, idEntrenador);
			stmt.setInt(2, pokemon1.getNum_pokedex());
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				equipo.add(new Pokemon(rs.getInt("ID_POKEMON"), rs.getInt("ID_ENTRENADOR"), rs.getInt("NUM_POKEDEX"),
						rs.getInt("ID_OBJETO"), rs.getString("TIPO1"), rs.getString("TIPO2"), rs.getInt("VITALIDAD"),
						rs.getInt("ATAQUE"), rs.getInt("DEFENSA"), rs.getInt("AT_ESPECIAL"), rs.getInt("DEF_ESPECIAL"),
						rs.getInt("VELOCIDAD"), rs.getInt("NIVEL"), rs.getInt("FERTILIDAD"), rs.getInt("EQUIPO"),
						rs.getString("NOM_POKEMON"), rs.getString("ESTADO"), rs.getString("SEXO").charAt(0),
						rs.getInt("VITALIDADMAX"), rs.getInt("VITALIDADMAX_OBJ"), rs.getInt("VITALIDAD_OBJ"),
						rs.getInt("ATAQUE_OBJ"), rs.getInt("DEFENSA_OBJ"), rs.getInt("AT_ESPECIAL_OBJ"),
						rs.getInt("DEF_ESPECIAL_OBJ"), rs.getInt("VELOCIDAD_OBJ"), rs.getInt("EXPERIENCIA")));
			}
			;

			System.out.println("Se ha realizado el metodo obtenerEquipo a la perfeccion");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return equipo;
	}

	public static boolean curarPokemon(int idEntrenador, int idPokemon) {
		try (Connection con = BDConecction.getConnection()) {

			String sql = "UPDATE POKEMON SET VITALIDAD = VITALIDADMAX, VITALIDAD_OBJ = VITALIDADMAX_OBJ WHERE ID_ENTRENADOR = ? AND ID_POKEMON = ?";

			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, idEntrenador);
			stmt.setInt(2, idPokemon);
			return stmt.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean recuperarPPMovimientos(int idEntrenador, int idPokemon) {
	    String sql = "UPDATE MOVIMIENTO_POKEMON mp " +
                "SET PP_ACTUALES = ( " +
                "    SELECT m.PP_MAX FROM MOVIMIENTO m WHERE m.ID_MOVIMIENTO = mp.ID_MOVIMIENTO" +
                ") " +
                "WHERE mp.ID_POKEMON = ? AND mp.ID_ENTRENADOR = ?";

   try (Connection con = BDConecction.getConnection();
        PreparedStatement pst = con.prepareStatement(sql)) {

       pst.setInt(1, idPokemon);
       pst.setInt(2, idEntrenador);

       int filasActualizadas = pst.executeUpdate();

       return filasActualizadas > 0;

   } catch (SQLException e) {
       e.printStackTrace();
       return false;
   }
}

	public static String obtenerRutaImagen(Pokemon p) {
		if (p == null || p.getNum_pokedex() <= 0) {
			System.err.println("Pokémon no válido o NUM_POKEDEX = 0");
			return "/imagenes/default.png";
		}

		try (Connection con = BDConecction.getConnection()) {
			String query = "SELECT SPRITES_FRONTAL FROM POKEDEX WHERE NUM_POKEDEX = ?";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, p.getNum_pokedex());
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				String ruta = "/imagenes/gifDelantero/" + rs.getString("SPRITES_FRONTAL");
				return PokemonBD.class.getResource(ruta) != null ? ruta : "/imagenes/default.png";
			} else {
				return "/imagenes/default.png";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "/imagenes/default.png";
		}
	}

	public static String obtenerRutaImagenTrasera(Pokemon p) {
		if (p == null || p.getNum_pokedex() <= 0) {
			System.err.println("Pokémon no válido o NUM_POKEDEX = 0");
			return "/imagenes/default.png";
		}

		try (Connection con = BDConecction.getConnection()) {
			String query = "SELECT SPRITES_TRASEROS FROM POKEDEX WHERE NUM_POKEDEX = ?";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, p.getNum_pokedex());
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				String ruta = "/imagenes/gifTrasero/" + rs.getString("SPRITES_TRASEROS");
				return PokemonBD.class.getResource(ruta) != null ? ruta : "/imagenes/default.png";
			} else {
				return "/imagenes/default.png";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "/imagenes/default.png";
		}
	}

	public static String obtenerRutaImagenFondo(Pokemon p) {
		try (Connection con = BDConecction.getConnection()) {
			String queryPokedex = "SELECT TIPO1 FROM POKEDEX WHERE NUM_POKEDEX = ?";
			PreparedStatement statementPokedex = con.prepareStatement(queryPokedex);
			statementPokedex.setInt(1, p.getNum_pokedex());
			ResultSet resultadoPokedex = statementPokedex.executeQuery();

			if (resultadoPokedex.next()) {
				// Construct the full path with the file protocol
				String ruta = "/imagenes/fondoEstadistica/fondo" + resultadoPokedex.getString("TIPO1") + ".png";
				System.out.println(ruta);
				return ruta;
			} else {
				throw new SQLException("No image found for NUM_POKEDEX: " + p.getNum_pokedex());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Error: no se ha encontrado bien la ruta del pokemon";
		}
	}

	public static String obtenerRutaImagenGenero(Pokemon p) {
		try (Connection con = BDConecction.getConnection()) {
			String queryPokedex = "SELECT SEXO FROM POKEMON WHERE NUM_POKEDEX = ?";
			PreparedStatement statementPokedex = con.prepareStatement(queryPokedex);
			statementPokedex.setInt(1, p.getNum_pokedex());
			ResultSet resultadoPokedex = statementPokedex.executeQuery();

			if (resultadoPokedex.next()) {
				// Construct the full path with the file protocol
				String ruta = "/imagenes/generos/" + resultadoPokedex.getString("SEXO") + ".png";
				System.out.println(ruta);
				return ruta;
			} else {
				throw new SQLException("No image found for NUM_POKEDEX: " + p.getNum_pokedex());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Error: no se ha encontrado bien la ruta del pokemon";
		}
	}

	public static String obtenerRutaImagenTipo1(Pokemon p) {
		try (Connection con = BDConecction.getConnection()) {
			String queryPokedex = "SELECT TIPO1 FROM POKEDEX WHERE NUM_POKEDEX = ?";
			PreparedStatement statementPokedex = con.prepareStatement(queryPokedex);
			statementPokedex.setInt(1, p.getNum_pokedex());
			ResultSet resultadoPokedex = statementPokedex.executeQuery();

			if (resultadoPokedex.next()) {
				// Construct the full path with the file protocol
				String ruta = "/imagenes/fondoEstadistica/tipo" + resultadoPokedex.getString("TIPO1") + ".png";
				System.out.println(ruta);
				return ruta;
			} else {
				throw new SQLException("No image found for NUM_POKEDEX: " + p.getNum_pokedex());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Error: no se ha encontrado bien la ruta del pokemon";
		}
	}

	public static String obtenerRutaImagenTipo2(Pokemon p) {
		try (Connection con = BDConecction.getConnection()) {
			String queryPokedex = "SELECT TIPO2 FROM POKEDEX WHERE NUM_POKEDEX = ?";
			PreparedStatement statementPokedex = con.prepareStatement(queryPokedex);
			statementPokedex.setInt(1, p.getNum_pokedex());
			ResultSet resultadoPokedex = statementPokedex.executeQuery();

			if (resultadoPokedex.next()) {
				// Construct the full path with the file protocol
				String ruta = "/imagenes/fondoEstadistica/tipo" + resultadoPokedex.getString("TIPO2") + ".png";
				System.out.println(ruta);
				return ruta;
			} else {
				throw new SQLException("No image found for NUM_POKEDEX: " + p.getNum_pokedex());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Error: no se ha encontrado bien la ruta del pokemon";
		}
	}

	public static boolean cambiarNombre(Pokemon p, Optional<String> nombre) {
		try (Connection con = BDConecction.getConnection()) {

			String sql = "UPDATE POKEMON SET NOM_POKEMON = ? WHERE ID_POKEMON = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, nombre.get());
			stmt.setInt(2, p.getId_pokemon());
			return stmt.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean cambiarPokemonEquipo(int idEntrenador, Pokemon deCaja, Pokemon deEquipo) {
		try (Connection con = BDConecction.getConnection()) {
			String sqlCaja = "UPDATE POKEMON SET EQUIPO = 2 WHERE ID_POKEMON = ? AND ID_ENTRENADOR = ?";
			PreparedStatement stmtCaja = con.prepareStatement(sqlCaja);
			stmtCaja.setInt(1, deCaja.getId_pokemon());
			stmtCaja.setInt(2, idEntrenador);
			stmtCaja.executeUpdate();

			String sqlEquipo = "UPDATE POKEMON SET EQUIPO = 3 WHERE ID_POKEMON = ? AND ID_ENTRENADOR = ?";
			PreparedStatement stmtEquipo = con.prepareStatement(sqlEquipo);
			stmtEquipo.setInt(1, deEquipo.getId_pokemon());
			stmtEquipo.setInt(2, idEntrenador);
			stmtEquipo.executeUpdate();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean añadirPokemonAlEquipo(int idEntrenador, Pokemon deCaja) {
		try (Connection con = BDConecction.getConnection()) {
			String sql = "UPDATE POKEMON SET EQUIPO = 2 WHERE ID_POKEMON = ? AND ID_ENTRENADOR = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, deCaja.getId_pokemon());
			stmt.setInt(2, idEntrenador);
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static Pokemon obtenerPokemonPrincipal(int idEntrenador) {

		Pokemon p = new Pokemon();

		try (Connection con = BDConecction.getConnection()) {
			String sql = "SELECT * \r\n" + "FROM POKEMON p \r\n"
					+ "JOIN ENTRENADOR e ON p.ID_ENTRENADOR = e.ID_ENTRENADOR \r\n"
					+ "WHERE e.ID_ENTRENADOR = ? AND p.EQUIPO = 1 ";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, idEntrenador);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				p = (new Pokemon(rs.getInt("ID_POKEMON"), rs.getInt("ID_ENTRENADOR"), rs.getInt("NUM_POKEDEX"),
						rs.getInt("ID_OBJETO"), rs.getString("TIPO1"), rs.getString("TIPO2"), rs.getInt("VITALIDAD"),
						rs.getInt("ATAQUE"), rs.getInt("DEFENSA"), rs.getInt("AT_ESPECIAL"), rs.getInt("DEF_ESPECIAL"),
						rs.getInt("VELOCIDAD"), rs.getInt("NIVEL"), rs.getInt("FERTILIDAD"), rs.getInt("EQUIPO"),
						rs.getString("NOM_POKEMON"), rs.getString("ESTADO"), rs.getString("SEXO").charAt(0),
						rs.getInt("VITALIDADMAX"), rs.getInt("VITALIDADMAX_OBJ"), rs.getInt("VITALIDAD_OBJ"),
						rs.getInt("ATAQUE_OBJ"), rs.getInt("DEFENSA_OBJ"), rs.getInt("AT_ESPECIAL_OBJ"),
						rs.getInt("DEF_ESPECIAL_OBJ"), rs.getInt("VELOCIDAD_OBJ"), rs.getInt("EXPERIENCIA")));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return p;
	}

	public static boolean cambiarPokemonPrincipal(int idEntrenador, Pokemon deCaja, Pokemon principalActual) {
		try (Connection con = BDConecction.getConnection()) {
			// Cambiar el Pokémon de la caja a principal
			String sqlCaja = "UPDATE POKEMON SET EQUIPO = 1 WHERE ID_POKEMON = ? AND ID_ENTRENADOR = ?";
			PreparedStatement stmtCaja = con.prepareStatement(sqlCaja);
			stmtCaja.setInt(1, deCaja.getId_pokemon());
			stmtCaja.setInt(2, idEntrenador);
			stmtCaja.executeUpdate();

			// Cambiar el Pokémon principal actual a la caja
			String sqlPrincipal = "UPDATE POKEMON SET EQUIPO = 3 WHERE ID_POKEMON = ? AND ID_ENTRENADOR = ?";
			PreparedStatement stmtPrincipal = con.prepareStatement(sqlPrincipal);
			stmtPrincipal.setInt(1, principalActual.getId_pokemon());
			stmtPrincipal.setInt(2, idEntrenador);
			stmtPrincipal.executeUpdate();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean cambiarPokemonPrincipalAEquipo(int idEntrenador, Pokemon deCaja, Pokemon principalActual) {
		try (Connection con = BDConecction.getConnection()) {
			// Cambiar el Pokémon de la caja a principal
			String sqlCaja = "UPDATE POKEMON SET EQUIPO = 1 WHERE ID_POKEMON = ? AND ID_ENTRENADOR = ?";
			PreparedStatement stmtCaja = con.prepareStatement(sqlCaja);
			stmtCaja.setInt(1, deCaja.getId_pokemon());
			stmtCaja.setInt(2, idEntrenador);
			stmtCaja.executeUpdate();

			// Cambiar el Pokémon principal actual a la caja
			String sqlPrincipal = "UPDATE POKEMON SET EQUIPO = 2 WHERE ID_POKEMON = ? AND ID_ENTRENADOR = ?";
			PreparedStatement stmtPrincipal = con.prepareStatement(sqlPrincipal);
			stmtPrincipal.setInt(1, principalActual.getId_pokemon());
			stmtPrincipal.setInt(2, idEntrenador);
			stmtPrincipal.executeUpdate();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean cambiarPrincipalConEquipo(int idEntrenador, Pokemon delEquipo, Pokemon principalActual) {
		try (Connection con = BDConecction.getConnection()) {
			// Cambiar el Pokémon del equipo a principal
			String sqlEquipo = "UPDATE POKEMON SET EQUIPO = 1 WHERE ID_POKEMON = ? AND ID_ENTRENADOR = ?";
			PreparedStatement stmtEquipo = con.prepareStatement(sqlEquipo);
			stmtEquipo.setInt(1, delEquipo.getId_pokemon());
			stmtEquipo.setInt(2, idEntrenador);
			stmtEquipo.executeUpdate();

			// Cambiar el Pokémon principal actual al equipo
			String sqlPrincipal = "UPDATE POKEMON SET EQUIPO = 2 WHERE ID_POKEMON = ? AND ID_ENTRENADOR = ?";
			PreparedStatement stmtPrincipal = con.prepareStatement(sqlPrincipal);
			stmtPrincipal.setInt(1, principalActual.getId_pokemon());
			stmtPrincipal.setInt(2, idEntrenador);
			stmtPrincipal.executeUpdate();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static Pokemon obtenerPokemonPorId(int idPokemon) {
		Pokemon pokemon = null;

		try (Connection con = BDConecction.getConnection()) {
			String query = "SELECT * FROM POKEMON WHERE ID_POKEMON = ?";
			PreparedStatement statement = con.prepareStatement(query);
			statement.setInt(1, idPokemon);
			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				pokemon = (new Pokemon(rs.getInt("ID_POKEMON"), rs.getInt("ID_ENTRENADOR"), rs.getInt("NUM_POKEDEX"),
						rs.getInt("ID_OBJETO"), rs.getString("TIPO1"), rs.getString("TIPO2"), rs.getInt("VITALIDAD"),
						rs.getInt("ATAQUE"), rs.getInt("DEFENSA"), rs.getInt("AT_ESPECIAL"), rs.getInt("DEF_ESPECIAL"),
						rs.getInt("VELOCIDAD"), rs.getInt("NIVEL"), rs.getInt("FERTILIDAD"), rs.getInt("EQUIPO"),
						rs.getString("NOM_POKEMON"), rs.getString("ESTADO"), rs.getString("SEXO").charAt(0),
						rs.getInt("VITALIDADMAX"), rs.getInt("VITALIDADMAX_OBJ"), rs.getInt("VITALIDAD_OBJ"),
						rs.getInt("ATAQUE_OBJ"), rs.getInt("DEFENSA_OBJ"), rs.getInt("AT_ESPECIAL_OBJ"),
						rs.getInt("DEF_ESPECIAL_OBJ"), rs.getInt("VELOCIDAD_OBJ"), rs.getInt("EXPERIENCIA")));
			}

			rs.close();
			statement.close();
		} catch (SQLException e) {
			System.err.println("Error al obtener los datos del Pokémon: " + e.getMessage());
			e.printStackTrace();
		}

		return pokemon;
	}

	public static Pokemon obtenerPokemonPorIdConMovimientos(int idPokemon) {
		Pokemon pokemon = obtenerPokemonPorId(idPokemon);

		if (pokemon == null) {
			System.err.println("No se encontró el Pokémon con ID: " + idPokemon);
			return null;
		}

		try (Connection con = BDConecction.getConnection()) {
			pokemon.setMovPrincipales(MovimientoBD.obtenerMovimientosPorPokemon(idPokemon, con));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pokemon;
	}

	public static Pokemon criarPokemonHijo(Pokemon p1, Pokemon p2) throws SQLException {
		// El hijo hereda el mejor valor de cada característica
		int vitalidad = Math.max(p1.getVitalidadMaxOBJ(), p2.getVitalidadMaxOBJ());
		int ataque = Math.max(p1.getAtaqueOBJ(), p2.getAtaqueOBJ());
		int defensa = Math.max(p1.getDefensaOBJ(), p2.getDefensaOBJ());
		int ataqueEspecial = Math.max(p1.getAtaque_especialOBJ(), p2.getAtaque_especialOBJ());
		int defensaEspecial = Math.max(p1.getDefensa_especialOBJ(), p2.getDefensa_especialOBJ());
		int velocidad = Math.max(p1.getVelocidadOBJ(), p2.getVelocidadOBJ());
		char sexo = new Random().nextBoolean() ? 'M' : 'F';

		Pokemon hijo = new Pokemon(
				// Ajusta los parámetros según tu constructor
				0, p1.getId_entrenador(), p1.getNum_pokedex(), 0, // ID_OBJETO
				p1.getTipo1(), p1.getTipo2(), vitalidad, ataque, defensa, ataqueEspecial, defensaEspecial, velocidad, 1, // nivel
				5, // fertilidad inicial
				3, // equipo (caja)
				p1.getNombre_pokemon(), "NORMAL", sexo, vitalidad, vitalidad, vitalidad, ataque, defensa,
				ataqueEspecial, defensaEspecial, velocidad, 0);

		int nuevoId = generarIdUnico(BDConecction.getConnection());
		hijo.setId_pokemon(nuevoId);
		// Aquí puedes llamar a tu método para otorgar el primer movimiento
		// hijo.otorgarPrimerMovimiento("Placaje");
		return hijo;
	}

	public static void restarFertilidad(Pokemon pokemon, Connection con) {
		try {
			String sql = "UPDATE POKEMON SET FERTILIDAD = ? WHERE ID_POKEMON = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, pokemon.getFertilidad());
			ps.setInt(2, pokemon.getId_pokemon());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static List<Integer> obtenerTodosIdsPokemon() {
		List<Integer> ids = new ArrayList<>();

		try (Connection con = BDConecction.getConnection()) {
			String query = "SELECT ID_POKEMON FROM POKEMON";
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ids.add(rs.getInt("ID_POKEMON"));
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ids;
	}

	public static Pokemon generarPokemonRivalAleatorio(Pokemon pokemon) {
		Pokemon nuevoPokemon = null;

		try (Connection con = BDConecction.getConnection()) {
			Random rd = new Random();

			String queryPokedex = "SELECT * FROM POKEDEX ORDER BY RAND() LIMIT 1";
			PreparedStatement ps = con.prepareStatement(queryPokedex);
			ResultSet rs = ps.executeQuery();

			if (!rs.next()) {
				throw new SQLException("No se encontró ningún Pokémon en la tabla POKEDEX.");
			}

			int vitalidad = pokemon.getVitalidad() + rd.nextInt(16);
			int ataque = pokemon.getAtaque() + rd.nextInt(6);
			int defensa = pokemon.getDefensa() + rd.nextInt(6);
			int ataqueEspecial = pokemon.getAtaque_especial() + rd.nextInt(6);
			int defensaEspecial = pokemon.getDefensa_especial() + rd.nextInt(6);
			int velocidad = pokemon.getVelocidad() + rd.nextInt(11);
			char sexo = rd.nextBoolean() ? 'M' : 'F';
			String estado = "NORMAL";
			String nombrePokemon = rs.getString("NOM_POKEMON");
			int nivel = pokemon.getNivel() + rd.nextInt(3);

			nuevoPokemon = new Pokemon(0, 0, rs.getInt("NUM_POKEDEX"), 0, rs.getString("TIPO1"), rs.getString("TIPO2"),
					vitalidad, ataque, defensa, ataqueEspecial, defensaEspecial, velocidad, nivel, 0, 0, nombrePokemon,
					estado, sexo, vitalidad, vitalidad, vitalidad, ataque, defensa, ataqueEspecial, defensaEspecial,
					velocidad, 0);

			// Asignar movimientos según el nivel
			LinkedList<Movimiento> movimientos = new LinkedList<>();

			int movimientosCreados = 0;

			int numMovimientos = 1;
			if (nivel >= 3 && nivel <= 5)
				numMovimientos = 2;
			else if (nivel >= 6 && nivel <= 8)
				numMovimientos = 3;
			else if (nivel >= 9)
				numMovimientos = 4;

			// Obtener movimientos aleatorios distintos de Placaje
			while (movimientosCreados < numMovimientos) {
				movimientos.add(MovimientoBD.asignarMovimientosAleatoriosRival(nuevoPokemon, movimientosCreados));
				++movimientosCreados;
			}

			// Asignar movimientos al Pokémon rival (ajusta según tu modelo)
			nuevoPokemon.setMovPrincipales(movimientos);

			rs.close();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nuevoPokemon;
	}

	public static List<Pokemon> equipoRival(List<Pokemon> equipo) {
		List<Pokemon> equipoRival = new ArrayList<>();
		int i = 0;

		while (i < equipo.size()) { // Asegúrate de no exceder el tamaño de la lista
			Pokemon pokemonRival = generarPokemonRivalAleatorio(equipo.get(i));
			equipoRival.add(pokemonRival);
			i++;
		}

		return equipoRival;
	}

	public static void actualizarPokemonExperiencia(Pokemon pokemon) {
		String sql = "UPDATE pokemon SET nivel = ?, experiencia = ? WHERE id = ?";

		try (Connection conn = BDConecction.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, pokemon.getNivel());
			stmt.setInt(2, pokemon.getExperiencia());
			stmt.setInt(3, pokemon.getId_pokemon());

			stmt.executeUpdate();
			System.out.println("Datos del Pokémon actualizados en la base de datos.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static int obtenerEvolucion(int numPokedex, int nivelActual) {
		String sql = "SELECT num_pokedex, nivel_evolucion FROM pokemon WHERE num_pokedex = ?";
		try (Connection conn = BDConecction.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			int numPokedexEvo = numPokedex + 1;
			stmt.setInt(1, numPokedexEvo); // Consultar el siguiente número de la Pokédex
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				int nivelEvolucion = rs.getInt("nivel_evolucion");

				// Verificar si el nivel actual cumple con los requisitos de evolución
				if ((nivelEvolucion == 2 && nivelActual >= 25) || (nivelEvolucion == 3 && nivelActual >= 50)) {
					return rs.getInt("num_pokedex"); // Devuelve el nuevo número de Pokédex
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return numPokedex; // Si no evoluciona, devuelve el mismo número de Pokédex
	}

	public static void actualizarVida(Pokemon pokemon) {
		String sql = "UPDATE POKEMON SET VITALIDAD_OBJ = ? WHERE ID_POKEMON = ?";
		try (Connection conexion = BDConecction.getConnection();
				PreparedStatement pstmt = conexion.prepareStatement(sql)) {
			pstmt.setInt(1, pokemon.getVitalidadOBJ());
			pstmt.setInt(2, pokemon.getId_pokemon());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error al actualizar la vida del Pokémon: " + e.getMessage());
		}
	}

}