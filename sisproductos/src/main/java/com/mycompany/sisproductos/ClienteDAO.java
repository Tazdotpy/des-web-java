/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sisproductos;

/**
 *
 * @author kevin
 */
public class ClienteDAO {

    public void guardar(Cliente c) {

        String sql =
        "INSERT INTO Cliente(nombre,telefono,direccion)"
        + " VALUES(?,?,?)";

      
    }
}