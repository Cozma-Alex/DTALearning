package com.example.dtalearning.Domain;

public class Psychologist extends User {

    private String phoneNumber;

    private String subscription;

    public Psychologist(String name, String username, String password, String email, String phoneNumber) {
        super(name, username, password, email);
        this.phoneNumber = phoneNumber;
    }

    public Psychologist(Long id, String name, String username, String password, String email, String phoneNumber,String sub) {
        super(name, username, password, email);
        this.phoneNumber = phoneNumber;
        setId(id);
        this.subscription = sub;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Psychologist{" +
                super.toString() +
                "phoneNumber='" + phoneNumber + '\'' +
                "}\n";
    }
}