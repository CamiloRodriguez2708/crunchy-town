package ui.controllers;

import factory.AcompanamientoFactory;
import factory.BebidaFactory;
import factory.ComidaFactory;
import factory.ProductoFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import main.Main;
import model.Acompanamiento;
import model.Administrador;
import model.Bebida;
import model.Comida;
import model.Cuenta;
import model.Producto;
import model.Stackeable;
import service.Sesion;
import utils.Alertas;
import utils.Formato;

public class AdministratorEditViewController {
    @FXML private Text labUnidades;
    @FXML private TextField txtNombre;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtUnidades;
    @FXML private TextArea txtDescripcion;
    @FXML private Label lbNombre;
    @FXML private Label lbDescripcion;
    @FXML private Label lbPrecio;
    @FXML private ImageView icono;

    private Producto producto;

    // Se llama DESPUÉS de cargar el FXML (initialize ya corrió), por eso aquí se rellenan los campos
    public void setProducto(Producto p) {
        this.producto = p;

        txtNombre.setText(p.getNombre());
        txtDescripcion.setText(p.getDescripcion());
        txtPrecio.setText(String.valueOf((int) p.getPrecio()));

        boolean esStackeable = p instanceof Stackeable;
        labUnidades.setVisible(esStackeable);
        txtUnidades.setVisible(esStackeable);
        if (esStackeable) {
            txtUnidades.setText(String.valueOf(((Stackeable) p).getUnidades()));
        }

        icono.setImage(iconoDe(p));
        actualizarInfo();
    }

    private Image iconoDe(Producto p) {
        String archivo;
        if (p instanceof Comida) {
            archivo = "Comida.png";
        } else if (p instanceof Bebida) {
            archivo = "Bebida.png";
        } else {
            archivo = "Acompanamiento.png";
        }
        return new Image(getClass().getResourceAsStream("/utils/imagenes/" + archivo));
    }

    @FXML
    private void guardarCambios() {
        Cuenta cuenta = Sesion.getInstancia().getCuentaActual();
        if (!(cuenta instanceof Administrador)) {
            Alertas.error("Solo el administrador puede modificar el menú");
            return;
        }

        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();

        int precio;
        try {
            precio = Integer.parseInt(txtPrecio.getText().trim());
        } catch (NumberFormatException e) {
            Alertas.error("El precio debe ser un número entero");
            return;
        }

        int unidades = 1;
        if (producto instanceof Stackeable) {
            try {
                unidades = Integer.parseInt(txtUnidades.getText().trim());
            } catch (NumberFormatException e) {
                Alertas.error("Las unidades deben ser un número entero");
                return;
            }
        }

        // Nombre repetido: vale si es el mismo producto que estamos editando
        Producto otro = Main.getMenuService().buscarPorNombre(nombre);
        if (otro != null && otro != producto) {
            Alertas.error("Ya existe un producto con ese nombre");
            return;
        }

        try {
            // Probamos los datos con la fábrica (valida nombre, precio y unidades)
            // ANTES de tocar el producto real, así no queda a medias si algo es inválido
            ProductoFactory fabrica;
            if (producto instanceof Comida) {
                fabrica = new ComidaFactory();
            } else if (producto instanceof Bebida) {
                fabrica = new BebidaFactory();
            } else {
                fabrica = new AcompanamientoFactory(unidades);
            }
            fabrica.crearProducto(nombre, descripcion, precio);
        } catch (IllegalArgumentException e) {
            Alertas.error(e.getMessage());
            return;
        }

        // Los datos son válidos: ahora sí se aplican al producto original
        if (producto instanceof Comida c) {
            c.setNombre(nombre).setDescripcion(descripcion).setPrecio(precio);
        } else if (producto instanceof Bebida b) {
            b.setNombre(nombre).setDescripcion(descripcion).setPrecio(precio);
        } else if (producto instanceof Acompanamiento a) {
            a.setNombre(nombre).setDescripcion(descripcion).setPrecio(precio).setUnidades(unidades);
        }

        Main.getMenuService().guardar();
        Alertas.info("Producto actualizado");
        volverAlMenu();
    }

    @FXML
    private void actualizarInfo() {
        Formato moneda = valor -> String.format("$%,.0f", valor);
        lbDescripcion.setText(txtDescripcion.getText());
        lbNombre.setText(txtNombre.getText());
        try {
            lbPrecio.setText(moneda.formatear(Double.parseDouble(txtPrecio.getText().trim())));
        } catch (NumberFormatException e) {
            lbPrecio.setText(moneda.formatear(0));
        }
    }

    @FXML
    private void irAInicioSesion() {
        Sesion.getInstancia().cerrar();
        Main.cambiarPantalla("/ui/fxml/Login.fxml");
    }

    @FXML
    private void volverAlMenu() {
        Main.cambiarPantalla("/ui/fxml/AdministratorMenuView.fxml");
    }
}
