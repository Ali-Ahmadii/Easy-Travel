package com.example.easytravelpro;

public class Card {

    private String cardNumber;
    private String ccv2;
    private String fullName;
    private String expiry;

    Card(String cardNumber, String ccv2, String fullName, String expiry) {
        this.cardNumber = cardNumber;
        this.ccv2 = ccv2;
        this.fullName = fullName;
        this.expiry = expiry;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getExpiry() {
        return expiry;
    }

    public String getFullName() {
        return fullName;
    }

    public String getCcv2() {
        return ccv2;
    }
}
