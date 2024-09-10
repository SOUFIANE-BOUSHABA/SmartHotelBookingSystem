package controller;

import model.Room;
import model.enums.RoomType;
import service.RoomService;

import java.util.List;

public class RoomController {
    private RoomService roomService;

    public RoomController() {
        this.roomService = new RoomService();

    }

    public void createRoom(int id, RoomType roomType, double prix, int hotelId) {
        Room room = new Room(id, roomType, prix, hotelId);
        roomService.createRoom(room);
    }

    public Room getRoom(int id) {
        return roomService.getRoomById(id);
    }

    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    public void updateRoom(int id, RoomType roomType, double prix, int hotelId) {
        Room room = new Room(id, roomType, prix, hotelId);
        roomService.updateRoom(room);
    }


    public void deleteRoom(int id) {
        roomService.deleteRoom(id);
    }
}
