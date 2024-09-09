package service;

import model.Room;
import repository.RoomRepository;
import repository.impl.RoomRepositoryImpl;

import java.util.List;

public class RoomService {
    private RoomRepository roomRepository;

    public RoomService() {
        this.roomRepository = new RoomRepositoryImpl();
    }

    public void createRoom(Room room) {
        if (room.getPrix() > 0) {
            roomRepository.create(room);
        } else {
            System.err.println(" prix must be positive");
        }
    }

    public Room getRoomById(int id) {
        return roomRepository.findById(id);
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public void updateRoom(Room room) {
        Room existingRoom = roomRepository.findById(room.getRoomNumber());
        if (existingRoom != null) {
            roomRepository.update(room);
        } else {
            System.err.println(" not found .");
        }
    }

    public void deleteRoom(int id) {
        Room existingRoom = roomRepository.findById(id);
        if (existingRoom != null) {
            roomRepository.delete(id);
        } else {
            System.err.println(" not found ");
        }
    }
}
