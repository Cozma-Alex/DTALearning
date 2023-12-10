package com.example.dtalearning.GameTypes;

import javafx.scene.image.Image;

import java.util.List;

public class GameTextImagePicker {

    public List<Image> images;

    public String intrebare;

    public String descriere;

    public int correct_variant;

    public GameTextImagePicker(List<Image> images, String intrebare, int correct_variant, String descriere) {
        this.images = images;
        this.intrebare = intrebare;
        this.correct_variant = correct_variant;
        this.descriere = descriere;
    }
}
