package com.example.dtalearning.Domain;


import java.util.Optional;

public class Patient extends User {
    private Optional<Long> psychologistID;

    public Patient(String name, String username, String password, String email,Optional<Long> psychologistID) {
        super(name, username, password, email);
        this.psychologistID = psychologistID;
    }

    public Patient(Long id,String name, String username, String password, String email) {
        super(name, username, password, email);
        setId(id);
    }
    public Patient(String name, String username, String password, String email) {
        super(name, username, password, email);
    }

    public Patient(Long id, String name, String username, String password, String email,Optional<Long> psychologistID) {
        super(name, username, password, email);
        this.psychologistID = psychologistID;
        setId(id);
    }


//    public Patient(String name, String username, String password, String email, String subscription, java.sql.Date dateOfBirth, String description) {
//        super(name, username, password, email);
//        this.subscription = subscription;
//        this.dateOfBirth = dateOfBirth;
//        this.description = description;
//    }
//    public Patient(Long id, String name, String username, String password, String email, String subscription, java.sql.Date dateOfBirth, String description, ArrayList<Long> homeworks, Optional<Long> psychologistID) {
//        super(name, username, password, email);
//        this.subscription = subscription;
//        this.dateOfBirth = dateOfBirth;
//        this.description = description;
//        this.homeworks = homeworks;
//        this.psychologistID = psychologistID;
//        setId(id);
//    }

    public Optional<Long> getPsychologistID() {
        return psychologistID;
    }

    public void setPsychologistID(Optional<Long> psychologistID) {
        this.psychologistID = psychologistID;
    }

    public void removePsychlogistId(){
        this.psychologistID = Optional.empty();
    }

    @Override
    public String toString() {
        return "Patient{" +
                super.toString() +
                ", psychologistID=" + psychologistID +
                "}\n";
    }
}