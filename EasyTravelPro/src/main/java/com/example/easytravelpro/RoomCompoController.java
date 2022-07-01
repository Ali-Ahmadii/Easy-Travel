package com.example.easytravelpro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

import static com.example.easytravelpro.HotelierController.k;
import static com.example.easytravelpro.HotelierController.rooms;

public class RoomCompoController implements Initializable {

    @FXML
    private Label location, hotelName, capacity;
    @FXML
    private Button rentBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        location.setText(rooms.get(k).getLocation());
        hotelName.setText(rooms.get(k).getHotelName());
        rentBtn.setText("$" + rooms.get(k).getPrice());
        capacity.setText("for " + rooms.get(k).getCapacity() + " N");
    }

    public void rent(ActionEvent event) {

    }

    public void call(ActionEvent event) {
//        Method.notification(Alert.AlertType.INFORMATION,
//                "Contact us",
//                "Hotelier info",
//                hoteliers.get(j - 1).getFullName() + "\n" +
//                        hoteliers.get(j - 1).getPhoneNumber() + "\n" +
//                        hoteliers.get(j - 1).getEmail());
    }

    public void info(ActionEvent event) {
//        Method.notification(Alert.AlertType.INFORMATION,
//                "Hotel Facility",
//                rooms.get(k - 1).getHotelName(),
//                rooms.get(k - 1).getFacility());
    }

    public void save(ActionEvent event) {

    }

    public void comment(ActionEvent event) {

    }


}
