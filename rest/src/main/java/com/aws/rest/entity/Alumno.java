package com.aws.rest.entity;

public class Alumno {

    private int id;
    private String nombres;
    private String apellidos;
    private String matricula;
    private float promedio;

    private int counter = 0;

    public Alumno(String nombres, String apellidos, String matricula, float promedio) {
        this.id = counter + 1;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.matricula = matricula;
        this.promedio = promedio;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombres;
    }

    public void setNombre(String nombre) {
        this.nombres = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public float getPromedio() {
        return promedio;
    }

    public void setPromedio(float promedio) {
        this.promedio = promedio;
    }

}
