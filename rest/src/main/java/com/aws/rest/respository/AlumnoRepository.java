package com.aws.rest.respository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.aws.rest.entity.Alumno;

@Repository
public class AlumnoRepository {
    private List<Alumno> alumnosList = new ArrayList<>();

    public List<Alumno> getAlumnosList() {
        return alumnosList;
    }

}
