package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Entrenador;

/**
 * Clase de acceso a datos para operaciones relacionadas con entrenadores.
 * Proporciona métodos para crear, consultar y actualizar entrenadores en la base de datos.
 */
public class EntrenadorBD {

    /**
     * Crea un nuevo entrenador en la base de datos y le asigna un ID único.
     *
     * @param con        Conexión activa a la base de datos.
     * @param entrenador Entrenador a crear.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    public static void crearEntrenador(Connection con, Entrenador entrenador) throws SQLException {
        entrenador.setIdEntrenador(obtenerIdEntrenador(con));
        String sql = "INSERT INTO ENTRENADOR (ID_ENTRENADOR, USUARIO, CONTRASENA, POKEDOLARES) VALUES(?,?,?,?)";
        PreparedStatement st = con.prepareStatement(sql);
        st.setInt(1, entrenador.getIdEntrenador());
        st.setString(2, entrenador.getUsuario());
        st.setString(3, entrenador.getPass());
        st.setInt(4, entrenador.getPokedolares());
        st.executeUpdate();
    }

    /**
     * Obtiene el siguiente ID disponible para un nuevo entrenador.
     *
     * @param con Conexión activa a la base de datos.
     * @return El siguiente ID de entrenador disponible.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    private static int obtenerIdEntrenador(Connection con) throws SQLException {
        int idEntrenador = 0;
        String sql = "SELECT MAX(ID_ENTRENADOR)+1 FROM ENTRENADOR";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            idEntrenador = rs.getInt(1);
        }
        return idEntrenador;
    }

    /**
     * Obtiene el ID y los pokedólares del entrenador a partir de su usuario y contraseña.
     *
     * @param conexion   Conexión activa a la base de datos.
     * @param entrenador Entrenador con usuario y contraseña establecidos.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    public static void obtenerIDPokedolaresEntre(Connection conexion, Entrenador entrenador) throws SQLException {
        String sql = "SELECT ID_ENTRENADOR, POKEDOLARES FROM ENTRENADOR WHERE USUARIO = ? AND CONTRASENA = ?";
        PreparedStatement ps = conexion.prepareStatement(sql);
        ps.setString(1, entrenador.getUsuario());
        ps.setString(2, entrenador.getPass());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            entrenador.setIdEntrenador(rs.getInt(1));
            entrenador.setPokedolares(rs.getInt(2));
        }
    }

    /**
     * Actualiza la cantidad de pokedólares de un entrenador en la base de datos.
     *
     * @param con        Conexión activa a la base de datos.
     * @param entrenador Entrenador con el nuevo valor de pokedólares.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    public static void actualizarPokedolares(Connection con, Entrenador entrenador) throws SQLException {
        String sql = "UPDATE ENTRENADOR SET POKEDOLARES = ? WHERE ID_ENTRENADOR = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, entrenador.getPokedolares());
        ps.setInt(2, entrenador.getIdEntrenador());
        ps.executeUpdate();
    }

}