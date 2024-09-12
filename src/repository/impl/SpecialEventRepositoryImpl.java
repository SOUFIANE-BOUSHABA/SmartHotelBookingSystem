package repository.impl;

import model.SpecialEvent;
import repository.SpecialEventRepository;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class SpecialEventRepositoryImpl implements SpecialEventRepository {

    private final Connection connection;

    public SpecialEventRepositoryImpl() {
        DatabaseConnection db = DatabaseConnection.getInstance();
        connection = db.getConnection();
    }


    @Override
    public SpecialEvent findEventByDate(LocalDate date) {
        String sql = "SELECT * FROM special_events WHERE event_date = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDate(1, java.sql.Date.valueOf(date));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new SpecialEvent(
                        resultSet.getInt("event_id"),
                        resultSet.getString("name"),
                        resultSet.getDate("event_date").toLocalDate(),
                        resultSet.getDouble("price_multiplier")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
