package repository;

import model.SpecialEvent;

import java.time.LocalDate;

public interface SpecialEventRepository {
    SpecialEvent findEventByDate(LocalDate date);
}
