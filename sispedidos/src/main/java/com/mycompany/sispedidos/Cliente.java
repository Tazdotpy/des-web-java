package com.mycompany.sispedidos.model;

/** client crud */
public class Cliente {
    private int    idCliente;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String direccion;

    public Cliente() {}

    public Cliente(String nombre, String apellido, String email,
                   String telefono, String direccion) {
        this.nombre    = nombre;
        this.apellido  = apellido;
        this.email     = email;
        this.telefono  = telefono;
        this.direccion = direccion;
    }

    public int    getIdCliente()              { return idCliente; }
    public void   setIdCliente(int id)        { this.idCliente = id; }
    public String getNombre()                 { return nombre; }
    public void   setNombre(String n)         { this.nombre = n; }
    public String getApellido()               { return apellido; }
    public void   setApellido(String a)       { this.apellido = a; }
    public String getEmail()                  { return email; }
    public void   setEmail(String e)          { this.email = e; }
    public String getTelefono()               { return telefono; }
    public void   setTelefono(String t)       { this.telefono = t; }
    public String getDireccion()              { return direccion; }
    public void   setDireccion(String d)      { this.direccion = d; }

    @Override
    public String toString() {
        return String.format("[%d] %s %s | %s | Tel: %s",
                idCliente, nombre, apellido, email, telefono);
    }
}
