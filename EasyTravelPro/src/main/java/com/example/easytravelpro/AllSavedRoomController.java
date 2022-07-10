package com.example.easytravelpro;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

import static com.example.easytravelpro.SavedPlacePageController.savedRooms;
import static com.example.easytravelpro.SavedPlacePageController.w;

public class AllSavedRoomController implements Initializable {
    @FXML
    private Label location, hotelName, capacity;
    @FXML
    private Button rentBtn;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        location.setText(savedRooms.get(w).getLocation());
        hotelName.setText(savedRooms.get(w).getHotelName());
        rentBtn.setText("$" + savedRooms.get(w).getPrice());
        capacity.setText("for " + savedRooms.get(w).getCapacity() + " N");
    }
}
