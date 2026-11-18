package factory;

import model.Comida;

public class ComidaFactory extends ProductoFactory {

    @Override
    public Comida crearProducto(String nombre, String descripcion, int precio) {
        return new Comida(nombre, descripcion, precio);
    }
}