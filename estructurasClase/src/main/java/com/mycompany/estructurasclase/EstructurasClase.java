/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.estructurasclase;

/**
 *
 * @author kevin
 */
public class EstructurasClase {

   public static void main(String[] args) {


        Perro perro = new Perro(
                "Coco",
                "Chihuahua",
                "05/03/2022",
                15.5f,
                "Santo Domingo"
        );

        Gato gato = new Gato(
                "Mitcher",
                "SNaranja",
                "03/08/2018",
                4.2f,
                2.5
        );

        perro.comer();
        perro.comunicarse();

        gato.comer();
        gato.comunicarse();
    }
}