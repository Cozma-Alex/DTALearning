package com.example.dtalearning.gui;

import com.example.dtalearning.Services.Service;
import com.example.dtalearning.Session;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PatientWindowController {

    public AnchorPane imgPane;
    public ImageView img1;
    public ImageView img2;
    public ImageView img3;
    public ImageView img4;
    private Service service;

    private List<ImageView> imageViews = new ArrayList<>();

    private Stage stage;

    public void setData(Stage stage, Service service) {
        this.stage = stage;
        this.service = service;
        init_view();

    }

    public void handleLogOut(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/polihackapp/views/Login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            LoginController loginController = fxmlLoader.getController();
            loginController.setData(stage, service);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void init_view() {
        var testList = this.service.getAllTestsAssignedToUser(Session.getLoggedUser().getId());
//        for (int i = 0; i < testList.size(); i++) {
//            ImageView imageView = new ImageView();
//            imageView.setFitHeight(100);
//            imageView.setFitWidth(100);
//            imageViews.add(imageView);
//            Image image = new Image(testList.get(i).getPreviewImage());
//            imageView.setImage(image);
//            int finalI = i;
//            EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
//                @Override
//                public void handle(MouseEvent event) {
//                    switch (testList.get(finalI).getCategory()) {
//                        case "Pick Text After Image":
//                            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/com/example/polihackapp/views/image-pickTextGame-view.fxml"));
//                            AnchorPane userLayout1 = null;
//                            try {
//                                userLayout1 = loader1.load();
//                            } catch (IOException e) {
//                                throw new RuntimeException(e);
//                            }
//                            Scene scene1 = new Scene(userLayout1);
//                            ImagePickTextGameController imagePickTextController = loader1.getController();
//
//                            List<MyImage> images = service.getAllImagesTest(testList.get(finalI).getId());
//
//                            GamePozaTextPicker game = new GamePozaTextPicker(new Image(images.get(0).getImage()), testList.get(finalI).getVariants(), testList.get(finalI).getCorrectAnswer(), testList.get(finalI).getQuestion(), testList.get(finalI).getDescription());
//
//                            stage.setScene(scene1);
//                            imagePickTextController.setData(stage, game);
//                            stage.setHeight(600);
//                            stage.setWidth(600);
//                            stage.centerOnScreen();
//                            break;
//                        case "Pick Image After Text":
//                            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/com/example/polihackapp/views/text-pickImage-view.fxml"));
//                            AnchorPane userLayout2 = null;
//                            try {
//                                userLayout2 = loader2.load();
//                            } catch (IOException e) {
//                                throw new RuntimeException(e);
//                            }
//                            Scene scene2 = new Scene(userLayout2);
//                            TextPickImageController textPickImageController = loader2.getController();
//                            stage.setScene(scene2);
//                            stage.setHeight(600);
//                            stage.setWidth(600);
//                            stage.centerOnScreen();
//                            textPickImageController.setData(stage, service);
//                            break;
//                        case "Order Objects":
//                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/polihackapp/views/order-view.fxml"));
//                            AnchorPane userLayout = null;
//                            try {
//                                userLayout = loader.load();
//                            } catch (IOException e) {
//                                throw new RuntimeException(e);
//                            }
//                            Scene scene = new Scene(userLayout);
//                            OrderController orderController = loader.getController();
//                            stage.setScene(scene);
//                            stage.setHeight(600);
//                            stage.setWidth(600);
//                            stage.centerOnScreen();
//                            orderController.setData(stage, service);
//                            break;
//                        default:
//                            break;
//
//                    }
//                }
//            };
//            imageView.setOnMouseClicked(eventHandler);
//
//        }
//
//        for (int i = 0; i < imageViews.size(); i++) {
//            ImageView button = imageViews.get(i);
//            button.setFitWidth(100);
//            button.setFitHeight(100);
//            imgPane.getChildren().add(button);
//            imgPane.setTopAnchor(button, 20.0);
//            imgPane.setLeftAnchor(button, i * 150.0);
//        }
//

        if (testList.size() >= 1) {
            EventHandler<MouseEvent> eventHandler1 = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Image image = new Image(testList.get(0).getPreviewImage());
                    img1.setImage(image);
                    System.out.println("mama ta");
                }
            };
            img1.setOnMouseClicked(eventHandler1);

            if (testList.size() >= 2) {
                EventHandler<MouseEvent> eventHandler2 = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Image image = new Image(testList.get(1).getPreviewImage());
                        img1.setImage(image);
                        System.out.println("tati tau");

                    }
                };

                if (testList.size() >= 3) {

                    img2.setOnMouseClicked(eventHandler2);

                    EventHandler<MouseEvent> eventHandler3 = new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            Image image = new Image(testList.get(2).getPreviewImage());
                            img1.setImage(image);
                            System.out.println("esti prost");
                        }
                    };

                    img3.setOnMouseClicked(eventHandler3);
                }
            }
        }
    }

    public void handleClose(ActionEvent actionEvent) {
        this.stage.close();
    }
}
