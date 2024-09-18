package  repository.impl;
import model.Reservation;
import model.Room;
import model.enums.RoomType;
import repository.ReservationRepository;
import utils.DatabaseConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationRepositoryImpl implements ReservationRepository {

    private Connection connection;
    private final Map<Integer, Boolean> roomAvailabilityMap = new HashMap<>();



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
        String sql = "INSERT INTO reservations (client_id, room_id, start_date, end_date, payment_status, total_price) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, reservation.getClientId());
            preparedStatement.setInt(2, reservation.getRoomId());
            preparedStatement.setDate(3, java.sql.Date.valueOf(reservation.getStartDate()));
            preparedStatement.setDate(4, java.sql.Date.valueOf(reservation.getEndDate()));
            preparedStatement.setString(5, reservation.getPaymentStatus());
            preparedStatement.setDouble(6, reservation.getTotalPrice());
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
    public boolean isUserReservation(int reservationId, int clientId) {
        String sql = "SELECT * FROM reservations WHERE id = ? AND client_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, reservationId);
            preparedStatement.setInt(2, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            System.err.println("Failed to check if user is the owner of the reservation.");
            e.printStackTrace();

        }
        return false;
    }


    @Override
    public List<Room> findAvailableRooms(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT * FROM rooms WHERE id NOT IN (SELECT room_id FROM reservations " +
                "WHERE payment_status != 'CANCELLED' " +
                "AND (start_date < ? AND end_date > ?))";
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
    public boolean checkDateAvailability(int roomId, LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT COUNT(*) FROM reservations WHERE room_id = ? " +
                "AND payment_status != 'CANCELLED' " +
                "AND (start_date < ? AND end_date > ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, roomId);
            preparedStatement.setDate(2, java.sql.Date.valueOf(endDate));
            preparedStatement.setDate(3, java.sql.Date.valueOf(startDate));
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


    @Override
    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservations";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                reservations.add(new Reservation(
                        resultSet.getInt("id"),
                        resultSet.getInt("client_id"),
                        resultSet.getInt("room_id"),
                        resultSet.getDate("start_date").toLocalDate(),
                        resultSet.getDate("end_date").toLocalDate(),
                        resultSet.getString("payment_status"),
                        resultSet.getDouble("total_price")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Failed to get all reservations.");
            e.printStackTrace();
        }
        return reservations;
    }











    private void loadRoomAvailability() {
        String sql = "SELECT id FROM rooms";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                roomAvailabilityMap.put(resultSet.getInt("id"), true);
            }
        } catch (SQLException e) {
            System.err.println("Failed to load room availability.");
            e.printStackTrace();
        }
    }




    public Map<Integer, Room> findAvailableRoomsForMiseENSItuation(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT * FROM rooms WHERE id NOT IN (SELECT room_id FROM reservations " +
                "WHERE payment_status != 'CANCELLED' " +
                "AND (start_date < ? AND end_date > ?))";

        Map<Integer, Room> availableRoomsMap = new HashMap<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDate(1, java.sql.Date.valueOf(endDate));
            preparedStatement.setDate(2, java.sql.Date.valueOf(startDate));
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Room room = new Room(
                        resultSet.getInt("id"),
                        RoomType.valueOf(resultSet.getString("roomType")),
                        resultSet.getDouble("prix"),
                        resultSet.getInt("hotel_id")
                );
                availableRoomsMap.put(room.getRoomNumber(), room);
            }
        } catch (SQLException e) {
            System.err.println("Failed to find available rooms.");
            e.printStackTrace();
        }
        return availableRoomsMap;
    }





}
