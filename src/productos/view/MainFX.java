package productos.view;

import enums.Marca;
import enums.CategoriaMercaderia;
import productos.models.GestionProductos;
import productos.models.Mercaderia;
import productos.models.Comida;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * Versión estilizada y reorganizada de MainFX
 * - Layout principal con BorderPane
 * - Panel lateral con formulario
 * - Barra superior tipo ToolBar
 * - Secciones con TitledPane
 * - Preparado para CSS externo
 */
public class MainFX extends Application {

    private GestionProductos gestion = new GestionProductos();

    // UI
    private ListView<Mercaderia> lstProductos = new ListView<>();

    private TextField txtId = new TextField();
    private TextField txtNombre = new TextField();
    private TextField txtPrecio = new TextField();
    private TextField txtStock = new TextField();
    private TextField txtPrecioMax = new TextField();

    private ComboBox<Marca> cboMarca = new ComboBox<>();
    private ComboBox<CategoriaMercaderia> cboCategoria = new ComboBox<>();

    private Label lblMensaje = new Label("Listo.");

    @Override
    public void start(Stage stage) {

        // =====================
        // Barra superior
        // =====================
        ToolBar toolBar = new ToolBar();
        Label titulo = new Label("Gestión de Productos – SUPERMERCADO.LOS.CHINARDOS");
        titulo.getStyleClass().add("titulo");
        toolBar.getItems().add(titulo);

        // =====================
        // Formulario lateral
        // =====================
        cboMarca.getItems().addAll(Marca.values());
        cboCategoria.getItems().addAll(CategoriaMercaderia.values());
        cboMarca.getSelectionModel().selectFirst();
        cboCategoria.getSelectionModel().selectFirst();

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(10));

        form.add(new Label("ID"), 0, 0);
        form.add(txtId, 1, 0);
        form.add(new Label("Nombre"), 0, 1);
        form.add(txtNombre, 1, 1);
        form.add(new Label("Precio"), 0, 2);
        form.add(txtPrecio, 1, 2);
        form.add(new Label("Stock"), 0, 3);
        form.add(txtStock, 1, 3);
        form.add(new Label("Marca"), 0, 4);
        form.add(cboMarca, 1, 4);
        form.add(new Label("Categoría"), 0, 5);
        form.add(cboCategoria, 1, 5);

        Button btnAgregar = new Button("Agregar");
        Button btnActualizar = new Button("Actualizar");
        Button btnEliminar = new Button("Eliminar");

        btnAgregar.setOnAction(e -> agregarProducto());
        btnActualizar.setOnAction(e -> actualizarProducto());
        btnEliminar.setOnAction(e -> eliminarProducto());

        HBox boxCrud = new HBox(10, btnAgregar, btnActualizar, btnEliminar);
        boxCrud.setAlignment(Pos.CENTER);

        VBox panelFormulario = new VBox(15, form, boxCrud);
        panelFormulario.setPadding(new Insets(10));

        TitledPane tpFormulario = new TitledPane("Producto", panelFormulario);
        tpFormulario.setCollapsible(false);

        // =====================
        // Centro – lista
        // =====================
        lstProductos.setPrefHeight(300);
        refrescarLista();

        lstProductos.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
            if (n != null) {
                txtId.setText(String.valueOf(n.getId()));
                txtNombre.setText(n.getNombre());
                txtPrecio.setText(String.valueOf(n.getPrecio()));
                txtStock.setText(String.valueOf(n.getStock()));
                cboMarca.setValue(n.getMarca());
                cboCategoria.setValue(n.getCategoria());
            }
        });

        TitledPane tpLista = new TitledPane("Listado de productos", lstProductos);
        tpLista.setCollapsible(false);

        // =====================
        // Panel inferior – acciones
        // =====================
        Button btnOrdenNombre = new Button("Nombre");
        Button btnOrdenPrecio = new Button("Precio");
        Button btnOrdenStock = new Button("Stock");

        btnOrdenNombre.setOnAction(e -> { gestion.ordenarPorNombre(); refrescarLista(); });
        btnOrdenPrecio.setOnAction(e -> { gestion.ordenarPorPrecio(); refrescarLista(); });
        btnOrdenStock.setOnAction(e -> { gestion.ordenarPorStock(); refrescarLista(); });

        HBox boxOrden = new HBox(10, new Label("Ordenar:"), btnOrdenNombre, btnOrdenPrecio, btnOrdenStock);
        boxOrden.setAlignment(Pos.CENTER_LEFT);

        Button btnFiltrar = new Button("Filtrar");
        btnFiltrar.setOnAction(e -> filtrarPorPrecio());

        HBox boxFiltro = new HBox(10, new Label("Precio máx"), txtPrecioMax, btnFiltrar);
        boxFiltro.setAlignment(Pos.CENTER_LEFT);

        Button btnCsv = new Button("CSV");
        Button btnJson = new Button("JSON");
        Button btnTxt = new Button("TXT");

        btnCsv.setOnAction(e -> guardarCsv(stage));
        btnJson.setOnAction(e -> guardarJson(stage));
        btnTxt.setOnAction(e -> exportarTxt(stage));

        HBox boxPersist = new HBox(10, new Label("Exportar:"), btnCsv, btnJson, btnTxt);
        boxPersist.setAlignment(Pos.CENTER_LEFT);

        VBox bottom = new VBox(10, boxOrden, boxFiltro, boxPersist, lblMensaje);
        bottom.setPadding(new Insets(10));

        // =====================
        // Root
        // =====================
        BorderPane root = new BorderPane();
        root.setTop(toolBar);
        root.setLeft(tpFormulario);
        root.setCenter(tpLista);
        root.setBottom(bottom);

        Scene scene = new Scene(root, 900, 600);

        var css = getClass().getResource("estilos.css");

        if (css != null) {
            scene.getStylesheets().add(css.toExternalForm());
            System.out.println("CSS cargado correctamente");
        } else {
            System.out.println("NO se encontró estilos.css");
        }

        stage.setTitle("Gestión de Productos");
        stage.setScene(scene);
        stage.show();
    }

    // =====================
    // Lógica
    // =====================
    private void refrescarLista() {
        ObservableList<Mercaderia> datos = FXCollections.observableArrayList(gestion.leerTodos());
        lstProductos.setItems(datos);
    }

    private void agregarProducto() {
        try {
            Comida s = new Comida(
                    Integer.parseInt(txtId.getText()),
                    txtNombre.getText(),
                    cboMarca.getValue(),
                    Double.parseDouble(txtPrecio.getText()),
                    Integer.parseInt(txtStock.getText())
            );
            s.setCategoria(cboCategoria.getValue());

            if (gestion.crear(s)) {
                refrescarLista();
                lblMensaje.setText("Producto agregado");
            } else lblMensaje.setText("ID duplicado");

        } catch (Exception e) {
            lblMensaje.setText("Datos inválidos");
        }
    }

    private void actualizarProducto() {
        try {
            Comida s = new Comida(
                    Integer.parseInt(txtId.getText()),
                    txtNombre.getText(),
                    cboMarca.getValue(),
                    Double.parseDouble(txtPrecio.getText()),
                    Integer.parseInt(txtStock.getText())
            );
            s.setCategoria(cboCategoria.getValue());

            if (gestion.actualizar(s)) {
                refrescarLista();
                lblMensaje.setText("Producto actualizado");
            } else lblMensaje.setText("No encontrado");

        } catch (Exception e) {
            lblMensaje.setText("Datos inválidos");
        }
    }

    private void eliminarProducto() {
        try {
            if (gestion.eliminarPorId(Integer.parseInt(txtId.getText()))) {
                refrescarLista();
                lblMensaje.setText("Producto eliminado");
            } else lblMensaje.setText("No encontrado");
        } catch (Exception e) {
            lblMensaje.setText("ID inválido");
        }
    }

    private void filtrarPorPrecio() {
        try {
            double max = Double.parseDouble(txtPrecioMax.getText());
            lstProductos.setItems(FXCollections.observableArrayList(gestion.filtrarPorPrecioMax(max)));
        } catch (Exception e) {
            lblMensaje.setText("Precio inválido");
        }
    }

    private void guardarCsv(Stage stage) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
        File f = fc.showSaveDialog(stage);
        if (f != null) gestion.guardarCsv(f.getAbsolutePath());
    }

    private void guardarJson(Stage stage) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        File f = fc.showSaveDialog(stage);
        if (f != null) gestion.guardarJson(f.getAbsolutePath());
    }

    private void exportarTxt(Stage stage) {
        try {
            double max = Double.parseDouble(txtPrecioMax.getText());
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT", "*.txt"));
            File f = fc.showSaveDialog(stage);
            if (f != null) gestion.exportarTxtFiltrado(f.getAbsolutePath(), max);
        } catch (Exception e) {
            lblMensaje.setText("Precio inválido");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

/*
==================== estilos.css ====================
.root {
    -fx-font-family: "Segoe UI";
    -fx-background-color: #f4f6f8;
}
.titulo {
    -fx-font-size: 18px;
    -fx-font-weight: bold;
}
.button {
    -fx-background-radius: 6;
}
.titled-pane > .title {
    -fx-background-color: #e0e0e0;
}
.list-view {
    -fx-background-radius: 6;
}
====================================================
*/

