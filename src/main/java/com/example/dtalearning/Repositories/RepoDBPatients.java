package com.example.dtalearning.Repositories;


import com.example.dtalearning.Domain.Patient;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class RepoDBPatients implements Repository<Long, Patient> {

    protected String url;
    protected String username;
    protected String password;

    public RepoDBPatients(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Optional<Patient> findOne(Long patientID) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("select * from Patient " +
                     "where id = ?");
        ) {
            statement.setInt(1, Math.toIntExact(patientID));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                Optional<Long> psychologist = Optional.of(resultSet.getLong("psychologist_id"));
                Patient patient = new Patient(id, name, username, password, email, psychologist);
                return Optional.of(patient);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Patient> findOne(String username){
        try (Connection connection = DriverManager.getConnection(url, this.username, password);
             PreparedStatement statement = connection.prepareStatement("select * from Patient " +
                     "where username = ?");
        ) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String username1 = resultSet.getString("username");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                Optional<Long> psychologist = Optional.of(resultSet.getLong("psychologist_id"));
                return Optional.of(new Patient(id, name, username1, password, email, psychologist));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Patient> findAll() {
        Set<Patient> patients = new HashSet<>();
        String selectPatientsSQL = "SELECT * FROM Patient";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(selectPatientsSQL);
             ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                Long patientID = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                Optional<Long> psychologist = Optional.of(resultSet.getLong("psychologist_id"));

                Patient patient = new Patient(patientID, name, username, password, email, psychologist);
                patients.add(patient);
            }
            return patients;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Patient> save(Patient entity) {
        if (entity == null)
            throw new IllegalArgumentException("Entity must not be null!");

        String insertSQL1 = "INSERT INTO Patient (name, username, password, email) values (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement1 = connection.prepareStatement(insertSQL1);
        ) {

            statement1.setString(1, entity.getName());
            statement1.setString(2, entity.getUsername());
            statement1.setString(3, entity.getPassword());
            statement1.setString(4, entity.getEmail());
//            if(entity.getPsychologistID().isEmpty())
//                statement1.setNull(5, Types.BIGINT);
//            else
//                statement1.setLong(5, entity.getPsychologistID().get());


            int result = statement1.executeUpdate();
            return result == 0 ? Optional.of(entity) : Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Patient> delete(Long patientID) {
        String deleteSQL = "DELETE FROM Patient WHERE id = ?";

        Optional<Patient> patientToDelete = findOne(patientID);
        if (patientToDelete.isPresent())
            try (Connection connection = DriverManager.getConnection(url, username, password);
                 PreparedStatement statement = connection.prepareStatement(deleteSQL);
            ) {
                statement.setLong(1, patientID);
                int result = statement.executeUpdate();
                if (result > 0)
                    return patientToDelete;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        return Optional.empty();
    }

    @Override
    public Optional<Patient> delete(String username) {
        String deleteSQL = "DELETE FROM Patient WHERE username = ?";

        Optional<Patient> patientToDelete = findOne(username);
        if (patientToDelete.isPresent())
            try (Connection connection = DriverManager.getConnection(url, this.username, password);
                 PreparedStatement statement = connection.prepareStatement(deleteSQL);
            ) {
                statement.setString(1, username);
                int result = statement.executeUpdate();
                if (result > 0)
                    return patientToDelete;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        return Optional.empty();
    }

    @Override
    public Optional<Patient> update(Patient entity) {
        String updateSQL = "UPDATE Patient SET name = ?, username = ?, password = ?, email = ?, psychologist_id = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(updateSQL);
        ) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getUsername());
            statement.setString(3, entity.getPassword());
            statement.setString(4, entity.getEmail());
            if(entity.getPsychologistID().isPresent())
                statement.setLong(5, entity.getPsychologistID().get());
            else
                statement.setNull(5,Types.BIGINT);
            statement.setLong(6, entity.getId());
            int result = statement.executeUpdate();
            return result == 0 ? Optional.empty() : Optional.of(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addHomework(Long id_test, Long id_patient){
        String updateSQL = "insert into assignments(id_test, id_patient) values (?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(updateSQL);
        ) {
            statement.setLong(1, id_test);
            statement.setLong(2, id_patient);
            int result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeHomework(Long id_test, Long id_patient){
        String updateSQL = "delete from assignments where id_test = ? and id_patient = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(updateSQL);
        ) {
            statement.setLong(1, id_test);
            statement.setLong(2, id_patient);
            int result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Iterable<Patient> findPatientsByPsychologist(String psychologistUsername) {
        Set<Patient> patients = new HashSet<>();
        String selectPatientsSQL = "SELECT Patient.*\n" +
                "FROM Patient\n" +
                "JOIN Psychologist ON Patient.psychologist_id = Psychologist.id\n" +
                "WHERE Psychologist.username = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(selectPatientsSQL)
        ) {
            statement.setString(1, psychologistUsername);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Long patientID = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    String email = resultSet.getString("email");
                    Optional<Long> psychologist = Optional.of(resultSet.getLong("psychologist_id"));

                    Patient patient = new Patient(patientID, name, username, password, email, psychologist);
                    patients.add(patient);
                }
            }
            return patients;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Iterable<Patient> findPatientsByPsychologistAndName(String psychologistUsername, String search) {
        Set<Patient> patients = new HashSet<>();
        String selectPatientsSQL = "SELECT Patient.*\n" +
                "FROM Patient\n" +
                "JOIN Psychologist ON Patient.psychologist_id = Psychologist.id\n" +
                "WHERE Psychologist.username = ? and Patient.username like ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(selectPatientsSQL)
        ) {
            search+='%';
            statement.setString(1, psychologistUsername);
            statement.setString(2,search);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Long patientID = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    String email = resultSet.getString("email");
                    Optional<Long> psychologist = Optional.of(resultSet.getLong("psychologist_id"));

                    Patient patient = new Patient(patientID, name, username, password, email, psychologist);
                    patients.add(patient);
                }
            }
            return patients;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addPatient(Long id_patient, Long id_psych){
        String insertStatement = "update patient set psychologist_id=? where id =?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(insertStatement)
        ) {
            statement.setLong(1, id_psych);
            statement.setLong(2, id_patient);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removePatient(Long id_patient, Long id_psych){
        String deleteStatement = "update patient set psychologist_id=? where id =?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(deleteStatement)
        ) {
            statement.setNull(1, Types.NULL);
            statement.setLong(2, id_patient);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Iterable<Patient> getAllFiltered(String substring){
        Set<Patient> patients = new HashSet<>();
        String selectPatientsSQL = "SELECT * FROM Patient where username like '%' || ? || '%'";
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = connection.prepareStatement(selectPatientsSQL);
            statement.setString(1, substring);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long patientID = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                Optional<Long> psychologist = Optional.of(resultSet.getLong("psychologist_id"));

                Patient patient = new Patient(patientID, name, username, password, email, psychologist);
                patients.add(patient);
            }
            return patients;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}