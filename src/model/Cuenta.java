package model;

public abstract class Cuenta {
    private final String id;
    private final String nombre;
    private final String correo;
    private final String contrasena;

    public Cuenta(String id, String nombre, String correo, String contrasena) {
        if (!verificar(correo,contrasena,nombre)){
            throw new IllegalArgumentException("Datos invalidos");
        }
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
    }

    public String getId() {
        return id;
    }

    public String getCorreo() {
        return correo;
    }

    public String getNombre() {
        return nombre;
    }
    public boolean verificar(String correo, String contrasena, String nombre) {
        Verificable<String> correoValido = c -> c != null && c.contains("@");
        Verificable<String> claveValida = p -> p != null && !p.isBlank();
        Verificable<String> nombreValido = d -> d != null && !d.isBlank();
        return correoValido.verificar(correo) && claveValida.verificar(contrasena) && nombreValido.verificar(nombre);
    }

    public boolean iniciarSesion(String correo, String contrasena) {
        return this.correo.equals(correo) && this.contrasena.equals(contrasena);
    }

    public abstract String getRol();
}
