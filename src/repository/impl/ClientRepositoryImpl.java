package repository.impl;

import model.Client;
import repository.ClientRepository;
import utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepositoryImpl implements ClientRepository {

    private Connection connection;

    public ClientRepositoryImpl() {
        DatabaseConnection db = DatabaseConnection.getInstance();
        connection = db.getConnection();

        if (connection != null) {
            System.out.println("Connection to database established in ClientRepositoryImpl!");
        } else {
            System.err.println("Failed to establish connection in ClientRepositoryImpl.");
        }
    }

    @Override
    public void create(Client client) {
        String sql = "INSERT INTO clients (name, email, phone_number, balance) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getEmail());
            preparedStatement.setString(3, client.getPhone());
            preparedStatement.setDouble(4, client.getBalance());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                client.setId(generatedKeys.getInt(1));
                System.out.println("Client created successfully with ID: " + client.getId());
            }
        } catch (SQLException e) {
            System.err.println("Failed to create client.");
            e.printStackTrace();
        }
    }

    @Override
    public Client findById(int id) {
        String sql = "SELECT * FROM clients WHERE client_id = ?";
        Client client = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                client = new Client(resultSet.getInt("client_id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone_number"),
                        resultSet.getDouble("balance"));
            }
        } catch (SQLException e) {
            System.err.println("Failed to get client.");
            e.printStackTrace();
        }

        return client;
    }

    @Override
    public List<Client> findAll() {
        String sql = "SELECT * FROM clients";
        List<Client> clients = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Client client = new Client(resultSet.getInt("client_id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone_number"),
                        resultSet.getDouble("balance"));
                clients.add(client);
            }
        } catch (SQLException e) {
            System.err.println("Failed to retrieve clients.");
            e.printStackTrace();
        }

        return clients;
    }

    @Override
    public void update(Client client) {
        String sql = "UPDATE clients SET name = ?, email = ?, phone_number = ?, balance = ? WHERE client_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getEmail());
            preparedStatement.setString(3, client.getPhone());
            preparedStatement.setDouble(4, client.getBalance());
            preparedStatement.setInt(5, client.getId());
            preparedStatement.executeUpdate();
            System.out.println("Client updated successfully.");
        } catch (SQLException e) {
            System.err.println("Failed to update client.");
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM clients WHERE client_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Client deleted successfully.");
        } catch (SQLException e) {
            System.err.println("Failed to delete client.");
            e.printStackTrace();
        }
    }
}
