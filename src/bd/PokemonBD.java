package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import model.Entrenador;
import model.Pokemon;

public class PokemonBD {
    public static Pokemon generarPokemonPrincipal(int idEntrenador, Connection conexion) {
        
    	Pokemon nuevoPokemon = null;
    	
    	try {
            Random rd = new Random();

            String queryPokedex = "SELECT * FROM POKEDEX ORDER BY RAND() LIMIT 1";
            PreparedStatement statementPokedex = conexion.prepareStatement(queryPokedex);
            ResultSet resultadoPokedex = statementPokedex.executeQuery();

            if (!resultadoPokedex.next()) {
                throw new SQLException("No se encontró ningún Pokémon en la tabla POKEDEX.");
            }

            int nuevoIdPokemon = generarIdUnico(conexion);
   
            int vitalidad = 50 + rd.nextInt(51);
            int ataque = 20 + rd.nextInt(81);   
            int defensa = 20 + rd.nextInt(81);  
            int ataqueEspecial = 20 + rd.nextInt(81); 
            int defensaEspecial = 20 + rd.nextInt(81); 
            int velocidad = 20 + rd.nextInt(81);  
            int fertilidad = 1 + rd.nextInt(5);   
            char sexo = rd.nextBoolean() ? 'M' : 'F';
            
            if (sexo != 'M' && sexo != 'F') {
                throw new IllegalArgumentException("Valor inválido para SEXO: " + sexo);
            }
            
            String estado = "NORMAL";
            
            System.out.println("Sexo que se ha generado:" + sexo);
            
            nuevoPokemon = new Pokemon(
            		nuevoIdPokemon,
            		idEntrenador,
            		resultadoPokedex.getInt("NUM_POKEDEX"),
            		0,
            		vitalidad,
            		ataque,
            		defensa,
            		ataqueEspecial,
            		defensaEspecial,
            		velocidad,
            		5,
            		fertilidad,
            		1,
            		resultadoPokedex.getString("NOM_POKEMON"),
            		estado,
            		sexo
            );
            	
            

            String queryInsertPokemon = "INSERT INTO POKEMON (ID_POKEMON, ID_ENTRENADOR, NUM_POKEDEX, ID_OBJETO, NOM_POKEMON, VITALIDAD, ATAQUE, DEFENSA, AT_ESPECIAL, DEF_ESPECIAL, VELOCIDAD, NIVEL, FERTILIDAD, SEXO, ESTADO, EQUIPO) " +
                                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement st = conexion.prepareStatement(queryInsertPokemon);

            st.setInt(1, nuevoIdPokemon);
            st.setInt(2, idEntrenador);
            st.setInt(3, resultadoPokedex.getInt("NUM_POKEDEX"));
            st.setInt(4, 0);
            st.setString(5, resultadoPokedex.getString("NOM_POKEMON"));
            st.setInt(6, vitalidad);
            st.setInt(7, ataque);
            st.setInt(8, defensa);
            st.setInt(9, ataqueEspecial);
            st.setInt(10, defensaEspecial);
            st.setInt(11, velocidad);
            st.setInt(12, 5); 
            st.setInt(13, fertilidad);
            st.setString(14, String.valueOf(sexo));
            st.setString(15, "NORMAL"); 
            st.setInt(16, 1); 
            
            st.executeUpdate();
            st.close();

            System.out.println("Se ha generado un Pokemon principal " + resultadoPokedex.getString("NOM_POKEMON") + " para el entrenador con ID: " + idEntrenador);

        } catch (SQLException e) {
            System.err.println("Error al generar el Pokemon principal: " + e.getMessage());
            e.printStackTrace(); 
        }
        
        return nuevoPokemon;
        
    }

    
    private static int generarIdUnico(Connection conexion) throws SQLException {
        String query = "SELECT MAX(ID_POKEMON) AS MAX_ID FROM POKEMON";
        PreparedStatement statement = conexion.prepareStatement(query);
        ResultSet resultado = statement.executeQuery();

        if (resultado.next()) {
            return resultado.getInt("MAX_ID") + 1; 
        } else {
            return 1; 
        }
    }
}
