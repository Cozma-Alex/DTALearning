package com.example.dtalearning.Validators;


import com.example.dtalearning.Domain.Patient;
import com.example.dtalearning.Repositories.Repository;

import java.util.ArrayList;

public class PatientValidator implements Validator<Patient> {
    Repository<Long, Patient> repo;

    public PatientValidator(Repository<Long, Patient> repo) {
        this.repo = repo;
    }

    @Override
    public void validate(Patient entity) throws ValidatorException {
        ArrayList<String> errs = new ArrayList<>();
        if(entity==null)
            errs.add("Psychologist is null");
        else
        {
            if(repo.findOne(entity.getUsername()).isPresent())
                errs.add("Username is taken");
            if (entity.getName().isBlank())
                errs.add("Name cannot be empty");
            if (entity.getEmail().isBlank())
                errs.add("Email cannot be empty");
            if (entity.getPassword().isBlank())
                errs.add("Password cannot be empty");
            if (entity.getUsername().isBlank())
                errs.add("Username cannot be empty");
        }

        if(!errs.isEmpty())
            throw new ValidatorException(errs);
    }
}