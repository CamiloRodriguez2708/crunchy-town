package ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Producto;
import utils.Formato;

public class TarjetaProductoController {
    private Runnable agregar = () -> {};
    private static final Formato moneda = valor -> String.format("$%,.0f", valor);

    // Lo que ocurre al pulsar "+" (lo decide quien crea la tarjeta)


    @FXML private ImageView imgProducto;
    @FXML private Label lblNombre;
    @FXML private Label lblDescripcion;
    @FXML private Label lblPrecio;

    private Producto producto;

    public void setProducto(Producto producto) {
        this.producto = producto;

        lblNombre.setText(producto.getNombre());
        lblDescripcion.setText(producto.getDescripcion());
        lblPrecio.setText(moneda.formatear(producto.getPrecio()));

        imgProducto.setImage(iconoDe(producto.getTipo()));

    }

    private Image iconoDe(String tipo) {
        String archivo = switch (tipo) {
            case "Comida" -> "Comida.png";
            case "Bebida" -> "Bebida.png";
            default -> "Acompanamiento.png";
        };
        return new Image(getClass().getResourceAsStream("/utils/imagenes/" + archivo));
    }

    public void setAgregar(Runnable agregar) {
        this.agregar = agregar;
    }

    @FXML
    private void agregar() {
        agregar.run();
    }
}
