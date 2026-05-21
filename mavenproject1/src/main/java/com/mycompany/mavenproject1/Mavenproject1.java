/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mavenproject1;

/**
 *
 * @author kevin
 */
public class Mavenproject1 {

   public static void main(String[] args) {
        System.out.println("hi");
        //ej 1 declaracion de valores
        int a;
        int x, y;
        double x1;
        double x2, y2;
        int a1 = 5, b = 6, c = 7;
        boolean sw = false;
        String cad = null;
        final double PI = 3.14; 
        
        
        
        //ej 2. convertir cadena "200" a entero E
        String cadena = "200";
        int E = Integer.parseInt(cadena);
        System.out.println("E = " +E);
        
        //ej 3. convertir cadena "200" a entero  en E1
        String cadena2 = "200";
        int E1 = Integer.parseInt(cadena2);
        System.out.println("E1 = " +E1);
        
        //ej 4 convertir cadena "200" a float
        String cadena3 = "200";
        Float f1 = Float.parseFloat(cadena3);
        System.out.println("f1 = " +f1);
        
        //ej 5 convertir float a cadena 
        float num = 23.84f;
        String nomb = String.valueOf(num);
        System.out.println("nomb = "+ nomb);
        
        
    }
}
