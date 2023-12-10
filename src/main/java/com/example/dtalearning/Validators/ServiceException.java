package com.example.dtalearning.Validators;

public class ServiceException extends Exception{
    private String error;
    public ServiceException(String s){
        this.error = s;
    }

    public String getError(){
        return this.error;
    }

    @Override
    public String toString() {
        return "UserError{" +
                "error='" + error + '\'' +
                '}';
    }
}