package ni.uam.edu.practicamenu.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ni.uam.edu.practicamenu.dao.MenuDao;
import ni.uam.edu.practicamenu.modelo.Producto;

public class MenuController {

    // Campos de texto
    @FXML
    private TextField txtID;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtCategoria;

    // Tabla
    @FXML
    private TableView<Producto> tblProductos;

    @FXML
    private TableColumn<Producto, String> colID;

    @FXML
    private TableColumn<Producto, String> colNombre;

    @FXML
    private TableColumn<Producto, String> colCategoria;

    @FXML
    private TableColumn<Producto, Double> colPrecio;

    // Botones
    @FXML
    private Button btnNuevoProducto;

    @FXML
    private Button btnGuardarProducto;

    @FXML
    private Button btnEditarProducto;

    @FXML
    private Button eliminarProducto;

    // DAO
    private final MenuDao menuDao = new MenuDao();

    // Inicialización
    @FXML
    public void initialize() {

        configurarTabla();

        tblProductos.setItems(menuDao.obtenerProductos());

        tblProductos.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, anterior, actual) -> {

                    if (actual != null) {
                        cargarProducto(actual);
                    }
                });
    }

    // Configuración de las columnas
    private void configurarTabla() {

        colID.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );

        colNombre.setCellValueFactory(
                new PropertyValueFactory<>("nombre")
        );

        colCategoria.setCellValueFactory(
                new PropertyValueFactory<>("categoria")
        );

        colPrecio.setCellValueFactory(
                new PropertyValueFactory<>("precio")
        );
    }

    // Nuevo
    @FXML
    private void nuevoProducto() {

        limpiar();

        txtID.requestFocus();
    }

    // Guardar
    @FXML
    private void guardarProducto() {

        String id = txtID.getText().trim();
        String nombre = txtNombre.getText().trim();
        String precioTexto = txtPrecio.getText().trim();
        String categoria = txtCategoria.getText().trim();

        if (id.isEmpty() || nombre.isEmpty()
                || precioTexto.isEmpty() || categoria.isEmpty()) {

            mostrarMensaje(
                    Alert.AlertType.WARNING,
                    "Datos incompletos",
                    "Debe llenar todos los campos."
            );

            return;
        }

        double precio;

        try {

            precio = Double.parseDouble(precioTexto);

        } catch (NumberFormatException e) {

            mostrarMensaje(
                    Alert.AlertType.ERROR,
                    "Precio incorrecto",
                    "El precio debe ser un número."
            );

            return;
        }

        if (menuDao.buscarProducto(id) != null) {

            mostrarMensaje(
                    Alert.AlertType.WARNING,
                    "Producto existente",
                    "Ya existe un producto con ese ID."
            );

            return;
        }

        Producto producto = new Producto(
                id,
                nombre,
                categoria,
                precio
        );

        menuDao.guardarProducto(producto);

        tblProductos.refresh();

        limpiar();

        mostrarMensaje(
                Alert.AlertType.INFORMATION,
                "Producto guardado",
                "El producto se guardó correctamente."
        );
    }

    // Editar
    @FXML
    private void editarProducto() {

        Producto productoSeleccionado =
                tblProductos.getSelectionModel().getSelectedItem();

        if (productoSeleccionado == null) {

            mostrarMensaje(
                    Alert.AlertType.WARNING,
                    "Editar producto",
                    "Seleccione un producto de la tabla."
            );

            return;
        }

        String nombre = txtNombre.getText().trim();
        String precioTexto = txtPrecio.getText().trim();
        String categoria = txtCategoria.getText().trim();

        if (nombre.isEmpty()
                || precioTexto.isEmpty()
                || categoria.isEmpty()) {

            mostrarMensaje(
                    Alert.AlertType.WARNING,
                    "Datos incompletos",
                    "Debe llenar todos los campos."
            );

            return;
        }

        double precio;

        try {

            precio = Double.parseDouble(precioTexto);

        } catch (NumberFormatException e) {

            mostrarMensaje(
                    Alert.AlertType.ERROR,
                    "Precio incorrecto",
                    "El precio debe ser un número."
            );

            return;
        }

        productoSeleccionado.setNombre(nombre);
        productoSeleccionado.setPrecio(precio);
        productoSeleccionado.setCategoria(categoria);

        tblProductos.refresh();

        limpiar();

        mostrarMensaje(
                Alert.AlertType.INFORMATION,
                "Producto editado",
                "El producto se actualizó correctamente."
        );
    }

    // Eliminar
    @FXML
    private void eliminarProducto() {

        Producto productoSeleccionado =
                tblProductos.getSelectionModel().getSelectedItem();

        if (productoSeleccionado == null) {

            mostrarMensaje(
                    Alert.AlertType.WARNING,
                    "Eliminar producto",
                    "Seleccione un producto de la tabla."
            );

            return;
        }

        menuDao.eliminarProducto(productoSeleccionado);

        limpiar();

        mostrarMensaje(
                Alert.AlertType.INFORMATION,
                "Producto eliminado",
                "El producto se eliminó correctamente."
        );
    }

    // Limpiar
    @FXML
    private void limpiar() {

        txtID.clear();
        txtNombre.clear();
        txtPrecio.clear();
        txtCategoria.clear();

        tblProductos.getSelectionModel().clearSelection();
    }

    // Cargar producto seleccionado
    private void cargarProducto(Producto producto) {

        txtID.setText(producto.getId());
        txtNombre.setText(producto.getNombre());
        txtPrecio.setText(String.valueOf(producto.getPrecio()));
        txtCategoria.setText(producto.getCategoria());
    }

    // Salir
    @FXML
    private void salir() {

        Stage stage = (Stage) tblProductos.getScene().getWindow();

        stage.close();
    }

    // Acerca de
    @FXML
    private void acerca() {

        mostrarMensaje(
                Alert.AlertType.INFORMATION,
                "Acerca de...",
                "Hola!, Bienvenido a Distribuidora Gueguense" +
                        "Calidad y Cultura!" +
                        "Usa los botones y campos para registrarte" +
                        "Y luego puedes ver la info en esa tabla de abajo!\n"
                        + "Creado por Alfredo Montealegre."
        );
    }

    // Mensajes
    private void mostrarMensaje(
            Alert.AlertType tipo,
            String titulo,
            String mensaje) {

        Alert alert = new Alert(tipo);

        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        alert.showAndWait();
    }
}