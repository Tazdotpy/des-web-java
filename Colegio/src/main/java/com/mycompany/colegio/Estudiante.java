package com.mycompany.colegio.model;

/**
 * MODEL — representa un estudiante con sus calificaciones.
 */
public class Estudiante {

    private String nombre;
    private String apellido;
    private String curso;
    private String mes;
    private double matematica;
    private double lengua;
    private double naturales;
    private double sociales;

    public Estudiante() {}

    public Estudiante(String nombre, String apellido, String curso, String mes,
                      double matematica, double lengua, double naturales, double sociales) {
        this.nombre     = nombre;
        this.apellido   = apellido;
        this.curso      = curso;
        this.mes        = mes;
        this.matematica = matematica;
        this.lengua     = lengua;
        this.naturales  = naturales;
        this.sociales   = sociales;
    }

    // --- Cálculos ---

    public double getPromedio() {
        double suma = matematica + lengua + naturales + sociales;
        double count = 0;
        if (matematica >= 0) count++;
        if (lengua     >= 0) count++;
        if (naturales  >= 0) count++;
        if (sociales   >= 0) count++;
        if (count == 0) return 0;   // evita división por cero
        return suma / count;
    }

    public String getLiteral() {
        double p = getPromedio();
        if (p > 90) return "A";
        if (p > 80) return "B";
        if (p > 70) return "C";
        return "D";
    }

    // --- Getters & Setters ---

    public String getNombre()               { return nombre; }
    public void   setNombre(String n)       { this.nombre = n; }
    public String getApellido()             { return apellido; }
    public void   setApellido(String a)     { this.apellido = a; }
    public String getCurso()                { return curso; }
    public void   setCurso(String c)        { this.curso = c; }
    public String getMes()                  { return mes; }
    public void   setMes(String m)          { this.mes = m; }
    public double getMatematica()           { return matematica; }
    public void   setMatematica(double v)   { this.matematica = v; }
    public double getLengua()               { return lengua; }
    public void   setLengua(double v)       { this.lengua = v; }
    public double getNaturales()            { return naturales; }
    public void   setNaturales(double v)    { this.naturales = v; }
    public double getSociales()             { return sociales; }
    public void   setSociales(double v)     { this.sociales = v; }

    /** Serializa a una línea CSV para guardar en .TXT */
    public String toCSV() {
        return String.join("|", nombre, apellido, curso, mes,
                String.valueOf(matematica), String.valueOf(lengua),
                String.valueOf(naturales),  String.valueOf(sociales));
    }

    /** Construye un Estudiante desde una línea CSV del .TXT */
    public static Estudiante fromCSV(String line) {
        String[] p = line.split("\\|");
        return new Estudiante(
                p[0], p[1], p[2], p[3],
                Double.parseDouble(p[4]),
                Double.parseDouble(p[5]),
                Double.parseDouble(p[6]),
                Double.parseDouble(p[7])
        );
    }
}
