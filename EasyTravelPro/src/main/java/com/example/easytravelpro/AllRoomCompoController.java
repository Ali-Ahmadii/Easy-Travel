package com.example.easytravelpro;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

import static com.example.easytravelpro.ExplorePageController.allRooms;
import static com.example.easytravelpro.ExplorePageController.m;

public class AllRoomCompoController implements Initializable {
    @FXML
    private Label location, hotelName, capacity;
    @FXML
    private Button rentBtn;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        location.setText(allRooms.get(m).getLocation());
        hotelName.setText(allRooms.get(m).getHotelName());
        rentBtn.setText("$" + allRooms.get(m).getPrice());
        capacity.setText("for " + allRooms.get(m).getCapacity() + " N");
    }
}
