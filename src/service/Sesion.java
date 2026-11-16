package service;

import model.Cuenta;

public class Sesion {
    private static Sesion instancia;
    private Cuenta cuentaActual;

    private Sesion() {}

    public static Sesion getInstancia() {
        if (instancia == null) {
            instancia = new Sesion();
        }
        return instancia;
    }

    public void iniciar(Cuenta cuenta) { this.cuentaActual = cuenta; }
    public void cerrar() { this.cuentaActual = null; }
    public Cuenta getCuentaActual() { return cuentaActual; }
    public boolean haySesion() { return cuentaActual != null; }
}