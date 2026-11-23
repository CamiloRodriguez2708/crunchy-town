package service;

import model.ItemCarrito;
import model.ItemPedido;
import model.Pedido;
import model.Usuario;
import persistence.PedidoRepository;
import strategy.MetodoPagoStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class PedidoService {
    private final PedidoRepository repositorio = new PedidoRepository();
    private final List<Pedido> pedidos = repositorio.cargar();

    public Pedido crearPedido(Usuario usuario, CarritoService carrito, MetodoPagoStrategy metodoPago, String dirrecion) {
        if (usuario == null) {
            throw new IllegalArgumentException("Debes iniciar sesión para hacer un pedido");
        }
        if (carrito == null || carrito.estaVacio()) {
            throw new IllegalArgumentException("El carrito está vacío");
        }
        if (metodoPago == null) {
            throw new IllegalArgumentException("Selecciona un método de pago");
        }

        double total = carrito.getTotal();
        metodoPago.procesarPago(total);

        List<ItemPedido> productos = new ArrayList<>();
        for (ItemCarrito item : carrito.getItems()) {
            productos.add(new ItemPedido(
                    item.getProducto().getNombre(),
                    item.getProducto().getTipo(),
                    item.getProducto().getPrecio(),
                    item.getCantidad()
            ));
        }

        Pedido pedido = new Pedido(
                UUID.randomUUID().toString(),
                usuario.getId(),
                "PENDIENTE",
                metodoPago.getNombre(),
                productos,
                total,
                dirrecion
        );
        pedidos.add(pedido);
        repositorio.guardar(pedidos);
        return pedido;
    }

    public List<Pedido> getPedidos() {
        return new ArrayList<>(pedidos);
    }

    public List<Pedido> filtrar(Predicate<Pedido> condicion) {
        List<Pedido> resultado = new ArrayList<>(pedidos);
        resultado.removeIf(p -> !condicion.test(p));
        return resultado;
    }

    // Guarda en el JSON los cambios hechos a los pedidos (por ejemplo, un cambio de estado)
    public void guardar() {
        repositorio.guardar(pedidos);
    }
}