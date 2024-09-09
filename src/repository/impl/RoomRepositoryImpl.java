package repository.impl;

import dao.RoomDAO;
import model.Room;
import repository.RoomRepository;

import java.util.List;

public class RoomRepositoryImpl implements RoomRepository {
    private RoomDAO roomDAO;

    public RoomRepositoryImpl() {
        this.roomDAO = new RoomDAO();
    }

    @Override
    public void create(Room room) {
        roomDAO.createRoom(room);
    }

    @Override
    public Room findById(int id) {
        return roomDAO.findById(id);
    }

    @Override
    public List<Room> findAll() {
        return roomDAO.findAll();
    }

    @Override
    public void update(Room room) {
        roomDAO.updateRoom(room);
    }

    @Override
    public void delete(int id) {
        roomDAO.deleteRoom(id);
    }
}
