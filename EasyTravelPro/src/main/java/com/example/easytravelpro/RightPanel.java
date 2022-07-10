package com.example.easytravelpro;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RightPanel implements Initializable {
    static boolean existingroom = false;
    @FXML
    private Label fullName, email;
    @FXML
    private ImageView profile;
    static String Name;
    static ArrayList<Room> allRoomSearch = new ArrayList<>();
    static ArrayList<AnchorPane> allRoomSearchCompos = new ArrayList<>();
    static int o = 0;
    static String Email;
    static final String DB_URL = "jdbc:mysql://localhost:3306/easy_travel";
    static final String USER = "root";
    static final String PASS = "integral4560sini";
    static Timeline tline;


    @FXML
    private ListView<String> current;
    static String[] ActiveRoom = {""};
    static boolean isBuyAnyRoom = true;
    @FXML
    private Button cancel;
    static String nightsinprice;
    public void cancel(ActionEvent event) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("it will cost half of your budget");
        if (alert.showAndWait().get() == ButtonType.OK) {
            if(SignInUp.enteredToApp) {
                Connection lastcon = DriverManager.getConnection(DB_URL,USER,PASS);
                PreparedStatement selectcurrent = lastcon.prepareStatement("SELECT Balance FROM passengers WHERE UserName = "+"'"+RightPanel.Name+"'");
                ResultSet getcurrentbalance = selectcurrent.executeQuery();
                getcurrentbalance.next();
                String currentbalance = getcurrentbalance.getString(1);
                Double newbalance = ((Double.parseDouble(nightsinprice.trim()))/2)+Double.parseDouble(currentbalance);
                PreparedStatement updatebalance = lastcon.prepareStatement("UPDATE passengers SET Balance = "+"'"+String.valueOf(newbalance)+"'"+" WHERE UserName = "+"'"+RightPanel.Name+"'");
                updatebalance.executeUpdate();
                PreparedStatement deletstate = lastcon.prepareStatement("DELETE FROM buyhistory WHERE Buyer = '"+RightPanel.Name+"'");
                deletstate.executeUpdate();
                lastcon.close();
                Method.notification(Alert.AlertType.INFORMATION, "Succesful!", "Succesful Operation", "Succesful Operation");
                current.getItems().clear();
                current.setStyle("-fx-border-color: red;" +
                        "-fx-border-width: 2;");
                cancel.setVisible(false);
                isBuyAnyRoom = false;
                existingroom = false;

            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (SignInUp.enteredToApp) {
            fullName.setText(Name);
            email.setText(Email);
            try {
                Connection com = DriverManager.getConnection(DB_URL,USER,PASS);
                PreparedStatement hotelauthorstate = com.prepareStatement("SELECT RoomAuthor FROM buyhistory WHERE Buyer = "+"'"+Name+"'");
                ResultSet situation = hotelauthorstate.executeQuery();
                if(situation.next()){
                    String author = situation.getString(1);
                    existingroom = true;
                    PreparedStatement roomprice = com.prepareStatement("SELECT Price FROM buyhistory WHERE Buyer = "+"'"+Name+"'");
                    ResultSet getprice = roomprice.executeQuery();
                    getprice.next();
                    String price = getprice.getString(1);
                    PreparedStatement nightsstate = com.prepareStatement("SELECT Nights FROM buyhistory WHERE Buyer = "+"'"+Name+"'");
                    ResultSet getnights = nightsstate.executeQuery();
                    getnights.next();
                    String nights = getnights.getString(1);
                    ActiveRoom = new String[]{"Hotel name: "+author,
                            "Number of night: "+nights,
                            "price for "+nights+" night : "+Double.parseDouble(price)*Integer.parseInt(nights)};
                    nightsinprice = String.valueOf(Double.parseDouble(price)*Integer.parseInt(nights));
                }
                com.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        tline = new Timeline(new KeyFrame(Duration.seconds(4), e -> {
            if (existingroom) {
                cancel.setVisible(true);
                current.getItems().clear();
                current.getItems().addAll(ActiveRoom);
                current.setStyle("-fx-border-color: green;" +
                        "-fx-border-width: 2;");
                System.out.println(ActiveRoom[0]);
            }
            if(SignInUp.enteredToApp) {
                try {
                    Connection imagecon = DriverManager.getConnection(DB_URL, USER, PASS);
                    PreparedStatement imagestate = imagecon.prepareStatement("SELECT PhotoURL FROM passengers WHERE UserName = " + "'" + RightPanel.Name + "'");
                    ResultSet geturl = imagestate.executeQuery();
                    geturl.next();
                    String urlimg = geturl.getString(1);
                    if (urlimg != null) {
                        profile.setImage(new Image(urlimg));
                    }
                    imagecon.close();
                }
                catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

        }));
        tline.setCycleCount(Animation.INDEFINITE);
        tline.play();

    }
}
