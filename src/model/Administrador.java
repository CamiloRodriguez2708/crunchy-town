package model;

public class Administrador extends Cuenta {
    private final String token;

    public Administrador(String id, String nombre, String token) {
        super(id, nombre);
        if (!verificar(token)){
            throw new IllegalArgumentException("Token inválido");
        }
        this.token = token;
    }

    // Orden de los datos: [0] token

    public boolean verificar(String token) {
        Verificable<String> tokenValido = t -> t != null && !t.isBlank();
        return tokenValido.verificar(token);
    }

    // Orden de las credenciales: [0] token
    @Override
    public boolean iniciarSesion(String id, String token) {
        if (!verificar(token)) {
            return false;
        }
        return this.getId().equals(id) && this.token.equals(token);
    }
}
