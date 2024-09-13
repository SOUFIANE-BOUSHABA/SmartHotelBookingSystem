package service;

import model.Reservation;
import model.Room;
import model.Season;
import model.SpecialEvent;
import repository.RoomRepository;
import repository.ReservationRepository;
import repository.impl.RoomRepositoryImpl;
import repository.impl.ReservationRepositoryImpl;

import java.time.LocalDate;
import java.util.List;

public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final SeasonService seasonService;
    private final SpecialEventService specialEventService;

    public ReservationService() {
        this.reservationRepository = new ReservationRepositoryImpl();
        this.roomRepository = new RoomRepositoryImpl();
        this.seasonService = new SeasonService();
        this.specialEventService = new SpecialEventService();
    }

    public void createReservation(Reservation reservation) {
        if (reservationRepository.checkDateAvailability(reservation.getRoomId(), reservation.getStartDate(), reservation.getEndDate())) {
            Room room = roomRepository.findById(reservation.getRoomId());
            if (room != null) {
                double totalPrice = calculateTotalPrice(reservation.getStartDate(), reservation.getEndDate(), room.getPrix());
                reservation.setTotalPrice(totalPrice);
                reservationRepository.create(reservation);
            } else {
                System.out.println("Room not found.");
            }
        } else {
            System.out.println("The selected dates are not available.");
        }
    }

    public void cancel(int reservationId , int clientId) {
        //check if the id  of  user are == id user in reservation
        if(reservationRepository.isUserReservation(reservationId,clientId)){
            reservationRepository.cancel(reservationId);
        }else{
            System.out.println("Reservation not found.");
        }
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
                .filter(r -> r.getPaymentStatus().equals("PAID")).count();

        return Math.max(0, (double) paidReservations / totalReservations * 100);
    }

    public long countCanceledReservations() {
        List<Reservation> reservations = reservationRepository.getAllReservations();
        return reservations.stream()
                .filter(r -> r.getPaymentStatus().equals("CANCELLED"))
                .count();
    }

    private double calculateTotalPrice(LocalDate startDate, LocalDate endDate, double basePrice) {
        double totalPrice = 0;
        LocalDate date = startDate;

        while (!date.isAfter(endDate)) {
            Season season = seasonService.findSeasonByDate(date);
            SpecialEvent event = specialEventService.findEventByDate(date);

            double dailyPrice = basePrice;

            if (season != null) {
                dailyPrice *= season.getPriceMultiplier();
            }
            if (event != null) {
                dailyPrice *= event.getPriceMultiplier();
            }

            totalPrice += dailyPrice;
            date = date.plusDays(1);
        }

        return totalPrice;
    }
}
