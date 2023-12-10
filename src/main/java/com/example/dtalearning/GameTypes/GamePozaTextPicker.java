package com.example.dtalearning.GameTypes;

import javafx.scene.image.Image;

import java.util.List;

public class GamePozaTextPicker {

    public Image image;

    public List<String> variants;

    public String descriere;

    public String correct_variant;

    public String intrebare;

    public GamePozaTextPicker(Image image, List<String> variants, String correct_variant, String intrebare, String descriere) {
        this.image = image;
        this.variants = variants;
        this.correct_variant = correct_variant;
        this.intrebare = intrebare;
        this.descriere = descriere;
    }

}
