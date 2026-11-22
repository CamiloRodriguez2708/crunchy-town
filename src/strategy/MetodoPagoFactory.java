package strategy;

import java.util.Arrays;
import java.util.List;

public final class MetodoPagoFactory {
    private MetodoPagoFactory() {
    }

    public static List<String> getMetodosDisponibles() {
        return Arrays.asList("Efectivo", "Tarjeta", "Nequi");
    }

    public static MetodoPagoStrategy crear(String nombre) {
        if ("Efectivo".equalsIgnoreCase(nombre)) {
            return new EfectivoStrategy();
        }
        if ("Tarjeta".equalsIgnoreCase(nombre)) {
            return new TarjetaStrategy();
        }
        if ("Nequi".equalsIgnoreCase(nombre)) {
            return new NequiStrategy();
        }
        throw new IllegalArgumentException("Método de pago no válido");
    }
}
