package com.aws.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

import com.aws.rest.entity.Profesor;
import com.aws.rest.service.ProfesorService;

@RestController
@RequestMapping(value = "/profesores")
public class ProfesorController {
    @Autowired
    private ProfesorService profesorService;

    @GetMapping()
    public ResponseEntity<?> getAllProfesores() throws RuntimeException {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        try {
            List<Profesor> profesores = profesorService.getAllProfesor();
            return new ResponseEntity<>(profesores, responseHeaders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProfesor(@PathVariable(value = "id") int id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        Optional<?> profesor = profesorService.getProfesorById(id);
        if (profesor.isPresent()) {
            return new ResponseEntity<>(profesor, responseHeaders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<?> postProfesor(@RequestBody Profesor profesor) {
        HttpHeaders respoHeaders = new HttpHeaders();
        respoHeaders.setContentType(MediaType.APPLICATION_JSON);
        Optional<?> profesorFound = profesorService.addProfesor(profesor);
        if (profesorFound.isPresent()) {
            return new ResponseEntity<>(profesor, respoHeaders, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(respoHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putProfesor(@RequestBody Profesor profesor, @PathVariable(value = "id") int id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        Optional<?> profesorOptional = profesorService.updateProfesor(profesor, id);
        if (profesorOptional.isPresent()) {
            return new ResponseEntity<>(responseHeaders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(responseHeaders, HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProfesor(@PathVariable(value = "id") int id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        Optional<?> profesorOptional = profesorService.deleteProfesor(id);
        if (profesorOptional.isPresent()) {
            return new ResponseEntity<>(responseHeaders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_FOUND);
        }
    }
}
