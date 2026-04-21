package dao;

import database.Conexion;
import model.Venta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO {

    public int insertarVenta(Venta venta, Connection conn) throws SQLException {
        String sql = "INSERT INTO ventas (subtotal, iva, total) VALUES (?, ?, ?) RETURNING id";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setDouble(1, venta.getSubtotal());
        ps.setDouble(2, venta.getIva());
        ps.setDouble(3, venta.getTotal());

        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getInt(1);
        return -1;
    }

    public boolean insertarDetalle(int ventaId, int productoId, int cantidad, double precioUnitario, Connection conn) throws SQLException {
        String sql = "INSERT INTO detalle_venta (venta_id, producto_id, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, ventaId);
        ps.setInt(2, productoId);
        ps.setInt(3, cantidad);
        ps.setDouble(4, precioUnitario);
        return ps.executeUpdate() > 0;
    }

    public List<String[]> obtenerHistorial() {
        List<String[]> historial = new ArrayList<>();
        String sql = """
            SELECT v.id, v.fecha, p.nombre, dv.cantidad,
                   dv.precio_unitario, v.subtotal, v.iva, v.total
            FROM ventas v
            JOIN detalle_venta dv ON v.id = dv.venta_id
            JOIN productos p ON dv.producto_id = p.id
            ORDER BY v.fecha DESC
            """;

        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String[] fila = {
                    String.valueOf(rs.getInt("id")),
                    rs.getTimestamp("fecha").toString().substring(0, 16),
                    rs.getString("nombre"),
                    String.valueOf(rs.getInt("cantidad")),
                    "$" + String.format("%.2f", rs.getDouble("precio_unitario")),
                    "$" + String.format("%.2f", rs.getDouble("subtotal")),
                    "$" + String.format("%.2f", rs.getDouble("iva")),
                    "$" + String.format("%.2f", rs.getDouble("total"))
                };
                historial.add(fila);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener historial: " + e.getMessage());
        }
        return historial;
    }
}