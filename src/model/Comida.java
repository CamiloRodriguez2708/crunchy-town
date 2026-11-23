package model;

public class Comida implements Producto {
    private  String nombre;
    private  String descripcion;
    private  double precio;

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

    public Comida setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public Comida setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public Comida setPrecio(double precio) {
        this.precio = precio;
        return this;
    }
}