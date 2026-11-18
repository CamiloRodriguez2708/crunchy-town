package model;

public class Comida implements Producto {
    private final String nombre;
    private final String descripcion;
    private final double precio;

    public Comida(String nombre, String descripcion, double precio) {
        this.nombre = nombre;
        this.descripcion = (descripcion == null) ? "" : descripcion;
        this.precio = precio;
        validar();
    }

    @Override
    public String getNombre() { return nombre; }

    @Override
    public String getDescripcion() { return descripcion; }

    @Override
    public double getPrecio() { return precio; }

    @Override
    public String getTipo() { return "Comida"; }

    @Override
    public String toString() {
        return nombre + " - $" + precio;
    }
}