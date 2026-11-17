package service;

import model.Cuenta;

import java.util.ArrayList;
import java.util.List;

public class CuentaService {
    private final List<Cuenta> cuentas = new ArrayList<>();

    public String generarId() {
        int mayor = 0;
        for (Cuenta c : cuentas) {
            int numero = Integer.parseInt(c.getId());
            if (numero > mayor) {
                mayor = numero;
            }
        }
        return String.valueOf(mayor + 1);
    }

    public boolean existeCorreo(String correo) {
        for (Cuenta c : cuentas) {
            if (c.getCorreo().equals(correo)) {
                return true;
            }
        }
        return false;
    }

    // Devuelve false si el correo ya está registrado
    public boolean agregar(Cuenta cuenta) {
        if (existeCorreo(cuenta.getCorreo())) {
            return false;
        }
        cuentas.add(cuenta);
        return true;
    }

    public void actualizarLista(List<Cuenta> cuentasP) {
        this.cuentas.addAll(cuentasP);
    }

    public boolean login(String factor1, String factor2) {
        for (Cuenta c : cuentas) {
            if (c.iniciarSesion(factor1, factor2)) {
                Sesion.getInstancia().iniciar(c);
                return true;
            }
        }
        return false;
    }

    public List<Cuenta> getCuentas() {
        return new ArrayList<>(cuentas);
    }
}