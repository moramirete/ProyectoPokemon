package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Objeto;

public class ObjetoBD {

    // Método para obtener todos los objetos de la base de datos
    public static ArrayList<Objeto> obtenerTodosLosObjetos() {
        ArrayList<Objeto> objetos = new ArrayList<>();
        try (Connection con = BDConecction.getConnection()) {
            String sql = "SELECT * FROM OBJETO\r\n"
            		+ "WHERE ID_OBJETO <> 0;";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Objeto objeto = new Objeto(
                    rs.getInt("ID_OBJETO"),
                    rs.getString("NOM_OBJETO"),
                    rs.getInt("ATAQUE"),
                    rs.getInt("DEFENSA"),
                    rs.getInt("AT_ESPECIAL"),
                    rs.getInt("DEF_ESPECIAL"),
                    rs.getInt("VELOCIDAD"),
                    rs.getInt("VITALIDAD"),
                    rs.getInt("PP"),
                    rs.getInt("PRECIO"),
                    rs.getString("RUTA_IMAGEN")
                );
                
                
                
                objetos.add(objeto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objetos;
    }
}
