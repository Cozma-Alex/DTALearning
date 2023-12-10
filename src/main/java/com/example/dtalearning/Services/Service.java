package com.example.dtalearning.Services;


import com.example.dtalearning.Domain.MyImage;
import com.example.dtalearning.Domain.Patient;
import com.example.dtalearning.Domain.Psychologist;
import com.example.dtalearning.Domain.Test;
import com.example.dtalearning.Repositories.RepoDBImages;
import com.example.dtalearning.Repositories.RepoDBPatients;
import com.example.dtalearning.Repositories.RepoDBTests;
import com.example.dtalearning.Repositories.Repository;
import com.example.dtalearning.Validators.ServiceException;
import com.example.dtalearning.Validators.Validator;
import com.example.dtalearning.Validators.ValidatorException;
import com.example.dtalearning.Validators.ValidatorTest;

import java.util.List;
import java.util.Optional;

public class Service {

    private RepoDBPatients repoPatients;
    private Repository<Long, Psychologist> repoPsychologists;
    private RepoDBTests repoTests;
    private RepoDBImages repoImages;
    private Validator<Patient> validatorPatients;
    private Validator<Psychologist> validatorPsychologist;
    private Validator<Test> validatorTests;

    public Service(RepoDBPatients patientRepository, Repository<Long, Psychologist> psychologistRepository, RepoDBTests repoTests, RepoDBImages repoImages, Validator<Patient> validatorPatients, Validator<Psychologist> validatorPsychologist) {
        this.repoPatients = patientRepository;
        this.repoPsychologists = psychologistRepository;
        this.repoTests = repoTests;
        this.repoImages = repoImages;
        this.validatorPatients = validatorPatients;
        this.validatorPsychologist = validatorPsychologist;
        this.validatorTests = new ValidatorTest();
    }

    public void addPatient(Patient patient) throws ValidatorException {
        validatorPatients.validate(patient);
        repoPatients.save(patient);
    }
    public void addPsychologist(Psychologist psychologist) throws ValidatorException {
        validatorPsychologist.validate(psychologist);
        repoPsychologists.save(psychologist);
    }


    public void removePatient(Patient patient) throws ValidatorException {
        validatorPatients.validate(patient);
        if (repoPatients.findOne(patient.getId()).isPresent())
            repoPatients.delete(patient.getId());
        else System.out.println("Cererea nu exista!");
    }
    public void removePatient(String username) throws ValidatorException {
        if (repoPatients.findOne(username).isPresent())
            repoPatients.delete(username);
        else System.out.println("Cererea nu exista!");
    }

    public void removePsychologist(Psychologist psychologist) throws ValidatorException {
        validatorPsychologist.validate(psychologist);
        if(repoPsychologists.findOne(psychologist.getId()).isPresent())
            repoPsychologists.delete(psychologist.getId());
        else System.out.println("Nu existaa");
    }
    public void removePsychologist(String username) throws ValidatorException {
        if(repoPsychologists.findOne(username).isPresent())
            repoPsychologists.delete(username);
        else System.out.println("Nu existaa");
    }


    public void updatePatient(Patient patient) {
        repoPatients.update(patient);
    }
    public void updatePsychologist(Psychologist psychologist) {
        repoPsychologists.update(psychologist);
    }


    public Psychologist findPsychologist(Long id){
        Optional<Psychologist> psychologist = repoPsychologists.findOne(id);
        if(psychologist.isPresent())
            return psychologist.get();
        else
            throw new IllegalArgumentException("Utilizatorul nu exista!");
    }
    public Psychologist findPsychologist(String username){
        Optional<Psychologist> psychologist = repoPsychologists.findOne(username);
        if(psychologist.isPresent())
            return psychologist.get();
        else
            throw new IllegalArgumentException("Utilizatorul nu exista!");
    }
    public Patient findPatient(Long patientID) {
        Optional<Patient> patient = repoPatients.findOne(patientID);
        if(patient.isPresent())
            return patient.get();
        else
            throw new IllegalArgumentException("Utilizatorul nu exista!");
    }
    public Patient findPatient(String username) {
        Optional<Patient> patient = repoPatients.findOne(username);
        if(patient.isPresent())
            return patient.get();
        else
            throw new IllegalArgumentException("Utilizatorul nu exista!");
    }


    public Iterable<Patient> getPatientsFilteredOfUser(String psychologistUsername, String search){
        return repoPatients.findPatientsByPsychologistAndName(psychologistUsername, search);
    }

    public Iterable<Patient> getAllPatientsByPsychologist(String psychologistUsername) {
        return repoPatients.findPatientsByPsychologist(psychologistUsername);
    }

    public Iterable<Patient> getAllPatients() {
        return repoPatients.findAll();
    }
    public Iterable<Psychologist> getAllPsychologist(){
        return repoPsychologists.findAll();
    }

    public void addPatientPsychologist(String usernamePsychologist, String usernamePatient) throws ServiceException {
        Optional<Psychologist>  psychologist= Optional.of(findPsychologist(usernamePsychologist));
        Optional<Patient> patient = Optional.of(findPatient(usernamePatient));

        if(psychologist.isPresent() && patient.isPresent()) {


            repoPatients.addPatient(patient.get().getId(), psychologist.get().getId());
//            updatePsychologist(psychologist.get());
//            updatePatient(patient.get());
        }
    }

    public void removePatientPsychologist(String usernamePsychologist, String usernamePatient){
        Optional<Psychologist>  psychologist= Optional.of(findPsychologist(usernamePsychologist));
        Optional<Patient> patient = Optional.of(findPatient(usernamePatient));

        if(psychologist.isPresent() && patient.isPresent()) {
            repoPatients.removePatient(patient.get().getId(), psychologist.get().getId());
//            patient.get().removePsychlogistId();
//            updatePsychologist(psychologist.get());
//            updatePatient(patient.get());
        }
    }

    public List<Test> getAllTestsAssignedToUser(Long id_patient){
        return this.repoTests.getAllTestsAssignedToUser(id_patient);
    }

    public void addHomework(String usernamePatient, Long homework) {
        Optional<Test> testOptional = repoTests.findOne(homework);
        Optional<Patient> patient = Optional.of(findPatient(usernamePatient));
        if(patient.isPresent() && testOptional.isPresent()){
            repoPatients.addHomework(homework, patient.get().getId());
//            updatePatient(patient.get());
        }
    }

    public void removeHomework(String usernamePatient, Long homework) {
        Optional<Patient> patient = Optional.of(findPatient(usernamePatient));
        if(patient.isPresent()){
            repoPatients.removeHomework(homework, patient.get().getId());
//            updatePatient(patient.get());
        }
    }

    public Iterable<Patient> getAllPatientsFiltered(String substring){
        return this.repoPatients.getAllFiltered(substring);
    }


    /* TESTS AND IMAGES PART */
    public Long addImage(String image, String description) {
        try {
            return repoImages.save(image, description);
        } catch(Exception e) {
            throw new IllegalArgumentException("Image couldn't have been saved");
        }
    }

    public MyImage searchForImage(Long imageID) {
        Optional<MyImage> image = repoImages.findOne(imageID);
        if (image.isPresent()) {
            return image.get();
        }
        return image.get();
    }

    public void addTestImage(Long id_test, Long id_image, Boolean correct){
        this.repoTests.addTestImage(id_test, id_image, correct);
    }

    public List<MyImage> getAllImages() {
        return repoImages.findAll();
    }

    public Long addTest(Test test) {
        try {
            validatorTests.validate(test);
            return repoTests.save(test.getVariants(), test.getCorrectAnswer(), test.getDescription(), test.getCategory(),test.getPreviewImage());
        } catch(Exception e) {
            throw new IllegalArgumentException("Test couldn't have been saved");
        }
    }

    public Test searchForTest(Long testID) {
        Optional<Test> test = repoTests.findOne(testID);
        if (test.isPresent()) {
            return test.get();
        }
        return test.get();
    }

    public List<Test> getAllTests() {
        return repoTests.findAll();
    }

    public List<MyImage> getAllImagesTest(Long id) {
        return repoImages.getAllImagesTest(id);
    }
}
