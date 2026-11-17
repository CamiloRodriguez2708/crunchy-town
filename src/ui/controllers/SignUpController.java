package ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.Main;
import model.Cuenta;
import model.Repartidor;
import model.Usuario;
import model.Verificable;
import persistence.CuentaRepository;
import service.CuentaService;
import utils.Alertas;

public class SignUpController {
    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtContrasena;
    @FXML private ComboBox<String> cbTipo;

    @FXML
    public void initialize() {
        cbTipo.getItems().addAll("Usuario", "Repartidor");
        cbTipo.setValue("Usuario");
    }

    @FXML
    private void crearCuenta() {
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String contrasena = txtContrasena.getText();

        Verificable<String> textoValido = t -> t != null && !t.isBlank();
        Verificable<String> correoValido = c -> c != null && c.contains("@");

        if (!textoValido.verificar(nombre) || !correoValido.verificar(correo) || !textoValido.verificar(contrasena)) {
            Alertas.error("Completa todos los campos y usa un correo válido");
            return;
        }

        CuentaService servicio = Main.getCuentaService();
        String id = servicio.generarId();

        Cuenta nueva;
        if (cbTipo.getValue().equals("Repartidor")) {
            nueva = new Repartidor(id, nombre, correo, contrasena);
        } else {
            nueva = new Usuario(id, nombre, correo, contrasena);
        }

        if (!servicio.agregar(nueva)) {
            Alertas.error("Ese correo ya está registrado");
            return;
        }

        new CuentaRepository().guardar(servicio.getCuentas());
        Alertas.info("Cuenta creada. Ya puedes iniciar sesión");
        Main.cambiarPantalla("/ui/fxml/Login.fxml");
    }

    @FXML
    private void irAInicioSesion() {
        Main.cambiarPantalla("/ui/fxml/Login.fxml");
    }
}