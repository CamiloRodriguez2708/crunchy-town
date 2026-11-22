package model;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private String id;
    private String usuarioId;
    private String estado;
    private String metodoPago;
    private List<ItemPedido> productos = new ArrayList<>();
    private double precioTotal;

    public Pedido() {
    }

    public Pedido(String id, String usuarioId, String estado, String metodoPago,
                  List<ItemPedido> productos, double precioTotal) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.estado = estado;
        this.metodoPago = metodoPago;
        this.productos = new ArrayList<>(productos);
        this.precioTotal = precioTotal;
    }

    public String getId() { return id; }
    public String getUsuarioId() { return usuarioId; }
    public String getEstado() { return estado; }
    public String getMetodoPago() { return metodoPago; }
    public List<ItemPedido> getProductos() { return new ArrayList<>(productos); }
    public double getPrecioTotal() { return precioTotal; }
}