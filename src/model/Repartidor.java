package model;

public class Repartidor extends Cuenta {



    public Repartidor(String id, String nombre, String correo, String contrasena) {
        super(id, nombre, correo, contrasena);
    }


    @Override
    public String getRol() {
        return "Repartidor";
    }

    public void completarPedido() {
        throw new UnsupportedOperationException("Pendiente: depende de Pedido");
    }
}