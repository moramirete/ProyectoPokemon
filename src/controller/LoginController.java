package controller;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

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
    	}
    }
    
    public void registrarUsuario(ActionEvent event) {
    	
    }
  
}
