package com.example.easytravelpro;

public class Passenger extends User {

    private double balance;


    Passenger(String username, String password, String fullName, String location, String phoneNumber, String email, double balance) {
        super(username, password, fullName, location, phoneNumber, email);
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
