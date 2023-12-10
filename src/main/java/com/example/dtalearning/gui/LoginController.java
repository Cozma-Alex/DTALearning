package com.example.dtalearning.gui;

import com.example.dtalearning.Services.Service;
import com.example.dtalearning.Session;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class LoginController {

    private Service service;
    public Label label_title;

    private Stage stage;
    private FadeTransition fadeOut;

    @FXML
    private Label errorMessageLabel;

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    public void setData(Stage stage, Service service) {
        this.stage = stage;
        this.stage.setWidth(600);
        this.stage.setHeight(500);
        this.stage.centerOnScreen();
        this.service = service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public void handleClose(ActionEvent actionEvent) {
        this.stage.close();
    }

    public void handleRegister(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/polihackapp/views/Register.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            RegisterController registerController = fxmlLoader.getController();
            registerController.setData(stage, service);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleLogin(ActionEvent actionEvent) throws IOException {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (isValidCredentials(username, password).equals("patient")) {
            Session.setLoggedUser(service.findPatient(username));
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/polihackapp/views/PatientWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            PatientWindowController psychologistPageController = fxmlLoader.getController();
            psychologistPageController.setData(stage, service);
            stage.setScene(scene);
            stage.setWidth(600);
            stage.setHeight(650);
            stage.centerOnScreen();
        }
        if (isValidCredentials(username, password).equals("psychologist")) {
            Session.setLoggedUser(service.findPsychologist(username));
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/polihackapp/views/PsychologistPage.fxml"));
                Scene scene = new Scene(fxmlLoader.load());

                PsychologistPageController psychologistPageController = fxmlLoader.getController();
                psychologistPageController.setData(stage, service);
                stage.setScene(scene);
                stage.setWidth(1536);
                stage.setHeight(824);
                stage.setX(0);
                stage.setY(0);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            showErrorAlert("Invalid credentials. Try again...", "error-label");
        }
    }

    private String isValidCredentials(String username, String password) {
        try {
            service.findPatient(username);
            return "patient";
        } catch (Exception e) {
            try {
                service.findPsychologist(username);
                return "psychologist";
            } catch (Exception er) {
                showErrorAlert("Invalid user data!", "error-label");
                return "";
            }
        }
    }

    private void showErrorAlert(String message, String styleClass) {
        errorMessageLabel.setText(message);
        errorMessageLabel.getStyleClass().setAll(styleClass);
        errorMessageLabel.setVisible(true);

        fadeOut = new FadeTransition(Duration.seconds(2), errorMessageLabel);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.play();
    }
}
