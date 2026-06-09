package com.mycompany.sispedidos.dao;

import com.mycompany.sispedidos.db.DatabaseConnection;
import com.mycompany.sispedidos.model.DetallePedido;
import com.mycompany.sispedidos.model.Pedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** DATA LAYER — CRUD operations for Pedido and DetallePedido. */
public class PedidoDAO {

    public void insert(Pedido pedido) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        conn.setAutoCommit(false);
        try {
            String sqlPedido = "INSERT INTO Pedido (id_cliente, fecha, estado) " +
                               "VALUES (?, date('now'), ?)";
            try (PreparedStatement ps = conn.prepareStatement(sqlPedido,
                    Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, pedido.getIdCliente());
                ps.setString(2, pedido.getEstado());
                ps.executeUpdate();
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) pedido.setIdPedido(keys.getInt(1));
                }
            }

            String sqlDetalle = "INSERT INTO DetallePedido " +
                                "(id_pedido, id_producto, cantidad, precio_unit) " +
                                "VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sqlDetalle,
                    Statement.RETURN_GENERATED_KEYS)) {
                for (DetallePedido d : pedido.getDetalles()) {
                    d.setIdPedido(pedido.getIdPedido());
                    ps.setInt(1, d.getIdPedido());
                    ps.setInt(2, d.getIdProducto());
                    ps.setInt(3, d.getCantidad());
                    ps.setDouble(4, d.getPrecioUnit());
                    ps.executeUpdate();
                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (keys.next()) d.setIdDetalle(keys.getInt(1));
                    }
                }
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public List<Pedido> findAll() throws SQLException {
        // Step 1: load all order headers into memory first, closing the ResultSet
        List<Pedido> list = new ArrayList<>();
        String sql = "SELECT * FROM Pedido";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(mapHeader(rs));
        }
        // Step 2: now safely load details for each order (no open ResultSet above)
        for (Pedido p : list) {
            p.setDetalles(findDetalles(p.getIdPedido()));
        }
        return list;
    }

    public List<Pedido> findByCliente(int idCliente) throws SQLException {
        // Same pattern: headers first, then details
        List<Pedido> list = new ArrayList<>();
        String sql = "SELECT * FROM Pedido WHERE id_cliente = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapHeader(rs));
            }
        }
        for (Pedido p : list) {
            p.setDetalles(findDetalles(p.getIdPedido()));
        }
        return list;
    }

    public void updateEstado(int idPedido, String estado) throws SQLException {
        String sql = "UPDATE Pedido SET estado = ? WHERE id_pedido = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setInt(2, idPedido);
            ps.executeUpdate();
        }
    }

    private List<DetallePedido> findDetalles(int idPedido) throws SQLException {
        List<DetallePedido> list = new ArrayList<>();
        String sql = "SELECT * FROM DetallePedido WHERE id_pedido = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DetallePedido d = new DetallePedido();
                    d.setIdDetalle(rs.getInt("id_detalle"));
                    d.setIdPedido(rs.getInt("id_pedido"));
                    d.setIdProducto(rs.getInt("id_producto"));
                    d.setCantidad(rs.getInt("cantidad"));
                    d.setPrecioUnit(rs.getDouble("precio_unit"));
                    list.add(d);
                }
            }
        }
        return list;
    }

    private Pedido mapHeader(ResultSet rs) throws SQLException {
        Pedido p = new Pedido();
        p.setIdPedido(rs.getInt("id_pedido"));
        p.setIdCliente(rs.getInt("id_cliente"));
        p.setFecha(rs.getString("fecha"));
        p.setEstado(rs.getString("estado"));
        return p;
    }
}
