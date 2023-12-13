package com.aws.rest.entity;

import java.util.Random;
import java.util.UUID;

public class SesionModel {
    private String id;
    private int fecha;
    private int alumnoId;
    private boolean active;
    private String sessionString;

    public SesionModel(int alumnoId){
        this.id = UUID.randomUUID().toString();
        this.fecha = (int) System.currentTimeMillis();
        this.alumnoId = alumnoId;
        this.active = true;
        this.sessionString = randomAlphanumericString(128);
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public int getFecha(){
        return fecha;
    }

    public void setFecha(int fecha){
        this.fecha = fecha;
    }

    public int getAlumnoId(){
        return alumnoId;
    }

    public void setAlumnoId(int alumnoId){
        this.alumnoId = alumnoId;
    }

    public boolean isActive(){
        return active;
    }

    public void setActive(boolean active){
        this.active = active;
    }

    public String getSessionString(){
        return sessionString;
    }

    public void setSessionString(String sessionString){
        this.sessionString = sessionString;
    }

    public String randomAlphanumericString(int length) {
        String alphanumericCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuv";
 
        StringBuffer randomString = new StringBuffer(length);
        Random random = new Random();
 
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(alphanumericCharacters.length());
            char randomChar = alphanumericCharacters.charAt(randomIndex);
            randomString.append(randomChar);
        }
 
        return randomString.toString();
    }
}
