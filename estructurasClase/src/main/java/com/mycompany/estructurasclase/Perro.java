/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.estructurasclase;

/**
 *
 * @author kevin
 */

class Perro extends Mamifero {

    String lugarEntrenamiento;

    public Perro(String nombre, String raza, String fechaNacimiento,
                 float peso, String lugarEntrenamiento) {

        super(nombre, raza, fechaNacimiento, peso);
        this.lugarEntrenamiento = lugarEntrenamiento;
    }

    @Override
    public void comunicarse() {
        System.out.println("Guau Guau");
    }
}