package com.example.easytravelpro;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class CardCompoController implements Initializable {

    @FXML
    private Label cardNumber, ccv2, fullName, expiry;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cardNumber.setText(PaymentPageController.cards.get(PaymentPageController.l).getCardNumber());
        ccv2.setText(PaymentPageController.cards.get(PaymentPageController.l).getCcv2());
        fullName.setText(PaymentPageController.cards.get(PaymentPageController.l).getFullName());
        expiry.setText(PaymentPageController.cards.get(PaymentPageController.l).getExpiry());

    }
}
