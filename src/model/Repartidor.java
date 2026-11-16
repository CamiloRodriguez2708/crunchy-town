package model;

public class Repartidor extends Cuenta {
    private final String idTrabajador;
    private final String contrasena;

    public Repartidor(String id, String nombre, String idTrabajador, String contrasena) {
        super(id, nombre);
        if (!verificar(idTrabajador, contrasena)) {
            throw new IllegalArgumentException("ID de trabajador o contraseña inválidos");
        }
        this.idTrabajador = idTrabajador;
        this.contrasena = contrasena;
    }

    public boolean verificar(String idTrabajador, String contrasena) {
        Verificable<String> textoValido = t -> t != null && !t.isBlank();
        return textoValido.verificar(idTrabajador) && textoValido.verificar(contrasena);
    }

    @Override
    public boolean iniciarSesion(String idTrabajador, String contrasena) {
        if (!verificar(idTrabajador, contrasena)) {
            return false;
        }
        return this.idTrabajador.equals(idTrabajador) && this.contrasena.equals(contrasena);
    }

    public String getIdTrabajador() { return idTrabajador; }
}