package ui.controllers;

import factory.AcompanamientoFactory;
import factory.BebidaFactory;
import factory.ComidaFactory;
import factory.ProductoFactory;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import main.Main;
import model.Administrador;
import model.Cuenta;
import model.Producto;
import service.Sesion;
import utils.Alertas;

public class AdministratorViewController {
    @FXML private Text txtBienvenida;
    @FXML private Text labUnidades;
    @FXML private ComboBox<String> cbTipo;
    @FXML private TextField txtNombre;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtUnidades;
    @FXML private TextArea txtDescripcion;

    @FXML
    public void initialize() {
        cbTipo.getItems().addAll("Comida", "Bebida", "Acompañamiento");
        cbTipo.setValue("Comida");
        cambiarTipo();
    }

    // Se ejecuta cuando el administrador elige otro tipo en el combo
    @FXML
    private void cambiarTipo() {
        boolean esAcompanamiento = "Acompañamiento".equals(cbTipo.getValue());
        labUnidades.setVisible(esAcompanamiento);
        txtUnidades.setVisible(esAcompanamiento);
    }

    @FXML
    private void agregarProducto() {
        Cuenta cuenta = Sesion.getInstancia().getCuentaActual();
        if (!(cuenta instanceof Administrador)) {
            Alertas.error("Solo el administrador puede modificar el menú");
            return;
        }
        Administrador admin = (Administrador) cuenta;

        String tipo = cbTipo.getValue();
        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();

        int precio;
        try {
            precio = Integer.parseInt(txtPrecio.getText().trim());
        } catch (NumberFormatException e) {
            Alertas.error("El precio debe ser un número entero");
            return;
        }

        // Factory Method: se elige la fábrica y desde ahí el código no sabe cuál es
        ProductoFactory fabrica;
        if (tipo.equals("Comida")) {
            fabrica = new ComidaFactory();
        } else if (tipo.equals("Bebida")) {
            fabrica = new BebidaFactory();
        } else {
            int unidades;
            try {
                unidades = Integer.parseInt(txtUnidades.getText().trim());
            } catch (NumberFormatException e) {
                Alertas.error("Las unidades deben ser un número entero");
                return;
            }
            fabrica = new AcompanamientoFactory(unidades);
        }

        if (Main.getMenuService().buscarPorNombre(nombre) != null) {
            Alertas.error("Ya existe un producto con ese nombre");
            return;
        }

        try {
            Producto nuevo = fabrica.crearProducto(nombre, descripcion, precio);
            admin.agregarProducto(Main.getMenuService(), nuevo);
            Alertas.info("Producto agregado al menú");
            limpiarCampos();
        } catch (IllegalArgumentException e) {
            Alertas.error(e.getMessage());
        }
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtPrecio.clear();
        txtUnidades.clear();
        txtDescripcion.clear();
    }

    @FXML
    private void irAInicioSesion() {
        Sesion.getInstancia().cerrar();
        Main.cambiarPantalla("/ui/fxml/Login.fxml");
    }
}