package com.aws.rest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aws.rest.entity.Profesor;
import com.aws.rest.respository.ProfesorRepository;

@Service
public class ProfesorService {
    @Autowired
    private ProfesorRepository profesorRepository;

    public List<Profesor> getAllProfesor() {
        return profesorRepository.getProfesoresList();
    }

    public Optional<Profesor> getProfesorById(int id) {
        List<Profesor> profesores = profesorRepository.getProfesoresList();
        for (Profesor profesor : profesores) {
            if (profesor.getId() == id) {
                return Optional.of(profesor);
            }
        }
        return Optional.empty();
    }

    public Optional<Profesor> addProfesor(Profesor profesor) {
        Boolean flag = validateFields(profesor);
        if (flag) {
            profesorRepository.getProfesoresList().add(profesor);
            return Optional.of(profesor);
        }
        return Optional.empty();
    }

    public Optional<Profesor> updateProfesor(Profesor profesor, int id) {
        Boolean flag = validateFields(profesor);
        if (flag) {
            Optional<Profesor> profesorFound = getProfesorById(id);
            if (profesorFound.isPresent()) {
                profesorFound.get().setNombres(profesor.getNombres());
                profesorFound.get().setHorasClase(profesor.getHorasClase());
            }
            return profesorFound;
        }
        Optional<Profesor> profesorNotFound = Optional.empty();
        return profesorNotFound;
    }

    public Boolean deleteProfesor(int id) {
        List<Profesor> profesoresList = profesorRepository.getProfesoresList();
        for (Profesor profesor : profesoresList) {
            if (profesor.getId() == id) {
                profesoresList.remove(profesor);
                return true;
            }
        }
        return false;
    }

    public Boolean validateFields(Profesor profesor) {
        if (profesor.getNombres() == "" || profesor.getNombres() == null) {
            return false;
        }
        if (profesor.getApellidos() == "" || profesor.getApellidos() == null) {
            return false;
        }
        if (profesor.getHorasClase() < 0 || profesor.getNumeroEmpleado() < 0) {
            return false;
        }
        return true;
    }
}
