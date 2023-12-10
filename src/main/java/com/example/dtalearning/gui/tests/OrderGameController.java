package com.example.dtalearning.gui.tests;

import com.example.dtalearning.GameTypes.GameOrderObject;
import com.example.dtalearning.Services.Service;
import com.example.dtalearning.gui.PsychologistPageController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class OrderGameController {

    private Service service;
    @FXML
    public AnchorPane rootPane;
    public AnchorPane buttonsPane;
    public Label intrebareLabel;
    private Stage stage;

    private ArrayList<ToggleButton> clickedButtons;

    private GameOrderObject game;

    public void setData(Stage stage, GameOrderObject game, Service service) {
        this.stage = stage;
        this.service = service;
        this.game = game;
        clickedButtons = new ArrayList<>();
        initModel();
    }

    private void initModel() {
        List<ToggleButton> buttons = new ArrayList<>();

        intrebareLabel.setText(game.intrebare);

        List<String> strs = new ArrayList<>(game.strings);

        Collections.shuffle(strs);

        while (strs.equals(game.strings)) {
            Collections.shuffle(strs);
        }

        strs.forEach(str -> {
            ToggleButton button = new ToggleButton(str);
            buttons.add(button);

            EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (button.isSelected()) {
                        if (Objects.equals(game.strings.get(clickedButtons.size()), button.getText())) {
                            clickedButtons.add(button);
                            if (clickedButtons.size() == game.strings.size()) {
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
                            }
                        } else {
                            button.setSelected(false);
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
            ToggleButton button = buttons.get(i);
            buttonsPane.getChildren().add(button);
            buttonsPane.setTopAnchor(button, 10.0);
            buttonsPane.setLeftAnchor(button, i * 80.0);
        }
    }
}
