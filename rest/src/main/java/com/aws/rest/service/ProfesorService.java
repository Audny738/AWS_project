package com.aws.rest.service;

import java.util.List;

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

    public Profesor getProfesorById(int id) {
        return profesorRepository.getProfesoresList().stream().filter(profesor -> profesor.getId() == id).findFirst()
                .get();
    }

    public void addProfesor(Profesor profesor) {
        profesorRepository.getProfesoresList().add(profesor);
    }

    public void updateProfesor(Profesor profesor, int id) {
        int counter = 0;
        List<Profesor> profesorList = profesorRepository.getProfesoresList();
        for (Profesor profesor1 : profesorList) {
            if (profesor1.getId() == id) {
                profesorList.set(counter, profesor);
            }
            counter++;
        }
    }

    public void delteProfesor(int id) {
        List<Profesor> profesorList = profesorRepository.getProfesoresList();
        profesorList.removeIf(profesor -> profesor.getId() == id);
    }

}
