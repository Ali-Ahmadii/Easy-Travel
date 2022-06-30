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

    static boolean isDark = false;
    @Override
    public void start(Stage stage) throws IOException {

        cssStyleDark = Objects.requireNonNull(this.getClass().getResource("DarkMode.css")).toExternalForm();
        cssStyleLight = Objects.requireNonNull(this.getClass().getResource("LightMode.css")).toExternalForm();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Frame.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Image image = new Image("hostel.png");
        stage.getIcons().add(image);
        scene.getStylesheets().add(Main.cssStyleLight);
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}