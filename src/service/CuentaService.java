package service;

import model.Cuenta;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

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

    // Búsqueda: devuelve la primera cuenta que cumple la condición, o null si ninguna
    public Cuenta buscar(Predicate<Cuenta> condicion) {
        for (Cuenta c : cuentas) {
            if (condicion.test(c)) {
                return c;
            }
        }
        return null;
    }

    public boolean existeCorreo(String correo) {
        return buscar(c -> c.getCorreo().equals(correo)) != null;
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

    public boolean login(String correo, String contrasena) {
        Cuenta cuenta = buscar(c -> c.iniciarSesion(correo, contrasena));
        if (cuenta == null) {
            return false;
        }
        Sesion.getInstancia().iniciar(cuenta);
        return true;
    }

    public List<Cuenta> getCuentas() {
        return new ArrayList<>(cuentas);
    }
}