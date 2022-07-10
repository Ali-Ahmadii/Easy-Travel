package com.example.easytravelpro;

public class Transition {

    private String amount,message,date,time;

    public Transition(String amount, String message, String date, String time) {
        this.amount = amount;
        this.message = message;
        this.date = date;
        this.time = time;
    }

    public String getAmount() {
        return amount;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
