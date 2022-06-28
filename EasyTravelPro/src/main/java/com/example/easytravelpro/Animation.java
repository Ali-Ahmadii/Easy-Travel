package com.example.easytravelpro;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class Animation {

    public static void scroll(Label node, int duration, int y) {
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(node);
        translate.setDuration(Duration.millis(duration));
        translate.setCycleCount(1);
        translate.setToY(y);
        translate.play();
    }

    public static void mainPageChanger(Label node, int duration, int x) {
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(node);
        translate.setDuration(Duration.millis(duration));
        translate.setCycleCount(1);
        translate.setByX(x);
        translate.play();
    }

    public static void Fade(Node node, int delay, int duration, int from, int to) {
        FadeTransition fade = new FadeTransition();
        fade.setNode(node);
        fade.setDelay(Duration.millis(delay));
        fade.setDuration(Duration.millis(duration));
        fade.setCycleCount(1);
        fade.setFromValue(from);
        fade.setToValue(to);
        fade.play();
    }

    public static void changeLoginPage(Node node, int duration, int x) {
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(node);
        translate.setDuration(Duration.millis(duration));
        translate.setCycleCount(1);
        translate.setByX(x);
        translate.play();
    }

}
