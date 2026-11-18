package model;

public interface Producto {
    String getNombre();
    String getDescripcion();
    double getPrecio();
    String getTipo();

    default void validar() {
        Verificable<String> textoValido = t -> t != null && !t.isBlank();
        if (!textoValido.verificar(getNombre())) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }
        if (getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }
    }
}