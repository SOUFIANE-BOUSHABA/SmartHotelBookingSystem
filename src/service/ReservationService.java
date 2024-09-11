package service;
import model.Reservation;
import model.Room;
import repository.ReservationRepository;
import repository.impl.ReservationRepositoryImpl;

import java.time.LocalDate;
import java.util.List;

public class ReservationService {
    private ReservationRepository reservationRepository;

    public ReservationService() {
        this.reservationRepository = new ReservationRepositoryImpl();
    }

    public void createReservation(Reservation reservation) {
        if (reservationRepository.checkDateAvailability(reservation.getStartDate(), reservation.getEndDate())) {
            reservationRepository.create(reservation);
        } else {
            System.out.println("The selected dates are not available.");
        }
    }

    public void cancelReservation(int reservationId) {
        reservationRepository.cancel(reservationId);
    }

    public List<Room> findAvailableRooms(LocalDate startDate, LocalDate endDate) {
        return reservationRepository.findAvailableRooms(startDate, endDate);
    }

    public boolean checkDateAvailability(LocalDate startDate, LocalDate endDate) {
        return reservationRepository.checkDateAvailability(startDate, endDate);
    }
}
