package model;

public class ItemPedido {
    private String nombreProducto;
    private String tipoProducto;
    private double precioUnitario;
    private int cantidad;
    private double subtotal;

    public ItemPedido() {
    }

    public ItemPedido(String nombreProducto, String tipoProducto, double precioUnitario, int cantidad) {
        this.nombreProducto = nombreProducto;
        this.tipoProducto = tipoProducto;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.subtotal = precioUnitario * cantidad;
    }

    public String getNombreProducto() { return nombreProducto; }
    public String getTipoProducto() { return tipoProducto; }
    public double getPrecioUnitario() { return precioUnitario; }
    public int getCantidad() { return cantidad; }
    public double getSubtotal() { return subtotal; }
}
