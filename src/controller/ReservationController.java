package controller;

import model.Reservation;
import model.Room;
import model.enums.PaymentStatus;
import service.ReservationService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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

        if (startDate.isBefore(LocalDate.now())) {
            System.out.println("Start date cannot be in the past.");
            return;
        }

        if (endDate.isBefore(startDate)) {
            System.out.println("End date cannot be before the start date.");
            return;
        }

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

        System.out.print("Enter your client ID: ");
        int clientId = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter the reservation ID to cancel: ");
        int reservationId = Integer.parseInt(scanner.nextLine());

        reservationService.cancel(reservationId , clientId);
    }
    public void showStatistics() {
        double occupancyRate = reservationService.calculateOccupancyRate();
        long canceledReservations = reservationService.countCanceledReservations();

        System.out.println("Occupancy Rate: " + occupancyRate + "%");
        System.out.println("Canceled Reservations: " + canceledReservations);
    }











































    public void createReservationForMiseEnSituation(){
        Scanner scanner = new Scanner(System.in);

            System.out.print(" start date YYYY-MM-DD: ");
            LocalDate startDate = LocalDate.parse(scanner.nextLine());

            System.out.print(" end date YYYY-MM-DD: ");
            LocalDate endDate = LocalDate.parse(scanner.nextLine());

            if (startDate.isBefore(LocalDate.now())) {
                System.out.println("cant.");
                return;
            }

            if (endDate.isBefore(startDate)) {
                System.out.println("noooo");
                return;
            }

        Optional<List<Room>> optionalAvailableRooms = reservationService.findAvailableRoomsForMiseENSItuation(startDate, endDate);

        if (optionalAvailableRooms.isPresent()) {
            List<Room> availableRooms = optionalAvailableRooms.get();
            if (availableRooms.isEmpty()) {
                System.out.println("No room available.");
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
        } else {
            System.out.println("No room available.");
        }
    }




}
