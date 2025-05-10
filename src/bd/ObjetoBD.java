package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Objeto;

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

}
