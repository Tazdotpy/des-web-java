package com.mycompany.sispedidos.service;

import com.mycompany.sispedidos.dao.ClienteDAO;
import com.mycompany.sispedidos.model.Cliente;

import java.sql.SQLException;
import java.util.List;

/** BUSINESS LAYER — business rules for Cliente. */
public class ClienteService {

    private final ClienteDAO dao = new ClienteDAO();

    public Cliente registerCliente(String nombre, String apellido, String email,
                                   String telefono, String direccion) throws SQLException {
        if (nombre.isBlank() || apellido.isBlank() || email.isBlank())
            throw new IllegalArgumentException("Nombre, apellido y correo son necesarios.");
        if (!email.contains("@"))
            throw new IllegalArgumentException("Correo electronico invalido.");

        Cliente c = new Cliente(nombre.trim(), apellido.trim(),
                                email.trim(), telefono.trim(), direccion.trim());
        dao.insert(c);
        return c;
    }

    public List<Cliente> listAll() throws SQLException {
        return dao.findAll();
    }

    public Cliente findById(int id) throws SQLException {
        Cliente c = dao.findById(id);
        if (c == null) throw new IllegalArgumentException("Customer #" + id + " not found.");
        return c;
    }
}
