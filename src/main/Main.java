package main;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

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
        launch(args);
    }
}