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
    private Label TxtContraseña;

    @FXML
    private Label TxtUsuario;

    @FXML
    private TextField LabelUsuario;

    @FXML
    private Button btnAceptar;

    @FXML
    private Button btnRegistrar;

    @FXML
    private Button btnCancelar;

    @FXML
    private Label labelError;

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
    		labelError.setText("Error: Insertar nombre de usuario");
    		labelError.setVisible(true);
    		JOptionPane.showMessageDialog(null, "Error: escribe el nombre de usuario"); //Panel de error
    	}else if (txtPassword.getText().isEmpty()) {
    		labelError.setText("Error: Insertar contraseña de usuario");
    		labelError.setVisible(true);
    	}else {
    		String usuario = txtUsuario.getText();
    		String pass = txtPassword.getText();
    	}
    }
  
}
