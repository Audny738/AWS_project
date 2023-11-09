package com.aws.rest.entity;

public class Profesor {

    private int id;
    private int numeroEmpleado;
    private String nombres;
    private String apellidos;
    private double horasClase;

    private int counter = 0;

    public Profesor(int numeroEmpleado, String nombres, String apellidos, double horasClase) {
        this.id = counter + 1;
        this.numeroEmpleado = numeroEmpleado;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.horasClase = horasClase;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNumeroEmpleado(int numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    public int getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getNombres() {
        return nombres;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setHorasClase(double horasClase) {
        this.horasClase = horasClase;
    }

    public double getHorasClase() {
        return horasClase;
    }

}
