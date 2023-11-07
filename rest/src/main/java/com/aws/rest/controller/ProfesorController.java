package com.aws.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aws.rest.entity.Profesor;
import com.aws.rest.service.ProfesorService;

@RestController
@RequestMapping(value = "/profesores")
public class ProfesorController {
    @Autowired
    private ProfesorService profesorService;

    @GetMapping()
    public List<Profesor> getAllProfesores() {
        return profesorService.getAllProfesor();
    }

    @GetMapping("/{id}")
    public Profesor getProfesor(@PathVariable(value = "id") int id) {
        return profesorService.getProfesorById(id);
    }

    @PostMapping()
    public void postProfesor(@RequestBody Profesor profesor) {
        profesorService.addProfesor(profesor);
    }

    @PutMapping("/{id}")
    public void putProfesor(@RequestBody Profesor profesor, @PathVariable(value = "id") int id) {
        profesorService.updateProfesor(profesor, id);
    }

    @DeleteMapping("/{id}")
    public void deleteProfesor(@PathVariable(value = "id") int id) {
        profesorService.delteProfesor(id);
    }
}
