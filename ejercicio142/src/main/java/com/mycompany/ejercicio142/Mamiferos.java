/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio142;

/**
 *
 * @author kevin
 */
public class Mamiferos implements IMamiferos {

    protected String nombre;
    protected String raza;
    protected String tipoAnimal;
    protected String fechaNacimiento;
    protected float peso;

    public Mamiferos(String nombre, String raza,
                     String tipoAnimal,
                     String fechaNacimiento,
                     float peso) {

        this.nombre = nombre;
        this.raza = raza;
        this.tipoAnimal = tipoAnimal;
        this.fechaNacimiento = fechaNacimiento;
        this.peso = peso;
    }

    public void comer() {
        System.out.println(nombre + " está comiendo.");
    }

    public void tipoAnimal() {
        System.out.println("Tipo: " + tipoAnimal);
    }

    @Override
    public void comunicarse() {
        System.out.println("Sonido genérico");
    }
}