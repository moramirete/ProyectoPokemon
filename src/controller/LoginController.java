package controller;

import java.io.IOException;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Entrenador;

public class LoginController {
	
	Entrenador entrenador = new Entrenador("admin", "123456", 1000);//Prueba de entrenador
	
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
	 private PasswordField txtPassword;

	 @FXML
	 private TextField txtUsuario;

    public void setStage(Stage primaryStage) {
    	stage = primaryStage;
	}
    
    @FXML
	public void cerrarAplicacion(ActionEvent event) {
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
	}
    
    @FXML
    public void comprobarLoguin(ActionEvent event) {
    	if(txtUsuario.getText().isEmpty()) {
    		JOptionPane.showMessageDialog(null, "Error: escribe el nombre de usuario"); 
    	}else if (txtPassword.getText().isEmpty()) {
    		JOptionPane.showMessageDialog(null, "Error: insertar contraseña del usuario"); 
    	}else {
    		String usuario = txtUsuario.getText();
    		String pass = txtPassword.getText();
    	
    		if(entrenador.getUsuario().equals(usuario)){
    			
    			if(entrenador.getPass().equals(pass)) {
    				
    				abrirPantallaMenu(entrenador);
    				
    			}else {
    				
    				JOptionPane.showMessageDialog(null, "Error: contraseña incorrecta"); 
    				
    			}
    			
    		}else{
    			
    			JOptionPane.showMessageDialog(null, "Error: nombre incorrecto"); 
    			
    		}
    	}
    }
    
    public void registrarUsuario(ActionEvent event) {
    	
    }
    
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
    		e.printStackTrace();
    	}
    	
    }
  
}
