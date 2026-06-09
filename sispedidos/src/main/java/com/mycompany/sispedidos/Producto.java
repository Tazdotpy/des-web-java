package com.mycompany.sispedidos.model;

/** model layer */
public class Producto {
    private int    idProducto;
    private String nombre;
    private String descripcion;
    private double precio;
    private int    stock;

    public Producto() {}

    public Producto(String nombre, String descripcion, double precio, int stock) {
        this.nombre      = nombre;
        this.descripcion = descripcion;
        this.precio      = precio;
        this.stock       = stock;
    }

    public int    getIdProducto()             { return idProducto; }
    public void   setIdProducto(int id)       { this.idProducto = id; }
    public String getNombre()                 { return nombre; }
    public void   setNombre(String n)         { this.nombre = n; }
    public String getDescripcion()            { return descripcion; }
    public void   setDescripcion(String d)    { this.descripcion = d; }
    public double getPrecio()                 { return precio; }
    public void   setPrecio(double p)         { this.precio = p; }
    public int    getStock()                  { return stock; }
    public void   setStock(int s)             { this.stock = s; }

    @Override
    public String toString() {
        return String.format("[%d] %-20s $%.2f  (stock: %d)  %s",
                idProducto, nombre, precio, stock, descripcion);
    }
}
