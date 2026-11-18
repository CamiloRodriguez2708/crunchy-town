package model;

public class Acompanamiento implements Producto, Stackeable {
    private final String nombre;
    private final String descripcion;
    private final double precio;
    private final int unidades;

    public Acompanamiento(String nombre, String descripcion, double precio, int unidades) {
        this.nombre = nombre;
        this.descripcion = (descripcion == null) ? "" : descripcion;
        this.precio = precio;
        this.unidades = unidades;
        validar();
    }

    @Override
    public void validar() {
        Producto.super.validar();
        if (unidades <= 0) {
            throw new IllegalArgumentException("Las unidades deben ser mayores a 0");
        }
    }

    @Override
    public String getNombre() { return nombre; }

    @Override
    public String getDescripcion() { return descripcion; }

    @Override
    public double getPrecio() { return precio; }

    @Override
    public String getTipo() { return "Acompañamiento"; }

    @Override
    public int getUnidades() { return unidades; }

    @Override
    public String toString() {
        String cantidad = (unidades > 1) ? " x" + unidades : "";
        return nombre + cantidad + " - $" + precio;
    }
}