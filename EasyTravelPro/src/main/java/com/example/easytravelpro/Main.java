package com.example.easytravelpro;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    static String cssStyleDark;
    static String cssStyleLight;

    static String loginStyleDark;
    static String loginStyleLight;

    static String hotelierStyleDark;
    static String hotelierStyleLight;

    static boolean isDark = false;
    @Override
    public void start(Stage stage) throws IOException {


        cssStyleDark = Objects.requireNonNull(this.getClass().getResource(
                "DarkMode.css")).toExternalForm();
        cssStyleLight = Objects.requireNonNull(this.getClass().getResource(
                "LightMode.css")).toExternalForm();

        loginStyleDark = Objects.requireNonNull(this.getClass().getResource(
                "DarkModeLoginPage.css")).toExternalForm();
        loginStyleLight = Objects.requireNonNull(this.getClass().getResource(
                "LightModeLoginPage.css")).toExternalForm();

        hotelierStyleDark = Objects.requireNonNull(this.getClass().getResource(
                "DarkModeHotelier.css")).toExternalForm();
        hotelierStyleLight = Objects.requireNonNull(this.getClass().getResource(
                "LightModeHotelier.css")).toExternalForm();


        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ScreenLoader.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Image image = new Image("hostel.png");
        stage.getIcons().add(image);
        if (Main.isDark) scene.getStylesheets().add(Main.cssStyleDark);
        else scene.getStylesheets().add(Main.cssStyleLight);
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}