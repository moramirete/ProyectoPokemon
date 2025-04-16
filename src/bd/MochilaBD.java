package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Mochila;

public class MochilaBD {

    
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