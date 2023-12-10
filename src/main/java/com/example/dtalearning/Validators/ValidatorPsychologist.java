package com.example.dtalearning.Validators;


import com.example.dtalearning.Domain.Psychologist;
import com.example.dtalearning.Repositories.Repository;

import java.util.ArrayList;

public class ValidatorPsychologist implements Validator<Psychologist>{
    Repository<Long, Psychologist> repo;

    public ValidatorPsychologist(Repository<Long, Psychologist> repo) {
        this.repo = repo;
    }

    @Override
    public void validate(Psychologist entity) throws ValidatorException {
        ArrayList<String> errs = new ArrayList<>();
        if(entity==null)
            errs.add("Psychologist is null");
        else
        {
            if(repo.findOne(entity.getUsername()).isPresent())
                errs.add("username is taken");
            if (entity.getName().isEmpty())
                errs.add("Name cannot be empty");
            if (entity.getEmail().isEmpty())
                errs.add("Email cannot be empty");
            if (entity.getPassword().isEmpty())
                errs.add("Password cannot be empty");
            if (entity.getUsername().isEmpty())
                errs.add("Username cannot be empty");
            if (entity.getPhoneNumber().isEmpty() || !entity.getPhoneNumber().matches("\\d+"))
                errs.add("Phone number cannot be empty");
        }

        if(!errs.isEmpty())
            throw new ValidatorException(errs);

        //TODO:change Array to string
    }
}
