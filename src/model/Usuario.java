package model;

public class Usuario extends Cuenta {
    private final String contrasena;
    private final String correo;

    public Usuario(String id, String nombre, String contrasena, String correo) {
        super(id, nombre);
        if (!verificar(correo, contrasena)) {
            throw new IllegalArgumentException("Correo o contraseña inválidos");
        }
        this.contrasena = contrasena;
        this.correo = correo;
    }

    public boolean verificar(String correo, String contrasena) {
        Verificable<String> correoValido = c -> c != null && c.contains("@");
        Verificable<String> claveValida = p -> p != null && !p.isBlank();
        return  correoValido.verificar(correo) && claveValida.verificar(contrasena);
    }

    @Override
    public boolean iniciarSesion(String correo, String contrasena) {
        if (!verificar(correo, contrasena)) {
            return false;
        }
        return this.correo.equals(correo) && this.contrasena.equals(contrasena);
    }
}
