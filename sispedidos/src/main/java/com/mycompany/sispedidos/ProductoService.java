package com.mycompany.sispedidos.service;

import com.mycompany.sispedidos.dao.ProductoDAO;
import com.mycompany.sispedidos.model.Producto;

import java.sql.SQLException;
import java.util.List;

/** business layer*/
public class ProductoService {

    private final ProductoDAO dao = new ProductoDAO();

    public Producto registerProducto(String nombre, String descripcion,
                                     double precio, int stock) throws SQLException {
        if (nombre.isBlank())
            throw new IllegalArgumentException("Product name is required.");
        if (precio < 0)
            throw new IllegalArgumentException("Price cannot be negative.");
        if (stock < 0)
            throw new IllegalArgumentException("Stock cannot be negative.");

        Producto p = new Producto(nombre.trim(), descripcion.trim(), precio, stock);
        dao.insert(p);
        return p;
    }

    public List<Producto> listAll() throws SQLException {
        return dao.findAll();
    }

    public Producto findById(int id) throws SQLException {
        Producto p = dao.findById(id);
        if (p == null) throw new IllegalArgumentException("Product #" + id + " not found.");
        return p;
    }

    public void decreaseStock(int idProducto, int cantidad) throws SQLException {
        Producto p = findById(idProducto);
        if (p.getStock() < cantidad)
            throw new IllegalStateException("Insufficient stock for product: " + p.getNombre());
        dao.updateStock(idProducto, p.getStock() - cantidad);
    }
}
