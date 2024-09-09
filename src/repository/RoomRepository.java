package repository;

import model.Room;
import java.util.List;

public interface RoomRepository {
    void create(Room room);
    Room findById(int id);
    List<Room> findAll();
    void update(Room room);
    void delete(int id);
}
