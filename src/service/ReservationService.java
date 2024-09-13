package service;

import model.*;
import repository.ClientRepository;
import repository.RoomRepository;
import repository.ReservationRepository;
import repository.impl.RoomRepositoryImpl;
import repository.impl.ReservationRepositoryImpl;
import repository.impl.ClientRepositoryImpl;

import java.time.LocalDate;
import java.util.List;

public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final SeasonService seasonService;
    private final SpecialEventService specialEventService;
    private final ClientRepository clientRepository;

    public ReservationService() {
        this.reservationRepository = new ReservationRepositoryImpl();
        this.clientRepository = new ClientRepositoryImpl();
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

                Client client = clientRepository.findById(reservation.getClientId());

                if (client != null) {
                    if (client.getBalance() >= totalPrice) {
                        client.setBalance(client.getBalance() - totalPrice);
                        clientRepository.update(client);

                        reservationRepository.create(reservation);
                        System.out.println("Reservation created successfully.");
                    } else {
                        System.out.println("Insufficient balance for the reservation.");
                    }
                } else {
                    System.out.println("Client not found.");
                }
            } else {
                System.out.println("Room not found.");
            }
        } else {
            System.out.println("The selected dates are not available.");
        }
    }


    public void cancel(int reservationId, int clientId) {
        // Check if the reservation belongs to the client
        if (reservationRepository.isUserReservation(reservationId, clientId)) {
            Reservation reservation = reservationRepository.getAllReservations().stream()
                    .filter(r -> r.getId() == reservationId)
                    .findFirst()
                    .orElse(null);

            if (reservation != null) {
                double totalPrice = reservation.getTotalPrice();
                Client client = clientRepository.findById(clientId);
                if (client != null) {
                    client.setBalance(client.getBalance() + totalPrice);
                    clientRepository.update(client);

                    reservationRepository.cancel(reservationId);
                    System.out.println(" money returned  successfully");
                } else {
                    System.out.println("Client not found.");
                }
            } else {
                System.out.println("Reservation not found.");
            }
        } else {
            System.out.println("Reservation does not belong to YOU HHH");
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
