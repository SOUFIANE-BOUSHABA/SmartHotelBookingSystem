import controller.RoomController;
import model.enums.RoomType;
import model.Room;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        RoomController roomController = new RoomController();

        // Create Rooms
        System.out.println("Creating rooms...");
        roomController.createRoom(1, RoomType.SINGLE, 100.0);
        roomController.createRoom(2, RoomType.DOUBLE, 150.0);
        roomController.createRoom(3, RoomType.TRIPLE, 200.0);

        // Get Room by ID
        System.out.println("\nFetching room with ID 1...");
        Room room1 = roomController.getRoom(1);
        if (room1 != null) {
            System.out.println("Room 1: " + room1.getRoomType() + ", Price: " + room1.getPrix());
        } else {
            System.out.println("Room not found.");
        }

        // Get All Rooms
        System.out.println("\nFetching all rooms...");
        List<Room> allRooms = roomController.getAllRooms();
        for (Room room : allRooms) {
            System.out.println("Room ID: " + room.getRoomNumber() + ", Type: " + room.getRoomType() + ", Price: " + room.getPrix());
        }

        // Update Room
        System.out.println("\nUpdating room with ID 1...");
        roomController.updateRoom(1, RoomType.SINGLE, 120.0);  // Change price of room 1

        // Verify the update
        System.out.println("\nVerifying update for room with ID 1...");
        Room updatedRoom = roomController.getRoom(1);
        if (updatedRoom != null) {
            System.out.println("Updated Room 1: " + updatedRoom.getRoomType() + ", Price: " + updatedRoom.getPrix());
        }

        // Delete Room
        System.out.println("\nDeleting room with ID 3...");
        roomController.deleteRoom(3);

        // Verify deletion
        System.out.println("\nVerifying deletion for room with ID 3...");
        Room deletedRoom = roomController.getRoom(3);
        if (deletedRoom == null) {
            System.out.println("Room 3 deleted successfully.");
        } else {
            System.out.println("Failed to delete room.");
        }

        // Fetch all remaining rooms after deletion
        System.out.println("\nFetching all rooms after deletion...");
        allRooms = roomController.getAllRooms();
        for (Room room : allRooms) {
            System.out.println("Room ID: " + room.getRoomNumber() + ", Type: " + room.getRoomType() + ", Price: " + room.getPrix());
        }
    }
}
