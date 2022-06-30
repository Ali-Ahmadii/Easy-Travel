package com.example.easytravelpro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class PaymentPageController {

    @FXML
    private HBox cardBox;
    @FXML
    private Label accountBalance;
    @FXML
    private TextField cardNumber, year, month, ccv2;
    @FXML
    private RadioButton p50, p100, p200, p500;
    double selectedAmount = 50;


    static ArrayList<Card> cards = new ArrayList<>();
    static ArrayList<AnchorPane> cardCompos = new ArrayList<>();
    static int l = 0;

    public void addCard(ActionEvent event) throws IOException {
        cards.add(new Card(cardNumber.getText()
                ,ccv2.getText(),SignInUp.passengers.get(SignInUp.i - 1).getFullName()
                ,year.getText() + "/" + month.getText()));

        cardCompos.add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("CardComponent.fxml"))));
        cardBox.getChildren().add(cardCompos.get(l));

        l++;
    }


    public void charge(ActionEvent event) {

        if (p50.isSelected())
            selectedAmount = 50;
        else if (p100.isSelected())
            selectedAmount = 100;
        else if (p200.isSelected())
            selectedAmount = 200;
        else if (p500.isSelected())
            selectedAmount = 500;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you Sure?");
        alert.setContentText("With this action you will add $" + selectedAmount + " to your Balance.");

        if (alert.showAndWait().get() == ButtonType.OK) {

            Method.notification(Alert.AlertType.INFORMATION,
                    "Great!",
                    "Successful Charge!",
                    "You add $" + selectedAmount + " to your Balance.");

            SignInUp.passengers.get(SignInUp.i - 1).setBalance(selectedAmount +
                    SignInUp.passengers.get(SignInUp.i - 1).getBalance());

            accountBalance.setText("Account Balance: " +
                    SignInUp.passengers.get(SignInUp.i - 1).getBalance());
        }
    }
}
