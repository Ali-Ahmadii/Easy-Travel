package com.example.easytravelpro;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class SignInUp {

    @FXML
    private ImageView beachPicture, roomPicture;
    @FXML
    private VBox signUpPage, signInPage;
    @FXML
    private Label suBtn,alreadyLogged,siBtn,newUser;

    public void changeColorEntered() {
        suBtn.setStyle("-fx-background-color: #ffaf52;" +
                "-fx-background-radius: 50;");

        siBtn.setStyle("-fx-background-color: #ffaf52;" +
                "-fx-background-radius: 50;");
    }

    public void changeColorExited() {
        suBtn.setStyle("-fx-background-color: #FF9212;" +
                "-fx-background-radius: 50;");

        siBtn.setStyle("-fx-background-color: #FF9212;" +
                "-fx-background-radius: 50;");
    }

    public void changeColorPressed() {
        suBtn.setStyle("-fx-background-color: #e38600;" +
                "-fx-background-radius: 50;");

        siBtn.setStyle("-fx-background-color: #e38600;" +
                "-fx-background-radius: 50;");
    }

    public void changeColorReleased() {
        suBtn.setStyle("-fx-background-color: #ffaf52;" +
                "-fx-background-radius: 50;");

        siBtn.setStyle("-fx-background-color: #ffaf52;" +
                "-fx-background-radius: 50;");
    }

    public void changeColorEnter() {
        alreadyLogged.setStyle("-fx-text-fill: #3172f6;");
        newUser.setStyle("-fx-text-fill: #3172f6;");
    }

    public void changeColorExit() {
        alreadyLogged.setStyle("-fx-text-fill: #123d93;");
        newUser.setStyle("-fx-text-fill: #123d93;");
    }

}
