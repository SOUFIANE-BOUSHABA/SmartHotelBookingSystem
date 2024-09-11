package  repository.impl;
import model.Reservation;
import model.Room;
import model.enums.RoomType;
import repository.ReservationRepository;
import utils.DatabaseConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepositoryImpl implements ReservationRepository {
    private Connection connection;

    public ReservationRepositoryImpl() {
        DatabaseConnection db = DatabaseConnection.getInstance();
        connection = db.getConnection();

        if (connection != null) {
            System.out.println("Connection to database established in ReservationRepository!");
        } else {
            System.err.println("Failed to establish connection in ReservationRepository.");
        }
    }

    @Override
    public void create(Reservation reservation) {
        String sql = "INSERT INTO reservations (client_id, room_id, start_date, end_date, payment_status) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, reservation.getClientId());
            preparedStatement.setInt(2, reservation.getRoomId());
            preparedStatement.setDate(3, java.sql.Date.valueOf(reservation.getStartDate()));
            preparedStatement.setDate(4, java.sql.Date.valueOf(reservation.getEndDate()));
            preparedStatement.setString(5, reservation.getPaymentStatus());
            preparedStatement.executeUpdate();
            System.out.println("Reservation created successfully.");
        } catch (SQLException e) {
            System.err.println("Failed to create reservation.");
            e.printStackTrace();
        }
    }

    @Override
    public void cancel(int reservationId) {
        String sql = "UPDATE reservations SET payment_status = 'CANCELLED' WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, reservationId);
            preparedStatement.executeUpdate();
            System.out.println("Reservation cancelled successfully.");
        } catch (SQLException e) {
            System.err.println("Failed to cancel reservation.");
            e.printStackTrace();
        }
    }

    @Override
    public List<Room> findAvailableRooms(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT * FROM rooms WHERE id NOT IN (SELECT room_id FROM reservations WHERE (start_date <= ? AND end_date >= ?))";
        List<Room> rooms = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDate(1, java.sql.Date.valueOf(endDate));
            preparedStatement.setDate(2, java.sql.Date.valueOf(startDate));
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                rooms.add(new Room(
                        resultSet.getInt("id"),
                        RoomType.valueOf(resultSet.getString("roomType")),
                        resultSet.getDouble("prix"),
                        resultSet.getInt("hotel_id")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Failed to find available rooms.");
            e.printStackTrace();
        }
        return rooms;
    }

    @Override
    public boolean checkDateAvailability(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT COUNT(*) FROM reservations WHERE (start_date <= ? AND end_date >= ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDate(1, java.sql.Date.valueOf(endDate));
            preparedStatement.setDate(2, java.sql.Date.valueOf(startDate));
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) == 0;
            }
        } catch (SQLException e) {
            System.err.println("Failed to check date availability.");
            e.printStackTrace();
        }
        return false;
    }
}
