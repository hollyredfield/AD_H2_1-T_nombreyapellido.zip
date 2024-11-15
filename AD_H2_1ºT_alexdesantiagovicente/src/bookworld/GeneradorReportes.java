package bookworld;

import java.io.*;
import java.util.List;

public class GeneradorReportes {
    private final LibroDAO libroDAO;

    public GeneradorReportes(LibroDAO libroDAO) {
        this.libroDAO = libroDAO;
    }

    public void generarReporteInventario(String rutaArchivo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(rutaArchivo))) {
            List<Libro> libros = libroDAO.obtenerTodos();
            
            writer.println("REPORTE DE INVENTARIO - BOOKWORLD");
            writer.println("=====================================");
            writer.printf("%-13s %-40s %-25s %-10s %-8s%n", 
                        "ISBN", "TÍTULO", "AUTOR", "PRECIO", "STOCK");
            writer.println("=====================================");
            
            for (Libro libro : libros) {
                writer.printf("%-13s %-40s %-25s %-10.2f %-8d%n",
                    libro.getIsbn(),
                    libro.getTitulo(),
                    libro.getAutor(),
                    libro.getPrecio(),
                    libro.getStock());
            }
            
            writer.println("\nTotal de libros: " + libros.size());
            System.out.println("Reporte generado en: " + rutaArchivo);
        } catch (IOException e) {
            GestorErrores.logError("Error al generar reporte", e);
        }
    }
}
