package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Entrenador;
import model.Pokemon;

public class PokemonBD {

	public static void crearPokemon(Connection con, Pokemon pokemon, Entrenador entrenador) throws SQLException {
		
		pokemon.setId_pokemon(obtenerIdPokemon(con));
		
		String sql = "INSERT INTO pokemon (ID_POKEMON, ID_ENTRENADOR, NUM_POKEDEX, ID_OBJETO, NOM_POKEMON, VITALIDAD, ATAQUE, DEFENSA, "
				+ "AT_ESPECIAL, DEF_ESPECIAL, VELOCIDAD, NIVEL, FERTILIDAD, SEXO, ESTADO, EQUIPO) \r\n"
					+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, pokemon.getId_pokemon());
		st.setInt(2, entrenador.getIdEntrenador());
		st.setInt(3, pokemon.getNum_pokedex());
		st.setInt(4, pokemon.getId_objeto());
		st.setString(5, pokemon.getNombre_pokemon());
		st.setInt(6, pokemon.getVitalidad());
		st.setInt(7, pokemon.getAtaque());
		st.setInt(8, pokemon.getDefensa());
		st.setInt(9, pokemon.getAtaque_especial());
		st.setInt(10, pokemon.getDefensa_especial());
		st.setInt(11, pokemon.getVelocidad());
		st.setInt(12, pokemon.getNivel());
		st.setInt(13, pokemon.getFertilidad());
		st.setInt(14, pokemon.getSexo());
		st.setString(15, pokemon.getEstado());
		st.setInt(16, pokemon.getEquipo());
		st.executeUpdate();
		
	}
	
	private static int obtenerIdPokemon(Connection con) throws SQLException{
		int idPokemon = 0;
		String sql = "SELECT MAX(ID_POKEMON)+1\r\n" + "FROM POKEMON";

		Statement st;

		st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);

		while (rs.next()) {
			idPokemon = rs.getInt(1);
		}

		return idPokemon;
	}
	
}
