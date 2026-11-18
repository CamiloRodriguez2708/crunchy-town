package persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Acompanamiento;
import model.Bebida;
import model.Comida;
import model.Producto;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepository {
    private static final String ruta = "src/persistence/productos.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void guardar(List<Producto> productos) {
        DatosProductos datos = new DatosProductos();
        for (Producto p : productos) {
            if (p instanceof Comida) {
                datos.comidas.add((Comida) p);
            } else if (p instanceof Bebida) {
                datos.bebidas.add((Bebida) p);
            } else if (p instanceof Acompanamiento) {
                datos.acompanamientos.add((Acompanamiento) p);
            }
        }
        try (FileWriter writer = new FileWriter(ruta)) {
            gson.toJson(datos, writer);
        } catch (IOException e) {
            System.out.println("No se pudo guardar el menú: " + e.getMessage());
        }
    }

    public List<Producto> cargar() {
        List<Producto> productos = new ArrayList<>();
        File archivo = new File(ruta);
        if (!archivo.exists()) {
            return productos;
        }
        try (FileReader reader = new FileReader(archivo)) {
            DatosProductos datos = gson.fromJson(reader, DatosProductos.class);
            if (datos != null) {
                productos.addAll(datos.comidas);
                productos.addAll(datos.bebidas);
                productos.addAll(datos.acompanamientos);
            }
        } catch (IOException e) {
            System.out.println("No se pudo cargar el menú: " + e.getMessage());
        }
        return productos;
    }
}