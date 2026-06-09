package com.mycompany.sispedidos.model;

import java.util.ArrayList;
import java.util.List;

/** model */
public class Pedido {
    private int    idPedido;
    private int    idCliente;
    private String fecha;
    private String estado;
    private List<DetallePedido> detalles = new ArrayList<>();

    public Pedido() {}

    public Pedido(int idCliente) {
        this.idCliente = idCliente;
        this.estado    = "PENDIENTE";
    }

    public int    getIdPedido()                       { return idPedido; }
    public void   setIdPedido(int id)                 { this.idPedido = id; }
    public int    getIdCliente()                      { return idCliente; }
    public void   setIdCliente(int id)                { this.idCliente = id; }
    public String getFecha()                          { return fecha; }
    public void   setFecha(String f)                  { this.fecha = f; }
    public String getEstado()                         { return estado; }
    public void   setEstado(String e)                 { this.estado = e; }
    public List<DetallePedido> getDetalles()          { return detalles; }
    public void   setDetalles(List<DetallePedido> d)  { this.detalles = d; }

    public double getTotal() {
        return detalles.stream()
                .mapToDouble(d -> d.getCantidad() * d.getPrecioUnit())
                .sum();
    }

    @Override
    public String toString() {
        return String.format("[%d] Cliente #%d | Fecha: %s | Estado: %s | Total: $%.2f",
                idPedido, idCliente, fecha, estado, getTotal());
    }
}
