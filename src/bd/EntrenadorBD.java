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
	
	public static void actualizarPokedolares(Connection con, Entrenador entrenador) throws SQLException {
        String sql = "UPDATE ENTRENADOR SET POKEDOLARES = ? WHERE ID_ENTRENADOR = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, entrenador.getPokedolares());
        ps.setInt(2, entrenador.getIdEntrenador());
        ps.executeUpdate();
    }
	
	public static Entrenador obtenerEntrenadorPorId(int idEntrenador) {
	    String sql = "SELECT ID_ENTRENADOR, USUARIO, CONTRASENA, POKEDOLARES FROM ENTRENADOR WHERE ID_ENTRENADOR = ?";
	    
	    try (Connection conexion = BDConecction.getConnection();
	         PreparedStatement pstmt = conexion.prepareStatement(sql)) {
	        
	        pstmt.setInt(1, idEntrenador);
	        
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                Entrenador entrenador = new Entrenador(rs.getString("USUARIO"), rs.getString("CONTRASENA"));
	                entrenador.setIdEntrenador(rs.getInt("ID_ENTRENADOR"));
	                entrenador.setPokedolares(rs.getInt("POKEDOLARES"));
	                return entrenador;
	            }
	        }
	        
	    } catch (SQLException e) {
	        System.err.println("Error al obtener entrenador por ID: " + e.getMessage());
	    }
	    
	    return null;
	}
	
	public static void actualizarPokedolares(Entrenador entrenador) {
	    String sql = "UPDATE ENTRENADOR SET POKEDOLARES = ? WHERE ID_ENTRENADOR = ?";
	    
	    try (Connection conexion = BDConecction.getConnection();
	         PreparedStatement pstmt = conexion.prepareStatement(sql)) {
	        
	        pstmt.setInt(1, entrenador.getPokedolares());
	        pstmt.setInt(2, entrenador.getIdEntrenador());
	        pstmt.executeUpdate();
	        
	    } catch (SQLException e) {
	        System.err.println("Error al actualizar pokédolares del entrenador: " + e.getMessage());
	    }
	}

}
