package strategy;

public interface MetodoPagoStrategy {
    String getNombre();
    String procesarPago(double monto);
}
