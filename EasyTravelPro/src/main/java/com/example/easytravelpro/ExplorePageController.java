package com.example.easytravelpro;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ExplorePageController implements Initializable {

    @FXML
    private Label villaLabel, tentLabel;
    int flag = 0;

    public void activePics() {
        if (flag == 0) {
            goRight();
        } else {
            goLeft();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int a = (int) Method.random(15, 25);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(a), e -> {
            activePics();
        }));
        timeline.setCycleCount(javafx.animation.Animation.INDEFINITE);
        timeline.play();
    }

    public void goRight() {
        if (flag == 0) {
            Method.changeMainPagesPhotos(villaLabel, tentLabel, 732);
            flag = 1;
        }
    }

    public void goLeft() {
        if (flag == 1) {
            Method.changeMainPagesPhotos(tentLabel, villaLabel, -732);
            flag = 0;
        }

    }
}
