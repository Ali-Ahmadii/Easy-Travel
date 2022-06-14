package com.example.easytravelpro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class FrameController implements Initializable {

    @FXML
    AnchorPane leftPanel, topPanel,rightPanel,mainPanel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            leftPanel.getChildren().add(
                    FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("leftPanel.fxml"))));
            topPanel.getChildren().add(
                    FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("TopPanel.fxml"))));
            rightPanel.getChildren().add(
                    FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("RightPanel.fxml"))));
            mainPanel.getChildren().add(
                    FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("MainPanel.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
