package com.example.dtalearning.gui.tests;

import com.example.dtalearning.Domain.Test;
import com.example.dtalearning.GameTypes.GameOrderObject;
import com.example.dtalearning.Services.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class OrderController {

    private Service service;

    public TextArea textArea;

    public TextField textField;

    public Stage stage;
    public TextField textFieldIntrebare;

    public void playGame(ActionEvent actionEvent) throws IOException {
        handleCreateGame(null);
        String objectsStringFromTextArea = textArea.getText();
        objectsStringFromTextArea.trim();
        String descriere = textField.getText();
        String intrebare = textFieldIntrebare.getText();

        List<String> objectStringList = Arrays.stream(objectsStringFromTextArea.split(" ")).toList();

        GameOrderObject game = new GameOrderObject(objectStringList, intrebare, descriere);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/polihackapp/views/orderGame-view.fxml"));
        AnchorPane userLayout = loader.load();
        Scene scene = new Scene(userLayout);
        stage.setHeight(400);
        stage.setWidth(600);
        stage.centerOnScreen();
        stage.setScene(scene);
        OrderGameController orderGameController = loader.getController();
        orderGameController.setData(stage, game, service);
    }

    public void setData(Stage stage, Service service) {
        this.stage = stage;
        this.service = service;
    }

    public void handleCreateGame(ActionEvent actionEvent) {
        String objectsStringFromTextArea = textArea.getText();
        objectsStringFromTextArea.trim();
        String descriere = textField.getText();
        String intrebare = textFieldIntrebare.getText();

        List<String> objectStringList = Arrays.stream(objectsStringFromTextArea.split(" ")).toList();

        Test test = new Test(objectStringList, "a", descriere, "Order", intrebare, null);
        this.service.addTest(test);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Game created successfully!");
        alert.setTitle("Create game");
        alert.showAndWait();
    }
}
