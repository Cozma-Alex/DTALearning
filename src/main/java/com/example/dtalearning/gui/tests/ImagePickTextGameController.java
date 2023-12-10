package com.example.dtalearning.gui.tests;

import com.example.dtalearning.GameTypes.GamePozaTextPicker;
import com.example.dtalearning.Services.Service;
import com.example.dtalearning.gui.PsychologistPageController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ImagePickTextGameController {

    private Service service;
    public AnchorPane rootPane;
    public AnchorPane photoPane;
    public AnchorPane buttonsPane;
    public Label labelIntrebare;
    private Stage stage;

    private GamePozaTextPicker game;

    public void setData(Stage stage, GamePozaTextPicker game, Service service) {
        this.stage = stage;
        this.service = service;
        this.game = game;
        initModel();
    }

    private void initModel() {
        ImageView imageView = new ImageView(game.image);

        imageView.setFitHeight(200);
        imageView.setFitWidth(200);

        photoPane.getChildren().add(imageView);

        labelIntrebare.setText(game.intrebare);

        List<Button> buttons = new ArrayList<>();

        game.variants.forEach(variant -> {
            Button button = new Button(variant);
            buttons.add(button);

            button.setStyle("-fx-background-color: rgb(31, 85, 222);\n" +
                    "    -fx-text-fill: white;\n" +
                    "    -fx-font-size: 18px;\n" +
                    "    -fx-padding: 5 10 5 10;\n" +
                    "    -fx-background-radius: 5;");

            EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    {
                        String textButton = button.getText();
                        if (Objects.equals(textButton, game.correct_variant)) {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Final Test");
                            alert.setContentText("Ati rezolvat testul");
                            alert.showAndWait();
                            //stage.close();
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
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Alegere gresita");
                            alert.setContentText("Alege alta varianta");
                            alert.showAndWait();
                        }
                    }
                }
            };
            button.setOnAction(eventHandler);

        });

        for (int i = 0; i < buttons.size(); i++) {
            Button button = buttons.get(i);
            buttonsPane.getChildren().add(button);
            buttonsPane.setTopAnchor(button, 10.0);
            buttonsPane.setLeftAnchor(button, i * 80.0);
        }

    }
}
