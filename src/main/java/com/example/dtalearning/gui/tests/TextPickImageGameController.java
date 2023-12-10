package com.example.dtalearning.gui.tests;

import com.example.dtalearning.GameTypes.GameTextImagePicker;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class TextPickImageGameController {

    public AnchorPane rootPane;
    public Label intrebareLabel;
    public AnchorPane photoPane;
    private Stage stage;

    private GameTextImagePicker game;

    public void setData(Stage stage, GameTextImagePicker game) {
        this.stage = stage;
        this.game = game;
        initModel();
    }

    private void initModel() {

        intrebareLabel.setText(game.intrebare);

        List<ImageView> views = new ArrayList<>();

        game.images.forEach(image -> {
            ImageView view = new ImageView(image);
            view.setFitWidth(100);
            view.setFitHeight(100);

            views.add(view);

            EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (areImagesEqual(view.getImage(),game.images.get(game.correct_variant))){
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Final Test");
                        alert.setContentText("Ati rezolvat testul");
                        alert.showAndWait();
                        stage.close();
                    }else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Alegere gresita");
                        alert.setContentText("Alege alta varianta");
                        alert.showAndWait();
                    }
                }
            };

            view.setOnMouseClicked(eventHandler);

        });

        for (int i = 0; i < views.size(); i++) {
            ImageView button = views.get(i);
            button.setFitWidth(100);
            button.setFitHeight(100);
            photoPane.getChildren().add(button);
            photoPane.setTopAnchor(button, 20.0);
            photoPane.setLeftAnchor(button, i * 150.0);
        }

    }

    private boolean areImagesEqual(Image image1, Image image2) {
        if (image1.getWidth() != image2.getWidth() || image1.getHeight() != image2.getHeight()) {
            return false;
        }

        for (int y = 0; y < image1.getHeight(); y++) {
            for (int x = 0; x < image1.getWidth(); x++) {
                if (image1.getPixelReader().getArgb(x, y) != image2.getPixelReader().getArgb(x, y)) {
                    return false;
                }
            }
        }

        return true;
    }
}

