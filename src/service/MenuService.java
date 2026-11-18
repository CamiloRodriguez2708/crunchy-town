package service;

import model.Administrador;
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

    public Producto buscarPorNombre(String nombre) {
        for (Producto p : menu) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        return null;
    }

    public boolean esAdministrador() {
        Sesion sesion = Sesion.getInstancia();
        return sesion.haySesion() && sesion.getCuentaActual() instanceof Administrador;
    }

    public void agregarProducto(Producto producto) {
        verificarAdministrador();
        menu.add(producto);
        repositorio.guardar(menu);
    }

    public boolean eliminarProducto(Producto producto) {
        verificarAdministrador();
        boolean eliminado = menu.removeIf(p -> p == producto);
        if (eliminado) {
            repositorio.guardar(menu);
        }
        return eliminado;
    }

    private void verificarAdministrador() {
        if (!esAdministrador()) {
            throw new IllegalStateException("Solo el administrador puede modificar el menú");
        }
    }
}
