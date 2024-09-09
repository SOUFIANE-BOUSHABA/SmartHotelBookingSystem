package service;

import model.Hotel;
import repository.HotelRepository;
import repository.impl.HotelRepositoryImpl;

import java.util.List;

public class HotelService {
    private HotelRepository hotelRepository;

    public HotelService() {
        hotelRepository = new HotelRepositoryImpl();
    }

    public void createHotel(Hotel hotel) {
        hotelRepository.create(hotel);
    }

    public List<Hotel> findAll() {
        return hotelRepository.findAll();
    }

    public void deleteHotel(int id) {
        hotelRepository.delete(id);
    }
}
