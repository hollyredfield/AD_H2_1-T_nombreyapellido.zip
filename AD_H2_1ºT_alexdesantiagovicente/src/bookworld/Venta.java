package bookworld;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Venta {
    public int idCliente;
    public LocalDateTime fechaVenta;
    public double total;
    public List<DetalleVenta> detalles;

    public Venta(int idCliente) {
        this.idCliente = idCliente;
        this.fechaVenta = LocalDateTime.now();
        this.detalles = new ArrayList<>();
    }

    public void agregarDetalle(DetalleVenta detalle) {
        detalles.add(detalle);
        recalcularTotal();
    }

    private void recalcularTotal() {
        total = detalles.stream().mapToDouble(d -> d.subtotal).sum();
    }
}
