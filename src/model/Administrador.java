package model;

import service.MenuService;

public class Administrador extends Cuenta {

    public Administrador(String id, String nombre, String correo, String contrasena) {
        super(id, nombre, correo, contrasena);
    }

    @Override
    public String getRol() {
        return "Administrador";
    }

    public void aceptarPedido(Pedido pedido) {
        pedido.setEstado("ACEPTADO");
    }

    public void denegarPedido(Pedido pedido) {
        pedido.setEstado("DENEGADO");
    }

    public void agregarProducto(MenuService menu, Producto producto) {
        menu.agregar(producto);
    }

    public boolean quitarProducto(MenuService menu, Producto producto) {
        return menu.eliminar(producto);
    }
}