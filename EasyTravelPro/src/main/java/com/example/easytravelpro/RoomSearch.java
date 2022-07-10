package com.example.easytravelpro;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

import static com.example.easytravelpro.RightPanel.allRoomSearch;
import static com.example.easytravelpro.RightPanel.o;


public class RoomSearch implements Initializable {
    @FXML
    private Label location, hotelName, capacity;
    @FXML
    private Button rentBtn;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        location.setText(allRoomSearch.get(o).getLocation());
        hotelName.setText(allRoomSearch.get(o).getHotelName());
        rentBtn.setText("$" + allRoomSearch.get(o).getPrice());
        capacity.setText("for " + allRoomSearch.get(o).getCapacity() + " N");
    }
}
