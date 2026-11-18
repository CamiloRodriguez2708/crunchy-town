package service;

import model.Producto;
import persistence.ProductoRepository;

import java.util.ArrayList;
import java.util.List;

public class MenuService {

    private final ProductoRepository repositorio = new ProductoRepository();
    private final List<Producto> menu = repositorio.cargar();

    public List<Producto> getMenu() {
        return new ArrayList<>(menu);
    }

    // Devuelve el producto, o null si no existe
    public Producto buscarPorNombre(String nombre) {
        for (Producto p : menu) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        return null;
    }

    public void agregar(Producto producto) {
        menu.add(producto);
        repositorio.guardar(menu);
    }

    public boolean eliminar(Producto producto) {
        boolean eliminado = menu.removeIf(p -> p == producto);
        if (eliminado) {
            repositorio.guardar(menu);
        }
        return eliminado;
    }
}