/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.estructurasclase;

/**
 *
 * @author kevin
 */
class Gato extends Mamifero {

    double alturaSalto;

    // Constructor
    public Gato(String nombre, String raza, String fechaNacimiento,
                float peso, double alturaSalto) {

        super(nombre, raza, fechaNacimiento, peso);
        this.alturaSalto = alturaSalto;
    }

    @Override
    public void comunicarse() {
        System.out.println("Miau Miau");
    }
}