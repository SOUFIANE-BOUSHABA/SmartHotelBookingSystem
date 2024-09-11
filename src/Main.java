import controller.ClientController;
import controller.HotelController;
import controller.ReservationController;
import controller.RoomController;
import model.enums.RoomType;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ClientController clientController = new ClientController();
    private static final HotelController hotelController = new HotelController();
    private static final RoomController roomController = new RoomController();
    private static final ReservationController reservationController = new ReservationController();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Client Management");
            System.out.println("2. Hotel Management");
            System.out.println("3. Room Management");
            System.out.println("4. Reservation Management");
            System.out.println("5. Exit");

            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    clientManagementMenu();
                    break;
                case 2:
                    hotelManagementMenu();
                    break;
                case 3:
                    roomManagementMenu();
                    break;
                case 4:
                    reservationManagementMenu();
                    break;
                case 5:
                    System.out.println("Exiting the system.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void clientManagementMenu() {
        while (true) {
            System.out.println("\n--- Client Management ---");
            System.out.println("1. Create Client");
            System.out.println("2. View Client by ID");
            System.out.println("3. View All Clients");
            System.out.println("4. Update Client");
            System.out.println("5. Delete Client");
            System.out.println("6. Back to Main Menu");

            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.print("Enter Client ID: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter Phone: ");
                    String phone = scanner.nextLine();
                    clientController.createClient(id, name, email, phone);
                    break;
                case 2:
                    System.out.print("Enter Client ID: ");
                    int clientId = Integer.parseInt(scanner.nextLine());
                    System.out.println(clientController.findById(clientId));
                    break;
                case 3:
                    System.out.println(clientController.findAll());
                    break;
                case 4:
                    System.out.print("Enter Client ID to Update: ");
                    int updateId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter New Name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter New Email: ");
                    String newEmail = scanner.nextLine();
                    System.out.print("Enter New Phone: ");
                    String newPhone = scanner.nextLine();
                    clientController.updateClient(updateId, newName, newEmail, newPhone);
                    break;
                case 5:
                    System.out.print("Enter Client ID to Delete: ");
                    int deleteId = Integer.parseInt(scanner.nextLine());
                    clientController.deleteClient(deleteId);
                    break;
                case 6:
                    return; // Back to main menu
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void hotelManagementMenu() {
        while (true) {
            System.out.println("\n--- Hotel Management ---");
            System.out.println("1. Create Hotel");
            System.out.println("2. View All Hotels");
            System.out.println("3. Delete Hotel");
            System.out.println("4. Back to Main Menu");

            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.print("Enter Hotel Name: ");
                    String hotelName = scanner.nextLine();
                    hotelController.createHotel(hotelName);
                    break;
                case 2:
                    System.out.println(hotelController.findALL());
                    break;
                case 3:
                    System.out.print("Enter Hotel ID to Delete: ");
                    int hotelId = Integer.parseInt(scanner.nextLine());
                    hotelController.deleteHotel(hotelId);
                    break;
                case 4:
                    return; // Back to main menu
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void roomManagementMenu() {
        while (true) {
            System.out.println("\n--- Room Management ---");
            System.out.println("1. Create Room");
            System.out.println("2. View Room by ID");
            System.out.println("3. View All Rooms");
            System.out.println("4. Update Room");
            System.out.println("5. Delete Room");
            System.out.println("6. Back to Main Menu");

            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.print("Enter Room ID: ");
                    int roomId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter Room Type (SINGLE, DOUBLE, SUITE): ");
                    RoomType roomType = RoomType.valueOf(scanner.nextLine().toUpperCase());
                    System.out.print("Enter Room Price: ");
                    double price = Double.parseDouble(scanner.nextLine());
                    System.out.print("Enter Hotel ID: ");
                    int hotelId = Integer.parseInt(scanner.nextLine());
                    roomController.createRoom(roomId, roomType, price, hotelId);
                    break;
                case 2:
                    System.out.print("Enter Room ID: ");
                    int roomById = Integer.parseInt(scanner.nextLine());
                    System.out.println(roomController.getRoom(roomById));
                    break;
                case 3:
                    System.out.println(roomController.getAllRooms());
                    break;
                case 4:
                    System.out.print("Enter Room ID to Update: ");
                    int updateRoomId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter New Room Type (SINGLE, DOUBLE, SUITE): ");
                    RoomType newRoomType = RoomType.valueOf(scanner.nextLine().toUpperCase());
                    System.out.print("Enter New Price: ");
                    double newPrice = Double.parseDouble(scanner.nextLine());
                    System.out.print("Enter Hotel ID: ");
                    int newHotelId = Integer.parseInt(scanner.nextLine());
                    roomController.updateRoom(updateRoomId, newRoomType, newPrice, newHotelId);
                    break;
                case 5:
                    System.out.print("Enter Room ID to Delete: ");
                    int deleteRoomId = Integer.parseInt(scanner.nextLine());
                    roomController.deleteRoom(deleteRoomId);
                    break;
                case 6:
                    return; // Back to main menu
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void reservationManagementMenu() {
        while (true) {
            System.out.println("\n--- Reservation Management ---");
            System.out.println("1. Create Reservation");
            System.out.println("2. Cancel Reservation");
            System.out.println("3. Show Reservation Statistics");
            System.out.println("4. Back to Main Menu");

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
                    reservationController.showStatistics();
                    break;
                case 4:
                    return; // Back to main menu
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
