package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Entrenador;
import model.Movimiento;
import model.Pokemon;

public class MovimientoBD {

	public static Movimiento otorgarPrimerMovimiento(Connection con, Entrenador entrenador, Pokemon pokemon){
		
		Movimiento mov = null;
		
		try {
			
            String queryPlacaje = "SELECT * FROM MOVIMIENTO WHERE NOM_MOVIMIENTO = \"Placaje\";";
            PreparedStatement statementPlacaje = con.prepareStatement(queryPlacaje);
            ResultSet resultadoPlacaje = statementPlacaje.executeQuery();
            
            if (!resultadoPlacaje.next()) {
                throw new SQLException("No se encontró Placaje en la tabla MOVIMIENTO.");
            }
            		
            //Creacion del movimiento
            
            mov = new Movimiento(
            		
            		resultadoPlacaje.getInt("ID_MOVIMIENTO"),
                	resultadoPlacaje.getString("NOM_MOVIMIENTO"),
                	pokemon.getId_pokemon(),
                	resultadoPlacaje.getString("DESCRIPCION"),
                	resultadoPlacaje.getInt("PRECI"),
                	resultadoPlacaje.getInt("PP_MAX"),
                	resultadoPlacaje.getInt("PP_MAX"), //Al principio los tiene al maximo
                	resultadoPlacaje.getString("TIPO"),
                	resultadoPlacaje.getString("TIPO_MOV"),
                	resultadoPlacaje.getInt("POTENCIA"),
                	resultadoPlacaje.getString("ESTADO"),
                	resultadoPlacaje.getInt("TURNOS"),
                	resultadoPlacaje.getInt("MEJORA"),
                	resultadoPlacaje.getInt("NUM_MOV"),
                	resultadoPlacaje.getInt("CANT_MEJORA")
            		
            		);
            
            String queryInsertMov = "INSERT INTO movimiento_pokemon (ID_ENTRENADOR , ID_MOVIMIENTO, PP_ACTUALES, POSICION, ID_POKEMON)" + 
            						"VALUES (?, ?, ?, ?, ?)";
            
            PreparedStatement st = con.prepareStatement(queryInsertMov);
            
            st.setInt(1, entrenador.getIdEntrenador());
            st.setInt(2, mov.getId_movimiento());
            st.setInt(3, mov.getPp_actual());
            st.setInt(4, 1); //Al ser el primer movimiento, 100x100 al principio esta en la 1 posicion
            st.setInt(5, pokemon.getId_pokemon());
            
            st.executeUpdate();
            st.close();
            
            System.out.println("Se ha agregado correctamente el movimiento " + resultadoPlacaje.getString("NOM_MOVIMIENTO"));
            
		}catch (SQLException e) {
            System.err.println("Error al generar el Pokemon principal: " + e.getMessage());
            e.printStackTrace(); 
        }
		
		return mov;
		
	}
	
}
