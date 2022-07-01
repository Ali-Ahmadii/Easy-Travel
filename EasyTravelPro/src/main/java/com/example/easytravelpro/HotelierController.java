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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class HotelierController implements Initializable {

    @FXML
    private TextField capacity, facility, price;
    @FXML
    private HBox roomBox, RateBox;
    @FXML
    private Label hotelNameLabel;
    String hotelName = "";
    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<AnchorPane> roomCompos = new ArrayList<>();
    static int k = 0;

    public void choosePhotoForRoom(ActionEvent event) {

    }

    public void addRoom(ActionEvent event) throws IOException {
        rooms.add(new Room(hotelName
                , SignInUp.hoteliers.get(SignInUp.j - 1).getLocation()
                , Integer.parseInt(capacity.getText())
                , facility.getText(),
                Double.parseDouble(price.getText())));

        roomCompos.add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("roomComponent.fxml"))));
        roomBox.getChildren().add(roomCompos.get(k));

        k++;
    }

    public void backToLoginPage(ActionEvent event) throws IOException {
        SignInUp.enteredToHotelierUI = false;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SignInUp.fxml")));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        com.example.easytravelpro.Animation.Fade(root, 400, 800, 0, 1);

        if (Main.isDark) scene.getStylesheets().add(Main.loginStyleDark);
        else scene.getStylesheets().add(Main.loginStyleLight);

        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), e -> {
            if (SignInUp.enteredToHotelierUI) {
                if (hotelName.equals(""))
                    hotelNameLabel.setText("Loading . . .");
                else
                    hotelNameLabel.setText(hotelName + " is a Lovely Place");
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        hotelName = Method.inputNotification();
    }
}
