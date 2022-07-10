package com.example.easytravelpro;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.security.spec.RSAOtherPrimeInfo;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.example.easytravelpro.RightPanel.*;

public class TopPanel implements Initializable {
    @FXML
    private HBox resultBox;
    @FXML
    private TextField searchfield;
    @FXML
    private Label suggestLabel;
    static final String DB_URL = "jdbc:mysql://localhost:3306/easy_travel";
    static final String USER = "root";
    static final String PASS = "integral4560sini";
    static String currentSearch;

    static int currentCityIndex = 0;
    static ArrayList<String> location = new ArrayList<String>();


    public void search() throws SQLException {
        resultBox.getChildren().removeAll();
        System.out.println(location);
        if (SignInUp.enteredToApp) {
            int input = location.size();
            for (int i = 0; i < location.size(); i++) {
                String a = location.get(i).toLowerCase();
                String b = searchfield.getText().toLowerCase();
                if (a.contains(b)) {
                    suggestLabel.setVisible(true);
                    System.out.println("yi");
                    suggestLabel.setText(location.get(i));
                    currentSearch = location.get(i);
                    input = i;
                }
            }
            if (searchfield.getText().length()>=3) {
                Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement hotelsstate = con.prepareStatement("SELECT Username FROM hotel WHERE Location =" + "'" + currentSearch + "'");
                ResultSet getusername = hotelsstate.executeQuery();
                while (getusername.next()) {
                    String username = getusername.getString(1);
                    PreparedStatement roomstate = con.prepareStatement("SELECT RoomID FROM rooms WHERE AuthorHotel = "+"'"+username+"'");
                    ResultSet getrooms = roomstate.executeQuery();
                    while(getrooms.next()) {
                        String id = getrooms.getString(1);
                        PreparedStatement capstate = con.prepareStatement("SELECT Capacity FROM rooms WHERE RoomID = "+"'"+id+"'");
                        ResultSet getcap  = capstate.executeQuery();
                        getcap.next();
                        String capacity = getcap.getString(1);
                        PreparedStatement facalitystate = con.prepareStatement("SELECT Facilities FROM rooms WHERE RoomID = "+"'"+id+"'");
                        ResultSet getfacality  = facalitystate.executeQuery();
                        getfacality.next();
                        String facality = getfacality.getString(1);
                        PreparedStatement pricestate = con.prepareStatement("SELECT Price FROM rooms WHERE RoomID = "+"'"+id+"'");
                        ResultSet getprice  = pricestate.executeQuery();
                        getprice.next();
                        String price = getprice.getString(1);
                        allRoomSearch.add(new Room(username, currentSearch, Integer.parseInt(capacity), facality, Double.parseDouble(price)));
                        try {
                            allRoomSearchCompos.add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("RoomSearchComponent.fxml"))));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        resultBox.getChildren().add(allRoomSearchCompos.get(o));
                        o++;
                    }
                }
                con.close();
            }
        } else
            Method.notification(Alert.AlertType.INFORMATION, "Account",
                    "You haven't logged in",
                    "to use our Application you must Sign up/in first");

        searchfield.clear();
        suggestLabel.setVisible(false);
//        location.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        suggestLabel.setVisible(false);
        boolean x = true;
        String constant = "";
        Connection con = null;
        try {
            con = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement getlocations = con.prepareStatement("SELECT Location FROM hotel");
            ResultSet getloc = getlocations.executeQuery();
            while (getloc.next()) {
                x = true;
                String newloc = getloc.getString(1);
                for (int j = 0; j < location.size(); j++) {
                    if (location.get(j).equals(newloc)) {
                        x = false;
                    } else {

                    }
                }
                if (x) {
                    location.add(newloc);
                }
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        searchfield.setOnKeyPressed(keyEvent -> {
            for (int i = 0; i < location.size(); i++) {
                System.out.println("ENNTERED TO FOR");
                String a = location.get(i).toLowerCase();
                String b = searchfield.getText().toLowerCase();
                if (a.contains(b)) {
                    suggestLabel.setVisible(true);
                    suggestLabel.setText(location.get(i));
                    currentSearch = location.get(i);
                }
            }
            if (keyEvent.getCode() == KeyCode.ENTER) {
                suggestLabel.setText("");
                suggestLabel.setVisible(false);
            }
            if (keyEvent.getCode() == KeyCode.BACK_SPACE) {
                suggestLabel.setText("");
            }
        });
    }
}
