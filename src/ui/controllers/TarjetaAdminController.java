package ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.Main;
import model.*;
import service.Sesion;
import utils.Formato;

import java.io.IOException;


public class TarjetaAdminController {
    private Runnable alEliminar = () -> {};
    private static final Formato moneda = valor -> String.format("$%,.0f", valor);

    @FXML private ImageView icono;
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
        icono.setImage(iconoDe(producto));

        String tipo = producto.getTipo();
        if (producto instanceof Stackeable) {
            tipo += " · x" + ((Stackeable) producto).getUnidades();
        }
        lblTipo.setText(tipo);
    }

    private Image iconoDe(Producto producto) {
        Image imagen;
        if (producto instanceof Comida) {
            return imagen = new Image(getClass().getResourceAsStream("/utils/imagenes/Comida.png"));
        }
        if (producto instanceof Bebida) {
            return imagen = new Image(getClass().getResourceAsStream("/utils/imagenes/Bebida.png"));
        }
        return imagen = new Image(getClass().getResourceAsStream("/utils/imagenes/Acompanamiento.png"));
    }

    @FXML
    private void eliminar() {
        Administrador administrador = (Administrador) Sesion.getInstancia().getCuentaActual();
        administrador.quitarProducto(Main.getMenuService(), producto);
        alEliminar.run();
    }

    @FXML
    private void editar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/AdministratorEditView.fxml"));
            Parent raiz = loader.load();

            AdministratorEditViewController controller = loader.getController();
            controller.setProducto(producto);

            icono.getScene().setRoot(raiz);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAlEliminar(Runnable alEliminar){
        this.alEliminar = alEliminar;
    }
}
