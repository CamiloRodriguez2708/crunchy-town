package strategy;

public class EfectivoStrategy implements MetodoPagoStrategy {
    @Override
    public String getNombre() {
        return "Efectivo";
    }

    @Override
    public String procesarPago(double monto) {
        return "Pago en efectivo al recibir el pedido.";
    }
}
