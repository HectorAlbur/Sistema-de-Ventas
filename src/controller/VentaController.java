package controller;

import dao.ProductoDAO;
import dao.VentaDAO;
import model.Producto;
import model.Venta;
import database.Conexion;

import java.sql.Connection;
import java.util.List;

public class VentaController {

    private VentaDAO ventaDAO = new VentaDAO();
    private ProductoDAO productoDAO = new ProductoDAO();

    public String registrarVenta(Producto producto, String cantidadStr) {

        if (producto == null) {
            return "ERROR: Debes seleccionar un producto.";
        }

        if (cantidadStr == null || cantidadStr.trim().isEmpty()) {
            return "ERROR: La cantidad no puede estar vacía.";
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(cantidadStr.trim());
            if (cantidad <= 0) {
                return "ERROR: La cantidad debe ser mayor a 0.";
            }
        } catch (NumberFormatException e) {
            return "ERROR: La cantidad debe ser un número entero.";
        }

        if (cantidad > producto.getStock()) {
            return "ERROR: Stock insuficiente. Disponible: " + producto.getStock();
        }

        Venta venta = new Venta(producto.getPrecio() * cantidad);

        Connection conn = null;
        try {
            conn = Conexion.getConexion();
            conn.setAutoCommit(false);

            // 1. Insertar la venta
            int ventaId = ventaDAO.insertarVenta(venta, conn);
            if (ventaId == -1) {
                conn.rollback();
                return "ERROR: No se pudo registrar la venta.";
            }

            // 2. Insertar el detalle
            ventaDAO.insertarDetalle(ventaId, producto.getId(), cantidad, producto.getPrecio(), conn);

            // 3. Descontar el stock
            productoDAO.actualizarStock(producto.getId(), cantidad, conn);

            conn.commit(); 
            return "OK";

        } catch (Exception e) {
            try { if (conn != null) conn.rollback(); } catch (Exception ex) {}
            return "ERROR: " + e.getMessage();
        }
    }

    public List<String[]> obtenerHistorial() {
        return ventaDAO.obtenerHistorial();
    }
}