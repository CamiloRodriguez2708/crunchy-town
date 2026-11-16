package persistence;

import model.Administrador;
import model.Repartidor;
import model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class DatosCuentas {
    public List<Usuario> usuarios = new ArrayList<>();
    public List<Administrador> administradores = new ArrayList<>();
    public List<Repartidor> repartidores = new ArrayList<>();
}