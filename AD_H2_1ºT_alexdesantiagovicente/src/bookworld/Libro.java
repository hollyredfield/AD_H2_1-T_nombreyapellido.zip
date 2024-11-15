package bookworld;

public class Libro {
    private int id;
    private String isbn;
    private String titulo;
    private String autor;
    private int idCategoria;
    private double precio;
    private int stock;

    public Libro(String isbn, String titulo, String autor, int idCategoria, double precio, int stock) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.idCategoria = idCategoria;
        this.precio = precio;
        this.stock = stock;
    }

    // Métodos getter
    public String getIsbn() {
        return isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }
}
