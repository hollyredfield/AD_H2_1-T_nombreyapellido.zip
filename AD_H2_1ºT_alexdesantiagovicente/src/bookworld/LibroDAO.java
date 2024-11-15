package bookworld;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {

    // Método actualizado para leer un libro e imprimir sus detalles
    public Libro leerLibro(int idLibro) {
        String sql = "SELECT * FROM libros WHERE id_libro = ?";
        try (Connection conn = ConexionDB.obtenerInstancia().obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idLibro);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Crear un objeto Libro con los datos obtenidos
                Libro libro = new Libro(
                    rs.getString("isbn"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getInt("id_categoria"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                );
                
                // Imprimir los detalles del libro en consola
                System.out.println("Detalles del libro:");
                System.out.println("ISBN: " + libro.getIsbn());
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Autor: " + libro.getAutor());
                System.out.println("ID Categoría: " + libro.getIdCategoria());
                System.out.println("Precio: " + libro.getPrecio());
                System.out.println("Stock: " + libro.getStock());
                
                return libro; // Devolver el objeto Libro
            } else {
                System.out.println("No se encontró un libro con ese ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean eliminarLibro(int idLibro) {
        String sql = "DELETE FROM libros WHERE id_libro = ?";
        try (Connection conn = ConexionDB.obtenerInstancia().obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idLibro);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void insertarLibro(Libro libro) {
        String sql = "INSERT INTO libros (isbn, titulo, autor, id_categoria, precio, stock) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.obtenerInstancia().obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, libro.getIsbn());
            stmt.setString(2, libro.getTitulo());
            stmt.setString(3, libro.getAutor());
            stmt.setInt(4, libro.getIdCategoria());
            stmt.setDouble(5, libro.getPrecio());
            stmt.setInt(6, libro.getStock());
            stmt.executeUpdate();
            System.out.println("Libro insertado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarStock(int idLibro, int nuevoStock) {
        String sql = "UPDATE libros SET stock = ? WHERE id_libro = ?";
        try (Connection conn = ConexionDB.obtenerInstancia().obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, nuevoStock);
            stmt.setInt(2, idLibro);
            stmt.executeUpdate();
            System.out.println("Stock actualizado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para obtener todos los libros
    public List<Libro> obtenerTodos() {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libros";
        try (Connection conn = ConexionDB.obtenerInstancia().obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                libros.add(new Libro(
                    rs.getString("isbn"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getInt("id_categoria"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libros;
    }
}
