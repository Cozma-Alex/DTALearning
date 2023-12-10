package com.example.dtalearning.gui;

import com.example.dtalearning.Domain.Psychologist;
import com.example.dtalearning.Services.Service;
import com.example.dtalearning.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;

public class SubscriptionsController {

    private Service service;
    public TextArea textArea1;
    public TextArea textArea2;
    public TextArea textArea3;
    public VBox vbox_mon_plan;
    public Label label_plans_mon;
    public HBox background_price_mon;
    public VBox vbox_bi_plan;
    public Label label_plans_bi;
    public HBox background_price_bi;
    public Label label_features_title_bi;
    public VBox vbox_ann_plan;
    public Label label_plans_ann;
    public HBox background_price_ann;
    public Label label_features_title_ann;
    private Stage stage;
    private Timer timer;

    //TODO: add user here

    private Label label;

    private final String hexColorBronze = "#cd7f32";
    private final String hexColorSilver = "#C0C0C0";
    private final String hexColorGold = "#CFB53B";

    public void setData(Stage stage, Service service){
        this.stage = stage;
        textArea1.setDisable(true);
        textArea2.setDisable(true);
        textArea3.setDisable(true);
        timer = new Timer();
        this.service = service;
    }

    public void handleClose(ActionEvent actionEvent) {
        this.stage.close();
    }



    public void handleBack(ActionEvent actionEvent) {
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

    public void handleConfirm(ActionEvent actionEvent) {
        String styleBox1 = vbox_mon_plan.getStyle();
        String styleBox2 = vbox_bi_plan.getStyle();
        String styleBox3 = vbox_ann_plan.getStyle();

        var currentUser = Session.getLoggedUser();

        if (styleBox1.contains(hexColorBronze)){
            this.service.updatePsychologist(new Psychologist(currentUser.getId(), currentUser.getName(), currentUser.getUsername(), currentUser.getPassword(), currentUser.getEmail(), "0", "Bronze"));
            loadPsychPage();
        } else if (styleBox2.contains(hexColorSilver)) {
            this.service.updatePsychologist(new Psychologist(currentUser.getId(), currentUser.getName(), currentUser.getUsername(), currentUser.getPassword(), currentUser.getEmail(), "0", "Silver"));
            loadPsychPage();
        } else if (styleBox3.contains(hexColorGold)) {
            this.service.updatePsychologist(new Psychologist(currentUser.getId(), currentUser.getName(), currentUser.getUsername(), currentUser.getPassword(), currentUser.getEmail(), "0", "Gold"));
            loadPsychPage();
        } else {
            //throw new RuntimeException("No subscription plan selected.");
            startBlinkAnimation();


//            // Schedule the timer to stop the FillTransition after 4 seconds
//            timer.schedule(new java.util.TimerTask() {
//                @Override
//                public void run() {
//                    startBlinkAnimation();
//                }
//            }, 150 + 150);
        }
    }

    private void loadPsychPage(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/polihackapp/views/Login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            LoginController loginController = fxmlLoader.getController();
            loginController.setData(stage,service);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void startBlinkAnimation() {
        // Add the CSS class to each subscription box
        vbox_mon_plan.getStyleClass().add("blink-border");
        vbox_bi_plan.getStyleClass().add("blink-border");
        vbox_ann_plan.getStyleClass().add("blink-border");

        // Schedule the timer to stop the FillTransition after 4 seconds
        timer.schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                vbox_mon_plan.getStyleClass().remove("blink-border");
                vbox_bi_plan.getStyleClass().remove("blink-border");
                vbox_ann_plan.getStyleClass().remove("blink-border");
            }
        }, 150);
    }

    public void handleMonPlan(MouseEvent mouseEvent) {
        vbox_mon_plan.setStyle("-fx-border-color: " + hexColorBronze + ";");
        vbox_bi_plan.setStyle("-fx-border:none;");
        vbox_ann_plan.setStyle("-fx-border:none;");
    }

    public void handleBiPlan(MouseEvent mouseEvent) {
        vbox_bi_plan.setStyle("-fx-border-color: " + hexColorSilver + ";");
        vbox_mon_plan.setStyle("-fx-border:none");
        vbox_ann_plan.setStyle("-fx-border:none");
    }

    public void handleAnnPlan(MouseEvent mouseEvent) {
        vbox_ann_plan.setStyle("-fx-border-color: " + hexColorGold + ";");
        vbox_bi_plan.setStyle("-fx-border:none");
        vbox_mon_plan.setStyle("-fx-border:none");
    }
}