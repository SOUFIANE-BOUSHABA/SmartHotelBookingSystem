package controller;

import model.Reservation;
import model.Room;
import model.enums.PaymentStatus;
import service.ReservationService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ReservationController {
    private ReservationService reservationService;

    public ReservationController() {
        this.reservationService = new ReservationService();
    }

    public void handleReservationCreation() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter start date (YYYY-MM-DD): ");
        LocalDate startDate = LocalDate.parse(scanner.nextLine());

        System.out.print("Enter end date (YYYY-MM-DD): ");
        LocalDate endDate = LocalDate.parse(scanner.nextLine());

        List<Room> availableRooms = reservationService.findAvailableRooms(startDate, endDate);

        if (availableRooms.isEmpty()) {
            System.out.println("No rooms available for the selected dates.");
            return;
        }

        System.out.println("Available rooms:");
        for (Room room : availableRooms) {
            System.out.println("Room ID: " + room.getRoomNumber() +
                    ", Type: " + room.getRoomType() +
                    ", Price: " + room.getPrix());
        }

        System.out.print("Enter the ID of the room you want to book: ");
        int roomId = Integer.parseInt(scanner.nextLine());

        if (availableRooms.stream().noneMatch(room -> room.getRoomNumber() == roomId)) {
            System.out.println("Invalid room ID.");
            return;
        }

        System.out.print("Enter your client ID: ");
        int clientId = Integer.parseInt(scanner.nextLine());

        Reservation reservation = new Reservation(0, clientId, roomId, startDate, endDate, PaymentStatus.PENDING.toString(), 0.0);

        reservationService.createReservation(reservation);
    }

    public void handleReservationCancellation() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the reservation ID to cancel: ");
        int reservationId = Integer.parseInt(scanner.nextLine());

        reservationService.cancel(reservationId);
        System.out.println("Reservation cancelled successfully.");
    }

    public void showStatistics() {
        double occupancyRate = reservationService.calculateOccupancyRate();
        long canceledReservations = reservationService.countCanceledReservations();

        System.out.println("Occupancy Rate: " + occupancyRate + "%");
        System.out.println("Canceled Reservations: " + canceledReservations);
    }
}
