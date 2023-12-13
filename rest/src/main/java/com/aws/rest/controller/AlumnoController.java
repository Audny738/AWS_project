package com.aws.rest.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

// import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;


import com.aws.rest.config.AmazonS3Service;
import com.aws.rest.config.AmazonSNSService;
import com.aws.rest.config.DynamoDBConfiguration;
import com.aws.rest.entity.Alumno;
import com.aws.rest.entity.SesionModel;
import com.aws.rest.error.AlumnoException;
import com.aws.rest.service.AlumnoService;


import aj.org.objectweb.asm.Attribute;

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

    @PutMapping("/{id}")
    public ResponseEntity<?> putAlumno(@RequestBody Alumno alumno, @PathVariable("id") int id) throws Exception {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        Optional<?> alumnoOptional = alumnoService.updateAlumno(alumno, id);
        if (alumnoOptional.isPresent()) {
            return new ResponseEntity<>(responseHeaders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAlumno(@PathVariable("id") int id) throws Exception{
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        Optional<?> alumnoOptional = alumnoService.deleteAlumno(id);
        if (alumnoOptional.isPresent()) {
            return new ResponseEntity<>(responseHeaders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/fotoPerfil")
    public ResponseEntity<?> postImageS3Bucket(@RequestParam("foto") MultipartFile foto, @PathVariable("id") int id) throws Exception{
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        try {
            Optional<Alumno> alumnoFound = alumnoService.getAlumnoById(id);
            if (alumnoFound.isPresent()) {
                Alumno alumno = alumnoFound.get();
                AmazonS3Service amazonS3Service = new AmazonS3Service();
                File convertedFile = convertMultiPartToFile(foto);
                String key = "foto-"+alumno.getMatricula()+"-"+alumno.getApellidos()+"-"+alumno.getNombres();
                amazonS3Service.getS3Client().putObject(PutObjectRequest.builder().bucket(amazonS3Service.getBucketName()).key(key).build(), convertedFile.toPath());
                alumno.setFotoPerfilURL("https://s3.amazonaws.com/"+amazonS3Service.getBucketName()+"/"+key);
                Optional<Alumno> alumnoAgregado = alumnoService.addAlumno(alumno);
                return new ResponseEntity<>(alumnoAgregado.get(),responseHeaders, HttpStatus.OK);
            } else{
                return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return new ResponseEntity<>(responseHeaders,HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/{id}/email")
    public ResponseEntity<?> enviarNotificacion(@PathVariable("id") int id){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        try {
            Optional<Alumno> alumnoFound = alumnoService.getAlumnoById(id);
            if (alumnoFound.isPresent()) {
                AmazonSNSService snsService = new AmazonSNSService();
                String message = "La calificacion final del alumno" + alumnoFound.get().getNombres()+ " "+ alumnoFound.get().getApellidos() + "es de: " + alumnoFound.get().getPromedio();
                snsService.getSnsClient().publish(PublishRequest.builder().topicArn(snsService.getTopicARN()).message(message).build());
                return new ResponseEntity<>(responseHeaders, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_FOUND);
        }
    }
    
    

    @PostMapping("/{id}/session/login")
    public ResponseEntity<?> postSession(@RequestBody Map<String, String> requestBody, @PathVariable("id") int id){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        try {
            Optional<Alumno> alumnoFound = alumnoService.getAlumnoById(id);
            if (alumnoFound.isPresent()) {
                Alumno alumno = alumnoFound.get();
                String passwordRequest = requestBody.get("password"); 
                if (alumno.getPassword().equals(passwordRequest)) {
                    SesionModel sesionModel = new SesionModel(id);
                    DynamoDBConfiguration alumnoSesion = new DynamoDBConfiguration();

                    Map<String, AttributeValue> item = new HashMap<>();
                    item.put("id", AttributeValue.builder().s(sesionModel.getId()).build());
                    item.put("fecha", AttributeValue.builder().n(Integer.toString(sesionModel.getFecha())).build());
                    item.put("alumnoId", AttributeValue.builder().n(Integer.toString(sesionModel.getAlumnoId())).build());
                    item.put("active", AttributeValue.builder().bool(sesionModel.isActive()).build());
                    item.put("sessionString", AttributeValue.builder().s(sesionModel.getSessionString()).build());

                    PutItemRequest putItemRequest = PutItemRequest.builder().tableName("sesiones-alumnos").item(item).build();

                    alumnoSesion.getDynamoClient().putItem(putItemRequest);
                    return new ResponseEntity<>(sesionModel, responseHeaders, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(responseHeaders, HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/session/verify")
    public void postVerify(@RequestBody Map<String, String> sessionString, @PathVariable("id") int id){
    }

    @PostMapping("/{id}/session/logout")
    public void postLogout(@RequestBody String password, @PathVariable("id") int id){
        
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        file.transferTo(convertedFile);
        return convertedFile;
    }

}   

