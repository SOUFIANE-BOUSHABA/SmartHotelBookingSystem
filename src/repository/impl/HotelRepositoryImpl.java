package repository.impl;

import model.Client;
import model.Hotel;
import repository.HotelRepository;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HotelRepositoryImpl implements HotelRepository {

    private Connection connection;

    public HotelRepositoryImpl() {
        DatabaseConnection db = DatabaseConnection.getInstance();
        connection = db.getConnection();

        if (connection != null) {
            System.out.println("Connection to database established in HotelDAO!");
        } else {
            System.err.println("Failed to establish connection in HotelDAO.");
        }
    }

    public void create(Hotel hotel) {
        String sql = "INSERT INTO hotels (name) VALUES (?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, hotel.getName());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                hotel.setId(generatedKeys.getInt(1));
                System.out.println("Hotel created success id : " + hotel.getId());
            }
        } catch (SQLException e) {
            System.err.println("Failed  create hotel.");
            e.printStackTrace();

        }

    }

    @Override
    public List<Hotel> findAll() {
        String sql = "SELECT * FROM Hotels";
        List<Hotel> hotels = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                hotels.add(new Hotel(resultSet.getInt("hotel_id"),
                        resultSet.getString("name")));
            }
        } catch (SQLException e) {
            System.err.println("Failed to find all hotels.");
            e.printStackTrace();
        }

        return hotels;
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM hotels WHERE hotel_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Hotel deleted successfully!");
        } catch (SQLException e) {
            System.err.println("Failed to delete hotel.");
            e.printStackTrace();

        }
    }
}
