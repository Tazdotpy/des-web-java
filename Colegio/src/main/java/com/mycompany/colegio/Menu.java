package com.mycompany.colegio.ui;

import com.mycompany.colegio.model.Estudiante;
import com.mycompany.colegio.service.EstudianteService;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * PRESENTATION LAYER — menú de consola con detección de ESC.
 */
public class Menu {

    private static final char ESC = 27;
    private final EstudianteService service = new EstudianteService();
    private final Scanner sc = new Scanner(System.in);

    public void iniciar() {
        boolean corriendo = true;
        while (corriendo) {
            imprimirMenuPrincipal();
            String entrada = sc.nextLine();

            // Detecta ESC (carácter 27) o la palabra "esc"
            if (!entrada.isEmpty() && entrada.charAt(0) == ESC) {
                corriendo = false;
                continue;
            }
            if (entrada.trim().equalsIgnoreCase("esc")) {
                corriendo = false;
                continue;
            }

            switch (entrada.trim()) {
                case "1" -> registrarCalificaciones();
                case "2" -> reportePorMes();
                case "3" -> corriendo = false;
                default  -> System.out.println("\n  Opción inválida. Intente de nuevo.");
            }
        }
        System.out.println("\n  ¡Hasta luego!");
    }

    // ---------------------------------------------------------------
    // Menú principal
    // ---------------------------------------------------------------
    private void imprimirMenuPrincipal() {
        System.out.println("\n====================================");
        System.out.println("      COLEGIO DIOS ES BUENO");
        System.out.println("   SISTEMA DE CALIFICACIONES");
        System.out.println("====================================");
        System.out.println("  1- Registro de calificaciones");
        System.out.println("  2- Reporte calificaciones por mes");
        System.out.println("  3- Salir");
        System.out.println("====================================");
        System.out.print("  Elija la opción deseada y pulse <ENTER>:  ");
        System.out.print("");
    }

 
    private void registrarCalificaciones() {
        System.out.println("\n  -- REGISTRO DE CALIFICACIONES --");
        try {
            System.out.print("  Nombre    : "); String nombre   = leerTexto();
            System.out.print("  Apellido  : "); String apellido = leerTexto();
            System.out.print("  Curso     : "); String curso    = leerTexto();
            System.out.print("  Mes       : "); String mes      = leerTexto();

            System.out.print("  Matemática: "); double mat = leerNota();
            System.out.print("  Literatura    : "); double len = leerNota();
            System.out.print("  Naturales : "); double nat = leerNota();
            System.out.print("  Sociales  : "); double soc = leerNota();

            service.registrarEstudiante(nombre, apellido, curso, mes, mat, len, nat, soc);
            System.out.println("\n   OK");

        } catch (IllegalArgumentException e) {
            System.out.println("\n   Error de validación: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("\n   Error al guardar el archivo: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\n   Error inesperado: " + e.getMessage());
        }
    }

 
    private void reportePorMes() {
        System.out.println("\n  -- REPORTE DE CALIFICACIONES --");
        try {
            System.out.print("  Curso : "); String curso = leerTexto();
            System.out.print("  Mes   : "); String mes   = leerTexto();

            List<Estudiante> lista = service.obtenerReporte(curso, mes);

            if (lista.isEmpty()) {
                System.out.println("\n  No se encontraron registros para " +
                                   curso + " - " + mes + ".");
                return;
            }

            imprimirReporte(lista, curso, mes);

        } catch (IllegalArgumentException e) {
            System.out.println("\n   Error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("\n   Error al leer el archivo: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\n   Error inesperado: " + e.getMessage());
        }
    }

    private void imprimirReporte(List<Estudiante> lista, String curso, String mes) {
        System.out.println("\n  Colegio Dios es Bueno.");
        System.out.println("  Reporte de Calificaciones de " + mes);
        System.out.println("  Curso: " + curso);
        System.out.println("  " + "=".repeat(75));
        System.out.printf("  %-12s %-12s %10s %8s %10s %9s %9s %8s%n",
                "Nombre", "Apellido", "Matemática", "Lengua",
                "Naturales", "Sociales", "Promedio", "Literal");
        System.out.println("  " + "=".repeat(75));

        for (Estudiante e : lista) {
            System.out.printf("  %-12s %-12s %10.0f %8.0f %10.0f %9.0f %9.1f %8s%n",
                    e.getNombre(), e.getApellido(),
                    e.getMatematica(), e.getLengua(),
                    e.getNaturales(),  e.getSociales(),
                    e.getPromedio(),   e.getLiteral());
        }

        System.out.println("  " + "-".repeat(75));
        System.out.println("  Total de estudiantes: " + lista.size());
    }

    // Helpers
    private String leerTexto() {
        while (true) {
            try {
                String valor = sc.nextLine().trim();
                if (valor.isEmpty()) {
                    System.out.print("  El campo no puede estar vacío. Intente de nuevo: ");
                    continue;
                }
                return valor;
            } catch (Exception e) {
                System.out.print("  Entrada inválida. Intente de nuevo: ");
            }
        }
    }

    private double leerNota() {
        while (true) {
            try {
                String entrada = sc.nextLine().trim();
                double valor = Double.parseDouble(entrada);
                if (valor < 0 || valor > 100) {
                    System.out.print("  La nota debe estar entre 0 y 100: ");
                    continue;
                }
                return valor;
            } catch (NumberFormatException e) {
                System.out.print("  Ingrese un número válido (ej: 85): ");
            }
        }
    }
}
