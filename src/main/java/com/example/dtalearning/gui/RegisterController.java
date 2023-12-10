package com.example.dtalearning.gui;

import com.example.dtalearning.Domain.Patient;
import com.example.dtalearning.Domain.Psychologist;
import com.example.dtalearning.Services.Service;
import com.example.dtalearning.Session;
import com.example.dtalearning.Validators.ValidatorException;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;

public class RegisterController {

    Service service;
    private FadeTransition fadeOut;
    @FXML
    private CheckBox patientCheckBox;

    @FXML
    private CheckBox psychologistCheckBox;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField passwordTextField;

    @FXML
    private Label nameXLabel;
    @FXML
    private Label usernameXLabel;
    @FXML
    private Label phoneXLabel;
    @FXML
    private Label emailXLabel;
    @FXML
    private Label passwordXLabel;
    @FXML
    private Label accountTypeX;

    private Stage stage;
    public void setData(Stage stage, Service service) {
        this.stage = stage;
        this.stage.setWidth(650);
        this.stage.setHeight(700);
        this.stage.centerOnScreen();
        this.service = service;
        setInvisibleXFields();
    }

    public void handleClose(ActionEvent actionEvent) {
        this.stage.close();
    }


    public void handleCheckPatient(ActionEvent actionEvent) {
        psychologistCheckBox.setSelected(false);
    }

    public void handleCheckPsychologist(ActionEvent actionEvent) {
        patientCheckBox.setSelected(false);
    }

    public void handleOpenLogin(ActionEvent actionEvent) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/polihackapp/views/Login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            LoginController loginController = fxmlLoader.getController();
            loginController.setData(stage,service);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleRegisterAccount(ActionEvent actionEvent) {
        String name = nameTextField.getText();
        String username = usernameTextField.getText();
        String phone = phoneTextField.getText();
        String email = emailTextField.getText();
        String password = passwordTextField.getText();
        try {
            if(phone.isEmpty()) {
                try {
                    ArrayList<String> errs = new ArrayList<String>();
                    errs.add("Phone Number cannot be empty");
                    throw new ValidatorException(errs);
                }catch (ValidatorException e) {
                    showX(e.getError());
                }
            }
            if(patientCheckBox.isSelected() ) {
                Patient patient = new Patient(name,username,password,email);
                service.addPatient(patient);
                handleOpenLogin(new ActionEvent());
                Session.setLoggedUser(patient);
            }
            else if (psychologistCheckBox.isSelected()) {
                Psychologist psychologist = new Psychologist(name, username, password, email, phone);
                service.addPsychologist(psychologist);
                Psychologist psychologist1 = service.findPsychologist(username);
                loadSubscriptionWindow();
                Session.setLoggedUser(psychologist1);
            }
            else{
                try {
                    ArrayList<String> errs = new ArrayList<String>();
                    errs.add("Account type is empty");
                    throw new ValidatorException(errs);
                }catch (ValidatorException e) {
                    showX(e.getError());
                }
            }
        } catch (ValidatorException e) {
            showX(e.getError());
        }
        setInvisibleXFields();
    }

    private void loadSubscriptionWindow() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/polihackapp/views/Subscriptions.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            SubscriptionsController subscriptionsController = fxmlLoader.getController();
            subscriptionsController.setData(stage,service);
            stage.setScene(scene);
            stage.setWidth(1536);
            stage.setHeight(824);
            stage.setX(0);
            stage.setY(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showX(String i){
        if(i.contains("Username")) {
            fadeOut = new FadeTransition(Duration.seconds(2), usernameXLabel);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.play();
        }
        if  (i.contains("Name")) {
            fadeOut = new FadeTransition(Duration.seconds(2), nameXLabel);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.play();
        }
        if  (i.contains("Email")) {
            fadeOut = new FadeTransition(Duration.seconds(2), emailXLabel);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.play();
        }
        if  (i.contains("Password")) {
            fadeOut = new FadeTransition(Duration.seconds(2), passwordXLabel);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.play();
        }
        if  (i.contains("Phone")) {
            fadeOut = new FadeTransition(Duration.seconds(2), phoneXLabel);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.play();
        }
        if(i.contains("Account")){
            fadeOut = new FadeTransition(Duration.seconds(2), accountTypeX);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.play();
        }
    }

    private void setInvisibleXFields(){
        accountTypeX.setOpacity(0.0);
        passwordXLabel.setOpacity(0.0);
        emailXLabel.setOpacity(0.0);
        phoneXLabel.setOpacity(0.0);
        usernameXLabel.setOpacity(0.0);
        nameXLabel.setOpacity(0.0);
    }
}
