package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import persistence.CuentaRepository;
import service.CuentaService;
import service.MenuService;

import java.io.IOException;

public class Main extends Application {
    private static Stage ventana;
    private static CuentaService cuentaService;
    private static MenuService menuService;


    @Override
    public void start(Stage stage) {
        ventana = stage;
        stage.setTitle("Crunchy Town");
        stage.setResizable(false);
        cambiarPantalla("/ui/fxml/Login.fxml");
        stage.show();
    }

    public static void cambiarPantalla(String ruta) {
        try {
            Parent raiz = FXMLLoader.load(Main.class.getResource(ruta));
            ventana.setScene(new Scene(raiz));
        } catch (IOException e) {
            System.out.println("No se pudo abrir " + ruta);
            e.printStackTrace();
        }
    }

    public static CuentaService getCuentaService() {
        return cuentaService;
    }

    public static MenuService getMenuService() {
        return menuService;
    }

    public static void main(String[] args) {
        CuentaRepository repository = new CuentaRepository();
        cuentaService = new CuentaService();
        cuentaService.actualizarLista(repository.cargar());
        menuService = new MenuService();
        launch(args);
    }
}