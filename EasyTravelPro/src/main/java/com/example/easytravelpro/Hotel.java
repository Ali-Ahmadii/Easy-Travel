package com.example.easytravelpro;

public class Hotel {

    private String hotelName;
    private String location;

    Hotel(String hotelName, String location) {
        this.hotelName = hotelName;
        this.location = location;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
