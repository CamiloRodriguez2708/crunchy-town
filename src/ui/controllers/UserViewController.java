package ui.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import main.Main;
import model.Cuenta;
import model.Producto;
import model.Usuario;
import service.Sesion;
import utils.Alertas;
import utils.Formato;


import java.util.List;

public class UserViewController {
    private static final Formato moneda = valor -> String.format("$%,.0f", valor);

    @FXML private VBox contenedorProductos;

    @FXML
    public void initialize() {
        mostrarMenu();
    }

    private void mostrarMenu() {
        contenedorProductos.getChildren().clear();
        List<Producto> menu = Main.getMenuService().getMenu();
        if (menu.isEmpty()) {
            Label vacio = new Label("Aún no hay productos en el menú.");
            vacio.setStyle("-fx-font-size: 18px; -fx-text-fill: #715e57; -fx-padding: 20px;");
            contenedorProductos.getChildren().add(vacio);
            return;
        }

        for (Producto producto : menu) {
            contenedorProductos.getChildren().add(crearTarjeta(producto));
        }
    }

    private HBox crearTarjeta(Producto producto) {
        HBox tarjeta = new HBox(16);
        tarjeta.setAlignment(Pos.CENTER_LEFT);
        tarjeta.setPadding(new Insets(14, 18, 14, 18));
        tarjeta.setMaxWidth(Double.MAX_VALUE);
        tarjeta.setStyle("-fx-background-color: white; -fx-background-radius: 18;");

        Label icono = new Label("🍽️");
        icono.setMinSize(58, 58);
        icono.setAlignment(Pos.CENTER);
        icono.setStyle("-fx-background-color: #ffbf47; -fx-background-radius: 16; -fx-font-size: 28px;");

        VBox datos = new VBox(4);
        Label nombre = new Label(producto.getNombre());
        nombre.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #30211e;");
        Label descripcion = new Label(producto.getDescripcion().isBlank()
                ? producto.getTipo()
                : producto.getTipo() + " · " + producto.getDescripcion());
        descripcion.setWrapText(true);
        descripcion.setStyle("-fx-font-size: 14px; -fx-text-fill: #715e57;");
        Label precio = new Label(moneda.formatear(producto.getPrecio()));
        precio.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #d62828;");
        datos.getChildren().addAll(nombre, descripcion, precio);
        HBox.setHgrow(datos, Priority.ALWAYS);

        Button agregar = new Button("Añadir al carrito");
        agregar.setStyle("-fx-background-color: #ffbf47; -fx-background-radius: 18; -fx-font-size: 15px; -fx-font-weight: bold;");
        agregar.setOnAction(event -> agregarAlCarrito(producto));
        tarjeta.getChildren().addAll(icono, datos, agregar);
        return tarjeta;
    }

    private void agregarAlCarrito(Producto producto) {
        Usuario usuario = usuarioActual();
        if (usuario == null) {
            Alertas.error("Inicia sesión como usuario para agregar productos.");
            return;
        }
        usuario.agregarAlCarrito(producto);
        Alertas.info(producto.getNombre() + " agregado al carrito.");
    }

    @FXML
    private void abrirPedidos() {
        if (usuarioActual() == null) {
            Alertas.error("Inicia sesión como usuario para ver tu pedido.");
            return;
        }
        Main.cambiarPantalla("/ui/fxml/CartView.fxml");
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
