package com.example.dtalearning.Domain;

import java.util.List;
import java.util.Objects;

public class Test {

    private Long id;
    private List<String> variants;
    private String correctAnswer;
    private String description;

    private String category;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    private String question;

    private String previewImage;

    public Test(Long id, List<String> variants, String correctAnswer, String description, String category, String previewImage) {
        this.id = id;
        this.variants = variants;
        this.correctAnswer = correctAnswer;
        this.description = description;
        this.category = category;
        this.previewImage = previewImage;
    }

    public Test(List<String> variants, String correctAnswer, String description, String category, String question, String previewImage) {
        this.variants = variants;
        this.correctAnswer = correctAnswer;
        this.description = description;
        this.category = category;
        this.question = question;
        this.previewImage = previewImage;
    }

//    public Test(Long id, String correctAnswer, String description) {
//        this.id = id;
//        this.variants = variants;
//        this.correctAnswer = correctAnswer;
//    }

    public Test(Long testID, String category, String image) {
        this.id = testID;
        this.category = category;
        this.previewImage = image;
    }

    public Test(String descriereString, String category, String question, String previewImage) {
        this.description = descriereString;
        this.category = category;
        this.question = question;
        this.previewImage = previewImage;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPreviewImage() {
        return previewImage;
    }

    public void setPreviewImage(String previewImage) {
        this.previewImage = previewImage;
    }

    public Long getTestID() {
        return this.id;
    }

    public void setTestID(Long id) {
        this.id = id;
    }

    public List<String> getVariants() {
        return variants;
    }

    public void setVariants(List<String> variants) {
        this.variants = variants;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Tests{" +
                ", variants=" + variants +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Test tests = (Test) o;
        return Objects.equals(variants, tests.variants) && Objects.equals(correctAnswer, tests.correctAnswer) && Objects.equals(description, tests.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(variants, correctAnswer, description);
    }
}
