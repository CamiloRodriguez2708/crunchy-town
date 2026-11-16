package main;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Cuenta;
import model.Usuario;
import persistence.CuentaRepository;
import service.CuentaService;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        String json = new Gson().toJson("Gson funciona!");
        System.out.println(json);
        stage.setScene(new Scene(new Label("Crunchy Town funciona!"), 300, 150));
        stage.setTitle("Crunchy Town");
        stage.show();
    }

    public static void main(String[] args) {
        CuentaRepository repository = new CuentaRepository();
        CuentaService cuentas = new CuentaService();
        cuentas.actualizarLista(repository.cargar());
        cuentas.agregar(new Usuario("12", "camilo", "hola", "camilo@gmail" ));
        repository.guardar(cuentas.getCuentas());
        launch(args);


    }
}