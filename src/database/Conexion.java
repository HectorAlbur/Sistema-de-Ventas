package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conexion {
    
    private static final String URL = "jdbc:postgresql://localhost:5432/supermercado_db";
    private static final String USUARIO = "postgres";
    private static final String PASSWORD = "admin1234";
    
    public static Connection getConexion() {
        Connection conn = null;
        try {
            // Cargar el driver JDBC
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(URL, USUARIO, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver no encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
        return conn;
    }
}