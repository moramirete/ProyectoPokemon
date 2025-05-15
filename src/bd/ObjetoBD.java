package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Objeto;
import model.Pokemon;

public class ObjetoBD {

	// Método para obtener todos los objetos de la base de datos
	public static ArrayList<Objeto> obtenerTodosLosObjetos() {
		ArrayList<Objeto> objetos = new ArrayList<>();
		try (Connection con = BDConecction.getConnection()) {
			String sql = "SELECT * FROM OBJETO\r\n" + "WHERE ID_OBJETO <> 0;";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Objeto objeto = new Objeto(rs.getInt("ID_OBJETO"), rs.getString("NOM_OBJETO"), rs.getInt("ATAQUE"),
						rs.getInt("DEFENSA"), rs.getInt("AT_ESPECIAL"), rs.getInt("DEF_ESPECIAL"),
						rs.getInt("VELOCIDAD"), rs.getInt("VITALIDAD"), rs.getInt("PP"), rs.getInt("PRECIO"),
						rs.getString("RUTA_IMAGEN"), rs.getString("DESCRIPCION"));

				System.out.println("Objeto: " + objeto.getNombreObjeto() + ", Descripción: " + objeto.getDescripcion());
				objetos.add(objeto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return objetos;
	}

	public static String obtenerNombreObjetoPorId(int idObjeto) {
		String nombreObjeto = null;
 
		try (Connection con = BDConecction.getConnection()){
			// Consulta para obtener el nombre del objeto por su ID
			String query = "SELECT NOM_OBJETO FROM OBJETO WHERE ID_OBJETO = ?";
			PreparedStatement statement = con.prepareStatement(query);
			statement.setInt(1, idObjeto);

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				nombreObjeto = resultSet.getString("NOM_OBJETO");
			} else {
				System.err.println("No se encontró ningún objeto con ID: " + idObjeto);
			}

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			System.err.println("Error al obtener el nombre del objeto: " + e.getMessage());
			e.printStackTrace();
		}

		return nombreObjeto;
	}
	
	public static int obtenerIdObjetoPorNombre(String nombreObjeto) {
	    int idObjeto = -1; // Valor por defecto si no se encuentra el objeto

	    try (Connection con = BDConecction.getConnection()) {
	        // Consulta para obtener el ID del objeto por su nombre
	        String query = "SELECT ID_OBJETO FROM OBJETO WHERE NOM_OBJETO = ?";
	        PreparedStatement st = con.prepareStatement(query);
	        st.setString(1, nombreObjeto);

	        ResultSet rs = st.executeQuery();
	        if (rs.next()) {
	            idObjeto = rs.getInt("ID_OBJETO");
	        }

	        rs.close();
	        st.close();
	    } catch (SQLException e) {
	        System.err.println("Error al obtener el ID del objeto: " + e.getMessage());
	        e.printStackTrace();
	    }

	    return idObjeto;
	}
	
	public static void quitarObjeto(Pokemon pokemon, int idEntrenador) {
	    try (Connection con = BDConecction.getConnection()) {
	        con.setAutoCommit(false); // Iniciar una transacción

	        // 1. Obtener el ID del objeto que se va a quitar
	        int idObjeto = pokemon.getId_objeto();

	        // 2. Quitar el objeto del Pokémon
	        String queryQuitarObjeto = "UPDATE POKEMON SET ID_OBJETO = 0, VITALIDAD_OBJ = VITALIDADMAX, ATAQUE_OBJ = ATAQUE, "
	                + "DEFENSA_OBJ = DEFENSA, AT_ESPECIAL_OBJ = AT_ESPECIAL, DEF_ESPECIAL_OBJ = DEF_ESPECIAL, "
	                + "VELOCIDAD_OBJ = VELOCIDAD WHERE ID_POKEMON = ?";
	        PreparedStatement statementQuitarObjeto = con.prepareStatement(queryQuitarObjeto);
	        statementQuitarObjeto.setInt(1, pokemon.getId_pokemon());
	        statementQuitarObjeto.executeUpdate();

	        // 3. Sumar el objeto a la mochila
	        String querySumarObjeto = "INSERT INTO MOCHILA (ID_ENTRENADOR, ID_OBJETO, CANTIDAD) "
	                + "VALUES (?, ?, 1) "
	                + "ON DUPLICATE KEY UPDATE CANTIDAD = CANTIDAD + 1";
	        PreparedStatement statementSumarObjeto = con.prepareStatement(querySumarObjeto);
	        statementSumarObjeto.setInt(1, idEntrenador);
	        statementSumarObjeto.setInt(2, idObjeto);
	        statementSumarObjeto.executeUpdate();

	        // Confirmar la transacción
	        con.commit();

	        System.out.println("El objeto se quitó del Pokémon y se añadió a la mochila.");
	    } catch (SQLException e) {
	        System.err.println("Error al quitar el objeto del Pokémon: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

	public static void equiparObjeto(Pokemon pokemon, int nuevoIdObjeto) {
	    try (Connection con = BDConecction.getConnection()) {
	        con.setAutoCommit(false); // Iniciar una transacción

	        // Paso 1: Obtener el ID del objeto actual del Pokémon
	        String queryGetObjetoActual = "SELECT ID_OBJETO FROM POKEMON WHERE ID_POKEMON = ?";
	        PreparedStatement stGetObjetoActual = con.prepareStatement(queryGetObjetoActual);
	        stGetObjetoActual.setInt(1, pokemon.getId_pokemon());
	        ResultSet rs = stGetObjetoActual.executeQuery();

	        int idObjetoActual = 0;
	        if (rs.next()) {
	            idObjetoActual = rs.getInt("ID_OBJETO");
	        }
	        rs.close();
	        stGetObjetoActual.close();

	        // Paso 2: Si tiene un objeto equipado, añadirlo a la mochila
	        if (idObjetoActual != 0) {
	            String queryAddToMochila = "INSERT INTO MOCHILA (ID_ENTRENADOR, ID_OBJETO, CANTIDAD) " +
	                                       "VALUES (?, ?, 1) " +
	                                       "ON DUPLICATE KEY UPDATE CANTIDAD = CANTIDAD + 1";
	            PreparedStatement stAddToMochila = con.prepareStatement(queryAddToMochila);
	            stAddToMochila.setInt(1, pokemon.getId_entrenador());
	            stAddToMochila.setInt(2, idObjetoActual);
	            stAddToMochila.executeUpdate();
	            stAddToMochila.close();
	        }

	        // Paso 3: Restar el nuevo objeto de la mochila
	        String queryRestarDeMochila = "UPDATE MOCHILA SET CANTIDAD = CANTIDAD - 1 " +
	                                      "WHERE ID_ENTRENADOR = ? AND ID_OBJETO = ? AND CANTIDAD > 0";
	        PreparedStatement stRestarDeMochila = con.prepareStatement(queryRestarDeMochila);
	        stRestarDeMochila.setInt(1, pokemon.getId_entrenador());
	        stRestarDeMochila.setInt(2, nuevoIdObjeto);
	        int rowsUpdated = stRestarDeMochila.executeUpdate();
	        stRestarDeMochila.close();

	        if (rowsUpdated == 0) {
	            throw new SQLException("No hay suficientes objetos en la mochila para equipar.");
	        }

	        // Paso 4: Obtener los porcentajes del nuevo objeto
	        String queryGetObjetoStats = "SELECT ATAQUE, DEFENSA, AT_ESPECIAL, DEF_ESPECIAL, VELOCIDAD, VITALIDAD " +
	                                     "FROM OBJETO WHERE ID_OBJETO = ?";
	        PreparedStatement stGetObjetoStats = con.prepareStatement(queryGetObjetoStats);
	        stGetObjetoStats.setInt(1, nuevoIdObjeto);
	        ResultSet rsObjeto = stGetObjetoStats.executeQuery();

	        int ataquePorc = 0, defensaPorc = 0, atEspecialPorc = 0, defEspecialPorc = 0, velocidadPorc = 0, vitalidadPorc = 0;
	        if (rsObjeto.next()) {
	            ataquePorc = rsObjeto.getInt("ATAQUE");
	            defensaPorc = rsObjeto.getInt("DEFENSA");
	            atEspecialPorc = rsObjeto.getInt("AT_ESPECIAL");
	            defEspecialPorc = rsObjeto.getInt("DEF_ESPECIAL");
	            velocidadPorc = rsObjeto.getInt("VELOCIDAD");
	            vitalidadPorc = rsObjeto.getInt("VITALIDAD");
	        }
	        rsObjeto.close();
	        stGetObjetoStats.close();

	        // Paso 5: Calcular el nuevo porcentaje de vida actual respecto a la vitalidad máxima
	        String queryGetVitalidad = "SELECT VITALIDAD, VITALIDADMAX FROM POKEMON WHERE ID_POKEMON = ?";
	        PreparedStatement stGetVitalidad = con.prepareStatement(queryGetVitalidad);
	        stGetVitalidad.setInt(1, pokemon.getId_pokemon());
	        ResultSet rsVitalidad = stGetVitalidad.executeQuery();

	        double porcentajeVida = 1.0; // Porcentaje de vida actual respecto a la máxima
	        int vitalidadActual = 0, vitalidadMaxima = 0;
	        if (rsVitalidad.next()) {
	            vitalidadActual = rsVitalidad.getInt("VITALIDAD");
	            vitalidadMaxima = rsVitalidad.getInt("VITALIDADMAX");
	            if (vitalidadMaxima > 0) {
	                porcentajeVida = (double) vitalidadActual / vitalidadMaxima;
	            }
	        }
	        rsVitalidad.close();
	        stGetVitalidad.close();

	        // Paso 6: Calcular los nuevos valores de VITALIDAD_OBJ y VITALIDAD
	        int nuevaVitalidadMax = vitalidadMaxima + (vitalidadMaxima * vitalidadPorc / 100);
	        int nuevaVitalidad = (int) Math.round(nuevaVitalidadMax * porcentajeVida);

	        // Paso 7: Actualizar la ID del objeto y las estadísticas en la base de datos
	        String queryUpdateStats = "UPDATE POKEMON SET " +
	                                  "ID_OBJETO = ?, " +
	                                  "VITALIDAD_OBJ = ?, " +
	                                  "VITALIDAD = ?, " +
	                                  "ATAQUE_OBJ = ATAQUE + (ATAQUE * ? / 100), " +
	                                  "DEFENSA_OBJ = DEFENSA + (DEFENSA * ? / 100), " +
	                                  "AT_ESPECIAL_OBJ = AT_ESPECIAL + (AT_ESPECIAL * ? / 100), " +
	                                  "DEF_ESPECIAL_OBJ = DEF_ESPECIAL + (DEF_ESPECIAL * ? / 100), " +
	                                  "VELOCIDAD_OBJ = VELOCIDAD + (VELOCIDAD * ? / 100) " +
	                                  "WHERE ID_POKEMON = ?";
	        PreparedStatement stUpdateStats = con.prepareStatement(queryUpdateStats);
	        stUpdateStats.setInt(1, nuevoIdObjeto); // ID del nuevo objeto
	        stUpdateStats.setInt(2, nuevaVitalidadMax); // Nueva vitalidad máxima con objeto
	        stUpdateStats.setInt(3, nuevaVitalidad); // Nueva vitalidad actual
	        stUpdateStats.setInt(4, ataquePorc);    // Porcentaje de ATAQUE_OBJ
	        stUpdateStats.setInt(5, defensaPorc);   // Porcentaje de DEFENSA_OBJ
	        stUpdateStats.setInt(6, atEspecialPorc); // Porcentaje de AT_ESPECIAL_OBJ
	        stUpdateStats.setInt(7, defEspecialPorc); // Porcentaje de DEF_ESPECIAL_OBJ
	        stUpdateStats.setInt(8, velocidadPorc); // Porcentaje de VELOCIDAD_OBJ
	        stUpdateStats.setInt(9, pokemon.getId_pokemon());
	        stUpdateStats.executeUpdate();
	        stUpdateStats.close();

	        con.commit(); // Confirmar la transacción

	    } catch (SQLException e) {
	        System.err.println("Error al equipar el objeto: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
}
