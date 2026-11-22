package ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.Main;
import model.Cuenta;
import model.Verificable;
import service.Sesion;
import utils.Alertas;

public class LoginController {
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtContrasena;

    @FXML
    private void iniciarSesion() {
        String correo = txtCorreo.getText().trim();
        String contrasena = txtContrasena.getText();

        Verificable<String> correoValido = elemento -> elemento.isEmpty() && !elemento.contains("@");
        Verificable<String> contrasenaValida = elemento -> elemento.isEmpty();

        if (correoValido.verificar(correo) || contrasenaValida.verificar(contrasena)) {
            Alertas.error("Completa el correo y la contraseña");
            return;
        }

        if (Main.getCuentaService().login(correo, contrasena)) {
            Cuenta cuenta = Sesion.getInstancia().getCuentaActual();
            if (cuenta.getRol().equals("Usuario")){
                Main.cambiarPantalla("/ui/fxml/UserView.fxml");
            }
            else if (cuenta.getRol().equals("Administrador")){
                Main.cambiarPantalla("/ui/fxml/AdministratorMenuView.fxml");
            }
            else {
                Main.cambiarPantalla("/ui/fxml/DeliverView.fxml");
            }
        } else {
            Alertas.error("Correo o contraseña incorrectos");
        }
    }

    @FXML
    private void irARegistro() {
        Main.cambiarPantalla("/ui/fxml/SignUp.fxml");
    }
}