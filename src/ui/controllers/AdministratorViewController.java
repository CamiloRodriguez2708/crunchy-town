package ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import service.Sesion;

public class AdministratorViewController {
    @FXML private Text txtBienvenida;


    @FXML
    public void initialize() {
        txtBienvenida.setText("Bienvenido estimado Administrador " + Sesion.getInstancia().getCuentaActual().getNombre());
    }
    }