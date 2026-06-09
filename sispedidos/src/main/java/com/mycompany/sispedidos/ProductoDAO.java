package com.mycompany.sispedidos.dao;

import com.mycompany.sispedidos.db.DatabaseConnection;
import com.mycompany.sispedidos.model.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** data layer */
public class ProductoDAO {

    public void insert(Producto p) throws SQLException {
        String sql = "INSERT INTO Producto (nombre, descripcion, precio, stock) " +
                     "VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql,
                     Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            ps.setDouble(3, p.getPrecio());
            ps.setInt(4, p.getStock());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) p.setIdProducto(keys.getInt(1));
            }
        }
    }

    public List<Producto> findAll() throws SQLException {
        List<Producto> list = new ArrayList<>();
        String sql = "SELECT * FROM Producto";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public Producto findById(int id) throws SQLException {
        String sql = "SELECT * FROM Producto WHERE id_producto = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    public void updateStock(int idProducto, int newStock) throws SQLException {
        String sql = "UPDATE Producto SET stock = ? WHERE id_producto = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, newStock);
            ps.setInt(2, idProducto);
            ps.executeUpdate();
        }
    }

    private Producto map(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setIdProducto(rs.getInt("id_producto"));
        p.setNombre(rs.getString("nombre"));
        p.setDescripcion(rs.getString("descripcion"));
        p.setPrecio(rs.getDouble("precio"));
        p.setStock(rs.getInt("stock"));
        return p;
    }
}
