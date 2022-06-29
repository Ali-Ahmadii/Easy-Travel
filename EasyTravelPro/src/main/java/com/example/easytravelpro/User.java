package com.example.easytravelpro;

public class User {

    final private String username;
    private String password;
    final private String fullName;
    final private String location;
    final private String phoneNumber;
    final private String email;

    User(String username,String password, String fullName, String location , String phoneNumber ,String email ) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
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
