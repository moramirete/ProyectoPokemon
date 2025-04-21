package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
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
   
            int vitalidad = 15 + rd.nextInt(16);
            int ataque = 5 + rd.nextInt(6);
            int defensa = 5 + rd.nextInt(6);
            int ataqueEspecial = 5 + rd.nextInt(6); 
            int defensaEspecial = 5 + rd.nextInt(6); 
            int velocidad = 5 + rd.nextInt(11);
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
            		resultadoPokedex.getString("TIPO1"),
            		resultadoPokedex.getString("TIPO2"),
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
            	
            

            String queryInsertPokemon = "INSERT INTO POKEMON (ID_POKEMON, ID_ENTRENADOR, NUM_POKEDEX, ID_OBJETO, NOM_POKEMON, VITALIDAD, ATAQUE, DEFENSA, AT_ESPECIAL, DEF_ESPECIAL, VELOCIDAD, NIVEL, FERTILIDAD, SEXO, ESTADO, EQUIPO, TIPO1, TIPO2) " +
                                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            st.setString(17, resultadoPokedex.getString("TIPO1"));
            st.setString(18, resultadoPokedex.getString("TIPO2"));
            
            st.executeUpdate();
            st.close();

            System.out.println("Se ha generado un Pokemon principal " + resultadoPokedex.getString("NOM_POKEMON") + " para el entrenador con ID: " + idEntrenador);

        } catch (SQLException e) {
            System.err.println("Error al generar el Pokemon principal: " + e.getMessage());
            e.printStackTrace(); 
        }
        
        return nuevoPokemon;
        
    }
    
    public static Pokemon generarPokemonCaptura(int idEntrenador, Connection conexion) {
        
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
   
            int vitalidad = 15 + rd.nextInt(16);
            int ataque = 5 + rd.nextInt(6);
            int defensa = 5 + rd.nextInt(6);
            int ataqueEspecial = 5 + rd.nextInt(6); 
            int defensaEspecial = 5 + rd.nextInt(6); 
            int velocidad = 5 + rd.nextInt(11);
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
            		resultadoPokedex.getString("TIPO1"),
            		resultadoPokedex.getString("TIPO2"),
            		vitalidad,
            		ataque,
            		defensa,
            		ataqueEspecial,
            		defensaEspecial,
            		velocidad,
            		5,
            		fertilidad,
            		3,
            		resultadoPokedex.getString("NOM_POKEMON"),
            		estado,
            		sexo
            );
            
        } catch (SQLException e) {
            System.err.println("Error al generar el Pokemon principal: " + e.getMessage());
            e.printStackTrace(); 
        }
        
        return nuevoPokemon;
        
    }
    
    public static void guardarPokemonCaptura(Pokemon pok, Connection conexion) {
    	try {

            	String queryInsertPokemon = "INSERT INTO POKEMON (ID_POKEMON, ID_ENTRENADOR, NUM_POKEDEX, ID_OBJETO, NOM_POKEMON, VITALIDAD, ATAQUE, DEFENSA, AT_ESPECIAL, DEF_ESPECIAL, VELOCIDAD, NIVEL, FERTILIDAD, SEXO, ESTADO, EQUIPO, TIPO1, TIPO2) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            	PreparedStatement st = conexion.prepareStatement(queryInsertPokemon);

            	st.setInt(1, pok.getId_pokemon());
            	st.setInt(2, pok.getId_entrenador());
            	st.setInt(3, pok.getNum_pokedex());
            	st.setInt(4, pok.getId_objeto());
            	st.setString(5, pok.getNombre_pokemon());
            	st.setInt(6, pok.getVitalidad());
            	st.setInt(7, pok.getAtaque());
            	st.setInt(8, pok.getDefensa());
            	st.setInt(9, pok.getAtaque_especial());
            	st.setInt(10, pok.getDefensa_especial());
            	st.setInt(11,  pok.getVelocidad());
            	st.setInt(12, pok.getNivel()); 
            	st.setInt(13,  pok.getFertilidad());
            	st.setString(14, String.valueOf( pok.getSexo()));
            	st.setString(15, pok.getEstado()); 
            	st.setInt(16, pok.getEquipo());
            	st.setString(17, pok.getTipo1());
            	st.setString(18, pok.getTipo2());

            	st.executeUpdate();
            	st.close();

    	}catch (SQLException e) {
            System.err.println("Error al generar el Pokemon principal: " + e.getMessage());
            e.printStackTrace(); 
        }
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
