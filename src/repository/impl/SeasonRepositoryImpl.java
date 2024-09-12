package repository.impl;

import model.Season;
import repository.SeasonRepository;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class SeasonRepositoryImpl implements SeasonRepository {

    private final Connection connection;

    public SeasonRepositoryImpl() {
        DatabaseConnection db = DatabaseConnection.getInstance();
        connection = db.getConnection();
    }


    @Override
    public Season findSeasonByDate(LocalDate date) {
        String sql = "SELECT * FROM seasons WHERE ? BETWEEN start_date AND end_date";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDate(1, java.sql.Date.valueOf(date));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Season(
                        resultSet.getInt("season_id"),
                        resultSet.getString("name"),
                        resultSet.getDate("start_date").toLocalDate(),
                        resultSet.getDate("end_date").toLocalDate(),
                        resultSet.getDouble("price_multiplier")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
