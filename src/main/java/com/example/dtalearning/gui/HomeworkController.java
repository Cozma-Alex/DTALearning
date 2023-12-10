package com.example.dtalearning.gui;

import com.example.dtalearning.Domain.Patient;
import com.example.dtalearning.Domain.Test;
import com.example.dtalearning.Services.Service;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class HomeworkController {
    private Service service;
    private Patient patient;

    private Stage stage;
    @FXML
    private TableView<Test> homeworkTableView;
    @FXML
    private TableColumn<Test, String> descriereTableColumn;
    private final ObservableList<Test> model = FXCollections.observableArrayList();

    public void setData(Stage stage, Service service, Patient patient){
        this.stage = stage;
        this.service = service;
        this.patient = patient;
        Iterable<Test> tests = service.getAllTests();
        initModel(tests);
    }

    public void initialize(){
        descriereTableColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getDescription()));
        homeworkTableView.setItems(model);
    }

    public void initModel(Iterable<Test> tests){
        List<Test> testList = StreamSupport.stream(tests.spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(testList);
    }

    public void closeHandler(ActionEvent ev) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/polihackapp/views/PsychologistPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        PsychologistPageController loginController = fxmlLoader.getController();
        loginController.setData(stage,service);
        stage.setMaxHeight(824);
        stage.setMaxHeight(1536);
        stage.setResizable(false);
        stage.setX(0);
        stage.setY(0);
//        stage.centerOnScreen();
    }

    public void addHandler(ActionEvent ev){
        Test test = (Test) homeworkTableView.getSelectionModel().getSelectedItem();
        service.addHomework(patient.getUsername(), test.getTestID());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Homework added!");
        alert.show();
    }
}
