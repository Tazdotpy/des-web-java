/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.estructurasclase;

/**
 *
 * @author kevin
 */

class Mamifero {

    String nombre;
    String raza;
    String fechaNacimiento;
    float peso;

    // Constructor
    public Mamifero(String nombre, String raza, String fechaNacimiento, float peso) {
        this.nombre = nombre;
        this.raza = raza;
        this.fechaNacimiento = fechaNacimiento;
        this.peso = peso;
    }
    public void comer() {
        System.out.println(nombre + " está comiendo.");
    }
    public void comunicarse() {
        System.out.println("El mamífero se comunica.");
    }
}