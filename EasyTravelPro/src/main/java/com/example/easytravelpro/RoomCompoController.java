package com.example.easytravelpro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.example.easytravelpro.HotelierController.k;
import static com.example.easytravelpro.HotelierController.rooms;
import static com.example.easytravelpro.SignInUp.hoteliers;
import static com.example.easytravelpro.SignInUp.j;

public class RoomCompoController implements Initializable {
//    static ArrayList<String> x = new ArrayList<>();
    @FXML
    private Label location, hotelName, capacity;
    @FXML
    private Button rentBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        location.setText(rooms.get(k).getLocation());
        hotelName.setText(rooms.get(k).getHotelName());
        rentBtn.setText("$" + rooms.get(k).getPrice());
        capacity.setText("for " + rooms.get(k).getCapacity() + " N");
    }

    public void rent(ActionEvent event) {

    }

    public void call(ActionEvent event) {
    }

    public void info(ActionEvent event) {
    }

    public void save(ActionEvent event) {

    }

    public void comment(ActionEvent event) {

    }


}
