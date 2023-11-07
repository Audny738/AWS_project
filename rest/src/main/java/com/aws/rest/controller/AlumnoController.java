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

import com.aws.rest.entity.Alumno;
import com.aws.rest.service.AlumnoService;

@RestController
@RequestMapping(value = "/alumnos")
public class AlumnoController {
    @Autowired
    private AlumnoService alumnoService;

    @GetMapping()
    public List<Alumno> getAllAlumnos() {
        return alumnoService.getAllAlumnos();
    }

    @GetMapping("/{id}")
    public Alumno getAlumno(@PathVariable(value = "id") int id) {
        return alumnoService.getAlumnoById(id);
    }

    @PostMapping()
    public void postAlumno(@RequestBody Alumno alumno) {
        alumnoService.addAlumno(alumno);
    }

    @PutMapping("/{id}")
    public void putAlumno(@RequestBody Alumno alumno, @PathVariable("id") int id) {
        alumnoService.updateAlumno(alumno, id);
    }

    @DeleteMapping("/{id}")
    public void deleteAlumno(@PathVariable("id") int id) {
        alumnoService.deleteAlumno(id);
    }
}
