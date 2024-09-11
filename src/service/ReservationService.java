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
        if (reservationRepository.checkDateAvailability(reservation.getRoomId(), reservation.getStartDate(), reservation.getEndDate())) {
            reservationRepository.create(reservation);
        } else {
            System.out.println("The selected dates are not available.");
        }
    }


    public void cancel(int reservationId) {
        reservationRepository.cancel(reservationId);
    }

    public List<Room> findAvailableRooms(LocalDate startDate, LocalDate endDate) {
        return reservationRepository.findAvailableRooms(startDate, endDate);
    }

    public boolean checkDateAvailability(int roomId, LocalDate startDate, LocalDate endDate) {
        return reservationRepository.checkDateAvailability(roomId, startDate, endDate);
    }


    public List<Reservation> getAllReservations() {
        return reservationRepository.getAllReservations();
    }

    public double calculateOccupancyRate() {
        List<Reservation> reservations = reservationRepository.getAllReservations();
        long totalReservations = reservations.size();
        long paidReservations = reservations.stream()
                .filter(r -> r.getPaymentStatus().equals("PAID"))
                .count();

        return Math.max(0, (double) paidReservations / totalReservations * 100);
    }




    public long countCanceledReservations() {
        List<Reservation> reservations = reservationRepository.getAllReservations();
        return reservations.stream()
                .filter(r -> r.getPaymentStatus().equals("CANCELLED"))
                .count();
    }

}
