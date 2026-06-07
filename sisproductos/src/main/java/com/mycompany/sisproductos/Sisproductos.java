/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sisproductos;

/**
 *
 * @author kevin
 */
public class Sisproductos {

public static void main(String[] args) {

        Cliente c = new Cliente();

        c.setNombre("Kevin");
        c.setTelefono("1111111111");
        c.setDireccion("Santo Domingo");

        ClienteNegocio negocio =
                new ClienteNegocio();

        negocio.registrarCliente(c);
    }
}
