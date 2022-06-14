package com.example.easytravelpro;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

public class LeftPanel {

    String activeColor = "#FF9212;";
    String deActiveColor = "#888888;";
    int animationDuration = 800;
    @FXML
    private Label exploreLabel, categoryLabel, savedPlaceLabel,
            accountLabel, paymentLabel, settingLabel, exitLabel, scroll;


    public void explore() {
        setActive(1);
        Animation.scroll(scroll, animationDuration, 154);

    }

    public void category() {
        setActive(2);
        Animation.scroll(scroll, animationDuration, 217);

    }

    public void savedPlace() {
        setActive(3);
        Animation.scroll(scroll, animationDuration, 280);

    }


    public void account() {
        setActive(4);
        Animation.scroll(scroll, animationDuration, 405);

    }

    public void payment() {
        setActive(5);
        Animation.scroll(scroll, animationDuration, 465);

    }

    public void setting() {
        setActive(6);
        Animation.scroll(scroll, animationDuration, 525);

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
            case 1 -> exploreLabel.setStyle("-fx-text-fill: " + activeColor);
            case 2 -> categoryLabel.setStyle("-fx-text-fill: " + activeColor);
            case 3 -> savedPlaceLabel.setStyle("-fx-text-fill: " + activeColor);
            case 4 -> accountLabel.setStyle("-fx-text-fill: " + activeColor);
            case 5 -> paymentLabel.setStyle("-fx-text-fill: " + activeColor);
            case 6 -> settingLabel.setStyle("-fx-text-fill: " + activeColor);
        }
    }

    public void setGray() {
        exploreLabel.setStyle("-fx-text-fill: " + deActiveColor);
        categoryLabel.setStyle("-fx-text-fill: " + deActiveColor);
        savedPlaceLabel.setStyle("-fx-text-fill: " + deActiveColor);
        accountLabel.setStyle("-fx-text-fill: " + deActiveColor);
        paymentLabel.setStyle("-fx-text-fill: " + deActiveColor);
        settingLabel.setStyle("-fx-text-fill: " + deActiveColor);
    }
}

/*
    @FXML
    private Button btn;

    public void changeColorEntered() {
        btn.setStyle("-fx-background-activeColor: #79EAFD;");
    }

    public void changeColorExited() {
        btn.setStyle("-fx-background-activeColor: #3CD4ED;");
    }

    public void changeColorPressed() {
        btn.setStyle("-fx-background-activeColor: #0CA2BB;");
    }

    public void changeColorReleased() {
        btn.setStyle("-fx-background-activeColor: #79EAFD;");
    }
*/