package com.example.easytravelpro;

public class Room extends Hotel {

    private int capacity;
    private String facility;
    private double price;

    Room(String hotelName, String location, int capacity, String facility, double price) {
        super(hotelName, location);
        this.capacity = capacity;
        this.facility = facility;
        this.price = price;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
