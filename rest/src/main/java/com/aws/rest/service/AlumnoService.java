package com.aws.rest.service;

//import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aws.rest.respository.AlumnoRepository;
import com.aws.rest.entity.Alumno;

import java.util.List;
import java.util.Optional;

@Service
public class AlumnoService {
    @Autowired
    private AlumnoRepository alumnoRepository;

    public List<Alumno> getAllAlumnos() {
        return alumnoRepository.getAlumnosList();
    }

    public Optional<Alumno> getAlumnoById(int id) {
        List<Alumno> alumnos = alumnoRepository.getAlumnosList();
        for (Alumno alumno : alumnos) {
            if (alumno.getId() == id) {
                return Optional.of(alumno);
            }
        }
        return Optional.empty();
    }

    public Optional<Alumno> addAlumno(Alumno alumno) {
        Boolean flag = validateFields(alumno);
        if (flag) {
            alumnoRepository.getAlumnosList().add(alumno);
            return Optional.of(alumno);
        }
        return Optional.empty();
    }

    public Optional<Alumno> updateAlumno(Alumno alumno, int id) {

        Boolean flag = validateFields(alumno);
        if (flag) {
            Optional<Alumno> alumnoFound = getAlumnoById(id);
            if (alumnoFound.isPresent()) {
                alumnoFound.get().setNombres(alumno.getNombres());
                alumnoFound.get().setMatricula(alumno.getMatricula());
            }
            return alumnoFound;
        }
        Optional<Alumno> alumnoNotFound = Optional.empty();
        return alumnoNotFound;

    }

    public boolean deleteAlumno(int id) {
        List<Alumno> alumnosList = alumnoRepository.getAlumnosList();
        for (Alumno alumno : alumnosList) {
            if (alumno.getId() == id) {
                alumnosList.remove(alumno);
                return true;
            }
        }
        return false;
    }

    public Boolean validateFields(Alumno alumno) {
        if (alumno.getNombres() == "" || alumno.getNombres() == null) {
            return false;
        }
        if (alumno.getApellidos() == "" || alumno.getApellidos() == null) {
            return false;
        }
        if (alumno.getPromedio() < 0) {
            return false;
        }
        return true;
    }
}
