package com.example.dtalearning.Validators;

public interface Validator<T> {
    void validate(T entity) throws ValidatorException;
}

