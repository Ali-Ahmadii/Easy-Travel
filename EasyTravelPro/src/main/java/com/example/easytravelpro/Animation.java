package com.example.easytravelpro;

import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class Animation {

    public static void scroll(Label node, int duration, int y) {
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(node);
        translate.setDuration(Duration.millis(duration));
        translate.setCycleCount(1);
        translate.setToY(y-154);
        translate.play();
    }
}
