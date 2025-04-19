package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;

import model.Mochila;
import model.Objeto;

public class MochilaBD {

    
	 public static LinkedList<Objeto> crearMochilaInicial(int idEntrenador) {
	        LinkedList<Objeto> mochilaInicial = new LinkedList<>();

	        try (Connection con = BDConecction.getConnection()) {
	           
	            String sqlObjetos = "SELECT * FROM OBJETO WHERE ID_OBJETO != 0"; // Excluir el objeto "Ninguno"
	            PreparedStatement stmtObjetos = con.prepareStatement(sqlObjetos);
	            ResultSet rsObjetos = stmtObjetos.executeQuery();

	            String sqlMochila = "INSERT INTO MOCHILA (ID_ENTRENADOR, ID_OBJETO, CANTIDAD) VALUES (?, ?, ?)";
	            PreparedStatement stmtMochila = con.prepareStatement(sqlMochila);

	            while (rsObjetos.next()) {
	                int idObjeto = rsObjetos.getInt("ID_OBJETO");
	                String nombreObjeto = rsObjetos.getString("NOM_OBJETO");
	                int cantidadInicial = (idObjeto == 8) ? 20 : 0; //Pokeball, cantidad inicial de 20

	                stmtMochila.setInt(1, idEntrenador);
	                stmtMochila.setInt(2, idObjeto);
	                stmtMochila.setInt(3, cantidadInicial);
	                stmtMochila.executeUpdate();

	                Objeto objeto = new Objeto(
	                    idObjeto,
	                    nombreObjeto,
	                    rsObjetos.getInt("ATAQUE"),
	                    rsObjetos.getInt("DEFENSA"),
	                    rsObjetos.getInt("AT_ESPECIAL"),
	                    rsObjetos.getInt("DEF_ESPECIAL"),
	                    rsObjetos.getInt("VELOCIDAD"),
	                    rsObjetos.getInt("VITALIDAD"),
	                    rsObjetos.getInt("PP"),
	                    rsObjetos.getInt("PRECIO")
	                );
	                mochilaInicial.add(objeto);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return mochilaInicial;
	    }
	
    public static boolean agregarObjeto(Mochila mochila) {
        try (Connection con = BDConecction.getConnection()) {
            String sql = "INSERT INTO MOCHILA (ID_ENTRENADOR, ID_OBJETO, CANTIDAD) VALUES (?, ?, ?) ";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, mochila.getIdEntrenador());
            stmt.setInt(2, mochila.getIdObjeto());
            stmt.setInt(3, mochila.getCantidad());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public static boolean eliminarObjeto(int idEntrenador, int idObjeto) {
        try (Connection con = BDConecction.getConnection()) {
            String sql = "DELETE FROM MOCHILA WHERE ID_ENTRENADOR = ? AND ID_OBJETO = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idEntrenador);
            stmt.setInt(2, idObjeto);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public static boolean actualizarCantidad(int idEntrenador, int idObjeto, int nuevaCantidad) {
        try (Connection con = BDConecction.getConnection()) {
            String sql = "UPDATE MOCHILA SET CANTIDAD = ? WHERE ID_ENTRENADOR = ? AND ID_OBJETO = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, nuevaCantidad);
            stmt.setInt(2, idEntrenador);
            stmt.setInt(3, idObjeto);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public static ArrayList<Mochila> obtenerMochila(int idEntrenador) {
        ArrayList<Mochila> mochila = new ArrayList<>();
        try (Connection con = BDConecction.getConnection()) {
            String sql = "SELECT * FROM MOCHILA WHERE ID_ENTRENADOR = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idEntrenador);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                mochila.add(new Mochila(
                    rs.getInt("ID_ENTRENADOR"),
                    rs.getInt("ID_OBJETO"),
                    rs.getInt("CANTIDAD")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mochila;
    }

    
}