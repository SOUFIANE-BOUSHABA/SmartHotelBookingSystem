package repository;

import model.Season;

import java.time.LocalDate;

public interface SeasonRepository {
    Season findSeasonByDate(LocalDate date);
}
