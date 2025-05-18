package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase de utilidad para gestionar la conexión con la base de datos MySQL del juego Pokémon.
 * Proporciona un método estático para obtener una conexión a la base de datos.
 * 
 * @author TuNombre
 */
public class BDConecction {
    /** Conexión activa a la base de datos. */
    public static Connection con;
    
    /** URL de conexión a la base de datos MySQL. */
    private static String url = "jdbc:mysql://localhost:3306/pokemon";
    /** Usuario de la base de datos. */
    private static String login = "root";
    /** Contraseña de la base de datos. */
    private static String password = "";
    
    /**
     * Obtiene una conexión a la base de datos MySQL.
     * Si la conexión es exitosa, la almacena en el atributo estático {@code con}.
     * 
     * @return La conexión establecida a la base de datos, o {@code null} si ocurre un error.
     */
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