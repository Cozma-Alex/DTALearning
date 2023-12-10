package com.example.dtalearning.gui;

import com.example.dtalearning.Domain.Patient;
import com.example.dtalearning.Domain.Psychologist;
import com.example.dtalearning.Domain.User;
import com.example.dtalearning.Services.Service;
import com.example.dtalearning.Session;
import com.example.dtalearning.Validators.ServiceException;
import com.example.dtalearning.gui.tests.ImagePickTextController;
import com.example.dtalearning.gui.tests.OrderController;
import com.example.dtalearning.gui.tests.TextPickImageController;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class PsychologistPageController {

    private Service service;
    private User loggedUser;
    public Label label_title;
    public Label create_test_label;
    public ComboBox<String> categoriesComboBox;
    private final ObservableList<Patient> model = FXCollections.observableArrayList();
    public TableView<Patient> patientsTableView;

    public TableColumn<Patient, String> patientFullName;

    public TableColumn<Patient, String> patientUsername;
    private Stage stage;

    @FXML
    private Label name;
    @FXML
    private Label username;
    @FXML
    private Label email;
    @FXML
    private TextField searchTextField;
    @FXML
    private CheckBox allCheckBox;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;

    public ImageView subscriptionImage;

    public void setData(Stage stage, Service service){
        this.stage = stage;
        ObservableList<String> options = FXCollections.observableArrayList("Pick Text After Image", "Pick Image After Text", "Order Objects");
        categoriesComboBox.setItems(options);
        setPsychologistInfo();
        Platform.runLater(() -> patientsTableView.lookup("TableHeaderRow").setVisible(false));
        patientsTableView.setStyle("-fx-table-header-background: transparent;");;

        Iterable<Patient> patients = service.getAllPatientsByPsychologist(loggedUser.getUsername());
        initModel(patients);
        searchTextField.textProperty().addListener(o->handleFilterPatients());

        this.service = service;

        Psychologist psychologist = (Psychologist) Session.getLoggedUser();
        if(Objects.equals(psychologist.getSubscription(), "Bronze")){
            System.out.println("Bronze");
            subscriptionImage.setImage(new Image("/com/example/polihackapp/images/bronze.png"));
        } else if (Objects.equals(psychologist.getSubscription(), "Silver")) {
            subscriptionImage.setImage(new Image("/com/example/polihackapp/images/silver.png"));
        } else if (Objects.equals(psychologist.getSubscription(), "Gold")) {
            subscriptionImage.setImage(new Image("/com/example/polihackapp/images/gold.png"));
        }
    }


    private void initModel(Iterable<Patient> patients){
        List<Patient> patientsList = StreamSupport.stream(patients.spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(patientsList);
    }

    public void initialize(){
        patientFullName.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getName()));
        patientUsername.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getUsername()));
        patientsTableView.setItems(model);
        addButton.setVisible(false);
    }

    public void setPsychologistInfo() {
        loggedUser = Session.getLoggedUser();
        name.setText(loggedUser.getName());
        username.setText(loggedUser.getUsername());
        email.setText(loggedUser.getEmail());
        label_title.setText("Dr. Psychologist " + loggedUser.getName());
    }

    public void handleFilterPatients() {
        String search = searchTextField.getText();
        if(!search.isBlank()){
            if(allCheckBox.isSelected()){
                initModel(this.service.getAllPatientsFiltered(search));
            }else{
                initModel(this.service.getPatientsFilteredOfUser(loggedUser.getUsername(), search));
            }
        }else {
            if(allCheckBox.isSelected()) {
                initModel(this.service.getAllPatients());
            }
            else{
                initModel(service.getAllPatientsByPsychologist(loggedUser.getUsername()));
            }

        }
    }

    public void handleClose(ActionEvent actionEvent) {
        this.stage.close();
    }

    public void allCheckBoxHandler(ActionEvent actionEvent){
        if(allCheckBox.isSelected()){
            initModel(service.getAllPatients());
            addButton.setVisible(true);
        }
        else{
            handleFilterPatients();
            addButton.setVisible(false);
        }
    }

    public void addButtonHandler(ActionEvent actionEvent){
        try {
            Patient patient = patientsTableView.getSelectionModel().getSelectedItem();
            service.addPatientPsychologist(loggedUser.getUsername(), patient.getUsername());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("User added");
            alert.show();
        } catch (ServiceException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getError());
            alert.show();
        }
    }

    public void deleteButtonHandler(ActionEvent actionEvent){
        if(!allCheckBox.isSelected()){
            Patient patient = patientsTableView.getSelectionModel().getSelectedItem();
            service.removePatientPsychologist(loggedUser.getUsername(),patient.getUsername());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("User deleted");
            alert.show();
            handleFilterPatients();
        }else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("All users is selected!");
            alert.show();
        }

    }

    public void homeworkHandler(ActionEvent actionEvent){
        Patient patient = patientsTableView.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/polihackapp/views/Homework.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            HomeworkController homeworkController = fxmlLoader.getController();
            homeworkController.setData(stage,service,patient);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleLogOut(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/polihackapp/views/Login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            LoginController loginController = fxmlLoader.getController();
            loginController.setData(stage,service);
            Session.clearSession();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleCreateTest(ActionEvent actionEvent) throws IOException {
        String selectedModel = categoriesComboBox.getSelectionModel().getSelectedItem();

        switch (selectedModel){
            case "Pick Text After Image":
                FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/com/example/polihackapp/views/image-pickText-view.fxml"));
                AnchorPane userLayout1 = loader1.load();
                Scene scene1 = new Scene(userLayout1);
                ImagePickTextController imagePickTextController = loader1.getController();
                stage.setScene(scene1);
                imagePickTextController.setData(stage, service);
                stage.setHeight(600);
                stage.setWidth(600);
                stage.centerOnScreen();
                break;
            case "Pick Image After Text":
                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/com/example/polihackapp/views/text-pickImage-view.fxml"));
                AnchorPane userLayout2 = loader2.load();
                Scene scene2 = new Scene(userLayout2);
                TextPickImageController textPickImageController = loader2.getController();
                stage.setScene(scene2);
                stage.setHeight(600);
                stage.setWidth(600);
                stage.centerOnScreen();
                textPickImageController.setData(stage, service);
                break;
            case "Order Objects":
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/polihackapp/views/order-view.fxml"));
                AnchorPane userLayout = loader.load();
                Scene scene = new Scene(userLayout);
                OrderController orderController = loader.getController();
                stage.setScene(scene);
                stage.setHeight(600);
                stage.setWidth(600);
                stage.centerOnScreen();
                orderController.setData(stage, service);
                break;
            default:
                break;
        }

    }
}