package service;

import model.Season;
import repository.impl.SeasonRepositoryImpl;

import java.time.LocalDate;

public class SeasonService {
    private final SeasonRepositoryImpl seasonRepository;

    public SeasonService() {
        this.seasonRepository = new SeasonRepositoryImpl();
    }

    public Season findSeasonByDate(LocalDate date) {
        return seasonRepository.findSeasonByDate(date);
    }



}
