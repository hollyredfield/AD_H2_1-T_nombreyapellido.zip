package bookworld;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.awt.Button;

import javafx.animation.FadeTransition;
import javafx.scene.effect.DropShadow;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LibroGUI extends Application {
    private final LibroDAO libroDAO = new LibroDAO();
    private final GeneradorReportes reportes = new GeneradorReportes(libroDAO);
    private VBox mainContainer;
    private GridPane formPane;

    @Override
    public void start(Stage primaryStage) {
        mainContainer = new VBox(20);
        mainContainer.setPadding(new Insets(20));
        mainContainer.setStyle("-fx-background-color: #f5f5f7;");

        // Header
        Label titleLabel = new Label("BookWorld");
        titleLabel.setFont(Font.font("SF Pro Display", FontWeight.BOLD, 28));
        titleLabel.setTextFill(Color.web("#1d1d1f"));

        // Main buttons container
        HBox buttonContainer = new HBox(15);
        buttonContainer.setPadding(new Insets(20));

        Button inventarioBtn = createStyledButton("Gestión de Inventario", "#0071e3");
        Button reportesBtn = createStyledButton("Consultas y Reportes", "#0071e3");

        buttonContainer.getChildren().addAll(inventarioBtn, reportesBtn);

        mainContainer.getChildren().addAll(titleLabel, buttonContainer);

        // Form container
        formPane = new GridPane();
        formPane.setHgap(10);
        formPane.setVgap(10);
        formPane.setPadding(new Insets(20));
        formPane.setStyle("-fx-background-color: white; -fx-background-radius: 10;");

        // Events
        inventarioBtn.setOnAction(e -> mostrarMenuInventario());
        reportesBtn.setOnAction(e -> mostrarMenuReportes());

        Scene scene = new Scene(mainContainer, 800, 600);

        primaryStage.setTitle("BookWorld");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Inicial animation
        FadeTransition ft = new FadeTransition(Duration.millis(1000), mainContainer);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

    private Button createStyledButton(String text, String color) {
        Button button = new Button(text);
        button.setStyle(String.format(
            "-fx-background-color: %s; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 14px; " +
            "-fx-padding: 10 20; " +
            "-fx-background-radius: 20;", color
        ));
        button.setEffect(new DropShadow(10, Color.rgb(0, 0, 0, 0.2)));
        return button;
    }

    private void mostrarMenuInventario() {
        formPane.getChildren().clear();
        mainContainer.getChildren().remove(formPane);

        VBox menuBox = new VBox(15);
        menuBox.setStyle("-fx-background-color: white; -fx-padding: 20; -fx-background-radius: 10;");

        Button agregarBtn = createStyledButton("Agregar Libro", "#34c759");
        Button actualizarBtn = createStyledButton("Actualizar Stock", "#5856d6");
        Button leerBtn = createStyledButton("Consultar Libro", "#ff9500");
        Button eliminarBtn = createStyledButton("Eliminar Libro", "#ff3b30");

        menuBox.getChildren().addAll(agregarBtn, actualizarBtn, leerBtn, eliminarBtn);

        agregarBtn.setOnAction(e -> mostrarFormularioAgregar());
        actualizarBtn.setOnAction(e -> mostrarFormularioActualizar());
        leerBtn.setOnAction(e -> mostrarFormularioConsultar());
        eliminarBtn.setOnAction(e -> mostrarFormularioEliminar());

        mainContainer.getChildren().add(menuBox);
        animateTransition(menuBox);
    }

    private void mostrarMenuReportes() {
        formPane.getChildren().clear();
        mainContainer.getChildren().remove(formPane);

        VBox menuBox = new VBox(15);
        menuBox.setStyle("-fx-background-color: white; -fx-padding: 20; -fx-background-radius: 10;");

        Button generarReporteBtn = createStyledButton("Generar Reporte de Inventario", "#0071e3");

        menuBox.getChildren().add(generarReporteBtn);

        generarReporteBtn.setOnAction(e -> generarReporteInventario());

        mainContainer.getChildren().add(menuBox);
        animateTransition(menuBox);
    }

    private void mostrarFormularioAgregar() {
        formPane.getChildren().clear();
        mainContainer.getChildren().remove(formPane);

        Label isbnLabel = new Label("ISBN:");
        TextField isbnField = new TextField();
        Label tituloLabel = new Label("Título:");
        TextField tituloField = new TextField();
        Label autorLabel = new Label("Autor:");
        TextField autorField = new TextField();
        Label idCategoriaLabel = new Label("ID Categoría:");
        TextField idCategoriaField = new TextField();
        Label precioLabel = new Label("Precio:");
        TextField precioField = new TextField();
        Label stockLabel = new Label("Stock:");
        TextField stockField = new TextField();
        Button agregarBtn = createStyledButton("Agregar", "#34c759");

        formPane.add(isbnLabel, 0, 0);
        formPane.add(isbnField, 1, 0);
        formPane.add(tituloLabel, 0, 1);
        formPane.add(tituloField, 1, 1);
        formPane.add(autorLabel, 0, 2);
        formPane.add(autorField, 1, 2);
        formPane.add(idCategoriaLabel, 0, 3);
        formPane.add(idCategoriaField, 1, 3);
        formPane.add(precioLabel, 0, 4);
        formPane.add(precioField, 1, 4);
        formPane.add(stockLabel, 0, 5);
        formPane.add(stockField, 1, 5);
        formPane.add(agregarBtn, 1, 6);

        agregarBtn.setOnAction(e -> {
            String isbn = isbnField.getText();
            String titulo = tituloField.getText();
            String autor = autorField.getText();
            int idCategoria = Integer.parseInt(idCategoriaField.getText());
            double precio = Double.parseDouble(precioField.getText());
            int stock = Integer.parseInt(stockField.getText());

            Libro libro = new Libro(isbn, titulo, autor, idCategoria, precio, stock);
            libroDAO.insertarLibro(libro);
            mostrarMensaje("Libro agregado correctamente.");
        });

        mainContainer.getChildren().add(formPane);
        animateTransition(formPane);
    }

    private void mostrarFormularioActualizar() {
        formPane.getChildren().clear();
        mainContainer.getChildren().remove(formPane);

        Label idLibroLabel = new Label("ID del Libro:");
        TextField idLibroField = new TextField();
        Label stockLabel = new Label("Nuevo Stock:");
        TextField stockField = new TextField();
        Button actualizarBtn = createStyledButton("Actualizar", "#5856d6");

        formPane.add(idLibroLabel, 0, 0);
        formPane.add(idLibroField, 1, 0);
        formPane.add(stockLabel, 0, 1);
        formPane.add(stockField, 1, 1);
        formPane.add(actualizarBtn, 1, 2);

        actualizarBtn.setOnAction(e -> {
            int idLibro = Integer.parseInt(idLibroField.getText());
            int nuevoStock = Integer.parseInt(stockField.getText());

            libroDAO.actualizarStock(idLibro, nuevoStock);
            mostrarMensaje("Stock actualizado correctamente.");
        });

        mainContainer.getChildren().add(formPane);
        animateTransition(formPane);
    }

    private void mostrarFormularioConsultar() {
        formPane.getChildren().clear();
        mainContainer.getChildren().remove(formPane);

        Label idLibroLabel = new Label("ID del Libro:");
        TextField idLibroField = new TextField();
        Button consultarBtn = createStyledButton("Consultar", "#ff9500");

        formPane.add(idLibroLabel, 0, 0);
        formPane.add(idLibroField, 1, 0);
        formPane.add(consultarBtn, 1, 1);

        consultarBtn.setOnAction(e -> {
            int idLibro = Integer.parseInt(idLibroField.getText());
            Libro libro = libroDAO.leerLibro(idLibro);
            if (libro != null) {
                mostrarMensaje("Detalles del libro:\n" +
                        "ISBN: " + libro.getIsbn() + "\n" +
                        "Título: " + libro.getTitulo() + "\n" +
                        "Autor: " + libro.getAutor() + "\n" +
                        "ID Categoría: " + libro.getIdCategoria() + "\n" +
                        "Precio: " + libro.getPrecio() + "\n" +
                        "Stock: " + libro.getStock());
            } else {
                mostrarMensaje("No se encontró un libro con el ID especificado.");
            }
        });

        mainContainer.getChildren().add(formPane);
        animateTransition(formPane);
    }

    private void mostrarFormularioEliminar() {
        formPane.getChildren().clear();
        mainContainer.getChildren().remove(formPane);

        Label idLibroLabel = new Label("ID del Libro:");
        TextField idLibroField = new TextField();
        Button eliminarBtn = createStyledButton("Eliminar", "#ff3b30");

        formPane.add(idLibroLabel, 0, 0);
        formPane.add(idLibroField, 1, 0);
        formPane.add(eliminarBtn, 1, 1);

        eliminarBtn.setOnAction(e -> {
            int idLibro = Integer.parseInt(idLibroField.getText());
            boolean eliminado = libroDAO.eliminarLibro(idLibro);
            if (eliminado) {
                mostrarMensaje("El libro se eliminó correctamente.");
            } else {
                mostrarMensaje("No se encontró un libro con el ID especificado.");
            }
        });

        mainContainer.getChildren().add(formPane);
        animateTransition(formPane);
    }

    private void generarReporteInventario() {
        reportes.generarReporteInventario("reporte_inventario.txt");
        mostrarMensaje("Reporte generado exitosamente.");
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void animateTransition(Pane pane) {
        FadeTransition ft = new FadeTransition(Duration.millis(500), pane);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}