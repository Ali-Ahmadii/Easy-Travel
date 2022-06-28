package com.example.easytravelpro;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainPanel implements Initializable {

    static Parent explorePage;
    static Parent categoryPage;
    static Parent savedPlacePage;
    static Parent accountPage;
    static Parent paymentPage;
    static Parent settingPage;

    @FXML
    private AnchorPane mainPanel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            explorePage = FXMLLoader.load(Objects.requireNonNull(
                    this.getClass().getResource("ExplorePage.fxml")));
            categoryPage = FXMLLoader.load(Objects.requireNonNull(
                    this.getClass().getResource("CategoryPage.fxml")));
            savedPlacePage = FXMLLoader.load(Objects.requireNonNull(
                    this.getClass().getResource("SavedPlacePage.fxml")));
            accountPage = FXMLLoader.load(Objects.requireNonNull(
                    this.getClass().getResource("AccountPage.fxml")));
            paymentPage = FXMLLoader.load(Objects.requireNonNull(
                    this.getClass().getResource("PaymentPage.fxml")));
            settingPage = FXMLLoader.load(Objects.requireNonNull(
                    this.getClass().getResource("SettingPage.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        mainPanel.getChildren().add(explorePage);
        mainPanel.getChildren().add(categoryPage);
        mainPanel.getChildren().add(savedPlacePage);
        mainPanel.getChildren().add(accountPage);
        mainPanel.getChildren().add(paymentPage);
        mainPanel.getChildren().add(settingPage);

        explorePage.setVisible(true);
        categoryPage.setVisible(false);
        savedPlacePage.setVisible(false);
        accountPage.setVisible(false);
        paymentPage.setVisible(false);
        settingPage.setVisible(false);
    }

}
