package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import bd.BDConecction;
import bd.EntrenadorBD;
import bd.MochilaBD;
import bd.MovimientoBD;
import bd.PokemonBD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Entrenador;
import model.Pokemon;

/**
 * Controlador de la vista de inicio de sesión.
 * Gestiona la autenticación, registro de nuevos usuarios y navegación al menú principal.
 * Incluye mensajes y lógica adicional para una experiencia más completa.
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
     * Método para cerrar la aplicación a través de un botón.
     */
    @FXML
    public void cerrarAplicacion(MouseEvent event) {
        int opcion = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres cerrar el proyectoPokemon?");

        if (opcion == JOptionPane.YES_OPTION) {
            Stage stage = (Stage) btnSalir.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Registra un usuario sin confirmación adicional.
     */
    private void registrarUsuarioSinConfirmar(Connection conexion, Entrenador entrenador) {
        try {
            EntrenadorBD.crearEntrenador(conexion, entrenador);

            entrenador.setPokPrincipal(PokemonBD.generarPokemonPrincipal(entrenador.getIdEntrenador(), conexion));
            entrenador.getEquipo().add(entrenador.getPokPrincipal());

            Pokemon pokemon = new Pokemon(entrenador.getPokPrincipal());
            pokemon.getMovPrincipales().add(MovimientoBD.otorgarPrimerMovimiento(conexion, entrenador, pokemon));

            entrenador.setMochila(MochilaBD.crearMochilaInicial(entrenador.getIdEntrenador()));

            abrirPantallaMenu(entrenador);
            System.out.println("Usuario registrado automáticamente y accediendo al menú");
        } catch (SQLException e) {
            System.out.println("Error al registrar usuario automáticamente.");
            e.printStackTrace();
        }
    }

    /**
     * Verifica los datos del usuario y si no existe, ofrece registrarlo.
     */
    @FXML
    public void comprobarLoguin(ActionEvent event) {
        if (txtUsuario.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error: escribe el nombre de usuario");
            System.out.println("Se ha detectado que no se ha puesto el nombre de usuario");
        } else if (txtPassword.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error: insertar contraseña del usuario");
            System.out.println("Se ha detectado que no se ha puesto la contraseña de usuario");
        } else {
            String usuario = txtUsuario.getText();
            String pass = txtPassword.getText();

            String sql = "SELECT CONTRASENA FROM ENTRENADOR WHERE USUARIO = ?";

            BDConecction con = new BDConecction();
            Connection conexion = con.getConnection();

            if (conexion == null) {
                JOptionPane.showMessageDialog(null, "Conexion con la base de datos fallida", "Error", 0);
                return;
            }

            try {
                PreparedStatement ps = conexion.prepareStatement(sql);
                ps.setString(1, usuario);

                ResultSet rs = ps.executeQuery();

                Entrenador entrenador = new Entrenador(usuario, pass);

                if (!rs.isBeforeFirst()) {
                    int opcion = JOptionPane.showConfirmDialog(null, "Usuario no registrado, ¿desea registrarlo?");

                    if (opcion == JOptionPane.YES_OPTION) {
                        registrarUsuarioSinConfirmar(conexion, entrenador);
                    } else {
                        System.out.println("Se ha elegido la opcion de no");
                        txtPassword.setText("");
                    }
                } else {
                    while (rs.next()) {
                        if (rs.getString(1).equals(pass)) {
                            System.out.println("Usuario encontrado");
                            EntrenadorBD.obtenerIDPokedolaresEntre(conexion, entrenador);
                            abrirPantallaMenu(entrenador);
                            System.out.println("Abriendo menu");
                        } else {
                           
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
     * Método para registrar el nombre y contraseña de usuario dentro de la base de datos.
     */
    @FXML
    public void registrarUsuario(ActionEvent event) {
        if (txtUsuario.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Inserte el nombre de usuario", "Error", 0);
            System.out.println("Se ha detectado que no se ha puesto el nombre de usuario");
        } else if (txtPassword.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Inserte la contraseña para el usuario", "Error", 0);
            System.out.println("Se ha detectado que no se ha puesto la contraseña del usuario");
        } else {
            String usuario = txtUsuario.getText();
            String pass = txtPassword.getText();

            String sql = "SELECT CONTRASENA FROM ENTRENADOR WHERE USUARIO = ?";

            BDConecction con = new BDConecction();
            Connection conexion = con.getConnection();

            if (conexion == null) {
                JOptionPane.showMessageDialog(null, "Conexion con la base de datos fallida", "Error", 0);
                return;
            }

            try {
                PreparedStatement ps = conexion.prepareStatement(sql);
                ps.setString(1, usuario);

                ResultSet rs = ps.executeQuery();

                Entrenador entrenador = new Entrenador(usuario, pass);

                if (!rs.isBeforeFirst()) {
                    int opcion = JOptionPane.showConfirmDialog(null, "¿Seguro que quiere registrar este usuario?");

                    if (opcion == JOptionPane.YES_OPTION) {
                        EntrenadorBD.crearEntrenador(conexion, entrenador);

                        entrenador.setPokPrincipal(PokemonBD.generarPokemonPrincipal(entrenador.getIdEntrenador(), conexion));
                        entrenador.getEquipo().add(entrenador.getPokPrincipal());

                        Pokemon pokemon = new Pokemon(entrenador.getPokPrincipal());
                        pokemon.getMovPrincipales().add(MovimientoBD.otorgarPrimerMovimiento(conexion, entrenador, pokemon));

                        entrenador.setMochila(MochilaBD.crearMochilaInicial(entrenador.getIdEntrenador()));

                        abrirPantallaMenu(entrenador);
                        System.out.println("Entrando a menu");

                    } else {
                        System.out.println("Se ha elegido la opcion de no");
                        txtPassword.setText("");
                        System.out.println("Cancelada la opcion de registrar");
                    }
                } else {
                    System.out.println("Usuario ya existente en la BD");
                    JOptionPane.showMessageDialog(null, "El usuario ya existe en la base de datos, es necesario el cambio de nombre", "Error", 0);
                    txtPassword.setText("");
                    System.out.println("Cancelada la opcion de registrar");
                }

            } catch (SQLException e) {
                System.out.println("Error al conectar con la base de datos.");
                e.printStackTrace();
            }
        }
    }

    /**
     * Método para abrir la pantalla del menú después de iniciar sesión con el usuario.
     */
    private void abrirPantallaMenu(Entrenador entrenador) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/menu.fxml"));
            Parent root = loader.load();

            MenuController menuController = loader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setTitle("Pokémon Super Nenes - Menu");
            stage.getIcons().add(new Image("/imagenes/lossupernenes.png"));
            stage.setScene(scene);
            menuController.init(entrenador, stage, this);

            stage.show();

            this.stage.close();

        } catch (IOException e) {
            System.out.println("Fallo en el archivo FXML.");
            e.printStackTrace();
        }
    }

    /**
     * Muestra la ventana del login vaciando los bloques de texto en Usuario y Contraseña.
     */
    public void show() {
        stage.show();
        txtUsuario.setText("");
        txtPassword.setText("");
    }


}