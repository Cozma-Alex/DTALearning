package com.example.dtalearning;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class SplashScreenController implements Initializable {

    @FXML
    public AnchorPane logoPane;

    @FXML
    private Pane spinnerPane;

    @FXML
    private AnchorPane rootPane;


    StartApplication main;
    Stage stage;

    public void main(Stage stage, StartApplication main) {
        this.main = main;
        this.stage = stage;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        logoPane.setVisible(false);
        TranslateTransition translateTransition3 = new TranslateTransition(Duration.seconds(0.5), logoPane);
        translateTransition3.setByY(700);
        translateTransition3.play();

        translateTransition3.setOnFinished(event -> {
            logoPane.setVisible(true);
            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(1), logoPane);
            translateTransition1.setByY(-700);
            translateTransition1.play();

            translateTransition1.setOnFinished(event1 -> {

                            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), spinnerPane);
                            fadeTransition.setFromValue(0);
                            fadeTransition.setToValue(1);
                            fadeTransition.play();

                            fadeTransition.setOnFinished(event4 -> {

                                        FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(4), rootPane);
                                        fadeTransition1.setFromValue(1);
                                        fadeTransition1.setToValue(0.1);
                                        fadeTransition1.play();

                                        fadeTransition1.setOnFinished(event5 -> {
                                            main.closeStage();
                                        });

                                    }
                            );
                        }
                );
            });
    }
}
