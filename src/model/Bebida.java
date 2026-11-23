package model;

public class Bebida implements Producto {
    private  String nombre;
    private  String descripcion;
    private  double precio;

    public Bebida(String nombre, String descripcion, double precio) {
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
    public String getTipo() { return "Bebida"; }

    @Override
    public String toString() {
        return nombre + " - $" + precio;
    }

    public Bebida setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public Bebida setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public Bebida setPrecio(double precio) {
        this.precio = precio;
        return this;
    }
}