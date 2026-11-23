package model;

public class Repartidor extends Cuenta {



    public Repartidor(String id, String nombre, String correo, String contrasena) {
        super(id, nombre, correo, contrasena);
    }


    @Override
    public String getRol() {
        return "Repartidor";
    }

    public void llevarPedido(Pedido pedido){
        pedido.setEstado("EN REPARTO");
        pedido.setIdRepartidor(getId());
    }

    public void completarPedido(Pedido pedido) {
        pedido.setEstado("ENTREGADO");
    }
}