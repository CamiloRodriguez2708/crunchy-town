package factory;

import model.Producto;

public abstract class ProductoFactory {

    public abstract Producto crearProducto(String nombre, String descripcion, int precio);

}
