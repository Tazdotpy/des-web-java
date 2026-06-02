/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio142;

/**
 *
 * @author kevin
 */
public class Perro extends Mamiferos {

    private String lugarEntrenamiento;

    public Perro(String nombre,
                 String raza,
                 String tipoAnimal,
                 String fechaNacimiento,
                 float peso,
                 String lugarEntrenamiento) {

        super(nombre, raza, tipoAnimal,
              fechaNacimiento, peso);

        this.lugarEntrenamiento = lugarEntrenamiento;
    }

    @Override
    public void comunicarse() {
        System.out.println("Guau Guau");
    }
}