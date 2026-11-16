package model;

public class Usuario extends Cuenta {
    private final String contrasena;
    private final String correo;

    public Usuario(String id, String nombre, String contrasena, String correo) {
        super(id, nombre);
        if (!verificar(new String[]{correo, contrasena})) {
            throw new IllegalArgumentException("Correo o contraseña inválidos");
        }
        this.contrasena = contrasena;
        this.correo = correo;
    }

    @Override
    public boolean iniciarSesion(String... credenciales) {
        if (!verificar(credenciales)) {
            return false;
        }
        return correo.equals(credenciales[0]) && contrasena.equals(credenciales[1]);
    }

    @Override
    public boolean verificar(String[] elemento) {
        if (elemento.length != 2){
            return false;
        }
        Verificable<String> correoValido = c -> c != null && c.contains("@");
        Verificable<String> claveValida = p -> p != null && !p.isBlank();
        return  correoValido.verificar(elemento[0]) && claveValida.verificar(elemento[1]);
    }
}
