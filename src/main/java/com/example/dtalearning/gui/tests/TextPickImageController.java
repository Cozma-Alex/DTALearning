package com.example.dtalearning.gui.tests;

import com.example.dtalearning.Domain.Test;
import com.example.dtalearning.GameTypes.GameTextImagePicker;
import com.example.dtalearning.Services.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class TextPickImageController {

    private Service service;

    public TextField textFieldDescriere;
    @FXML
    private TextField textFieldIntrebare;
    @FXML
    private Button buttonWrongImages;
    @FXML
    private Button buttonCorrectImage;
    private Stage stage;
    private List<Image> images;

    private List<InputStream> inputStreams;

    private List<String> urls = new ArrayList<>();
    private int correctImagePoz;

    public void setData(Stage stage, Service service) {
        this.stage = stage;
        this.service = service;
        correctImagePoz = 0;
        images = new ArrayList<>();
    }

    public void handleAddWrongImages(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Images");
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(stage);
        if (selectedFiles != null) {
            selectedFiles.forEach(file -> {
                try {
                    InputStream inputStream = new FileInputStream(file);
                    Image image = new Image(inputStream);
                    images.add(image);
                    inputStreams.add(inputStream);
                    urls.add(file.toURI().toURL().toString());

                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }




    public void handleAddCorrectImage(ActionEvent actionEvent) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            InputStream inputStream = new FileInputStream(file);
            Image image = new Image(inputStream);
            correctImagePoz = images.size();
            images.add(image);
            inputStreams.add(inputStream);
        }
    }

    public void handlePlayGame(ActionEvent actionEvent) throws IOException {
        String intrebareString = textFieldIntrebare.getText();
        String descriereString = textFieldDescriere.getText();
        GameTextImagePicker game = new GameTextImagePicker(images, intrebareString, correctImagePoz, descriereString);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/polihackapp/views/text-pickImageGame-view.fxml"));
        AnchorPane userLayout = loader.load();
        Scene scene = new Scene(userLayout);
        stage.setScene(scene);
        stage.setHeight(500);
        stage.setWidth(600);
        stage.centerOnScreen();
        TextPickImageGameController orderGameController = loader.getController();
        orderGameController.setData(stage, game);

    }

    public void handleCreateGame(ActionEvent actionEvent){
        String intrebareString = textFieldIntrebare.getText();
        String descriereString = textFieldDescriere.getText();

        Test test = new Test(descriereString, "TextPickImage", intrebareString, null);
        Long testID = this.service.addTest(test);
        for (int i = 0; i < images.size(); i++) {
            Long imageId = this.service.addImage(urls.get(i), descriereString);
            this.service.addTestImage(testID, imageId, correctImagePoz == i);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Game created successfully!");
        alert.setTitle("Create game");
        alert.showAndWait();
    }
}
