package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BDConecction {
	public static Connection con;
	//LinkedList<Pokemon> p = PokemonBD.selectPokemon();
	
	private static String url = "jdbc:mysql://localhost:3306/pokemon";
	private static String login = "root";
	private static String password = "";
	
	public static Connection getConnection(){
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, login, password);
			System.out.println("Conexión establecida");
		} catch (ClassNotFoundException e) {
	        System.out.println("Driver MySQL no encontrado.");
	        e.printStackTrace();
	    } catch (SQLException e) {
	        System.out.println("Error al conectar con la base de datos.");
	        e.printStackTrace();
	    }
		return con;
	}
}
