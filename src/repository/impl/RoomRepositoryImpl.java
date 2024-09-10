package repository.impl;


import model.Room;
import model.enums.RoomType;
import repository.RoomRepository;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomRepositoryImpl implements RoomRepository {
    private Connection connection;

    public RoomRepositoryImpl() {
        DatabaseConnection db = DatabaseConnection.getInstance();
        connection = db.getConnection();

        if (connection != null) {
            System.out.println("Connection to database established in RoomDAO!");
        } else {
            System.err.println("Failed to establish connection in RoomDAO.");
        }
    }

    @Override
    public void create(Room room) {
        String sql = "INSERT INTO rooms (roomType, prix, hotel_id) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, room.getRoomType().toString());
            preparedStatement.setDouble(2, room.getPrix());
            preparedStatement.setInt(3, room.getHotelId());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                room.setRoomNumber(generatedKeys.getInt(1));
                System.out.println("Room created with ID: " + room.getRoomNumber());
            }
        } catch (SQLException e) {
            System.err.println("Failed to create room.");
            e.printStackTrace();
        }
    }

    @Override
    public Room findById(int id) {
        String sql = "SELECT * FROM rooms WHERE id = ?";
        Room room = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                room = new Room(resultSet.getInt("id"),
                        RoomType.valueOf(resultSet.getString("roomType")),
                        resultSet.getDouble("prix"),
                        resultSet.getInt("hotel_id"));
            }
        } catch (SQLException e) {
            System.err.println("Failed to get room.");
            e.printStackTrace();
        }
        return room;
    }


    @Override
    public List<Room> findAll() {
        String sql = "SELECT * FROM rooms";
        List<Room> rooms = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Room room = new Room(resultSet.getInt("id"),
                        RoomType.valueOf(resultSet.getString("roomType")),
                        resultSet.getDouble("prix"),
                        resultSet.getInt("hotel_id"));
                rooms.add(room);
            }
        } catch (SQLException e) {
            System.err.println("Failed to get rooms.");
            e.printStackTrace();
        }
        return rooms;
    }


    @Override
    public void update(Room room) {
        String sql = "UPDATE rooms SET roomType = ?, prix = ?, hotel_id = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, room.getRoomType().toString());
            preparedStatement.setDouble(2, room.getPrix());
            preparedStatement.setInt(3, room.getHotelId());
            preparedStatement.setInt(4, room.getRoomNumber());
            preparedStatement.executeUpdate();
            System.out.println("Room updated successfully");
        } catch (SQLException e) {
            System.err.println("Failed to update room.");
            e.printStackTrace();
        }
    }


    @Override
    public void delete(int id) {
        String sql = "DELETE FROM rooms WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Room deleted success");
        } catch (SQLException e) {
            System.err.println("Failed  delete room ");
            e.printStackTrace();
        }
    }
}
