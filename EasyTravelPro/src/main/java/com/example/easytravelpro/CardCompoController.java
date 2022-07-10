package com.example.easytravelpro;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CardCompoController implements Initializable {

    @FXML
    private Label cardNumber, ccv2, fullName, expiry;
    @FXML
    private AnchorPane cardAnchor;

    static ArrayList<String> cardTouches = new ArrayList<>();
    static ArrayList<AnchorPane> cardAnchors = new ArrayList<>();

    static String lastCardTouch = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cardNumber.setText(PaymentPageController.cards.get(PaymentPageController.l).getCardNumber());
        ccv2.setText(PaymentPageController.cards.get(PaymentPageController.l).getCcv2());
        fullName.setText(PaymentPageController.cards.get(PaymentPageController.l).getFullName());
        expiry.setText(PaymentPageController.cards.get(PaymentPageController.l).getExpiry());

    }
    public void touchCard() {
        if (!(cardTouches.contains(cardNumber.getText()))) {
            cardTouches.add(cardNumber.getText());
            cardAnchors.add(cardAnchor);
        }
        for (int i = 0; i < cardAnchors.size(); i++) {
            cardAnchors.get(i).setStyle("-fx-background-color: #212121;" +
                    "-fx-background-radius: 15;");
        }
        cardAnchor.setStyle("-fx-background-color: #212121;" +
                "-fx-background-radius: 15;" +
                "-fx-border-style: solid;" +
                "-fx-border-color: #ff3d9e;" +
                "-fx-border-radius: 15;" +
                "-fx-border-width: 3");

        lastCardTouch = cardNumber.getText();
        RecentTransitionController.isAnyCardSelected = true;

    }

}
