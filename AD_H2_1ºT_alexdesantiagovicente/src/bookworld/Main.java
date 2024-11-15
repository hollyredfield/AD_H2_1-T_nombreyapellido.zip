package bookworld;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final LibroDAO libroDAO = new LibroDAO();
    private static final GeneradorReportes reportes = new GeneradorReportes(libroDAO);

    public static void main(String[] args) {
        try {
            mostrarMenuPrincipal();
        } finally {
            ConexionDB.obtenerInstancia().cerrarConexion();
            scanner.close();
        }
    }

    private static void mostrarMenuPrincipal() {
        while (true) {
            System.out.println("\nBienvenido a BookWorld");
            System.out.println("1. Gestión de Inventario");
            System.out.println("2. Consultas y Reportes");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    gestionInventario();
                    break;
                case 2:
                    gestionReportes();
                    break;
                case 3:
                    System.out.println("¡Gracias por usar BookWorld!");
                    return;
                default:
                    System.out.println("Opción no válida");
            }
        }
    }

    private static void gestionInventario() {
        System.out.println("\nGestión de Inventario");
        System.out.println("1. Agregar Libro");
        System.out.println("2. Actualizar Stock");
        System.out.println("3. Leer Libro");
        System.out.println("4. Eliminar Libro");
        System.out.print("Seleccione una opción: ");

        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        switch (opcion) {
            case 1:
                agregarLibro();
                break;
            case 2:
                actualizarStock();
                break;
            case 3:
                leerLibro();
                break;
            case 4:
                eliminarLibro();
                break;
            default:
                System.out.println("Opción no válida");
        }
    }

    private static void agregarLibro() {
        System.out.println("\nIngrese los datos del libro:");

        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        System.out.print("Título: ");
        String titulo = scanner.nextLine();

        System.out.print("Autor: ");
        String autor = scanner.nextLine();

        System.out.print("ID Categoría: ");
        int idCategoria = scanner.nextInt();

        double precio;
        while (true) {
            System.out.print("Precio: ");
            if (scanner.hasNextDouble()) {
                precio = scanner.nextDouble();
                scanner.nextLine(); // Limpiar buffer
                break;
            } else {
                System.out.println("Por favor, ingrese un valor válido para el precio.");
                scanner.next(); // Limpiar entrada inválida
            }
        }

        System.out.print("Stock: ");
        int stock = scanner.nextInt();

        Libro libro = new Libro(isbn, titulo, autor, idCategoria, precio, stock);
        libroDAO.insertarLibro(libro);
    }

    private static void actualizarStock() {
        System.out.print("\nID del libro: ");
        int idLibro = scanner.nextInt();

        System.out.print("Nuevo stock: ");
        int nuevoStock = scanner.nextInt();

        libroDAO.actualizarStock(idLibro, nuevoStock);
    }

    private static void leerLibro() {
        System.out.print("\nID del libro: ");
        int idLibro = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        Libro libro = libroDAO.leerLibro(idLibro);
        if (libro != null) {
            System.out.println("\nDetalles del libro:");
            System.out.println(libro);
        } else {
            System.out.println("No se encontró un libro con el ID especificado.");
        }
    }

    private static void eliminarLibro() {
        System.out.print("\nID del libro: ");
        int idLibro = scanner.nextInt();

        boolean eliminado = libroDAO.eliminarLibro(idLibro);
        if (eliminado) {
            System.out.println("El libro se eliminó correctamente.");
        } else {
            System.out.println("No se encontró un libro con el ID especificado.");
        }
    }

    private static void gestionReportes() {
        System.out.println("\nConsultas y Reportes");
        System.out.println("1. Generar Reporte de Inventario");
        System.out.print("Seleccione una opción: ");

        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        if (opcion == 1) {
            reportes.generarReporteInventario("reporte_inventario.txt");
        } else {
            System.out.println("Opción no válida");
        }
    }
}
