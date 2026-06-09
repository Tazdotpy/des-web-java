package com.mycompany.sispedidos.model;

/** model */
public class DetallePedido {
    private int    idDetalle;
    private int    idPedido;
    private int    idProducto;
    private int    cantidad;
    private double precioUnit;

    public DetallePedido() {}

    public DetallePedido(int idPedido, int idProducto, int cantidad, double precioUnit) {
        this.idPedido    = idPedido;
        this.idProducto  = idProducto;
        this.cantidad    = cantidad;
        this.precioUnit  = precioUnit;
    }

    public int    getIdDetalle()              { return idDetalle; }
    public void   setIdDetalle(int id)        { this.idDetalle = id; }
    public int    getIdPedido()               { return idPedido; }
    public void   setIdPedido(int id)         { this.idPedido = id; }
    public int    getIdProducto()             { return idProducto; }
    public void   setIdProducto(int id)       { this.idProducto = id; }
    public int    getCantidad()               { return cantidad; }
    public void   setCantidad(int c)          { this.cantidad = c; }
    public double getPrecioUnit()             { return precioUnit; }
    public void   setPrecioUnit(double p)     { this.precioUnit = p; }

    public double getSubtotal()               { return cantidad * precioUnit; }

    @Override
    public String toString() {
        return String.format("  Producto #%d | Cant: %d | Precio: $%.2f | Subtotal: $%.2f",
                idProducto, cantidad, precioUnit, getSubtotal());
    }
}
