package model;

import service.CarritoService;

public class Usuario extends Cuenta {
    // transient: Gson no lo guarda en el JSON, el carrito es temporal
    private transient CarritoService carrito;

    public Usuario(String id, String nombre, String correo, String contrasena) {
        super(id, nombre, correo, contrasena);
    }

    @Override
    public String getRol() {
        return "Usuario";
    }

    // Gson no ejecuta el constructor al cargar el JSON, por eso el carrito se crea aquí si falta
    public CarritoService getCarrito() {
        if (carrito == null) {
            carrito = new CarritoService();
        }
        return carrito;
    }

    public void agregarAlCarrito(Producto producto) {
        getCarrito().agregar(producto);
    }

    public void disminuirDelCarrito(Producto producto) {
        getCarrito().disminuir(producto);
    }

    public void quitarDelCarrito(Producto producto) {
        getCarrito().quitar(producto);
    }

    public void hacerPedido() {
        throw new UnsupportedOperationException("Pendiente");
    }

    public void cancelarPedido() {
        throw new UnsupportedOperationException("Pendiente");
    }
}