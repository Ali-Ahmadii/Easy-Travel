package com.example.easytravelpro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.io.IOException;

public class LeftPanel {
    String activeColor = "#FF9212;";
    String deActiveColor = "#888888;";
    int animationDuration = 800;
    @FXML
    private Label exploreLabel, categoryLabel, savedPlaceLabel,
            accountLabel, paymentLabel, settingLabel,
            exitLabel, scroll;

    ActionEvent event;

    public void explore() throws IOException {
        if (SignInUp.enteredToApp) {
            setActive(1);
            Animation.scroll(scroll, animationDuration, 0);
        } else
            Method.notification(Alert.AlertType.INFORMATION, "Account",
                    "You haven't logged in",
                    "to use our Application you must Sign up/in first");
    }

    public void category() {
        if (SignInUp.enteredToApp) {
            setActive(2);
            Animation.scroll(scroll, animationDuration, 63);
        } else
            Method.notification(Alert.AlertType.INFORMATION, "Account",
                    "You haven't logged in",
                    "to use our Application you must Sign up/in first");
    }

    public void savedPlace() {
        if (SignInUp.enteredToApp) {
            setActive(3);
            Animation.scroll(scroll, animationDuration, 126);
        } else
            Method.notification(Alert.AlertType.INFORMATION, "Account",
                    "You haven't logged in",
                    "to use our Application you must Sign up/in first");
    }


    public void account() {
        if (SignInUp.enteredToApp) {
            setActive(4);
            Animation.scroll(scroll, animationDuration, 251);
        } else
            Method.notification(Alert.AlertType.INFORMATION, "Account",
                    "You haven't logged in",
                    "to use our Application you must Sign up/in first");
    }

    public void payment() {
        if (SignInUp.enteredToApp) {
            setActive(5);
            Animation.scroll(scroll, animationDuration, 311);
        } else
            Method.notification(Alert.AlertType.INFORMATION, "Account",
                    "You haven't logged in",
                    "to use our Application you must Sign up/in first");
    }

    public void setting() {
        if (SignInUp.enteredToApp) {
            setActive(6);
            Animation.scroll(scroll, animationDuration, 371);
        } else
            Method.notification(Alert.AlertType.INFORMATION, "Account",
                    "You haven't logged in",
                    "to use our Application you must Sign up/in first");
    }

    public void close() {
        exitLabel.setStyle("-fx-text-fill: #cc0000;");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Exit From Easy Travel");
        alert.setContentText("Are you sure that you want to close the Application?");
        if (alert.showAndWait().get() == ButtonType.OK) System.exit(0);
        else exitLabel.setStyle("-fx-text-fill: " + deActiveColor);
    }

    public void setActive(int flag) {
        setGray();
        switch (flag) {
            case 1 -> {
                MainPanel.explorePage.setVisible(true);
                exploreLabel.setStyle("-fx-text-fill: " + activeColor);
            }
            case 2 -> {
                MainPanel.categoryPage.setVisible(true);
                categoryLabel.setStyle("-fx-text-fill: " + activeColor);
            }
            case 3 -> {
                MainPanel.savedPlacePage.setVisible(true);
                savedPlaceLabel.setStyle("-fx-text-fill: " + activeColor);
            }
            case 4 -> {
                MainPanel.accountPage.setVisible(true);
                accountLabel.setStyle("-fx-text-fill: " + activeColor);
            }
            case 5 -> {
                MainPanel.paymentPage.setVisible(true);
                paymentLabel.setStyle("-fx-text-fill: " + activeColor);
            }
            case 6 -> {
                MainPanel.settingPage.setVisible(true);
                settingLabel.setStyle("-fx-text-fill: " + activeColor);
            }
        }
    }

    public void setGray() {
        exploreLabel.setStyle("-fx-text-fill: " + deActiveColor);
        categoryLabel.setStyle("-fx-text-fill: " + deActiveColor);
        savedPlaceLabel.setStyle("-fx-text-fill: " + deActiveColor);
        accountLabel.setStyle("-fx-text-fill: " + deActiveColor);
        paymentLabel.setStyle("-fx-text-fill: " + deActiveColor);
        settingLabel.setStyle("-fx-text-fill: " + deActiveColor);

        MainPanel.explorePage.setVisible(false);
        MainPanel.categoryPage.setVisible(false);
        MainPanel.savedPlacePage.setVisible(false);
        MainPanel.accountPage.setVisible(false);
        MainPanel.paymentPage.setVisible(false);
        MainPanel.settingPage.setVisible(false);
    }
}