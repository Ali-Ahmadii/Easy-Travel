package com.example.easytravelpro;

public class Passenger {

    private String username;
    private String password;
    private String fullName;
    private String location;
    private String phoneNumber;
    private String email;
    Passenger(String username,String password, String fullName, String location , String phoneNumber ,String email) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getLocation() {
        return location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }
}
