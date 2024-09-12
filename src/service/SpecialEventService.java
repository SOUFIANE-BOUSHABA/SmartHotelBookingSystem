package service;

import model.SpecialEvent;
import repository.impl.SpecialEventRepositoryImpl;

import java.time.LocalDate;

public class SpecialEventService {
    private final SpecialEventRepositoryImpl specialEventRepository;

    public SpecialEventService() {
        this.specialEventRepository = new SpecialEventRepositoryImpl();
    }

    public SpecialEvent findEventByDate(LocalDate date) {
        return specialEventRepository.findEventByDate(date);
    }
}
