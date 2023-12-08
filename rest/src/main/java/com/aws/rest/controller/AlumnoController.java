package com.aws.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aws.rest.entity.Alumno;
import com.aws.rest.error.AlumnoException;
import com.aws.rest.service.AlumnoService;

@RestController
@RequestMapping(value = "/alumnos")
public class AlumnoController {
    @Autowired
    private AlumnoService alumnoService;

    @GetMapping()
    public ResponseEntity<?> getAllAlumnos() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        try {
            List<Alumno> alumnos = alumnoService.getAllAlumnos();
            return new ResponseEntity<>(alumnos, responseHeaders, HttpStatus.OK);
        } catch (AlumnoException ex) {
            return new ResponseEntity<>(ex.getMessage(), responseHeaders, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAlumno(@PathVariable(value = "id") int id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        // Optional<?> alumno = alumnoService.getAlumnoById(id);
        // if (alumno.isPresent()) {
        // return new ResponseEntity<>(alumno, responHeaders, HttpStatus.OK);
        // } else {
        // return new ResponseEntity<>(responHeaders, HttpStatus.NOT_FOUND);
        // }
        try {
            return new ResponseEntity<>(alumnoService.getAlumnoById(id), responseHeaders, HttpStatus.OK);
        } catch (AlumnoException ex) {
            return new ResponseEntity<>(ex.getMessage(), responseHeaders, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping()
    public ResponseEntity<?> postAlumno(@RequestBody Alumno alumno) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        Optional<?> alumnoFound = alumnoService.addAlumno(alumno);
        if (alumnoFound.isPresent()) {
            return new ResponseEntity<>(alumno, responseHeaders, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(responseHeaders, HttpStatus.BAD_REQUEST);
        }

    }

    // @PutMapping("/{id}")
    // public ResponseEntity<?> putAlumno(@RequestBody Alumno alumno,
    // @PathVariable("id") int id) throws Exception {
    // HttpHeaders responseHeaders = new HttpHeaders();
    // responseHeaders.setContentType(MediaType.APPLICATION_JSON);
    // Optional<?> alumnoOptional = alumnoService.updateAlumno(alumno, id);
    // if (alumnoOptional.isPresent()) {
    // return new ResponseEntity<>(responseHeaders, HttpStatus.OK);
    // } else {
    // return new ResponseEntity<>(responseHeaders, HttpStatus.BAD_REQUEST);
    // }
    // }
    @PutMapping("/{id}")
    public ResponseEntity<?> putAlumno(@RequestBody Alumno alumno, @PathVariable("id") int id) {
        HttpHeaders responHeaders = new HttpHeaders();
        responHeaders.setContentType(MediaType.APPLICATION_JSON);
        try {
            alumnoService.updateAlumno(alumno, id);
            return new ResponseEntity<>(responHeaders, HttpStatus.OK);
        } catch (AlumnoException ex) {
            return new ResponseEntity<>(ex.getMessage(), responHeaders, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new RuntimeException();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAlumno(@PathVariable("id") int id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        try {
            alumnoService.deleteAlumno(id);
            return new ResponseEntity<>(responseHeaders, HttpStatus.OK);
        } catch (AlumnoException ex) {
            return new ResponseEntity<>(ex.getMessage(), responseHeaders, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException();
        }

        // if (flag) {
        // } else {
        // return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_FOUND);
        // }

    }
}
