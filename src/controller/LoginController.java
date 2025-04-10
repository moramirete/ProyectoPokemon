package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import bd.BDConecction;
import bd.EntrenadorBD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Entrenador;
	
	/**
	 * Controlador de la vista de inicio de sesión.
	 * 
	 * @param Gestiona la autenticación, registro de nuevos usuarios y navegación al menú principal.
	 */

public class LoginController {
	
	public Stage stage;
	
	@FXML
    private ImageView ImgLogo;

    @FXML
    private ImageView Imgfondo;

    @FXML
    private Button btnAceptar;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnRegistrar;

    @FXML
    private ImageView btnSalir;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsuario;
    public void setStage(Stage primaryStage) {
    	stage = primaryStage;
	}
    
    
    
   /**
    * 
    * metodo para cerrar la aplicacion a traves de un boton
    * 
    * 
    * @param event. Evento del mouse al hacer clic sobre el botón de salir.
    */
    
    
    @FXML
	public void cerrarAplicacion(MouseEvent event) {
		int opcion = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres cerrar el proyectoPokemon?");
		
		if(opcion == JOptionPane.YES_OPTION) {
			
			Stage stage = (Stage) btnSalir.getScene().getWindow();
			stage.close();
			
		}
	}
    
    /**
     * 
     * Metodo para verificar los datos los datos del usuario y si no estan guardados, se pide crear el usuario directamente.
     * 
     * 
     * @param event. Evento del mouse al hacer clic sobre el botón de btnAceptar.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    
    
    
    @FXML
	public void comprobarLoguin(ActionEvent event) {
		//Object evt = event.getSource();

		if (txtUsuario.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Error: escribe el nombre de usuario"); 
		} else if (txtPassword.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Error: insertar contraseña del usuario"); 
		} else {
			String usuario = txtUsuario.getText();
			String pass = txtPassword.getText();

			String sql = "SELECT CONTRASENA FROM ENTRENADOR WHERE USUARIO = ?";

			BDConecction con = new BDConecction();

			Connection conexion = con.getConnection();
			
			if (conexion == null) {
			    System.out.println("Error: conexión a la base de datos fallida.");
			    return;
			}

			try {
				PreparedStatement ps = conexion.prepareStatement(sql);
				ps.setString(1, usuario);
				
				ResultSet rs = ps.executeQuery();
				
				Entrenador entrenador = new Entrenador(usuario, pass);
				
				if(!rs.isBeforeFirst()) {
					
					int opcion = JOptionPane.showConfirmDialog(null, "Usuario no registrado, ¿desea registrarlo?");
					
					if(opcion == JOptionPane.YES_OPTION) {
						
						EntrenadorBD.crearEntrenador(conexion, entrenador);
						abrirPantallaMenu(entrenador);
						
					}else {
						txtPassword.setText("");
					}
				}else {
					while (rs.next()) {
						if (rs.getString(1).equals(pass)) {
							System.out.println("Usuario encontrado");
							EntrenadorBD.obtenerIDPokedolaresEntre(conexion, entrenador);
							abrirPantallaMenu(entrenador);
							
							
						}else {
							JOptionPane.showMessageDialog(null, "Error: Contraseña incorrecta"); 
						}
					}
				}

			} catch (SQLException e) {
				System.out.println("Error al conectar con la base de datos.");
				e.printStackTrace();
			}

		}
	}
    
  
    
    /**
     * 
     * Metodo para registrar el nombre y contraseña de usuario dentro de la base de datos.
     * 
     * 
     * @param event. Evento del mouse al hacer clic sobre el btnRegistrar.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    
    
    
    
    @FXML
    public void registrarUsuario(ActionEvent event) {
    	if (txtUsuario.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Error: escribe el nombre de usuario"); 
		} else if (txtPassword.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Error: insertar contraseña del usuario"); 
		} else {
			String usuario = txtUsuario.getText();
			String pass = txtPassword.getText();

			String sql = "SELECT CONTRASENA FROM ENTRENADOR WHERE USUARIO = ?";

			BDConecction con = new BDConecction();

			Connection conexion = con.getConnection();
			
			if (conexion == null) {
			    System.out.println("Error: conexión a la base de datos fallida.");
			    return;
			}

			try {
				PreparedStatement ps = conexion.prepareStatement(sql);
				ps.setString(1, usuario);
				
				ResultSet rs = ps.executeQuery();
				
				Entrenador entrenador = new Entrenador(usuario, pass);
				
				if(!rs.isBeforeFirst()) {
					
					int opcion = JOptionPane.showConfirmDialog(null, "¿Seguro que quiere registrar este usuario?");
					
					if(opcion == JOptionPane.YES_OPTION) {
						
						EntrenadorBD.crearEntrenador(conexion, entrenador);
						abrirPantallaMenu(entrenador);
						
					}else {
						txtPassword.setText("");
					}
				}else {
					while (rs.next()) {
						if (rs.getString(1).equals(pass)) {
							System.out.println("Usuario ya existente en la BD");
							JOptionPane.showMessageDialog(null, "Error: este usuario ya esta creado");
							txtPassword.setText("");
						}
					}
				}

			} catch (SQLException e) {
				System.out.println("Error al conectar con la base de datos.");
				e.printStackTrace();
			}

		}
    }
    
    
    /**
     * 
     * Metodo para abrir la pantalla del menu despues de iniciar sesion con el usuario.
     * 
     * 
     * 
     * @param event. Evento del mouse al hacer clic sobre el botón de iniciar sesion.
     * 
     * @throws IOException. Si ocurre un error al cargar el archivo FXML.
     */
    
    
    
    //Metodo para abrir el menu
    private void abrirPantallaMenu(Entrenador entrenador) {
    	
    	try {
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/menu.fxml"));
	        Parent root = loader.load();
	        
	        MenuController menuController = loader.getController();
	        
	        Scene scene = new Scene(root);
    		Stage stage = new Stage();
    		
	        stage.setTitle("Pokémon Super Nenes - Menu");
	        stage.setScene(scene);
	        menuController.init(entrenador, stage, this);
	        
	        stage.show();
	        
	        this.stage.close();
    		
    	}catch(IOException e) {
    		System.out.println("Fallo en el archivo FXML.");
    		e.printStackTrace();
    	}
    	
    }
    
    /**
     * 
     * Metodo para mostrar la ventana del login vaciando los bloques de texto en Usuario y Contraseña.
     * 
     * 
     */
    
    //Vuelve a mostrar la ventana de inicio de sesión y limpia los campos de usuario y contraseña.
	public void show() {
		stage.show();
		txtUsuario.setText("");
		txtPassword.setText("");
		
	}
  
}
