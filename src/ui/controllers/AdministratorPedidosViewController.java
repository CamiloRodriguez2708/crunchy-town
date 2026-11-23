package ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import main.Main;
import model.Administrador;
import model.Pedido;
import service.Sesion;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

public class AdministratorPedidosViewController {

    @FXML private FlowPane gestion;

    @FXML
    public void initialize() {
        mostrarTodos();
    }

    // Una tarjeta por pedido. El administrador solo puede decidir sobre los pedidos pendientes
    @FXML
    public void mostrarTodos() {
        gestion.getChildren().clear();
        List<Pedido> pedidos = Main.getPedidoService().getPedidos();

        if (pedidos.isEmpty()) {
            Label vacio = new Label("Aún no hay pedidos.");
            vacio.setStyle("-fx-font-size: 18px; -fx-text-fill: #715e57; -fx-padding: 20px;");
            gestion.getChildren().add(vacio);
            return;
        }

        Predicate<Pedido> estaPendiente = p -> p.getEstado().equals("PENDIENTE");

        for (Pedido p : pedidos) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/TarjetaPedido.fxml"));
                Parent tarjeta = loader.load();

                TarjetaPedidoController controller = loader.getController();
                controller.setPedido(p);
                controller.configurarCheck(estaPendiente, pedido -> aceptar(pedido));
                controller.configurarX(estaPendiente, pedido -> denegar(pedido));

                gestion.getChildren().add(tarjeta);
                FlowPane.setMargin(tarjeta, new Insets(10));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void aceptar(Pedido pedido) {
        administrador().aceptarPedido(pedido);
        Main.getPedidoService().guardar();
        mostrarTodos();
    }

    private void denegar(Pedido pedido) {
        administrador().denegarPedido(pedido);
        Main.getPedidoService().guardar();
        mostrarTodos();
    }

    private Administrador administrador() {
        return (Administrador) Sesion.getInstancia().getCuentaActual();
    }

    @FXML
    private void irAInicioSesion() {
        Sesion.getInstancia().cerrar();
        Main.cambiarPantalla("/ui/fxml/Login.fxml");
    }

    @FXML
    private void irAMenu() {
        Main.cambiarPantalla("/ui/fxml/AdministratorMenuView.fxml");
    }
}