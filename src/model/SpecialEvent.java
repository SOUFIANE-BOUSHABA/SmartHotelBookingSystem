package model;

import java.time.LocalDate;

public class SpecialEvent {
    private int id;
    private String name;
    private LocalDate eventDate;
    private double priceMultiplier;

    public SpecialEvent(int id, String name, LocalDate eventDate, double priceMultiplier) {
        this.id = id;
        this.name = name;
        this.eventDate = eventDate;
        this.priceMultiplier = priceMultiplier;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public double getPriceMultiplier() {
        return priceMultiplier;
    }

    public void setPriceMultiplier(double priceMultiplier) {
        this.priceMultiplier = priceMultiplier;
    }
}
