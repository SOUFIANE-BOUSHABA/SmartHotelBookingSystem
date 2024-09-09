package model;

import model.enums.RoomType;

public class Room {
    private  int id;
    private RoomType roomType;
    private double prix;

    public Room(int id, RoomType roomType, double prix) {
        this.id = id;
        this.roomType = roomType;
        this.prix = prix;
    }


    public int getRoomNumber() {
        return id;
    }

    public void setRoomNumber(int id) {
        this.id = id;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
}
