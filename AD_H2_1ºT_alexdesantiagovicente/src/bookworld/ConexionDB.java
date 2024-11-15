// ConexionDB.java
package bookworld;

import java.sql.*;

public class ConexionDB {
    private static ConexionDB instancia;
    private Connection conexion;
    
    private static final String URL = "jdbc:mysql://localhost:3306/bookworld";
    private static final String USUARIO = "root";
    private static final String CLAVE = "";

    private ConexionDB() {}

    public static ConexionDB obtenerInstancia() {
        if (instancia == null) {
            instancia = new ConexionDB();
        }
        return instancia;
    }

    public Connection obtenerConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);
                System.out.println("Conexión exitosa.");
            }
        } catch (SQLException e) {
            System.err.println("Error en la conexión: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return conexion;
    }

    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexión cerrada.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}