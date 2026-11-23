package ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.Main;
import model.Cuenta;
import model.ItemPedido;
import model.Pedido;
import utils.Formato;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;


public class TarjetaPedidoController {
    private static final Formato moneda = valor -> String.format("$%,.0f", valor);

    @FXML private Label lbNombre;
    @FXML private Label lbDirrecion;
    @FXML private Label lbProductos;
    @FXML private Label lbTotal;
    @FXML private Label lbPago;
    @FXML private Label lbEstado;
    @FXML private ImageView icono;
    @FXML private Button btnAceptar;
    @FXML private Button btnRechazar;

    private Pedido pedido;
    private Consumer<Pedido> alCheck = p -> { };
    private Consumer<Pedido> alX = p -> { };

    // Llena la tarjeta con los datos del pedido. Los botones quedan ocultos hasta que se configuren
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;

        Cuenta dueno = Main.getCuentaService().buscarID(pedido.getUsuarioId());
        lbNombre.setText(dueno == null ? "Usuario desconocido" : dueno.getNombre());
        lbDirrecion.setText(pedido.getDirreccion());
        lbProductos.setText(orgPedidos(pedido.getProductos()));
        lbTotal.setText(moneda.formatear(pedido.getPrecioTotal()));
        lbPago.setText(pedido.getMetodoPago());
        lbEstado.setText(pedido.getEstado());

        if (!pedido.getProductos().isEmpty()) {
            icono.setImage(iconoDe(pedido.getProductos().get(0).getTipoProducto()));
        }

        mostrar(btnAceptar, false);
        mostrar(btnRechazar, false);
    }

    // El chulo: visible cuando se cumple la condición, y al pulsarlo se ejecuta la acción
    public void configurarCheck(Predicate<Pedido> visible, Consumer<Pedido> accion) {
        this.alCheck = accion;
        mostrar(btnAceptar, visible.test(pedido));
    }

    // La X: visible cuando se cumple la condición, y al pulsarla se ejecuta la acción
    public void configurarX(Predicate<Pedido> visible, Consumer<Pedido> accion) {
        this.alX = accion;
        mostrar(btnRechazar, visible.test(pedido));
    }

    private void mostrar(Button boton, boolean visible) {
        boton.setVisible(visible);
        boton.setManaged(visible);
    }

    public String orgPedidos(List<ItemPedido> pedidos) {
        List<String> partes = new ArrayList<>();
        for (ItemPedido p : pedidos) {
            partes.add(p.getCantidad() + "x " + p.getNombreProducto());
        }
        return String.join(" · ", partes);
    }

    private Image iconoDe(String producto) {
        if (producto.equals("Comida")) {
            return new Image(getClass().getResourceAsStream("/utils/imagenes/Comida.png"));
        }
        if (producto.equals("Bebida")) {
            return new Image(getClass().getResourceAsStream("/utils/imagenes/Bebida.png"));
        }
        return new Image(getClass().getResourceAsStream("/utils/imagenes/Acompanamiento.png"));
    }

    @FXML
    private void accionCheck() {
        alCheck.accept(pedido);
    }

    @FXML
    private void accionX() {
        alX.accept(pedido);
    }
}