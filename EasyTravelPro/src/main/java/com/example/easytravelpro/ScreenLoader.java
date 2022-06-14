package com.example.easytravelpro;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ScreenLoader implements Initializable {

    @FXML
    private ProgressBar bar;
    @FXML
    private Button btn;

    double pro = 0;

    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), e -> {
        if (pro < 1) {
            pro += 0.01;
            bar.setProgress(pro);
        } else {
            btn.setVisible(true);
        }
    }));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bar.setStyle("-fx-accent: #424242");
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void enter(ActionEvent event) throws IOException {
        timeline.stop();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Frame.fxml")));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

}
