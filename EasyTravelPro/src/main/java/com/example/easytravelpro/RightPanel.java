package com.example.easytravelpro;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class RightPanel implements Initializable {

    @FXML
    private Label fullName, email;
    @FXML
    private ImageView profile;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> {
            if (SignInUp.enteredToApp) {
                fullName.setText(SignInUp.passengers.get(SignInUp.i - 1).getFullName());
                email.setText(SignInUp.passengers.get(SignInUp.i - 1).getEmail());
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
