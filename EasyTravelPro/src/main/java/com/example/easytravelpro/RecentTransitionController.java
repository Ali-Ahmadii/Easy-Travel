package com.example.easytravelpro;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

import static com.example.easytravelpro.PaymentPageController.q;
import static com.example.easytravelpro.PaymentPageController.transitions;

public class RecentTransitionController implements Initializable {

    @FXML
    private VBox mainPanel;
    @FXML
    private Label amount, message, date, time, cardNumber;

    static boolean isAnyCardSelected = false;
    static boolean isSuccessfulCharge;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!(isSuccessfulCharge)) {
            mainPanel.setStyle("-fx-background-color: #ff6d6d;" +
                    "-fx-background-radius: 15;");
            amount.setStyle("-fx-text-fill: #ff0000;");
            message.setStyle("-fx-text-fill: #ff0000;");
        }
            amount.setText("$" + transitions.get(q).getAmount());
            message.setText(transitions.get(q).getMessage());
            date.setText(transitions.get(q).getDate());
            time.setText(transitions.get(q).getTime());
            cardNumber.setText(CardCompoController.lastCardTouch);
    }

}
