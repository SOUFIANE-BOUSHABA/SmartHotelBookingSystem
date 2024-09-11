import controller.ReservationController;
import model.Room;
import model.enums.PaymentStatus;
import service.ReservationService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ReservationController reservationController = new ReservationController();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Create Reservation");
            System.out.println("2. Cancel Reservation");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    reservationController.handleReservationCreation();
                    break;
                case 2:
                    reservationController.handleReservationCancellation();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
