package repository;

import model.Hotel;

import java.util.List;

public interface HotelRepository {

    void create(Hotel hotel);
    List<Hotel> findAll();
    void delete(int id);
}
