package strategy;

public class TarjetaStrategy implements MetodoPagoStrategy {
    @Override
    public String getNombre() {
        return "Tarjeta";
    }

    @Override
    public String procesarPago(double monto) {
        return "Pago con tarjeta registrado en modo demostración.";
    }
}
