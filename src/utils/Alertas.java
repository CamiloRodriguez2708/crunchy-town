package utils;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Alertas {
    public static void error(String mensaje) {
        mostrar("Error", mensaje, "\uF8B6", "#d62828", "alerta-error");
    }

    public static void info(String mensaje) {
        mostrar("Aviso", mensaje, "\uE88E", "#ffbf47", "alerta-info");
    }

    private static void mostrar(String titulo, String mensaje,
                                String icono, String color, String clase) {
        Alert alerta = new Alert(Alert.AlertType.NONE, null,
                new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE));
        alerta.setTitle(titulo);

        Label lblIcono = new Label(icono);
        lblIcono.setMinSize(40, 40);
        lblIcono.setPrefSize(40, 40);
        lblIcono.setMaxSize(40, 40);
        lblIcono.setAlignment(Pos.CENTER);
        lblIcono.setStyle("-fx-font-family: 'Material Symbols Rounded'; -fx-font-size: 22px;"
                + " -fx-text-fill: white; -fx-background-color: " + color + ";"
                + " -fx-background-radius: 20;");

        Label lblTitulo = new Label(titulo);
        lblTitulo.getStyleClass().add("alerta-titulo");

        Label lblMensaje = new Label(mensaje);
        lblMensaje.getStyleClass().add("alerta-mensaje");
        lblMensaje.setWrapText(true);
        lblMensaje.setMaxWidth(300);

        HBox contenido = new HBox(14, lblIcono, new VBox(3, lblTitulo, lblMensaje));
        contenido.setAlignment(Pos.CENTER_LEFT);

        DialogPane pane = alerta.getDialogPane();
        pane.setContent(contenido);
        pane.setHeaderText(null);
        pane.setGraphic(null);
        pane.getStylesheets().add(
                Alertas.class.getResource("/ui/css/alertas.css").toExternalForm());
        pane.getStyleClass().add(clase);
        pane.setPrefWidth(430);

        alerta.showAndWait();
    }
}
