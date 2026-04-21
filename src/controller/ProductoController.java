package controller;

import dao.ProductoDAO;
import model.Producto;
import java.util.List;

public class ProductoController {

    private ProductoDAO dao = new ProductoDAO();

    public List<Producto> obtenerTodos() {
        return dao.obtenerTodos();
    }

    public String registrar(String nombre, String descripcion, String precioStr, String stockStr) {

        if (nombre == null || nombre.trim().isEmpty()) {
            return "ERROR: El nombre no puede estar vacío.";
        }
        if (precioStr == null || precioStr.trim().isEmpty()) {
            return "ERROR: El precio no puede estar vacío.";
        }
        if (stockStr == null || stockStr.trim().isEmpty()) {
            return "ERROR: El stock no puede estar vacío.";
        }

        double precio;
        try {
            precio = Double.parseDouble(precioStr.trim());
            if (precio <= 0) {
                return "ERROR: El precio debe ser mayor a 0.";
            }
        } catch (NumberFormatException e) {
            return "ERROR: El precio debe ser un número válido (ej: 24.50).";
        }

        int stock;
        try {
            stock = Integer.parseInt(stockStr.trim());
            if (stock < 0) {
                return "ERROR: El stock no puede ser negativo.";
            }
        } catch (NumberFormatException e) {
            return "ERROR: El stock debe ser un número entero (ej: 50).";
        }

        Producto p = new Producto(0, nombre.trim(), descripcion.trim(), precio, stock);
        boolean exito = dao.insertar(p);

        if (exito) {
            return "El producto se guardo en la base de datos";
        } else {
            return "No se pudo guardar en la base de datos.";
        }
    }

 }
