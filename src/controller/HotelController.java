package controller;

import model.Hotel;
import service.HotelService;

import java.util.List;

public class HotelController {

    private HotelService hotelService;

    public HotelController() {
        hotelService = new HotelService();
    }

    public void createHotel(String name) {
        Hotel hotel = new Hotel(1, name);
        hotelService.createHotel(hotel);
    }

    // Fix: Return the list of hotels
    public List<Hotel> findALL() {
        return hotelService.findAll();
    }

    public void deleteHotel(int id) {
        hotelService.deleteHotel(id);
    }
}
