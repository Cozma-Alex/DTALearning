package com.example.dtalearning.Repositories;


import com.example.dtalearning.Domain.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RepoDBTests {

    protected String url;
    protected String username;
    protected String password;

    public RepoDBTests(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Optional<Test> findOne(Long testID) {
        String selectSQL = "SELECT * FROM Tests WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(selectSQL)) {

            statement.setLong(1, testID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                List<String> variants = Arrays.asList((String[]) resultSet.getArray("variants").getArray());
                String correctAnswer = resultSet.getString("correct_answer");
                String description = resultSet.getString("description");
                String category = resultSet.getString("category");
                String image = resultSet.getString("preview_image");

                Test test = new Test(testID, variants, correctAnswer, description, category, image);
                return Optional.of(test);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void addTestImage(Long id_test, Long id_image, Boolean correct) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            // Insert image data into the database
            String insertSQL = "INSERT INTO testimages (id_test, id_image, correct_image) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(insertSQL)) {
                statement.setLong(1, id_test);
                statement.setLong(2, id_image);
                statement.setString(3, correct.toString());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Test> findAll() {
        List<Test> tests = new ArrayList<>();
        String selectSQL = "SELECT * FROM Tests";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL)) {

            while (resultSet.next()) {
                Long testID = resultSet.getLong("id");
                List<String> variants = Arrays.asList((String[]) resultSet.getArray("variants").getArray());
                String correctAnswer = resultSet.getString("correct_answer");
                String description = resultSet.getString("description");
                String category = resultSet.getString("category");
                String image = resultSet.getString("preview_image");

                Test test = new Test(testID, variants, correctAnswer, description, category, image);
                tests.add(test);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tests;
    }

    public Long save(List<String> variants, String correctAnswer, String description, String category, String previewImage) throws FileNotFoundException {

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
//            var inputStream = readImageFile(previewImage);
            // Insert image data into the database
            String insertSQL = "INSERT INTO Tests (variants, correct_answer, description, category, preview_image) VALUES (?, ?, ?, ?, ?) returning id";
            try (PreparedStatement statement = connection.prepareStatement(insertSQL)) {
                statement.setArray(1, connection.createArrayOf("VARCHAR", variants.toArray()));
                statement.setString(2, correctAnswer);
                statement.setString(3, description);
                statement.setString(4, category);
                statement.setString(5, previewImage);
//                statement.setBinaryStream(6, previewImage, previewImage.available());

                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                return resultSet.getLong("id");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] readImageFile(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[1024]; // You can adjust the buffer size as needed

        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();
        System.out.println(buffer);
        return buffer.toByteArray();
    }


    public List<Test> getAllTestsAssignedToUser(Long user_id) {

        List<Test> tests = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            // Insert image data into the database
            String insertSQL = "select t.preview_image, t.id, t.category from tests t inner join public.assignments a on t.id = a.id_test inner join public.patient p on p.id = a.id_patient where p.id = ?";
            try (PreparedStatement statement = connection.prepareStatement(insertSQL)) {
                statement.setLong(1, user_id);

                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Long testID = resultSet.getLong("id");
                    String category = resultSet.getString("category");
                    String image = resultSet.getString("preview_image");

                    Test test = new Test(testID, category, image);
                    tests.add(test);
                }


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tests;
    }

}
