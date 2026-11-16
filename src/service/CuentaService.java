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

    public void agregar(Cuenta cuenta) {
        cuentas.add(cuenta);
    }

    // Devuelve la cuenta, o null si no existe
    public Cuenta buscarPorId(String id) {
        for (Cuenta c : cuentas) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }


    public boolean login(String... credenciales) {
        for (Cuenta c : cuentas) {
            if (c.iniciarSesion(credenciales)) {
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