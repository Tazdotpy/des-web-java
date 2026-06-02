/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio142;

/**
 *
 * @author kevin
 */
public class Gato extends Mamiferos {

    private double alturaSalto;

    public Gato(String nombre,
                String raza,
                String tipoAnimal,
                String fechaNacimiento,
                float peso,
                double alturaSalto) {

        super(nombre, raza, tipoAnimal,
              fechaNacimiento, peso);

        this.alturaSalto = alturaSalto;
    }

    @Override
    public void comunicarse() {
        System.out.println("Miau Miau");
    }
}