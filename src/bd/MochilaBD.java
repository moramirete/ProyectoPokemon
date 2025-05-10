package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;

import model.Mochila;
import model.Objeto;
import model.ObjetoEnMochila;

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
	                    rsObjetos.getInt("PRECIO"),
	                    rsObjetos.getString("RUTA_IMAGEN"),
	                    rsObjetos.getString("DESCRIPCION")
	                );
	                mochilaInicial.add(objeto);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return mochilaInicial;
	    }
	
    public static boolean agregarObjeto(Mochila mochila) {
    	
    	if (mochila.getCantidad() <= 0) {
			return false;
		}
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
        	//Si es cerro no aparece
        	if (nuevaCantidad <= 0) {
                
                return eliminarObjeto(idEntrenador, idObjeto);
            }
        	
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

    public static ArrayList<ObjetoEnMochila> obtenerContenidoMochila(int idEntrenador) {
        ArrayList<ObjetoEnMochila> lista = new ArrayList<>();
        try (Connection con = BDConecction.getConnection()) {
            String sql = """
                SELECT o.NOM_OBJETO, o.DESCRIPCION, m.CANTIDAD, o.RUTA_IMAGEN
                FROM MOCHILA m 
                JOIN OBJETO o ON m.ID_OBJETO = o.ID_OBJETO 
                WHERE m.ID_ENTRENADOR = ? AND m.CANTIDAD > 0
            """;
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idEntrenador);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(new ObjetoEnMochila(
                    rs.getString("NOM_OBJETO"),
                    rs.getString("DESCRIPCION"),
                    rs.getInt("CANTIDAD"),
                    rs.getString("RUTA_IMAGEN")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public static ArrayList<ObjetoEnMochila> obtenerContenidoTablaObjetos(int idEntrenador) {
        ArrayList<ObjetoEnMochila> lista = new ArrayList<>();
        try (Connection con = BDConecction.getConnection()) {
            String sql = """
                SELECT o.NOM_OBJETO, o.DESCRIPCION, m.CANTIDAD, o.RUTA_IMAGEN
                FROM MOCHILA m 
                JOIN OBJETO o ON m.ID_OBJETO = o.ID_OBJETO 
                WHERE m.ID_ENTRENADOR = ? AND m.CANTIDAD > 0
                AND o.ID_OBJETO NOT IN (8, 9, 10, 11)
            """;
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idEntrenador);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(new ObjetoEnMochila(
                    rs.getString("NOM_OBJETO"),
                    rs.getString("DESCRIPCION"),
                    rs.getInt("CANTIDAD"),
                    rs.getString("RUTA_IMAGEN")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public static int obtenerIdObjetoNombre(String nombreObjeto) {
        try (Connection con = BDConecction.getConnection()) {
            String sql = "SELECT ID_OBJETO FROM OBJETO WHERE NOM_OBJETO = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nombreObjeto);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("ID_OBJETO");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int obtenerPrecioObjetoId(int idObjeto) {
        try (Connection con = BDConecction.getConnection()) {
            String sql = "SELECT PRECIO FROM OBJETO WHERE ID_OBJETO = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idObjeto);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("PRECIO");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}