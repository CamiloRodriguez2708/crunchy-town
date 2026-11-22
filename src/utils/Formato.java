package utils;

/**
 * Interfaz funcional para dar formato a valores monetarios.
 * La implementación se define con una lambda en el lugar donde se use.
 */
@FunctionalInterface
public interface Formato {

    String formatear(double valor);
}
