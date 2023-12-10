package com.example.dtalearning.Validators;


import com.example.dtalearning.Domain.Test;

public class ValidatorTest implements Validator<Test> {

    @Override
    public void validate(Test entity) throws ValidationException {
        if (entity.getVariants().isEmpty() || entity.getDescription().isEmpty() || entity.getCorrectAnswer().isEmpty())
            throw new ValidationException("Invalid user data!");
        if (entity.getVariants() == null || entity.getDescription() == null || entity.getCorrectAnswer() == null)
            throw new ValidationException("Invalid user data!");
    }
}
