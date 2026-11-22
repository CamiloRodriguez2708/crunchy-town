package service;

import model.ItemCarrito;
import model.Producto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CarritoService {
    private final Map<String, ItemCarrito> items = new LinkedHashMap<>();

    public void agregar(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }
        String clave = clave(producto);
        ItemCarrito existente = items.get(clave);
        if (existente == null) {
            items.put(clave, new ItemCarrito(producto));
        } else {
            existente.aumentar();
        }
    }

    public void disminuir(Producto producto) {
        ItemCarrito existente = items.get(clave(producto));
        if (existente == null) {
            return;
        }
        if (existente.getCantidad() <= 1) {
            items.remove(clave(producto));
        } else {
            existente.disminuir();
        }
    }

    public void quitar(Producto producto) {
        if (producto != null) {
            items.remove(clave(producto));
        }
    }

    public List<ItemCarrito> getItems() {
        return new ArrayList<>(items.values());
    }

    public double getTotal() {
        double total = 0;
        for (ItemCarrito item : items.values()) {
            total += item.getSubtotal();
        }
        return total;
    }

    public int getCantidadTotal() {
        int total = 0;
        for (ItemCarrito item : items.values()) {
            total += item.getCantidad();
        }
        return total;
    }

    public boolean estaVacio() {
        return items.isEmpty();
    }

    public void limpiar() {
        items.clear();
    }

    private String clave(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }
        return producto.getTipo() + ":" + producto.getNombre().toLowerCase();
    }
}
