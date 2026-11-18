package persistence;

import model.Acompanamiento;
import model.Bebida;
import model.Comida;

import java.util.ArrayList;
import java.util.List;

public class DatosProductos {
    public List<Comida> comidas = new ArrayList<>();
    public List<Bebida> bebidas = new ArrayList<>();
    public List<Acompanamiento> acompanamientos = new ArrayList<>();
}