package model;

public class Repartidor extends Cuenta {
    private String idTrabajador;
    private String contrasena;

    public Repartidor(String id, String nombre, String idTrabajador, String contrasena) {
        super(id, nombre);
        if (!verificar(new String[]{idTrabajador, contrasena})) {
            throw new IllegalArgumentException("ID de trabajador o contraseña inválidos");
        }
        this.idTrabajador = idTrabajador;
        this.contrasena = contrasena;
    }

    // Orden de los datos: [0] ID de trabajador, [1] contraseña
    @Override
    public boolean verificar(String[] datos) {
        if (datos.length != 2) {
            return false;
        }
        Verificable<String> textoValido = t -> t != null && !t.isBlank();
        return textoValido.verificar(datos[0]) && textoValido.verificar(datos[1]);
    }

    // Orden de las credenciales: [0] ID de trabajador, [1] contraseña
    @Override
    public boolean iniciarSesion(String... credenciales) {
        if (!verificar(credenciales)) {
            return false;
        }
        return idTrabajador.equals(credenciales[0]) && contrasena.equals(credenciales[1]);
    }

    public String getIdTrabajador() { return idTrabajador; }
}