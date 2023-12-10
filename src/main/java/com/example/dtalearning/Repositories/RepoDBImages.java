package com.example.dtalearning.Repositories;


import com.example.dtalearning.Domain.MyImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepoDBImages {

    protected String url;
    protected String username;
    protected String password;

    public RepoDBImages(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Optional<MyImage> findOne(Long imageID) {
        String selectSQL = "SELECT * FROM Images WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(selectSQL)) {

            statement.setLong(1, imageID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                InputStream imageStream = resultSet.getBinaryStream("image_data");
                String description = resultSet.getString("description");

                MyImage image = new MyImage(imageID, imageStream, description);
                return Optional.of(image);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<MyImage> findAll() {
        List<MyImage> images = new ArrayList<>();
        String selectAllSQL = "SELECT * FROM Images";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(selectAllSQL);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long imageID = resultSet.getLong("id");
                InputStream imageStream = resultSet.getBinaryStream("image_data");
                //byte[] imageData = resultSet.getBytes("image_data");
                String description = resultSet.getString("description");

                MyImage image = new MyImage(imageID, imageStream, description);
                images.add(image);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return images;
    }

    public Long save(String imageFile, String description) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            // Read image file into byte array
//            byte[] imageData = readImageFile(imageFile);

            // Insert image data into the database
            String insertSQL = "INSERT INTO Images (image_data, description) VALUES (?, ?) returning id";
            try (PreparedStatement statement = connection.prepareStatement(insertSQL)) {
                statement.setString(1, imageFile);
                statement.setString(2, description);

                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                return resultSet.getLong("id");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    private byte[] readImageFile(File imageFile) throws IOException {
        try (FileInputStream fis = new FileInputStream(imageFile)) {
            byte[] imageData = new byte[(int) imageFile.length()];
            fis.read(imageData);
            return imageData;
        }
    }

    public List<MyImage> getAllImagesTest(Long id) {
        List<MyImage> images = new ArrayList<>();
        String selectAllSQL = "SELECT * FROM images inner join testimages on testimages.id_image = images.id inner join tests on tests.id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(selectAllSQL);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long imageID = resultSet.getLong("id");
                InputStream imageStream = resultSet.getBinaryStream("image_data");
                //byte[] imageData = resultSet.getBytes("image_data");
                String description = resultSet.getString("description");

                MyImage image = new MyImage(imageID, imageStream, description);
                images.add(image);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return images;
    }
}
