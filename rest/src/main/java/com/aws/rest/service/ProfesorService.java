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
    private static int count = 0;

    public List<Profesor> getAllProfesor() {
        return profesorRepository.getProfesoresList();
    }

    // public Profesor getProfesorById(int id) {
    // return profesorRepository.getProfesoresList().stream().filter(profesor ->
    // profesor.getId() == id).findFirst()
    // .get();
    // }

    public Optional<Profesor> getProfesorById(int id) {
        List<Profesor> profesores = profesorRepository.getProfesoresList();
        for (Profesor profesor : profesores) {
            if (profesor.getId() == id) {
                return Optional.of(profesor);
            }
        }
        return Optional.empty();
    }

    public void addProfesor(Profesor profesor) {
        profesorRepository.getProfesoresList().add(profesor);
        count += 1;
        profesor.setId(count);
    }

    // public void updateProfesor(Profesor profesor, int id) {
    // int counter = 0;
    // List<Profesor> profesorList = profesorRepository.getProfesoresList();
    // for (Profesor profesor1 : profesorList) {
    // if (profesor1.getId() == id) {
    // profesorList.set(counter, profesor);
    // }
    // counter++;
    // }
    // }

    public Optional<Profesor> updateProfesor(Profesor profesor, int id) {
        Optional<Profesor> profesorFound = getProfesorById(id);
        if (profesorFound.isPresent()) {
            profesorFound.get().setNombres(profesor.getNombres());
            profesorFound.get().setHorasClase(profesor.getHorasClase());
        }
        return profesorFound;
    }
    // public void delteProfesor(int id) {
    // List<Profesor> profesorList = profesorRepository.getProfesoresList();
    // profesorList.removeIf(profesor -> profesor.getId() == id);
    // }

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

}
