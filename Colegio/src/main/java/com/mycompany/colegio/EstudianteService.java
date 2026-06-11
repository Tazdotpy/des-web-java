package com.mycompany.colegio.service;

import com.mycompany.colegio.dao.EstudianteDAO;
import com.mycompany.colegio.model.Estudiante;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class EstudianteService {

    private final EstudianteDAO dao = new EstudianteDAO();

    public void registrarEstudiante(String nombre, String apellido, String curso, String mes,
                                    double matematica, double lengua,
                                    double naturales, double sociales) throws IOException {

        if (nombre.isBlank() || apellido.isBlank())
            throw new IllegalArgumentException("El nombre y apellido son obligatorios.");
        if (curso.isBlank())
            throw new IllegalArgumentException("El curso es obligatorio.");
        if (mes.isBlank())
            throw new IllegalArgumentException("El mes es obligatorio.");

        validarNota("Matemática", matematica);
        validarNota("Lengua",     lengua);
        validarNota("Naturales",  naturales);
        validarNota("Sociales",   sociales);

        Estudiante e = new Estudiante(nombre.trim(), apellido.trim(),
                                      curso.trim(), mes.trim(),
                                      matematica, lengua, naturales, sociales);
        dao.guardar(e);
    }


     // Retorna la lista de estudiantes.
    public List<Estudiante> obtenerReporte(String curso, String mes) throws IOException {
        if (curso.isBlank() || mes.isBlank())
            throw new IllegalArgumentException("Curso y mes son obligatorios.");

        List<Estudiante> lista = dao.buscarPorCursoYMes(curso, mes);
        lista.sort(Comparator.comparing(Estudiante::getApellido,
                   String.CASE_INSENSITIVE_ORDER));
        return lista;
    }

    private void validarNota(String materia, double nota) {
        if (nota < 0 || nota > 100)
            throw new IllegalArgumentException(
                    materia + ": la nota debe estar entre 0 y 100.");
    }
}
