package utils;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Optional;

public class Alertas {

    public static void error(String mensaje) {
        mostrar("Error", mensaje, "alerta_error.png", "alerta-error");
    }

    public static void info(String mensaje) {
        mostrar("Aviso", mensaje, "alerta_info.png", "alerta-info");
    }

    public static void factura(String mensaje) {
        mostrar("Factura", mensaje, "alerta_info.png", "alerta-info");
    }

    public static Optional<String> elegirMetodoPago(List<String> metodos) {
        Dialog<String> dialogo = new Dialog<>();
        ButtonType aceptar = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

        ComboBox<String> selector = new ComboBox<>();
        selector.getItems().addAll(metodos);
        selector.setMaxWidth(Double.MAX_VALUE);
        if (!metodos.isEmpty()) {
            selector.setValue(metodos.get(0));
        }
        selector.setStyle(estiloCampo());

        DialogPane pane = prepararDialogo(dialogo, "Método de pago",
                "Selecciona cómo quieres pagar", filaCampo("Método de pago:", selector));
        pane.getButtonTypes().addAll(aceptar, cancelar);
        dialogo.setResultConverter(boton -> boton == aceptar ? selector.getValue() : null);
        return dialogo.showAndWait();
    }

    // Diálogo para pedir la dirección. Devuelve vacío si el usuario cancela.
    public static Optional<String> solicitarDireccion() {
        Dialog<String> dialogo = new Dialog<>();
        ButtonType aceptar = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

        TextField direccion = new TextField();
        direccion.setPromptText("Calle, número, barrio y referencias");
        direccion.setMaxWidth(Double.MAX_VALUE);
        direccion.setStyle(estiloCampo());

        DialogPane pane = prepararDialogo(dialogo, "Ubicación de entrega",
                "¿Dónde quieres recibir tu pedido?", filaCampo("Dirección:", direccion));
        pane.getButtonTypes().addAll(aceptar, cancelar);
        dialogo.setResultConverter(boton -> boton == aceptar ? direccion.getText() : null);
        return dialogo.showAndWait();
    }

    private static DialogPane prepararDialogo(Dialog<String> dialogo, String titulo,
                                               String mensaje, Node campos) {
        dialogo.setTitle(titulo);
        DialogPane pane = dialogo.getDialogPane();
        pane.setContent(contenidoDialogo(titulo, mensaje, campos));
        pane.setHeaderText(null);
        pane.setGraphic(null);
        pane.getStylesheets().add(
                Alertas.class.getResource("/ui/css/alertas.css").toExternalForm());
        pane.getStyleClass().add("alerta-info");
        pane.setPrefWidth(430);
        return pane;
    }

    private static HBox contenidoDialogo(String titulo, String mensaje, Node campos) {
        ImageView imgIcono = icono("alerta_info.png");

        Label lblTitulo = new Label(titulo);
        lblTitulo.getStyleClass().add("alerta-titulo");
        Label lblMensaje = new Label(mensaje);
        lblMensaje.getStyleClass().add("alerta-mensaje");
        lblMensaje.setWrapText(true);
        lblMensaje.setMaxWidth(300);

        VBox texto = new VBox(4, lblTitulo, lblMensaje, campos);
        HBox contenido = new HBox(14, imgIcono, texto);
        contenido.setAlignment(Pos.CENTER_LEFT);
        return contenido;
    }

    private static HBox filaCampo(String etiqueta, Node campo) {
        Label label = new Label(etiqueta);
        label.getStyleClass().add("alerta-mensaje");
        HBox fila = new HBox(10, label, campo);
        fila.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(campo, Priority.ALWAYS);
        return fila;
    }

    private static String estiloCampo() {
        return "-fx-background-color: #fffdfa; -fx-border-color: #eadfd7;"
                + " -fx-border-radius: 10; -fx-background-radius: 10;"
                + " -fx-padding: 8 10; -fx-font-family: 'Carlito'; -fx-font-size: 14px;";
    }

    private static ImageView icono(String imagen) {
        Image img = new Image(
                Alertas.class.getResourceAsStream("/utils/imagenes/" + imagen));
        ImageView imgIcono = new ImageView(img);
        imgIcono.setFitWidth(40);
        imgIcono.setFitHeight(40);
        imgIcono.setPreserveRatio(true);
        return imgIcono;
    }

    private static void mostrar(String titulo, String mensaje,
                                String imagen, String clase) {

        Alert alerta = new Alert(Alert.AlertType.NONE, null,
                new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE));

        alerta.setTitle(titulo);

        ImageView imgIcono = icono(imagen);

        Label lblTitulo = new Label(titulo);
        lblTitulo.getStyleClass().add("alerta-titulo");

        Label lblMensaje = new Label(mensaje);
        lblMensaje.getStyleClass().add("alerta-mensaje");
        lblMensaje.setWrapText(true);
        lblMensaje.setMaxWidth(300);

        HBox contenido = new HBox(
                14,
                imgIcono,
                new VBox(3, lblTitulo, lblMensaje)
        );

        contenido.setAlignment(Pos.CENTER_LEFT);

        DialogPane pane = alerta.getDialogPane();
        pane.setContent(contenido);
        pane.setHeaderText(null);
        pane.setGraphic(null);

        pane.getStylesheets().add(
                Alertas.class.getResource("/ui/css/alertas.css").toExternalForm()
        );

        pane.getStyleClass().add(clase);
        pane.setPrefWidth(430);

        alerta.showAndWait();
    }
}
