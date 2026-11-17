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
import javafx.scene.text.Text;
import persistence.CuentaRepository;
import service.CuentaService;
import service.Sesion;
import utils.Alertas;

public class UserViewController {
    @FXML private Text txtBienvenida;


    @FXML
    public void initialize() {
        txtBienvenida.setText("Bienvenido estimado Usuario " + Sesion.getInstancia().getCuentaActual().getNombre());
    }
    }