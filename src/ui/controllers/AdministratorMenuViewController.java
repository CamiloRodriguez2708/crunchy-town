package ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import main.Main;
import model.Producto;
import service.Sesion;

import java.util.List;

public class AdministratorMenuViewController {

    @FXML
    FlowPane gestion;
    @FXML
    TextField txtBuscar;
    @FXML
    ComboBox cbFiltroTipo;

    @FXML
    public void initialize() {
        cbFiltroTipo.getItems().addAll("Todo", "Comida", "Bebida", "Acompañamiento");
        cbFiltroTipo.setValue("Todo");
        mostrarTodos();
    }

    @FXML
    public void mostrarTodos() {
        gestion.getChildren().clear();
        List<Producto> temp = Main.getMenuService().getMenu();

        for (Producto p : temp){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/TarjetaAdmin.fxml"));
                Parent tarjeta = loader.load();

                TarjetaAdminController controller = loader.getController();
                controller.setProducto(p);

                gestion.getChildren().add(tarjeta);
                FlowPane.setMargin(tarjeta, new javafx.geometry.Insets(10));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void filtrarNombres() {
        gestion.getChildren().clear();
        List<Producto> temp = Main.getMenuService().getMenu();

        for (Producto p : temp){
            if (p.getNombre().contains(txtBuscar.getText())) {
                if (cbFiltroTipo.getSelectionModel().getSelectedItem().toString().equals(p.getTipo()) || cbFiltroTipo.getSelectionModel().getSelectedItem().toString().equals("Todo") ){
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/TarjetaAdmin.fxml"));
                    Parent tarjeta = loader.load();

                    TarjetaAdminController controller = loader.getController();
                    controller.setProducto(p);

                    gestion.getChildren().add(tarjeta);
                    FlowPane.setMargin(tarjeta, new javafx.geometry.Insets(10));

                } catch (Exception e) {
                    e.printStackTrace();
                }
                }
            }
        }
    }

    public void filtrarTipos() {
        gestion.getChildren().clear();
        List<Producto> temp = Main.getMenuService().getMenu();

        if (txtBuscar.equals("")) {
            for (Producto p : temp) {
                if (cbFiltroTipo.getSelectionModel().getSelectedItem().toString().equals(p.getTipo()) || cbFiltroTipo.getSelectionModel().getSelectedItem().toString().equals("Todo")) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/TarjetaAdmin.fxml"));
                        Parent tarjeta = loader.load();

                        TarjetaAdminController controller = loader.getController();
                        controller.setProducto(p);

                        gestion.getChildren().add(tarjeta);
                        FlowPane.setMargin(tarjeta, new javafx.geometry.Insets(10));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        else {
            filtrarNombres();
        }
    }
    @FXML
    private void irAInicioSesion() {
        Sesion.getInstancia().cerrar();
        Main.cambiarPantalla("/ui/fxml/Login.fxml");
    }
    @FXML
    private void irACrear() {
        Main.cambiarPantalla("/ui/fxml/AdministratorView.fxml");
    }
}
