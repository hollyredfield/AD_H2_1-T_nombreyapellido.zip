package bookworld;

public class DetalleVenta {
    public int idLibro;
    public int cantidad;
    public double precioUnitario;
    public double subtotal;

    public DetalleVenta(int idLibro, int cantidad, double precioUnitario) {
        this.idLibro = idLibro;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = cantidad * precioUnitario;
    }
}
