package ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import main.Main;
import model.Producto;
import model.Usuario;
import service.Sesion;
import utils.Alertas;
import utils.Formato;

import java.util.List;
import javafx.scene.control.Button;

public class UserViewController {
    private static final String NOMBRE_PROMO = "Doble Town";
    private static final String RUTA_TARJETA = "/ui/fxml/TarjetaProducto.fxml";
    private static final String RUTA_CARRITO = "/ui/fxml/CartView.fxml";

    private static final Formato moneda = valor -> String.format("$%,.0f", valor);

    private static final String ESTILO_CHIP = "-fx-background-color: white; -fx-text-fill: #222222;"
            + " -fx-background-radius: 20; -fx-padding: 6 16 6 16; -fx-font-size: 11px; -fx-cursor: hand;";
    private static final String ESTILO_CHIP_ACTIVO = "-fx-background-color: #d62828; -fx-text-fill: white;"
            + " -fx-background-radius: 20; -fx-padding: 6 16 6 16; -fx-font-size: 11px; -fx-cursor: hand;";

    @FXML private ScrollPane pagina;
    @FXML private VBox contenido;
    @FXML private TextField campoBusqueda;
    @FXML private ToggleGroup grupoCategorias;
    @FXML private HBox tarjetaPromo;
    @FXML private Label promoTitulo;
    @FXML private Label promoDetalle;
    @FXML private Label promoPrecio;
    @FXML private StackPane promoImagen;
    @FXML private Label tituloMenu;
    @FXML private FlowPane contenedorProductos;
    @FXML private Label mensajeVacio;
    @FXML private Button btnCarrito;

    private List<Producto> productos;
    private Producto productoPromo;

    @FXML
    public void initialize() {
        productos = Main.getMenuService().getMenu();

        // Siempre debe haber una categoría marcada: si se toca la activa, se vuelve a marcar
        grupoCategorias.selectedToggleProperty().addListener((obs, anterior, actual) -> {
            if (actual == null && anterior != null) {
                anterior.setSelected(true);
            }
        });
        filtrar(); // pinta "Todo" de rojo y muestra los productos

        Usuario usuario = usuarioActual();
        if (usuario != null) {
            btnCarrito.setText("Carrito (" + usuario.getCarrito().getCantidadTotal() + ")");
        }
    }

    // ---------- Barra superior ----------

    @FXML
    private void irAlInicio() {
        pagina.setVvalue(0);
    }

    @FXML
    private void irAlMenu() {
        double rango = contenido.getHeight() - pagina.getViewportBounds().getHeight();
        if (rango > 0) {
            double posicion = tituloMenu.getBoundsInParent().getMinY();
            pagina.setVvalue(Math.min(1, posicion / rango));
        }
    }

    @FXML
    private void irAPedidos() {
        Main.cambiarPantalla("/ui/fxml/UserPedidosView.fxml");
    }

    @FXML
    private void abrirCarrito() {
        if (Main.class.getResource(RUTA_CARRITO) == null) {
            Alertas.error("El carrito aún no está disponible");
            return;
        }
        Main.cambiarPantalla(RUTA_CARRITO);
    }

    @FXML
    private void irAInicioSesion() {
        Sesion.getInstancia().cerrar();
        Main.cambiarPantalla("/ui/fxml/Login.fxml");
    }


    // ---------- Menú ----------

    @FXML
    private void filtrar() {
        // Pinta de rojo el botón marcado y deja los demás en blanco
        for (Toggle opcion : grupoCategorias.getToggles()) {
            ToggleButton boton = (ToggleButton) opcion;
            boton.setStyle(boton.isSelected() ? ESTILO_CHIP_ACTIVO : ESTILO_CHIP);
        }
        mostrarProductos();
    }

    @FXML
    private void mostrarProductos() {
        contenedorProductos.getChildren().clear();

        String texto = campoBusqueda.getText() == null ? "" : campoBusqueda.getText().trim().toLowerCase();
        Toggle seleccionado = grupoCategorias.getSelectedToggle();
        String categoria = (seleccionado == null) ? null : (String) seleccionado.getUserData();

        int mostrados = 0;
        for (Producto producto : productos) {
            String descripcion = (producto.getDescripcion() == null) ? "" : producto.getDescripcion().toLowerCase();
            String tipo = (producto.getTipo() == null) ? "" : producto.getTipo();

            boolean coincideTexto = producto.getNombre().toLowerCase().contains(texto)
                    || descripcion.contains(texto)
                    || tipo.toLowerCase().contains(texto);
            boolean coincideCategoria = categoria == null || categoria.equals(tipo);

            if (coincideTexto && coincideCategoria) {
                Node tarjeta = crearTarjeta(producto);
                if (tarjeta != null) {
                    contenedorProductos.getChildren().add(tarjeta);
                    mostrados++;
                }
            }
        }

        boolean vacio = (mostrados == 0);
        mensajeVacio.setText(productos.isEmpty()
                ? "El menú aún no tiene productos"
                : "No encontramos productos con ese filtro");
        mensajeVacio.setVisible(vacio);
        mensajeVacio.setManaged(vacio);
    }

    // Carga TarjetaProducto.fxml y le entrega el producto
    private Node crearTarjeta(Producto producto) {
        try {
            FXMLLoader cargador = new FXMLLoader(Main.class.getResource(RUTA_TARJETA));
            Node tarjeta = cargador.load();
            TarjetaProductoController controlador = cargador.getController();
            controlador.setProducto(producto);
            controlador.setAgregar(() -> agregarAlCarrito(producto));
            return tarjeta;
        } catch (Exception e) {
            System.out.println("No se pudo cargar la tarjeta de " + producto.getNombre());
            e.printStackTrace();
            return null;
        }
    }

    // ---------- Carrito ----------

    private void agregarAlCarrito(Producto producto) {
        Usuario usuario = usuarioActual();
        if (usuario == null) {
            Alertas.error("Inicia sesión para agregar productos al carrito.");
            return;
        }
        usuario.agregarAlCarrito(producto);
        btnCarrito.setText("Carrito (" + usuario.getCarrito().getCantidadTotal() + ")");
    }

    private Usuario usuarioActual() {
        return Sesion.getInstancia().getCuentaActual() instanceof Usuario usuario ? usuario : null;
    }

    private Image iconoDe(String tipo) {
        String archivo = switch (tipo) {
            case "Comida" -> "Comida.png";
            case "Bebida" -> "Bebida.png";
            default -> "Acompanamiento.png";
        };
        return new Image(getClass().getResourceAsStream("/utils/imagenes/" + archivo));
    }
}