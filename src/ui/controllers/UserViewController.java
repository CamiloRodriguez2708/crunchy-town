package ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import main.Main;
import model.Producto;
import service.Sesion;

import java.util.List;

public class UserViewController {
    @FXML private Text txtProductos;

    @FXML
    public void initialize() {
        mostrarMenu();
    }

    private void mostrarMenu() {
        List<Producto> menu = Main.getMenuService().getMenu();
        if (menu.isEmpty()) {
            txtProductos.setText("Aún no hay productos en el menú");
            return;
        }

        String texto = "";
        for (Producto p : menu) {
            texto += "[" + p.getTipo() + "] " + p + "\n";
        }
        txtProductos.setText(texto);
    }

    @FXML
    private void irAInicioSesion() {
        Sesion.getInstancia().cerrar();
        Main.cambiarPantalla("/ui/fxml/Login.fxml");
    }
}