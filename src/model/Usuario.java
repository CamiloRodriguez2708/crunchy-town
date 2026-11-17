package model;

public class Usuario extends Cuenta {


    public Usuario(String id, String nombre, String correo, String contrasena) {
        super(id, nombre, correo, contrasena);
    }

    @Override
    public String getRol() {
        return "Usuario";
    }

    public void hacerPedido() {
        throw new UnsupportedOperationException("Pendiente: depende de Pedido");
    }

    public void cancelarPedido() {
        throw new UnsupportedOperationException("Pendiente: depende de Pedido");
    }

    public void agregarAlCarrito() {
        throw new UnsupportedOperationException("Pendiente: depende de Comida");
    }

    public void quitarDelCarrito() {
        throw new UnsupportedOperationException("Pendiente: depende de Comida");
    }


}
