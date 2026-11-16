package model;

public class Administrador extends Cuenta {
    private String token;

    public Administrador(String id, String nombre, String token) {
        super(id, nombre);
        if (!verificar(new String[]{token})) {
            throw new IllegalArgumentException("Token inválido");
        }
        this.token = token;
    }

    // Orden de los datos: [0] token
    @Override
    public boolean verificar(String[] datos) {
        if (datos.length != 1) {
            return false;
        }
        Verificable<String> tokenValido = t -> t != null && !t.isBlank();
        return tokenValido.verificar(datos[0]);
    }

    // Orden de las credenciales: [0] token
    @Override
    public boolean iniciarSesion(String... credenciales) {
        if (!verificar(credenciales)) {
            return false;
        }
        return token.equals(credenciales[0]);
    }
}
