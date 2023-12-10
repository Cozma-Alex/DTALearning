package com.example.dtalearning.Domain;

import java.io.InputStream;
import java.util.Objects;

public class MyImage {

    private Long imageID;
    private InputStream image;
    private String description;

    public MyImage(Long imageID, InputStream image, String description) {
        this.imageID = imageID;
        this.image = image;
        this.description = description;
    }

    public Long getImageID() {
        return imageID;
    }

    public void setImageID(Long imageID) {
        this.imageID = imageID;
    }

    public InputStream getImage() {
        return image;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyImage image = (MyImage) o;
        return Objects.equals(imageID, image.imageID) && Objects.equals(description, image.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageID, image, description);
    }

    @Override
    public String toString() {
        return "Image{" +
                "imageID=" + imageID +
                ", image=" + image +
                ", description='" + description + '\'' +
                '}';
    }
}
