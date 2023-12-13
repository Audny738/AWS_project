package com.aws.rest.service;

//import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aws.rest.respository.AlumnoRepository;
import com.aws.rest.entity.Alumno;
import com.aws.rest.entity.Profesor;
import com.aws.rest.error.AlumnoException;

import java.util.List;
import java.util.Optional;

@Service
public class AlumnoService {
    @Autowired
    private AlumnoRepository alumnoRepository;

    public List<Alumno> getAllAlumnos() throws Exception {
        List<Alumno> alumnos = alumnoRepository.findAll();
        if (alumnos.isEmpty()) {
            throw new AlumnoException("No hay alumnos");
        }
        return alumnos;
    }

    public Optional<Alumno> getAlumnoById(int id) throws Exception {
        Optional<Alumno> alumno = alumnoRepository.findById(id);
        if (alumno.isEmpty()) {
            throw new AlumnoException("No hay alumno con ese id");
        }
        return alumno;
    }

    public Optional<Alumno> addAlumno(Alumno alumno) {
        Boolean flag = validateFields(alumno);
        if (flag) {
            alumnoRepository.save(alumno);
            return Optional.of(alumno);
        }
        return Optional.empty();
    }

    public Optional<Alumno> updateAlumno(Alumno alumno, int id) {

        Boolean flag = validateFields(alumno);
        if (flag) {
            Optional<Alumno> alumnoFound;
            try {
                alumnoFound = getAlumnoById(id);
                if (alumnoFound.isPresent()) {
                    alumnoFound.get().setNombres(alumno.getNombres());
                    alumnoFound.get().setMatricula(alumno.getMatricula());
                    alumnoRepository.save(alumnoFound.get());
                }
                return alumnoFound;
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }
        Optional<Alumno> alumnoNotFound = Optional.empty();
        return alumnoNotFound;

    }

    public Optional<Alumno> deleteAlumno(int id) throws Exception {
        Optional<Alumno> alumnoExist = alumnoRepository.findById(id);
        if (alumnoExist.isPresent()) {
            alumnoRepository.deleteById(id);
        }
        return alumnoExist;
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
