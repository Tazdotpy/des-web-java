package com.mycompany.sispedidos.service;

import com.mycompany.sispedidos.dao.PedidoDAO;
import com.mycompany.sispedidos.model.DetallePedido;
import com.mycompany.sispedidos.model.Pedido;
import com.mycompany.sispedidos.model.Producto;

import java.sql.SQLException;
import java.util.List;

/** business. */
public class PedidoService {

    private final PedidoDAO       pedidoDAO   = new PedidoDAO();
    private final ProductoService productoSvc = new ProductoService();
    private final ClienteService  clienteSvc  = new ClienteService();

    public Pedido createPedido(int idCliente, List<int[]> lines) throws SQLException {
        clienteSvc.findById(idCliente);

        Pedido pedido = new Pedido(idCliente);

        for (int[] line : lines) {
            int idProducto = line[0];
            int cantidad   = line[1];
            Producto p = productoSvc.findById(idProducto);
            if (p.getStock() < cantidad)
                throw new IllegalStateException(
                        "Insufficient stock for: " + p.getNombre() +
                        " (available: " + p.getStock() + ")");
            pedido.getDetalles().add(
                    new DetallePedido(0, idProducto, cantidad, p.getPrecio()));
        }

        pedidoDAO.insert(pedido);

        for (int[] line : lines)
            productoSvc.decreaseStock(line[0], line[1]);

        return pedido;
    }

    public List<Pedido> listAll() throws SQLException {
        return pedidoDAO.findAll();
    }

    public List<Pedido> listByCliente(int idCliente) throws SQLException {
        return pedidoDAO.findByCliente(idCliente);
    }

    public void changeEstado(int idPedido, String estado) throws SQLException {
        List<String> valid = List.of("PENDIENTE", "CONFIRMADO", "CANCELADO");
        if (!valid.contains(estado.toUpperCase()))
            throw new IllegalArgumentException("Invalid status. Use: " + valid);
        pedidoDAO.updateEstado(idPedido, estado.toUpperCase());
    }
}
