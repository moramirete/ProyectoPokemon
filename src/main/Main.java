package main;

import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

	 @Override
	    public void start(Stage primaryStage) throws Exception{
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/login.fxml"));
	        Parent root = loader.load();
	      
	        Scene scene = new Scene(root);
	        
	        primaryStage.setTitle("Pokémon Super Nenes - Login");
	        primaryStage.setScene(scene);
	        primaryStage.getIcons().add(new Image("/imagenes/lossupernenes.png"));
	        
	        LoginController controller = loader.getController();
	        controller.setStage(primaryStage);
	        
	        primaryStage.show();
	    }


	    public static void main(String[] args) {
	        launch(args);
	    }
	    
	    
}