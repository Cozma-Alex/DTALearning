package com.example.dtalearning.gui;

import com.example.dtalearning.Domain.Psychologist;
import com.example.dtalearning.Repositories.*;
import com.example.dtalearning.Services.Service;
import com.example.dtalearning.Validators.PatientValidator;
import com.example.dtalearning.Validators.ValidatorPsychologist;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloApplication extends Application {

    private static String url;

    private static String username;

    private static String password;

    @Override
    public void start(Stage stage) throws IOException {


        var repoPatients = new RepoDBPatients(url, username, password);
        PatientValidator validatorPatient = new PatientValidator(repoPatients);
        Repository<Long, Psychologist> repoPsychologists = new RepoDBPsychologists(url, username, password);
        ValidatorPsychologist validatorPsychologist = new ValidatorPsychologist(repoPsychologists);
        RepoDBTests repoTests = new RepoDBTests(url, username, password);
        RepoDBImages repoImages = new RepoDBImages(url, username, password);
        Service service = new Service(repoPatients, repoPsychologists, repoTests, repoImages, validatorPatient, validatorPsychologist);

        ValidatorPsychologist validator = new ValidatorPsychologist(repoPsychologists);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/polihackapp/views/SplashScreen.fxml"));
            AnchorPane pane = loader.load();
            SplashScreenController controller = loader.getController();
            controller.setData(stage, service);
            Scene scene = new Scene(pane);
            stage.initStyle(StageStyle.UNDECORATED);

            stage.setScene(scene);
            stage.show();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        url = args[0];
        username = args[1];
        password = args[2];
        launch();
    }
}