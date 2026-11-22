package ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.Main;
import model.*;
import service.Sesion;
import utils.Formato;


public class TarjetaAdminController {
    private static final Formato moneda = valor -> String.format("$%,.0f", valor);

    @FXML private Label lblIcono;
    @FXML private Label lblNombre;
    @FXML private Label lblDescripcion;
    @FXML private Label lblPrecio;
    @FXML private Label lblTipo;

    private Producto producto;

    public void setProducto(Producto producto) {
        this.producto = producto;

        lblNombre.setText(producto.getNombre());
        lblDescripcion.setText(producto.getDescripcion().isBlank() ? "Sin descripción" : producto.getDescripcion());
        lblPrecio.setText(moneda.formatear(producto.getPrecio()));
        lblIcono.setText(iconoDe(producto));

        String tipo = producto.getTipo();
        if (producto instanceof Stackeable) {
            tipo += " · x" + ((Stackeable) producto).getUnidades();
        }
        lblTipo.setText(tipo);
    }

    private String iconoDe(Producto producto) {
        if (producto instanceof Comida) {
            return "\uEA61"; // lunch_dining (hamburguesa)
        }
        if (producto instanceof Bebida) {
            return "\uE544"; // local_drink (bebida)
        }
        return "\uF1E9"; // tapas (acompañamiento)
    }

    @FXML
    private void eliminar() {
        Administrador administrador = (Administrador) Sesion.getInstancia().getCuentaActual();
        administrador.quitarProducto(Main.getMenuService(), producto);
    }
}
