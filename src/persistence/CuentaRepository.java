package persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Administrador;
import model.Cuenta;
import model.Repartidor;
import model.Usuario;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CuentaRepository {
    private static final String ruta = "src/persistence/cuentas.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void guardar(List<Cuenta> cuentas) {
        DatosCuentas datos = new DatosCuentas();
        for (Cuenta c : cuentas) {
            if (c instanceof Usuario) {
                datos.usuarios.add((Usuario) c);
            } else if (c instanceof Administrador) {
                datos.administradores.add((Administrador) c);
            } else if (c instanceof Repartidor) {
                datos.repartidores.add((Repartidor) c);
            }
        }
        try (FileWriter writer = new FileWriter(ruta)) {
            gson.toJson(datos, writer);
        } catch (IOException e) {
            System.out.println("No se pudo guardar: " + e.getMessage());
        }
    }

    public List<Cuenta> cargar() {
        List<Cuenta> cuentas = new ArrayList<>();
        File archivo = new File(ruta);
        if (!archivo.exists()) {
            return cuentas;
        }
        try (FileReader reader = new FileReader(archivo)) {
            DatosCuentas datos = gson.fromJson(reader, DatosCuentas.class);
            if (datos != null) {
                cuentas.addAll(datos.usuarios);
                cuentas.addAll(datos.administradores);
                cuentas.addAll(datos.repartidores);
            }
        } catch (IOException e) {
            System.out.println("No se pudo cargar: " + e.getMessage());
        }
        return cuentas;
    }
}
