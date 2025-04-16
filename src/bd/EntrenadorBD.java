package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Entrenador;

public class EntrenadorBD {
	public static void crearEntrenador(Connection con, Entrenador entrenador) throws SQLException {
		
		entrenador.setIdEntrenador(obtenerIdEntrenador(con));
		
		Statement statement = null;
		
		String sql = "INSERT INTO ENTRENADOR (ID_ENTRENADOR, USUARIO, CONTRASENA, POKEDOLARES) \r\n"
					+ "VALUES(?,?,?,?)";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, entrenador.getIdEntrenador());
		st.setString(2, entrenador.getUsuario());
		st.setString(3, entrenador.getPass());
		st.setInt(4, entrenador.getPokedolares());
		st.executeUpdate();
		
	}
	
	private static int obtenerIdEntrenador(Connection con) throws SQLException{
		int idEntrenador = 0;
		String sql = "SELECT MAX(ID_ENTRENADOR)+1\r\n" + "FROM ENTRENADOR";

		Statement st;

		st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);

		while (rs.next()) {
			idEntrenador = rs.getInt(1);
		}

		return idEntrenador;
	}
	
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

}
