package ui.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import main.Main;
import model.Cuenta;
import model.ItemCarrito;
import model.Pedido;
import model.Producto;
import model.Usuario;
import service.CarritoService;
import service.Sesion;
import strategy.MetodoPagoFactory;
import strategy.MetodoPagoStrategy;
import utils.Alertas;

import utils.Formato;


import java.util.Optional;

public class CartViewController {
    private static final Formato moneda = valor -> String.format("$%,.0f", valor);

    @FXML private VBox contenedorItems;
    @FXML private Text txtCantidad;
    @FXML private Text txtSubtotal;
    @FXML private Text txtTotal;
    @FXML private Button btnPagar;
    
    @FXML
    public void initialize() {
        actualizarVista();
    }

    private void actualizarVista() {
        contenedorItems.getChildren().clear();
        Usuario usuario = usuarioActual();
        if (usuario == null) {
            Label vacio = new Label("Inicia sesión para usar el carrito.");
            vacio.setStyle("-fx-font-size: 17px; -fx-text-fill: #715e57; -fx-padding: 20px;");
            contenedorItems.getChildren().add(vacio);
            txtCantidad.setText("0");
            txtSubtotal.setText(moneda.formatear(0));
            txtTotal.setText(moneda.formatear(0));
            btnPagar.setDisable(true);
            return;
        }

        CarritoService carrito = usuario.getCarrito();
        if (carrito.estaVacio()) {
            Label vacio = new Label("Tu carrito está vacío. Vuelve al menú para elegir productos.");
            vacio.setWrapText(true);
            vacio.setStyle("-fx-font-size: 17px; -fx-text-fill: #715e57; -fx-padding: 20px;");
            contenedorItems.getChildren().add(vacio);
        } else {
            for (ItemCarrito item : carrito.getItems()) {
                contenedorItems.getChildren().add(crearFila(item));
            }
        }

        txtCantidad.setText(String.valueOf(carrito.getCantidadTotal()));
        txtSubtotal.setText(moneda.formatear(carrito.getTotal()));
        txtTotal.setText(moneda.formatear(carrito.getTotal()));
        btnPagar.setDisable(carrito.estaVacio());
    }

    private HBox crearFila(ItemCarrito item) {
        Producto producto = item.getProducto();
        HBox fila = new HBox(12);
        fila.setAlignment(Pos.CENTER_LEFT);
        fila.setPadding(new Insets(14));
        fila.setMaxWidth(Double.MAX_VALUE);
        fila.setStyle("-fx-background-color: white; -fx-background-radius: 18;");

        ImageView imageView = new ImageView(iconoDe(producto.getTipo()));
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(62);
        imageView.setFitWidth(62);

        VBox datos = new VBox(5);
        Label nombre = new Label(producto.getNombre());
        nombre.setWrapText(true);
        nombre.setStyle("-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: #30211e;");
        Label precio = new Label(moneda.formatear(producto.getPrecio()) + " c/u · "
                + moneda.formatear(item.getSubtotal()));
        precio.setStyle("-fx-font-size: 14px; -fx-text-fill: #d62828; -fx-font-weight: bold;");
        datos.getChildren().addAll(nombre, precio);
        HBox.setHgrow(datos, Priority.ALWAYS);

        Button menos = new Button("−");
        menos.setOnAction(event -> cambiarCantidad(producto, false));
        Button cantidad = new Button(String.valueOf(item.getCantidad()));
        cantidad.setDisable(true);
        Button mas = new Button("+");
        mas.setOnAction(event -> cambiarCantidad(producto, true));
        Button quitar = new Button("Quitar");
        quitar.setOnAction(event -> quitarProducto(producto));
        for (Button boton : new Button[]{menos, cantidad, mas}) {
            boton.setStyle("-fx-background-color: #fff0dc; -fx-background-radius: 12; -fx-font-size: 14px;");
        }
        quitar.setStyle("-fx-background-color: transparent; -fx-text-fill: #d62828; -fx-font-size: 13px;");
        fila.getChildren().addAll(imageView, datos, menos, cantidad, mas, quitar);
        return fila;
    }

    private void cambiarCantidad(Producto producto, boolean aumentar) {
        Usuario usuario = usuarioActual();
        if (usuario == null) {
            return;
        }
        if (aumentar) {
            usuario.agregarAlCarrito(producto);
        } else {
            usuario.disminuirDelCarrito(producto);
        }
        actualizarVista();
    }

    private void quitarProducto(Producto producto) {
        Usuario usuario = usuarioActual();
        if (usuario != null) {
            usuario.quitarDelCarrito(producto);
            actualizarVista();
        }
    }

    @FXML
    private void pagar() {
        Usuario usuario = usuarioActual();
        if (usuario == null || usuario.getCarrito().estaVacio()) {
            Alertas.error("Agrega productos al carrito antes de pagar.");
            return;
        }

        Optional<String> seleccionado = Alertas.elegirMetodoPago(
                MetodoPagoFactory.getMetodosDisponibles());
        if (!seleccionado.isPresent()) {
            return;
        }

        String direccionEntrega = solicitarDireccionEntrega();
        if (direccionEntrega == null) {
            return;
        }

        try {
            MetodoPagoStrategy estrategia = MetodoPagoFactory.crear(seleccionado.get());
            Pedido pedido = Main.getPedidoService().crearPedido(
                    usuario, usuario.getCarrito(), estrategia, direccionEntrega);
            Alertas.factura("Pedido guardado correctamente.\n"
                    + "Número: " + pedido.getId() + "\n"
                    + "Estado: " + pedido.getEstado() + "\n"
                    + "Método de pago: " + pedido.getMetodoPago() + "\n"
                    + "Dirección de entrega: " + pedido.getDirreccion() + "\n"
                    + "Total: " + moneda.formatear(pedido.getPrecioTotal()));
            usuario.getCarrito().limpiar();
            Main.cambiarPantalla("/ui/fxml/UserView.fxml");
        } catch (RuntimeException e) {
            Alertas.error(e.getMessage() == null ? "No se pudo crear el pedido." : e.getMessage());
        }
    }

    private Image iconoDe(String tipo) {
        String archivo = switch (tipo) {
            case "Comida" -> "Comida.png";
            case "Bebida" -> "Bebida.png";
            default -> "Acompanamiento.png";
        };
        return new Image(getClass().getResourceAsStream("/utils/imagenes/" + archivo));
    }


    private String solicitarDireccionEntrega() {
        while (true) {
            Optional<String> resultado = Alertas.solicitarDireccion();
            if (!resultado.isPresent()) {
                return null;
            }

            String direccion = resultado.get().trim();
            if (!direccion.isEmpty()) {
                return direccion;
            }
            Alertas.error("Ingresa una dirección para poder entregar el pedido.");
        }
    }

    @FXML
    private void volverMenu() {
        Main.cambiarPantalla("/ui/fxml/UserView.fxml");
    }

    @FXML
    private void irAInicioSesion() {
        Usuario usuario = usuarioActual();
        if (usuario != null) {
            usuario.getCarrito().limpiar();
        }
        Sesion.getInstancia().cerrar();
        Main.cambiarPantalla("/ui/fxml/Login.fxml");
    }

    private Usuario usuarioActual() {
        Cuenta cuenta = Sesion.getInstancia().getCuentaActual();
        return cuenta instanceof Usuario ? (Usuario) cuenta : null;
    }
}
