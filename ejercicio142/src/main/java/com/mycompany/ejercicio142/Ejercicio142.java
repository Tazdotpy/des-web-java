/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ejercicio142;

/**
 *
 * @author kevin
 */
public class Ejercicio142 {

    public static void main(String[] args) {

        Perro p = new Perro(
                "Coco",
                "Chihuahua",
                "Perro",
                "31/08/2077",
                20.5f,
                "Mi casa");

        Gato g = new Gato(
                "Mitcher",
                "Naranja",
                "Gato",
                "15/03/2021",
                5.2f,
                2.5);

        p.comunicarse();
        g.comunicarse();
    }
}
