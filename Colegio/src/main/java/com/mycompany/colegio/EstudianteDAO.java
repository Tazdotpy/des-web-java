package com.mycompany.colegio.dao;

import com.mycompany.colegio.model.Estudiante;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class EstudianteDAO {

    private static final String DATA_DIR = "calificaciones";

    public EstudianteDAO() {
        // Crea la carpeta si no existe
        new File(DATA_DIR).mkdirs();
    }

    /** Agrega un estudiante al archivo correspondiente */
    public void guardar(Estudiante e) throws IOException {
        File archivo = getArchivo(e.getCurso(), e.getMes());
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            bw.write(e.toCSV());
            bw.newLine();
        }
    }


    public List<Estudiante> buscarPorCursoYMes(String curso, String mes) throws IOException {
        List<Estudiante> lista = new ArrayList<>();
        File archivo = getArchivo(curso, mes);
        if (!archivo.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (!linea.isEmpty()) {
                    try {
                        lista.add(Estudiante.fromCSV(linea));
                    } catch (Exception ex) {
                        // línea mal formada, se omite
                        System.err.println("Línea inválida omitida: " + linea);
                    }
                }
            }
        }
        return lista;
    }

    private File getArchivo(String curso, String mes) {
        // Sanitiza el nombre para evitar caracteres inválidos en el path
        String nombre = curso.replaceAll("[^a-zA-Z0-9]", "") + "_" +
                        mes.replaceAll("[^a-zA-ZáéíóúÁÉÍÓÚ]", "") + ".txt";
        return new File(DATA_DIR, nombre);
    }
}
