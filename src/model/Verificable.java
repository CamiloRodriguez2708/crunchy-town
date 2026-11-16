package model;

@FunctionalInterface
public interface Verificable <T>{
    abstract boolean verificar(T elemento);
}
