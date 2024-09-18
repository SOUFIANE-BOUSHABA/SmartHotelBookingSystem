import controller.ClientController;
import controller.HotelController;
import controller.ReservationController;
import controller.RoomController;
import model.enums.RoomType;

import java.util.Scanner;

public class Main {

    // ANSI color codes
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";



    private static final Scanner scanner = new Scanner(System.in);
    private static final ClientController clientController = new ClientController();
    private static final HotelController hotelController = new HotelController();
    private static final RoomController roomController = new RoomController();
    private static final ReservationController reservationController = new ReservationController();


    public static void main(String[] args) {

        System.out.println("\n\n");
        System.out.println("  ____ ____ ____ ____ ____    _    _    ____ _    ____ ____ ");
        System.out.println(" ||G |||R |||A |||N |||D ||  / \\  / \\  / ___| |  |  _ \\  _ \\");
        System.out.println(" ||__|||__|||__|||__|||__|| / _ \\| | | | |   | |  | | | | | |");
        System.out.println(" |/__\\|/__\\|/__\\|/__\\|/__\\|/ ___ \\ | |_| |___| |__| |_| |_| |");
        System.out.println("                      WELCOME TO OUR HOTEL!                 ");
        System.out.println("\n\n");

        while (true) {
            System.out.println( WHITE + "\n--- Main Menu ---" + RESET);
            System.out.println(GREEN + "1. Client Management" + RESET);
            System.out.println(GREEN + "2. Hotel Management" + RESET);
            System.out.println(GREEN + "3. Room Management" + RESET);
            System.out.println(GREEN + "4. Reservation Management" + RESET);
            System.out.println(RED + "5. Exit" + RESET);

            System.out.print(CYAN + "Choose an option: " + RESET);
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
                    System.out.println(RED + "Exiting the system." + RESET);
                    return;
                default:
                    System.out.println(RED + "Invalid option. Please try again." + RESET);
            }
        }
    }

    private static void clientManagementMenu() {
        while (true) {
            System.out.println(WHITE + "\n--- Client Management ---" + RESET);
            System.out.println(GREEN + "1. Create Client" + RESET);
            System.out.println(GREEN + "2. View Client by ID" + RESET);
            System.out.println(GREEN + "3. View All Clients" + RESET);
            System.out.println(GREEN + "4. Update Client" + RESET);
            System.out.println(GREEN + "5. Delete Client" + RESET);
            System.out.println(RED + "6. Back to Main Menu" + RESET);

            System.out.print(CYAN + "Choose an option: " + RESET);
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.print(CYAN + "Enter Client ID: " + RESET);
                    int id = Integer.parseInt(scanner.nextLine());
                    System.out.print(CYAN + "Enter Name: " + RESET);
                    String name = scanner.nextLine();
                    System.out.print(CYAN + "Enter Email: " + RESET);
                    String email = scanner.nextLine();
                    System.out.print(CYAN + "Enter Phone: " + RESET);
                    String phone = scanner.nextLine();
                    System.out.print(CYAN + "Enter Balance: " + RESET);
                    double balance = Double.parseDouble(scanner.nextLine()); // Read balance

                    clientController.createClient(id, name, email, phone, balance); // Pass balance
                    break;

                case 2:
                    System.out.print(CYAN + "Enter Client ID: " + RESET);
                    int clientId = Integer.parseInt(scanner.nextLine());
                    System.out.println(clientController.findById(clientId));
                    break;

                case 3:
                    System.out.println(clientController.findAll());
                    break;

                case 4:
                    System.out.print(CYAN + "Enter Client ID to Update: " + RESET);
                    int updateId = Integer.parseInt(scanner.nextLine());
                    System.out.print(CYAN + "Enter New Name: " + RESET);
                    String newName = scanner.nextLine();
                    System.out.print(CYAN + "Enter New Email: " + RESET);
                    String newEmail = scanner.nextLine();
                    System.out.print(CYAN + "Enter New Phone: " + RESET);
                    String newPhone = scanner.nextLine();
                    System.out.print(CYAN + "Enter New Balance: " + RESET);
                    double newBalance = Double.parseDouble(scanner.nextLine()); // Read new balance

                    clientController.updateClient(updateId, newName, newEmail, newPhone, newBalance); // Pass new balance
                    break;

                case 5:
                    System.out.print(CYAN + "Enter Client ID to Delete: " + RESET);
                    int deleteId = Integer.parseInt(scanner.nextLine());
                    clientController.deleteClient(deleteId);
                    break;

                case 6:
                    return;

                default:
                    System.out.println(RED + "Invalid option. Please try again." + RESET);
            }
        }
    }


    private static void hotelManagementMenu() {
        while (true) {
            System.out.println( WHITE + "\n--- Hotel Management ---" + RESET);
            System.out.println(GREEN + "1. Create Hotel" + RESET);
            System.out.println(GREEN + "2. View All Hotels" + RESET);
            System.out.println(GREEN + "3. Delete Hotel" + RESET);
            System.out.println(RED + "4. Back to Main Menu" + RESET);

            System.out.print(CYAN + "Choose an option: " + RESET);
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.print(CYAN + "Enter Hotel Name: " + RESET);
                    String hotelName = scanner.nextLine();
                    hotelController.createHotel(hotelName);
                    break;
                case 2:
                    System.out.println(hotelController.findALL());
                    break;
                case 3:
                    System.out.print(CYAN + "Enter Hotel ID to Delete: " + RESET);
                    int hotelId = Integer.parseInt(scanner.nextLine());
                    hotelController.deleteHotel(hotelId);
                    break;
                case 4:
                    return;
                default:
                    System.out.println(RED + "Invalid option. Please try again." + RESET);
            }
        }
    }

    private static void roomManagementMenu() {
        while (true) {
            System.out.println( WHITE + "\n--- Room Management ---" + RESET);
            System.out.println(GREEN + "1. Create Room" + RESET);
            System.out.println(GREEN + "2. View Room by ID" + RESET);
            System.out.println(GREEN + "3. View All Rooms" + RESET);
            System.out.println(GREEN + "4. Update Room" + RESET);
            System.out.println(GREEN + "5. Delete Room" + RESET);
            System.out.println(RED + "6. Back to Main Menu" + RESET);

            System.out.print(CYAN + "Choose an option: " + RESET);
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.print(CYAN + "Enter Room ID: " + RESET);
                    int roomId = Integer.parseInt(scanner.nextLine());
                    System.out.print(CYAN + "Enter Room Type (SINGLE, DOUBLE, SUITE): " + RESET);
                    RoomType roomType = RoomType.valueOf(scanner.nextLine().toUpperCase());
                    System.out.print(CYAN + "Enter Room Price: " + RESET);
                    double price = Double.parseDouble(scanner.nextLine());
                    System.out.print(CYAN + "Enter Hotel ID: " + RESET);
                    int hotelId = Integer.parseInt(scanner.nextLine());
                    roomController.createRoom(roomId, roomType, price, hotelId);
                    break;
                case 2:
                    System.out.print(CYAN + "Enter Room ID: " + RESET);
                    int roomById = Integer.parseInt(scanner.nextLine());
                    System.out.println(roomController.getRoom(roomById));
                    break;
                case 3:
                    System.out.println(roomController.getAllRooms());
                    break;
                case 4:
                    System.out.print(CYAN + "Enter Room ID to Update: " + RESET);
                    int updateRoomId = Integer.parseInt(scanner.nextLine());
                    System.out.print(CYAN + "Enter New Room Type (SINGLE, DOUBLE, SUITE): " + RESET);
                    RoomType newRoomType = RoomType.valueOf(scanner.nextLine().toUpperCase());
                    System.out.print(CYAN + "Enter New Price: " + RESET);
                    double newPrice = Double.parseDouble(scanner.nextLine());
                    System.out.print(CYAN + "Enter Hotel ID: " + RESET);
                    int newHotelId = Integer.parseInt(scanner.nextLine());
                    roomController.updateRoom(updateRoomId, newRoomType, newPrice, newHotelId);
                    break;
                case 5:
                    System.out.print(CYAN + "Enter Room ID to Delete: " + RESET);
                    int deleteRoomId = Integer.parseInt(scanner.nextLine());
                    roomController.deleteRoom(deleteRoomId);
                    break;
                case 6:
                    return;
                default:
                    System.out.println(RED + "Invalid option. Please try again." + RESET);
            }
        }
    }

    private static void reservationManagementMenu() {
        while (true) {
            System.out.println( WHITE + "\n--- Reservation Management ---" + RESET);
            System.out.println(GREEN + "1. Create Reservation" + RESET);
            System.out.println(GREEN + "2. Cancel Reservation" + RESET);

            System.out.println(GREEN + "3. Show Reservation Statistics" + RESET);
            System.out.println(RED + "4. Back to Main Menu" + RESET);
            System.out.println(GREEN + "5. create  Reservation for MISE EN SITUATION " + RESET);

            System.out.print(CYAN + "Choose an option: " + RESET);
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
                    return;

                    case 5:
                        reservationController.createReservationForMiseEnSituation();
                default:
                    System.out.println(RED + "Invalid option. Please try again." + RESET);
            }
        }
    }
}
