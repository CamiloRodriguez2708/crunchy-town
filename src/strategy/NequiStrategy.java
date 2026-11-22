package strategy;

public class NequiStrategy implements MetodoPagoStrategy {
    @Override
    public String getNombre() {
        return "Nequi";
    }

    @Override
    public String procesarPago(double monto) {
        return "Pago con Nequi registrado en modo demostración.";
    }
}
