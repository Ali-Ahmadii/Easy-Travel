package com.example.easytravelpro;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

import static com.example.easytravelpro.CategoryPageController.categoryRooms;
import static com.example.easytravelpro.CategoryPageController.n;

public class AllCategoryRoomController implements Initializable {
    @FXML
    private Label location, hotelName, capacity;
    @FXML
    private Button rentBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        location.setText(categoryRooms.get(n).getLocation());
        hotelName.setText(categoryRooms.get(n).getHotelName());
        rentBtn.setText("$" + categoryRooms.get(n).getPrice());
        capacity.setText("for " + categoryRooms.get(n).getCapacity() + " N");
    }
}
