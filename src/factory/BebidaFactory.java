package factory;

import model.Bebida;

public class BebidaFactory extends ProductoFactory{

    @Override
    public Bebida crearProducto(String nombre, String descripcion, int precio) {
        return new Bebida(nombre, descripcion, precio);
    }
}
