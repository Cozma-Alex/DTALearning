package com.example.dtalearning.Validators;

import java.util.ArrayList;

public class ValidatorException extends Exception{
    private ArrayList<String> errs = new ArrayList<>();

    @Override
    public String toString() {
        return "ValidatorException{" +
                "errs=" + errs +
                '}';
    }

    public ValidatorException(ArrayList<String> list){
        this.errs = list;
    }

    public String getError(){
        return String.join("\n",this.errs);
    }


}