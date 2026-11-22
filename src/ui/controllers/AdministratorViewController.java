package ui.controllers;

import factory.AcompanamientoFactory;
import factory.BebidaFactory;
import factory.ComidaFactory;
import factory.ProductoFactory;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import main.Main;
import model.*;
import service.Sesion;
import utils.Alertas;
import utils.Formato;

public class AdministratorViewController {
    @FXML private Text labUnidades;
    @FXML private ComboBox<String> cbTipo;
    @FXML private TextField txtNombre;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtUnidades;
    @FXML private TextArea txtDescripcion;
    @FXML private Label lbNombre;
    @FXML private Label lbDescripcion;
    @FXML private Label lbPrecio;
    @FXML private Label lbIcono;

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
        if (cbTipo.getSelectionModel().getSelectedItem().toString().equals("Comida")) {
            lbIcono.setText("\uEA61"); // lunch_dining (hamburguesa)
        }
        else if (cbTipo.getSelectionModel().getSelectedItem().toString().equals("Bebida")) {
            lbIcono.setText("\uE544"); // local_drink (bebida)
        }
        else {
            lbIcono.setText("\uF1E9"); // tapas (acompañamiento)
        }
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

        // Factory Method: se elige la fábrica
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
    private void actualizarInfo() {
        Formato moneda = valor -> String.format("$%,.0f", valor);
        lbDescripcion.setText(txtDescripcion.getText());
        lbNombre.setText(txtNombre.getText());
        lbPrecio.setText(moneda.formatear(Double.parseDouble(txtPrecio.getText())));
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