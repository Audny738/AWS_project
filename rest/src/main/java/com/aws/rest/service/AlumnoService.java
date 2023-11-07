package com.aws.rest.service;

//import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aws.rest.respository.AlumnoRepository;
import com.aws.rest.entity.Alumno;

import java.util.List;

@Service
public class AlumnoService {
    @Autowired
    private AlumnoRepository alumnoRepository;

    public List<Alumno> getAllAlumnos() {
        return alumnoRepository.getAlumnosList();
    }

    public Alumno getAlumnoById(int id) {
        return alumnoRepository.getAlumnosList().stream().filter(alumno -> alumno.getId() == id).findFirst().get();
    }

    public void addAlumno(Alumno alumno) {
        alumnoRepository.getAlumnosList().add(alumno);
    }

    public void updateAlumno(Alumno alumno, int id) {
        int counter = 0;
        List<Alumno> alumnosList = alumnoRepository.getAlumnosList();
        for (Alumno alumno1 : alumnosList) {
            if (alumno1.getId() == id) {
                alumnosList.set(counter, alumno);
            }
            counter++;
        }
    }

    public void deleteAlumno(int id) {
        List<Alumno> alumnosList = alumnoRepository.getAlumnosList();
        alumnosList.removeIf(alumno -> alumno.getId() == id);
    }
}
