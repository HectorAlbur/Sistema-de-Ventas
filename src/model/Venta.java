package model;

import java.time.LocalDateTime;

public class Venta {
    
    private int id;
    private LocalDateTime fecha;
    private double subtotal;
    private double iva;
    private double total;
    
    public Venta() {}
    
 //IVA Calculada 

    public Venta(double subtotal) {
        this.subtotal = subtotal;
        this.iva = subtotal * 0.16;
        this.total = subtotal + this.iva;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    
    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
    
    public double getIva() { return iva; }
    public void setIva(double iva) { this.iva = iva; }
    
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}