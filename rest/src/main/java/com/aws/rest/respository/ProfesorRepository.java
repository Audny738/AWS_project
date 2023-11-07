package com.aws.rest.respository;

import com.aws.rest.entity.Profesor;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class ProfesorRepository {
    private List<Profesor> profesoresList = new ArrayList<>();

    public List<Profesor> getProfesoresList() {
        return profesoresList;
    }

}
