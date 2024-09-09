package dao;

import model.Client;
import utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

    private Connection connection;

    public ClientDAO() {
        DatabaseConnection db = DatabaseConnection.getInstance();
        connection = db.getConnection();

        if (connection != null) {
            System.out.println("Connection to database established in ClientDAO!");
        } else {
            System.err.println("Failed to establish connection in ClientDAO.");
        }
    }

    // Create Client
    public void createClient(Client client) {
        String sql = "INSERT INTO clients (name, email, phone_number) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getEmail());
            preparedStatement.setString(3, client.getPhone());
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

    // Find Client by ID
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
                        resultSet.getString("phone_number"));
            }
        } catch (SQLException e) {
            System.err.println("Failed to get client.");
            e.printStackTrace();
        }

        return client;
    }

    // Find all clients
    public List<Client> findAll() {
        String sql = "SELECT * FROM clients";
        List<Client> clients = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Client client = new Client(resultSet.getInt("client_id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone_number"));
                clients.add(client);
            }
        } catch (SQLException e) {
            System.err.println("Failed to retrieve clients.");
            e.printStackTrace();
        }

        return clients;
    }

    // Update Client
    public void updateClient(Client client) {
        String sql = "UPDATE clients SET name = ?, email = ?, phone_number = ? WHERE client_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getEmail());
            preparedStatement.setString(3, client.getPhone());
            preparedStatement.setInt(4, client.getId());
            preparedStatement.executeUpdate();
            System.out.println("Client updated successfully.");
        } catch (SQLException e) {
            System.err.println("Failed to update client.");
            e.printStackTrace();
        }
    }

    // Delete Client
    public void deleteClient(int id) {
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
