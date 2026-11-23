package ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import main.Main;
import model.Pedido;
import model.Usuario;
import service.Sesion;

import java.util.List;
import java.util.function.Predicate;

public class UserPedidosViewController {

    @FXML private FlowPane gestion;

    @FXML
    public void initialize() {
        mostrarTodos();
    }

    // Una tarjeta por pedido. El usuario solo ve los suyos y solo puede cancelar los pendientes
    @FXML
    public void mostrarTodos() {
        gestion.getChildren().clear();
        Usuario usuario = usuarioActual();

        List<Pedido> pedidos = Main.getPedidoService().filtrar(
                p -> usuario.getId().equals(p.getUsuarioId()));

        if (pedidos.isEmpty()) {
            Label vacio = new Label("Aún no tienes pedidos.");
            vacio.setStyle("-fx-font-size: 18px; -fx-text-fill: #715e57; -fx-padding: 20px;");
            gestion.getChildren().add(vacio);
            return;
        }

        Predicate<Pedido> sePuedeCancelar = p -> "PENDIENTE".equals(p.getEstado());

        for (Pedido p : pedidos) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/TarjetaPedido.fxml"));
                Parent tarjeta = loader.load();

                TarjetaPedidoController controller = loader.getController();
                controller.setPedido(p);
                controller.configurarX(sePuedeCancelar, pedido -> cancelar(pedido));

                gestion.getChildren().add(tarjeta);
                FlowPane.setMargin(tarjeta, new Insets(10));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void cancelar(Pedido pedido) {
        Usuario usuario = usuarioActual();
        if (usuario == null) {
            return;
        }
        if (!"PENDIENTE".equals(pedido.getEstado())) {
            return;
        }
        usuario.cancelarPedido(pedido);
        Main.getPedidoService().guardar();
        mostrarTodos();
    }

    private Usuario usuarioActual() {
        return Sesion.getInstancia().getCuentaActual() instanceof Usuario usuario ? usuario : null;
    }

    @FXML
    private void volverAlMenu() {
        Main.cambiarPantalla("/ui/fxml/UserView.fxml");
    }

    @FXML
    private void irAInicioSesion() {
        Sesion.getInstancia().cerrar();
        Main.cambiarPantalla("/ui/fxml/Login.fxml");
    }
}
