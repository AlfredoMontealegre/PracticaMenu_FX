package ni.uam.edu.practicamenu.dao;

import ni.uam.edu.practicamenu.modelo.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MenuDao {

    private final ObservableList<Producto> productos;

    public MenuDao() {
        productos = FXCollections.observableArrayList();
    }

    // Obtener productos
    public ObservableList<Producto> obtenerProductos() {
        return productos;
    }

    // Guardar producto
    public void guardarProducto(Producto producto) {
        productos.add(producto);
    }

    // Eliminar producto
    public void eliminarProducto(Producto producto) {
        productos.remove(producto);
    }

    // Buscar producto por ID
    public Producto buscarProducto(String id) {

        for (Producto producto : productos) {

            if (producto.getId().equals(id)) {
                return producto;
            }
        }

        return null;
    }
}