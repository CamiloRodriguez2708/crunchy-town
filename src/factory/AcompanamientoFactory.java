package factory;

import model.Acompanamiento;

public class AcompanamientoFactory extends ProductoFactory {
    private final int unidades;

    public AcompanamientoFactory() {
        this.unidades = 1;
    }

    public AcompanamientoFactory(int unidades) {
        this.unidades = unidades;
    }

    @Override
    public Acompanamiento crearProducto(String nombre, String descripcion, int precio) {
        return new Acompanamiento(nombre, descripcion, precio, unidades);
    }
}