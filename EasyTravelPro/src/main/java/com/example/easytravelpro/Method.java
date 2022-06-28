package com.example.easytravelpro;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.util.ArrayList;

public class Method {

    public static void notification(Alert.AlertType alertType, String title, String header, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static long random(long min, long max) {
        long myRandom;
        myRandom = (long) Math.floor(Math.random() * (max - min + 1) + min);
        return myRandom;
    }

    public static void changePagesInLoginPage(Node page1, Node pic1, Node page2, Node pic2, int duration, int x1, int x2) {
        Animation.changeLoginPage(page1, duration, x1);
        Animation.changeLoginPage(pic1, duration, x2);
        Animation.Fade(page1, 0, duration, 1, 0);
        Animation.Fade(pic1, 0, duration, 1, 0);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds((float) duration / 1000), e -> {
            page1.setVisible(false);
            pic1.setVisible(false);
            Animation.changeLoginPage(page1, duration, x2);
            Animation.changeLoginPage(pic1, duration, x1);
            Animation.Fade(page2, 0, duration, 0, 1);
            Animation.Fade(pic2, 0, duration, 0, 1);
        }));
        timeline.play();
        page2.setVisible(true);
        pic2.setVisible(true);
    }

    public static void changeMainPagesPhotos(Label pic1, Label pic2, int a) {
//        Animation.mainPageChanger(pic1, 700, a);
//        Animation.mainPageChanger(pic2, 700, a);
        Animation.Fade(pic1, 0, 1000, 1, 0);
        pic2.setVisible(true);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> {
            pic1.setVisible(false);
        }));
        timeline.play();
        Animation.Fade(pic2, 0, 1000, 0, 1);
    }

    public static boolean searchEmptyField(ArrayList<TextField> textFields) {
        boolean isEmpty = false;
        for (int i = 0; i < textFields.size(); i++) {
            isEmpty = textFields.get(i).getText().equals("");
            if (isEmpty){
                break;
            }
        }
        return !isEmpty;
    }

}
