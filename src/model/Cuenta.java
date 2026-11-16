package model;

public abstract class Cuenta {
    private final String id;
    private final String nombre;

    public Cuenta(String id, String nombre) {
        Verificable<String> textoValido = t -> t != null && !t.isBlank();
        if (!textoValido.verificar(id) || !textoValido.verificar(nombre)){
            throw new IllegalArgumentException("Datos invalidos");
        }
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public abstract boolean iniciarSesion(String factor1, String factor2);
}
