package ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import main.Main;
import model.Pedido;
import model.Repartidor;
import service.Sesion;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

public class DeliverViewController {

    @FXML private FlowPane gestion;

    @FXML
    public void initialize() {
        mostrarTodos();
    }

    // Una tarjeta por pedido. El repartidor ve los pedidos aceptados que nadie ha tomado y los suyos
    @FXML
    public void mostrarTodos() {
        gestion.getChildren().clear();
        String miId = repartidor().getId();

        Predicate<Pedido> visibleParaMi = p ->
                (p.getEstado().equals("ACEPTADO") && p.getIdRepartidor() == null)
                        || miId.equals(p.getIdRepartidor());
        List<Pedido> pedidos = Main.getPedidoService().filtrar(visibleParaMi);

        if (pedidos.isEmpty()) {
            Label vacio = new Label("No tienes pedidos por entregar.");
            vacio.setStyle("-fx-font-size: 18px; -fx-text-fill: #715e57; -fx-padding: 20px;");
            gestion.getChildren().add(vacio);
            return;
        }

        // El chulo aparece mientras el pedido esté aceptado o en camino, y la X nunca
        Predicate<Pedido> sePuedeAvanzar = p ->
                p.getEstado().equals("ACEPTADO") || p.getEstado().equals("EN REPARTO");

        for (Pedido p : pedidos) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/TarjetaPedido.fxml"));
                Parent tarjeta = loader.load();

                TarjetaPedidoController controller = loader.getController();
                controller.setPedido(p);
                controller.configurarCheck(sePuedeAvanzar, pedido -> avanzar(pedido));
                controller.configurarX(pedido -> false, pedido -> { });

                gestion.getChildren().add(tarjeta);
                FlowPane.setMargin(tarjeta, new Insets(10));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Primer chulo: lo lleva (EN CAMINO). Segundo chulo: lo entrega (ENTREGADO)
    private void avanzar(Pedido pedido) {
        Repartidor repartidor = repartidor();

        // Un pedido que ya tomó otro repartidor no se puede tocar
        String tomadoPor = pedido.getIdRepartidor();
        if (tomadoPor != null && !tomadoPor.equals(repartidor.getId())) {
            mostrarTodos();
            return;
        }

        if (pedido.getEstado().equals("ACEPTADO")) {
            repartidor.llevarPedido(pedido);
        } else {
            repartidor.completarPedido(pedido);
        }
        Main.getPedidoService().guardar();
        mostrarTodos();
    }

    private Repartidor repartidor() {
        return (Repartidor) Sesion.getInstancia().getCuentaActual();
    }

    @FXML
    private void irAInicioSesion() {
        Sesion.getInstancia().cerrar();
        Main.cambiarPantalla("/ui/fxml/Login.fxml");
    }
}