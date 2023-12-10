package com.example.dtalearning.GameTypes;

import java.util.List;

public class GameOrderObject {

    public List<String> strings;

    public String descriere ;

    public String intrebare;

    public GameOrderObject(List<String> strings, String intrebare, String descriere) {
        this.strings = strings;
        this.intrebare = intrebare;
        this.descriere = descriere;
    }
}
