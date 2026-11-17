package model;

public class Administrador extends Cuenta {

    public Administrador(String id, String nombre, String correo, String contrasena) {
        super(id, nombre, correo, contrasena);
    }

    @Override
    public String getRol() {
        return "Administrador";
    }

    public void aceptarPedido() {
        throw new UnsupportedOperationException("Pendiente: depende de Pedido");
    }

    public void denegarPedido() {
        throw new UnsupportedOperationException("Pendiente: depende de Pedido");
    }

    public void agregarProducto() {
        throw new UnsupportedOperationException("Pendiente: depende de Comida");
    }

    public void quitarProducto() {
        throw new UnsupportedOperationException("Pendiente: depende de Comida");
    }

    public void agregarCombo() {
        throw new UnsupportedOperationException("Pendiente: depende de los combos");
    }

    public void quitarCombo() {
        throw new UnsupportedOperationException("Pendiente: depende de los combos");
    }

}
