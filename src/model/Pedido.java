package model;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private final String id;
    private final String usuarioId;
    private String estado;
    private final String metodoPago;
    private final List<ItemPedido> productos;
    private final double precioTotal;
    private final String dirreccion;
    private String idRepartidor;


    public Pedido(String id, String usuarioId, String estado, String metodoPago,
                  List<ItemPedido> productos, double precioTotal, String dirreccion) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.estado = estado;
        this.metodoPago = metodoPago;
        this.productos = new ArrayList<>(productos);
        this.precioTotal = precioTotal;
        this.dirreccion = dirreccion;
        this.idRepartidor = null;
    }

    public String getId() { return id; }
    public String getUsuarioId() { return usuarioId; }
    public String getEstado() { return estado; }
    public String getMetodoPago() { return metodoPago; }
    public List<ItemPedido> getProductos() { return new ArrayList<>(productos); }
    public double getPrecioTotal() { return precioTotal; }
    public String getDirreccion() { return dirreccion; }
    public String getIdRepartidor() { return idRepartidor; }

    public Pedido setEstado(String estado) {
        this.estado = estado;
        return this;
    }

    public void setIdRepartidor(String idRepartidor) {
        this.idRepartidor = idRepartidor;
    }
}