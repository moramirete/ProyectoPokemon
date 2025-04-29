package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import model.Entrenador;
import model.Objeto;
import model.ObjetoEnMochila;
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
            		1,
            		fertilidad,
            		1,
            		resultadoPokedex.getString("NOM_POKEMON"),
            		estado,
            		sexo,
            		vitalidad
            );
            	
            

            String queryInsertPokemon = "INSERT INTO POKEMON (ID_POKEMON, ID_ENTRENADOR, NUM_POKEDEX, ID_OBJETO, NOM_POKEMON, VITALIDAD, ATAQUE, DEFENSA, AT_ESPECIAL, DEF_ESPECIAL, VELOCIDAD, NIVEL, FERTILIDAD, SEXO, ESTADO, EQUIPO, TIPO1, TIPO2, VITALIDADMAX) " +
                                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            st.setInt(12, 1); 
            st.setInt(13, fertilidad);
            st.setString(14, String.valueOf(sexo));
            st.setString(15, "NORMAL"); 
            st.setInt(16, 1);
            st.setString(17, resultadoPokedex.getString("TIPO1"));
            st.setString(18, resultadoPokedex.getString("TIPO2"));
            st.setInt(19, vitalidad);
            
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
            		1,
            		fertilidad,
            		3,
            		resultadoPokedex.getString("NOM_POKEMON"),
            		estado,
            		sexo,
            		vitalidad
            );
            
        } catch (SQLException e) {
            System.err.println("Error al generar el Pokemon principal: " + e.getMessage());
            e.printStackTrace(); 
        }
        
        return nuevoPokemon;
        
    }
    
    public static void guardarPokemonCaptura(Pokemon pok, Connection conexion) {
    	try {

            	String queryInsertPokemon = "INSERT INTO POKEMON (ID_POKEMON, ID_ENTRENADOR, NUM_POKEDEX, ID_OBJETO, NOM_POKEMON, "
            			+ "VITALIDAD, ATAQUE, DEFENSA, AT_ESPECIAL, DEF_ESPECIAL, VELOCIDAD, NIVEL, FERTILIDAD, SEXO, ESTADO, "
            			+ "EQUIPO, TIPO1, TIPO2, VITALIDADMAX) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            	st.setInt(19, pok.getVitalidad());
            	

            	st.executeUpdate();
            	st.close();

    	}catch (SQLException e) {
            System.err.println("Error al generar el Pokemon: " + e.getMessage());
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
    
    public static ArrayList<Pokemon> obtenerEquipo(int idEntrenador){
    	ArrayList<Pokemon> equipo = new ArrayList<>();
    	
    	try (Connection con = BDConecction.getConnection()) {
            String sql = "SELECT * \r\n"
            		+ "FROM POKEMON p \r\n"
            		+ "JOIN ENTRENADOR e ON p.ID_ENTRENADOR = e.ID_ENTRENADOR \r\n"
            		+ "WHERE e.ID_ENTRENADOR = ? AND p.EQUIPO > 0 AND p.EQUIPO < 3 ";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idEntrenador);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                equipo.add(new Pokemon(
                    rs.getInt("ID_POKEMON"),       
                    rs.getInt("ID_ENTRENADOR"),     
                    rs.getInt("NUM_POKEDEX"),       
                    rs.getInt("ID_OBJETO"),         
                    rs.getString("TIPO1"),       
                    rs.getString("TIPO2"),           
                    rs.getInt("VITALIDAD"),       
                    rs.getInt("ATAQUE"),           
                    rs.getInt("DEFENSA"),            
                    rs.getInt("AT_ESPECIAL"),        
                    rs.getInt("DEF_ESPECIAL"),      
                    rs.getInt("VELOCIDAD"),          
                    rs.getInt("NIVEL"),             
                    rs.getInt("FERTILIDAD"),         
                    rs.getInt("EQUIPO"),             
                    rs.getString("NOM_POKEMON"),     
                    rs.getString("ESTADO"),          
                    rs.getString("SEXO").charAt(0),
                    rs.getInt("VITALIDADMAX")
                ));
            };
                
                System.out.println("Se ha realizado el metodo obtenerEquipo a la perfeccion");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
    	return equipo;
    }
    
    public static ArrayList<Pokemon> obtenerCaja(int idEntrenador){
    	ArrayList<Pokemon> equipo = new ArrayList<>();
    	
    	try (Connection con = BDConecction.getConnection()) {
            String sql = "SELECT * \r\n"
            		+ "FROM POKEMON p \r\n"
            		+ "JOIN ENTRENADOR e ON p.ID_ENTRENADOR = e.ID_ENTRENADOR \r\n"
            		+ "WHERE e.ID_ENTRENADOR = ? AND p.EQUIPO = 3 ";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idEntrenador);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                equipo.add(new Pokemon(
                    rs.getInt("ID_POKEMON"),       
                    rs.getInt("ID_ENTRENADOR"),     
                    rs.getInt("NUM_POKEDEX"),       
                    rs.getInt("ID_OBJETO"),         
                    rs.getString("TIPO1"),       
                    rs.getString("TIPO2"),           
                    rs.getInt("VITALIDAD"),       
                    rs.getInt("ATAQUE"),           
                    rs.getInt("DEFENSA"),            
                    rs.getInt("AT_ESPECIAL"),        
                    rs.getInt("DEF_ESPECIAL"),      
                    rs.getInt("VELOCIDAD"),          
                    rs.getInt("NIVEL"),             
                    rs.getInt("FERTILIDAD"),         
                    rs.getInt("EQUIPO"),             
                    rs.getString("NOM_POKEMON"),     
                    rs.getString("ESTADO"),          
                    rs.getString("SEXO").charAt(0),
                    rs.getInt("VITALIDADMAX")
                ));
            };
                
                System.out.println("Se ha realizado el metodo obtenerEquipo a la perfeccion");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
    	return equipo;
    }

    public static boolean curarPokemon(int idEntrenador, int idPokemon) {
        try (Connection con = BDConecction.getConnection()) {
        	
            String sql = "UPDATE POKEMON SET VITALIDAD = VITALIDADMAX WHERE ID_ENTRENADOR = ? AND ID_POKEMON = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idEntrenador);
            stmt.setInt(2, idPokemon);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static String obtenerRutaImagen(Pokemon p) {
        try (Connection con = BDConecction.getConnection()) {
            String queryPokedex = "SELECT IMG_FRONTAL FROM POKEDEX WHERE NUM_POKEDEX = ?";
            PreparedStatement statementPokedex = con.prepareStatement(queryPokedex);
            statementPokedex.setInt(1, p.getNum_pokedex());
            ResultSet resultadoPokedex = statementPokedex.executeQuery();

            if (resultadoPokedex.next()) {
                // Construct the full path with the file protocol
                String ruta = "/imagenes/" + resultadoPokedex.getString("IMG_FRONTAL");
                return ruta;
            } else {
                throw new SQLException("No image found for NUM_POKEDEX: " + p.getNum_pokedex());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: no se ha encontrado bien la ruta del pokemon";
        }
    }
    
}