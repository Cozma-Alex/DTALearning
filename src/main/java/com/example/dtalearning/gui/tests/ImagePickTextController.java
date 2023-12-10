package com.example.dtalearning.gui.tests;

import com.example.dtalearning.Domain.Test;
import com.example.dtalearning.GameTypes.GamePozaTextPicker;
import com.example.dtalearning.Services.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

public class ImagePickTextController {

    private Service service;

    public AnchorPane rootPane;
    public TextArea textAreaAllOptions;
    public TextField textFieldGoodOptions;
    public TextField textFieldDescriere;
    public TextField textFieldIntrebare;
    private Stage stage;

    private Image image;

    private InputStream inputStream;

    private String fileUrl;
    
    public void handleAddImage(ActionEvent actionEvent) throws FileNotFoundException, MalformedURLException {

        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        fileUrl = file.toURI().toURL().toString();
        if (file != null){
            InputStream inputStream = new FileInputStream(file);
            this.image = new Image(inputStream);
            this.inputStream = inputStream;
        }

    }

    public void setData(Stage stage, Service service) {
        this.stage = stage;
        this.service = service;
    }

    public void handlePlayGame(ActionEvent actionEvent) throws IOException {
        handleCreateGame(null);

        String allOptionsString = textAreaAllOptions.getText();
        String goodOption = textFieldGoodOptions.getText();
        String intrebare = textFieldIntrebare.getText();
        String descriere = textFieldDescriere.getText();
        allOptionsString.trim();
        goodOption.trim();
        List<String> allOptionsListString = Arrays.stream(allOptionsString.split(" ")).toList();

        GamePozaTextPicker game = new GamePozaTextPicker(this.image, allOptionsListString, goodOption, intrebare, descriere);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/polihackapp/views/image-pickTextGame-view.fxml"));
        AnchorPane userLayout = loader.load();
        Scene scene = new Scene(userLayout);
        stage.setHeight(400);
        stage.setWidth(600);
        stage.centerOnScreen();
        stage.setScene(scene);
        ImagePickTextGameController imagePickTextGameController = loader.getController();
        imagePickTextGameController.setData(stage, game, service);

    }

    public void handleCreateGame(ActionEvent actionEvent) throws FileNotFoundException {
        String allOptionsString = textAreaAllOptions.getText();
        String goodOption = textFieldGoodOptions.getText();
        String intrebare = textFieldIntrebare.getText();
        String descriere = textFieldDescriere.getText();
        allOptionsString.trim();
        goodOption.trim();
        List<String> allOptionsListString = Arrays.stream(allOptionsString.split(" ")).toList();

        Test test = new Test(allOptionsListString, goodOption, descriere, "ImagePickText", intrebare,"F:\\Facultate\\AN2\\SEM1\\PoliHackApp\\src\\main\\resources\\com\\example\\polihackapp\\images\\appBackground.jpg");
        Long testID = this.service.addTest(test);
        Long imageID = this.service.addImage(fileUrl, descriere);
        this.service.addTestImage(testID, imageID, true);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Game created successfully!");
        alert.setTitle("Create game");
        alert.showAndWait();
    }
}
