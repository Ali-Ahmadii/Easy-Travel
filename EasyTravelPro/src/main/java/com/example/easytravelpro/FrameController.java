package com.example.easytravelpro;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class FrameController implements Initializable {

    @FXML
    private AnchorPane leftPanel, topPanel, rightPanel, mainPanel;
    @FXML
    private Button startBtn;



    public void goToLoginPage(ActionEvent event) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.4), e -> {
            startBtn.setVisible(false);
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SignInUp.fxml")));
                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);

                if (Main.isDark) scene.getStylesheets().add(Main.loginStyleDark);
                else scene.getStylesheets().add(Main.loginStyleLight);

                scene.setFill(Color.TRANSPARENT);
                stage.setScene(scene);
                Animation.Fade(root,400,800,0,1);
                stage.show();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }));
        timeline.play();
        Animation.Fade(startBtn, 0, 600, 1, 0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){


        if (!SignInUp.enteredToApp) {
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4), e -> {
                startBtn.setVisible(true);
            }));
            timeline.play();
            Animation.Fade(startBtn, 4000, 1000, 0, 1);
        }
        try {
            rightPanel.getChildren().add(
                    FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("RightPanel.fxml"))));
            topPanel.getChildren().add(
                    FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("TopPanel.fxml"))));
            mainPanel.getChildren().add(
                    FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("MainPanel.fxml"))));
            leftPanel.getChildren().add(
                    FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("leftPanel.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
