package ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import service.Sesion;

public class DeliverViewController {
    @FXML private Text txtBienvenida;


    @FXML
    public void initialize() {
        txtBienvenida.setText("Bienvenido estimado Repartidor " + Sesion.getInstancia().getCuentaActual().getNombre());
    }
    }