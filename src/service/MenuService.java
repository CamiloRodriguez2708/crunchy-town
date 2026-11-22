package service;

import model.Producto;
import persistence.ProductoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class MenuService {

    private final ProductoRepository repositorio = new ProductoRepository();
    private final List<Producto> menu = repositorio.cargar();

    public List<Producto> getMenu() {
        return new ArrayList<>(menu);
    }

    // Búsqueda: devuelve el primer producto que cumple la condición, o null si ninguno
    public Producto buscar(Predicate<Producto> condicion) {
        for (Producto p : menu) {
            if (condicion.test(p)) {
                return p;
            }
        }
        return null;
    }

    public Producto buscarPorNombre(String nombre) {
        return buscar(p -> p.getNombre().equalsIgnoreCase(nombre));
    }

    // Filtro: devuelve solo los productos que cumplen la condición
    public List<Producto> filtrar(Predicate<Producto> condicion) {
        List<Producto> resultado = new ArrayList<>(menu);
        resultado.removeIf(p -> !condicion.test(p));
        return resultado;
    }

    // Orden: de menor a mayor precio
    public List<Producto> ordenadosPorPrecio() {
        List<Producto> copia = new ArrayList<>(menu);
        copia.sort((a, b) -> Double.compare(a.getPrecio(), b.getPrecio()));
        return copia;
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