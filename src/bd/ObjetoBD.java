package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Objeto;
import model.Pokemon;

/**
 * Clase de acceso a datos para operaciones relacionadas con objetos.
 * Proporciona métodos para consultar, equipar y quitar objetos en la base de datos.
 */
public class ObjetoBD {

    /**
     * Obtiene todos los objetos de la base de datos, excepto el objeto con ID 0.
     *
     * @return Una lista de objetos disponibles en la base de datos.
     */
    public static ArrayList<Objeto> obtenerTodosLosObjetos() {
        ArrayList<Objeto> objetos = new ArrayList<>();
        try (Connection con = BDConecction.getConnection()) {
            String sql = "SELECT * FROM OBJETO WHERE ID_OBJETO <> 0;";
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

    /**
     * Obtiene el nombre de un objeto a partir de su ID.
     *
     * @param idObjeto El identificador del objeto.
     * @return El nombre del objeto, o null si no se encuentra.
     */
    public static String obtenerNombreObjetoPorId(int idObjeto) {
        String nombreObjeto = null;

        try (Connection con = BDConecction.getConnection()) {
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

    /**
     * Obtiene el ID de un objeto a partir de su nombre.
     *
     * @param nombreObjeto El nombre del objeto.
     * @return El ID del objeto si existe, -1 en caso contrario.
     */
    public static int obtenerIdObjetoPorNombre(String nombreObjeto) {
        int idObjeto = -1; // Valor por defecto si no se encuentra el objeto

        try (Connection con = BDConecction.getConnection()) {
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

    /**
     * Quita el objeto equipado de un Pokémon y lo añade a la mochila del entrenador.
     * Actualiza las estadísticas del Pokémon y la mochila en la base de datos.
     *
     * @param pokemon      El Pokémon al que se le quita el objeto.
     * @param idEntrenador El identificador del entrenador.
     */
    public static void quitarObjeto(Pokemon pokemon, int idEntrenador) {
        try (Connection con = BDConecction.getConnection()) {
            con.setAutoCommit(false); // Iniciar una transacción

            // 1. Obtener el ID del objeto que se va a quitar
            int idObjeto = pokemon.getId_objeto();

            // 2. Quitar el objeto del Pokémon y restaurar estadísticas base
            String queryQuitarObjeto = "UPDATE POKEMON SET ID_OBJETO = 0, VITALIDAD_OBJ = VITALIDAD, VITALIDADMAX = VITALIDADMAX_OBJ,"
                    + " ATAQUE_OBJ = ATAQUE, DEFENSA_OBJ = DEFENSA, AT_ESPECIAL_OBJ = AT_ESPECIAL, DEF_ESPECIAL_OBJ = DEF_ESPECIAL, "
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

    /**
     * Equipa un objeto a un Pokémon, actualizando sus estadísticas y la mochila en la base de datos.
     * Si el Pokémon ya tenía un objeto equipado, lo devuelve a la mochila.
     *
     * @param pokemon      El Pokémon al que se le equipa el objeto.
     * @param nuevoIdObjeto El identificador del nuevo objeto a equipar.
     */
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

            double porcentajeVida = 1.0;
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
                    "VITALIDADMAX_OBJ = ?, " +
                    "ATAQUE_OBJ = ATAQUE + (ATAQUE * ? / 100), " +
                    "DEFENSA_OBJ = DEFENSA + (DEFENSA * ? / 100), " +
                    "AT_ESPECIAL_OBJ = AT_ESPECIAL + (AT_ESPECIAL * ? / 100), " +
                    "DEF_ESPECIAL_OBJ = DEF_ESPECIAL + (DEF_ESPECIAL * ? / 100), " +
                    "VELOCIDAD_OBJ = VELOCIDAD + (VELOCIDAD * ? / 100) " +
                    "WHERE ID_POKEMON = ?";
            PreparedStatement stUpdateStats = con.prepareStatement(queryUpdateStats);
            stUpdateStats.setInt(1, nuevoIdObjeto);
            stUpdateStats.setInt(2, nuevaVitalidad);
            stUpdateStats.setInt(3, nuevaVitalidadMax);
            stUpdateStats.setInt(4, ataquePorc);
            stUpdateStats.setInt(5, defensaPorc);
            stUpdateStats.setInt(6, atEspecialPorc);
            stUpdateStats.setInt(7, defEspecialPorc);
            stUpdateStats.setInt(8, velocidadPorc);
            stUpdateStats.setInt(9, pokemon.getId_pokemon());
            stUpdateStats.executeUpdate();
            stUpdateStats.close();

            con.commit();

        } catch (SQLException e) {
            System.err.println("Error al equipar el objeto: " + e.getMessage());
            e.printStackTrace();
        }
    }

}