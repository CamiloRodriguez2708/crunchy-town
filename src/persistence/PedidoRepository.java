package persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PedidoRepository {
    private static final String ruta = "src/persistence/pedidos.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void guardar(List<Pedido> pedidos) {
        DatosPedidos datos = new DatosPedidos();
        for (Pedido p : pedidos) {
            datos.pedidos.add(p);
        }
        try (FileWriter writer = new FileWriter(ruta)) {
            gson.toJson(datos, writer);
        } catch (IOException e) {
            System.out.println("No se pudo guardar: " + e.getMessage());
        }
    }

    public List<Pedido> cargar() {
        List<Pedido> pedidos = new ArrayList<>();
        File archivo = new File(ruta);
        if (!archivo.exists()) {
            return pedidos;
        }
        try (FileReader reader = new FileReader(archivo)) {
            DatosPedidos datos = gson.fromJson(reader, DatosPedidos.class);
            if (datos != null) {
                pedidos.addAll(datos.pedidos);
            }
        } catch (IOException e) {
            System.out.println("No se pudo cargar: " + e.getMessage());
        }
        return pedidos;
    }
}
