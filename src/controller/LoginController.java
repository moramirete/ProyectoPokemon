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
    private TextField LabelUsuario;

    @FXML
    private Label TxtContraseña;

    @FXML
    private Label TxtUsuario;

    @FXML
    private Button btnAceptar;

    @FXML
    private Button btnRegistrar;

    @FXML
    private Button btncancelar;

    @FXML
    private Label labelError;

    @FXML
    private PasswordField labelPass;

    public void setStage(Stage primaryStage) {
    	stage = primaryStage;
	}
    
    void comprobarLoguin(ActionEvent event) {
    	if(TxtUsuario.getText().isEmpty()) {
    		labelError.setText("Error: Insertar nombre de usuario");
    		labelError.setVisible(true);
    		JOptionPane.showMessageDialog(null, "Error: escribe el nombre de usuario"); //Panel de error
    	}else if (TxtContraseña.getText().isEmpty()) {
    		labelError.setText("Error: Insertar contraseña de usuario");
    		labelError.setVisible(true);
    	}else {
    		String usuario = TxtUsuario.getText();
    		String pass = TxtContraseña.getText();
    	}
    }
  
}
