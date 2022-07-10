package com.example.easytravelpro;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;

import static com.example.easytravelpro.SignInUp.enteredToApp;

public class SavedPlacePageController implements Initializable {
    @FXML
    private HBox hBox;
    @FXML
    private VBox vBox1, vBox2, vBox3;

    static ArrayList<Room> savedRooms = new ArrayList<>();
    static ArrayList<AnchorPane> savedRoomsCompos = new ArrayList<>();

    static final String DB_URL = "jdbc:mysql://localhost:3306/easy_travel";
    static final String USER = "root";
    static final String PASS = "integral4560sini";

    static int w = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(enteredToApp) {
                savedRooms.clear();
                savedRoomsCompos.clear();
                w = 0;
                vBox1.getChildren().removeAll();
                vBox2.getChildren().removeAll();
                vBox3.getChildren().removeAll();
                try {
                    Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
                    PreparedStatement savesstate = con.prepareStatement("SELECT RoomID FROM savedplaces WHERE Saver = " + "'" + RightPanel.Name + "'");
                    ResultSet setrooms = savesstate.executeQuery();
                    while (setrooms.next()) {
                        String roomid = setrooms.getString(1);
                        System.out.println(roomid);
                        PreparedStatement astate = con.prepareStatement("SELECT AuthorHotel FROM rooms WHERE RoomID = " + "'" + roomid + "'");
                        ResultSet getas = astate.executeQuery();
                        getas.next();
                        String hotelauth = getas.getString(1);
                        PreparedStatement capstate = con.prepareStatement("SELECT Capacity FROM rooms WHERE RoomID = " + "'" + roomid + "'");
                        ResultSet getcp = capstate.executeQuery();
                        getcp.next();
                        String roomcp = getcp.getString(1);
                        PreparedStatement facilityst = con.prepareStatement("SELECT Facilities FROM rooms WHERE RoomID = " + "'" + roomid + "'");
                        ResultSet gtfac = facilityst.executeQuery();
                        gtfac.next();
                        String fc = gtfac.getString(1);
                        PreparedStatement pricest = con.prepareStatement("SELECT Price FROM rooms WHERE RoomID = " + "'" + roomid + "'");
                        ResultSet getpricee = pricest.executeQuery();
                        getpricee.next();
                        String pricee = getpricee.getString(1);
                        savedRooms.add(new Room(hotelauth, "", Integer.parseInt(roomcp), fc, Double.parseDouble(pricee)));
                        try {
                            savedRoomsCompos.add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("SavedRoomsCompos.fxml"))));
                            ImageView iv = (ImageView)savedRoomsCompos.get(w).getChildren().get(0);
                            PreparedStatement urlstate = con.prepareStatement("SELECT PhotoAdress FROM rooms WHERE RoomID = "+"'"+roomid+"'");
                            ResultSet seturl = urlstate.executeQuery();
                            seturl.next();
                            String urlimg = seturl.getString(1);
                            if(!(urlimg.equals("null"))) {
                                iv.setImage(new Image(urlimg));
                            }
                            w++;
                        } catch (IOException ecx) {
                            ecx.printStackTrace();
                        }
                    }
                    con.close();
                } catch (SQLException eas) {
                    eas.printStackTrace();
                }

            try {
                Connection com = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement hwstate = com.prepareStatement("SELECT RoomID FROM savedplaces WHERE Saver = " + "'" + RightPanel.Name + "'");
                ResultSet ids = hwstate.executeQuery();
                int i = 0;
                while (ids.next()) {
                    String id = ids.getString(1);
                    PreparedStatement authorstate = com.prepareStatement("SELECT AuthorHotel FROM rooms WHERE RoomID = " + "'" + id + "'");
                    ResultSet getauthor = authorstate.executeQuery();
                    getauthor.next();
                    String hotelauthor = getauthor.getString(1);
                    PreparedStatement capacitystate = com.prepareStatement("SELECT Capacity FROM rooms WHERE RoomID = " + "'" + id + "'");
                    ResultSet getcap = capacitystate.executeQuery();
                    getcap.next();
                    String roomcap = getcap.getString(1);
                    PreparedStatement facilitystate = com.prepareStatement("SELECT Facilities FROM rooms WHERE RoomID = " + "'" + id + "'");
                    ResultSet getfacility = facilitystate.executeQuery();
                    getfacility.next();
                    String facility = getfacility.getString(1);
                    PreparedStatement pricestate = com.prepareStatement("SELECT Price FROM rooms WHERE RoomID = " + "'" + id + "'");
                    ResultSet getprice = pricestate.executeQuery();
                    getprice.next();
                    String price = getprice.getString(1);
                    if (i % 3 == 0) {
                        vBox1.getChildren().add(savedRoomsCompos.get(i));
                        HBox hbx = (HBox) savedRoomsCompos.get(i).getChildren().get(5);
                        Button btn = (Button) hbx.getChildren().get(0);
                        btn.setOnAction(event1 -> {
                            Method.notification(Alert.AlertType.INFORMATION, "Room's Author", "Author", "Author Of Room : " + hotelauthor);
                        });
                        Button btn1 = (Button) hbx.getChildren().get(1);
                        btn1.setOnAction(event1 -> {
                            Method.notification(Alert.AlertType.INFORMATION, "About Room", "Room", "Room's Capacity : " + roomcap + "Room's Price : " + price + "Room's facility : " + facility);
                        });
                        Button btn2 = (Button) hbx.getChildren().get(2);
                        btn2.setOnAction(event1 -> {
                            try {
                                if (SignInUp.enteredToApp) {
                                    PreparedStatement determinatiinstate = com.prepareStatement("SELECT Saver FROM savedplaces WHERE RoomID = " + "'" + id + "'");
                                    ResultSet getsave = determinatiinstate.executeQuery();
                                    if (!(getsave.next())) {
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setTitle("Save");
                                        alert.setHeaderText("Do You Want To Save This Place?");
                                        if (alert.showAndWait().get() == ButtonType.OK) {
                                            PreparedStatement setstate = com.prepareStatement("INSERT INTO savedplaces (Saver,RoomID) VALUES " + "('" + RightPanel.Name + "','" + id + "')");
                                            setstate.execute();
                                        }
                                    } else {
                                        Method.notification(Alert.AlertType.INFORMATION, "Error!",
                                                "Problem In Saving", "You Have Saved This Place Before");
                                    }
                                } else {
                                    Method.notification(Alert.AlertType.ERROR, "Error!",
                                            "Not Signed In", "First Please Log In");
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        });
                        Button btn3 = (Button) hbx.getChildren().get(3);
                        btn3.setOnAction(event1 -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Comments");
                            alert.setHeaderText("Comments");
                            try {
                                PreparedStatement getcomments = com.prepareStatement("SELECT Comments FROM rooms WHERE RoomID = " + "'" + id + "'");
                                ResultSet getcomment = getcomments.executeQuery();
                                getcomment.next();
                                String comments = getcomment.getString(1);
                                String all[] = comments.split(",");
                                String showing = "";
                                for (int j = 0; j < all.length; j++) {
                                    showing += all[j] + "\n";
                                }
                                alert.setContentText(showing);
                                ButtonType buttonTypeOne = new ButtonType("Put a Comment");
                                ButtonType buttonTypeCancel = new ButtonType("Back", ButtonBar.ButtonData.CANCEL_CLOSE);
                                alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == buttonTypeOne) {
                                    TextInputDialog dialog = new TextInputDialog("Puting Comment");
                                    dialog.setTitle("Comment");
                                    dialog.setHeaderText("PUTING COMMENT");
                                    dialog.setContentText("Please enter your comment :");
                                    Optional<String> resultx = dialog.showAndWait();
                                    if (resultx.isPresent()) {
                                        PreparedStatement commentstate = com.prepareStatement("SELECT Comments FROM rooms WHERE RoomID = " + "'" + id + "'");
                                        ResultSet getcom = commentstate.executeQuery();
                                        getcom.next();
                                        String ex = getcom.getString(1);
                                        if (!(ex.equals("null"))) {
                                            String newcomment = ex + " , " + dialog.getResult();
                                            PreparedStatement updatecomment = com.prepareStatement("UPDATE rooms SET Comments = " + "'" + newcomment + "'" + " WHERE RoomID = " + "'" + id + "'");
                                            updatecomment.executeUpdate();
                                        } else {
                                            PreparedStatement updatecomment = com.prepareStatement("UPDATE rooms SET Comments = " + "'" + dialog.getResult() + "'" + " WHERE RoomID = " + "'" + id + "'");
                                            updatecomment.executeUpdate();
                                        }
                                    }
                                } else {

                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        });
                        Button buybtn = (Button) savedRoomsCompos.get(i).getChildren().get(4);
                        buybtn.setOnAction(event3 -> {
                            Boolean z = RightPanel.existingroom;
                            if (!(z)) {
                                try {
                                    PreparedStatement balancestate = com.prepareStatement("SELECT Balance FROM passengers WHERE UserName = " + "'" + RightPanel.Name + "'");
                                    ResultSet getbalance = balancestate.executeQuery();
                                    getbalance.next();
                                    String bal = getbalance.getString(1);
                                    if (Double.parseDouble(bal) >= Double.parseDouble(price)) {
                                        List<String> choices = new ArrayList<>();
                                        choices.add("1");
                                        choices.add("2");
                                        choices.add("3");
                                        choices.add("4");
                                        choices.add("5");
                                        choices.add("6");
                                        choices.add("7");
                                        choices.add("8");
                                        choices.add("9");
                                        choices.add("10");
                                        ChoiceDialog<String> dialog = new ChoiceDialog<>("1", choices);
                                        dialog.setTitle("How Many Days");
                                        dialog.setHeaderText("How Many Days");
                                        dialog.setContentText("Choose :");
                                        Optional<String> result = dialog.showAndWait();
                                        if (result.isPresent()) {
                                            int choice = Integer.parseInt(dialog.getResult());
                                            double overallprice = choice * Double.parseDouble(price);
                                            if (Double.parseDouble(bal) >= overallprice) {
                                                Double newbalance = Double.parseDouble(bal) - overallprice;
                                                PreparedStatement setnewbalance = com.prepareStatement("UPDATE passengers SET Balance = " + "'" + String.valueOf(newbalance) + "'" + "WHERE UserName = " + "'" + RightPanel.Name + "'");
                                                setnewbalance.executeUpdate();
                                                //change nights
                                                PreparedStatement buyerstate = com.prepareStatement("INSERT INTO buyhistory (Buyer,RoomAuthor,RoomID,Price,Nights) VALUES" + "('" + RightPanel.Name + "','" + hotelauthor + "','" + id + "','" + price + "','" + String.valueOf(choice) + "')");
                                                buyerstate.execute();
                                                PreparedStatement hotelbalancestate = com.prepareStatement("SELECT Balance FROM hotel WHERE UserName = " + "'" + hotelauthor + "'");
                                                ResultSet hotelbalanceresult = hotelbalancestate.executeQuery();
                                                hotelbalanceresult.next();
                                                String hotelexbalance = hotelbalanceresult.getString(1);
                                                Double next = Double.parseDouble(hotelexbalance) + overallprice;
                                                PreparedStatement updatehotelbalance = com.prepareStatement("UPDATE hotel SET Balance = " + "'" + String.valueOf(next) + "'" + "WHERE UserName = " + "'" + hotelauthor + "'");
                                                updatehotelbalance.executeUpdate();
                                                try {
                                                    Connection comm = DriverManager.getConnection(DB_URL,USER,PASS);
                                                    PreparedStatement hotelauthorstate = comm.prepareStatement("SELECT RoomAuthor FROM buyhistory WHERE Buyer = "+"'"+RightPanel.Name+"'");
                                                    ResultSet situation = hotelauthorstate.executeQuery();
                                                    if(situation.next()){
                                                        String author = situation.getString(1);
                                                        RightPanel.existingroom = true;
                                                        PreparedStatement roomprice = comm.prepareStatement("SELECT Price FROM buyhistory WHERE Buyer = "+"'"+RightPanel.Name+"'");
                                                        ResultSet getpricee = roomprice.executeQuery();
                                                        getpricee.next();
                                                        String pricee = getpricee.getString(1);
                                                        PreparedStatement nightsstate = comm.prepareStatement("SELECT Nights FROM buyhistory WHERE Buyer = "+"'"+RightPanel.Name+"'");
                                                        ResultSet getnights = nightsstate.executeQuery();
                                                        getnights.next();
                                                        String nights = getnights.getString(1);
                                                        RightPanel.ActiveRoom = new String[]{"Hotel name: "+author,
                                                                "Number of night: "+nights,
                                                                "price for "+nights+" night : "+Double.parseDouble(pricee)*Integer.parseInt(nights)};
                                                    }
                                                } catch (SQLException e) {
                                                    e.printStackTrace();
                                                }
                                            } else {
                                                Method.notification(Alert.AlertType.ERROR, "Failure", "Failure", "insufficient availability !");
                                            }
                                        }
                                    } else {
                                        Method.notification(Alert.AlertType.ERROR, "Failure", "Failure", "insufficient availability !");
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Method.notification(Alert.AlertType.ERROR, "Failure", "Failure", "You Have Rent a Room Already!");
                            }
                        });
                    } else if (i % 3 == 1) {
                        vBox2.getChildren().add(savedRoomsCompos.get(i));
                        HBox hbx = (HBox) savedRoomsCompos.get(i).getChildren().get(5);
                        Button btn = (Button) hbx.getChildren().get(0);
                        btn.setOnAction(event1 -> {
                            Method.notification(Alert.AlertType.INFORMATION, "Room's Author", "Author", "Author Of Room : " + hotelauthor);
                        });
                        Button btn1 = (Button) hbx.getChildren().get(1);
                        btn1.setOnAction(event1 -> {
                            Method.notification(Alert.AlertType.INFORMATION, "About Room", "Room", "Room's Capacity : " + roomcap + "Room's Price : " + price + "Room's facility : " + facility);
                        });
                        Button btn2 = (Button) hbx.getChildren().get(2);
                        btn2.setOnAction(event1 -> {
                            try {
                                if (SignInUp.enteredToApp) {
                                    PreparedStatement determinatiinstate = com.prepareStatement("SELECT Saver FROM savedplaces WHERE RoomID = " + "'" + id + "'");
                                    ResultSet getsave = determinatiinstate.executeQuery();
                                    if (!(getsave.next())) {
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setTitle("Save");
                                        alert.setHeaderText("Do You Want To Save This Place?");
                                        if (alert.showAndWait().get() == ButtonType.OK) {
                                            PreparedStatement setstate = com.prepareStatement("INSERT INTO savedplaces (Saver,RoomID) VALUES " + "('" + RightPanel.Name + "','" + id + "')");
                                            setstate.execute();
                                        }
                                    } else {
                                        Method.notification(Alert.AlertType.INFORMATION, "Error!",
                                                "Problem In Saving", "You Have Saved This Place Before");
                                    }
                                } else {
                                    Method.notification(Alert.AlertType.ERROR, "Error!",
                                            "Not Signed In", "First Please Log In");
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        });
                        Button btn3 = (Button) hbx.getChildren().get(3);
                        btn3.setOnAction(event1 -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Comments");
                            alert.setHeaderText("Comments");
                            try {
                                PreparedStatement getcomments = com.prepareStatement("SELECT Comments FROM rooms WHERE RoomID = " + "'" + id + "'");
                                ResultSet getcomment = getcomments.executeQuery();
                                getcomment.next();
                                String comments = getcomment.getString(1);
                                String all[] = comments.split(",");
                                String showing = "";
                                for (int j = 0; j < all.length; j++) {
                                    showing += all[j] + "\n";
                                }
                                alert.setContentText(showing);
                                ButtonType buttonTypeOne = new ButtonType("Put a Comment");
                                ButtonType buttonTypeCancel = new ButtonType("Back", ButtonBar.ButtonData.CANCEL_CLOSE);
                                alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == buttonTypeOne) {
                                    TextInputDialog dialog = new TextInputDialog("Puting Comment");
                                    dialog.setTitle("Comment");
                                    dialog.setHeaderText("PUTING COMMENT");
                                    dialog.setContentText("Please enter your comment :");
                                    Optional<String> resultx = dialog.showAndWait();
                                    if (resultx.isPresent()) {
                                        PreparedStatement commentstate = com.prepareStatement("SELECT Comments FROM rooms WHERE RoomID = " + "'" + id + "'");
                                        ResultSet getcom = commentstate.executeQuery();
                                        getcom.next();
                                        String ex = getcom.getString(1);
                                        if (!(ex.equals("null"))) {
                                            String newcomment = ex + " , " + dialog.getResult();
                                            PreparedStatement updatecomment = com.prepareStatement("UPDATE rooms SET Comments = " + "'" + newcomment + "'" + " WHERE RoomID = " + "'" + id + "'");
                                            updatecomment.executeUpdate();
                                        } else {
                                            PreparedStatement updatecomment = com.prepareStatement("UPDATE rooms SET Comments = " + "'" + dialog.getResult() + "'" + " WHERE RoomID = " + "'" + id + "'");
                                            updatecomment.executeUpdate();
                                        }
                                    }
                                } else {

                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        });
                        Button buybtn = (Button) savedRoomsCompos.get(i).getChildren().get(4);
                        buybtn.setOnAction(event3 -> {
                            Boolean z = RightPanel.existingroom;
                            if (!(z)) {
                                try {
                                    PreparedStatement balancestate = com.prepareStatement("SELECT Balance FROM passengers WHERE UserName = " + "'" + RightPanel.Name + "'");
                                    ResultSet getbalance = balancestate.executeQuery();
                                    getbalance.next();
                                    String bal = getbalance.getString(1);
                                    if (Double.parseDouble(bal) >= Double.parseDouble(price)) {
                                        List<String> choices = new ArrayList<>();
                                        choices.add("1");
                                        choices.add("2");
                                        choices.add("3");
                                        choices.add("4");
                                        choices.add("5");
                                        choices.add("6");
                                        choices.add("7");
                                        choices.add("8");
                                        choices.add("9");
                                        choices.add("10");
                                        ChoiceDialog<String> dialog = new ChoiceDialog<>("1", choices);
                                        dialog.setTitle("How Many Days");
                                        dialog.setHeaderText("How Many Days");
                                        dialog.setContentText("Choose :");
                                        Optional<String> result = dialog.showAndWait();
                                        if (result.isPresent()) {
                                            int choice = Integer.parseInt(dialog.getResult());
                                            double overallprice = choice * Double.parseDouble(price);
                                            if (Double.parseDouble(bal) >= overallprice) {
                                                Double newbalance = Double.parseDouble(bal) - overallprice;
                                                PreparedStatement setnewbalance = com.prepareStatement("UPDATE passengers SET Balance = " + "'" + String.valueOf(newbalance) + "'" + "WHERE UserName = " + "'" + RightPanel.Name + "'");
                                                setnewbalance.executeUpdate();
                                                //change nights
                                                PreparedStatement buyerstate = com.prepareStatement("INSERT INTO buyhistory (Buyer,RoomAuthor,RoomID,Price,Nights) VALUES" + "('" + RightPanel.Name + "','" + hotelauthor + "','" + id + "','" + price + "','" + String.valueOf(choice) + "')");
                                                buyerstate.execute();
                                                PreparedStatement hotelbalancestate = com.prepareStatement("SELECT Balance FROM hotel WHERE UserName = " + "'" + hotelauthor + "'");
                                                ResultSet hotelbalanceresult = hotelbalancestate.executeQuery();
                                                hotelbalanceresult.next();
                                                String hotelexbalance = hotelbalanceresult.getString(1);
                                                Double next = Double.parseDouble(hotelexbalance) + overallprice;
                                                PreparedStatement updatehotelbalance = com.prepareStatement("UPDATE hotel SET Balance = " + "'" + String.valueOf(next) + "'" + "WHERE UserName = " + "'" + hotelauthor + "'");
                                                updatehotelbalance.executeUpdate();
                                                try {
                                                    Connection comm = DriverManager.getConnection(DB_URL,USER,PASS);
                                                    PreparedStatement hotelauthorstate = comm.prepareStatement("SELECT RoomAuthor FROM buyhistory WHERE Buyer = "+"'"+RightPanel.Name+"'");
                                                    ResultSet situation = hotelauthorstate.executeQuery();
                                                    if(situation.next()){
                                                        String author = situation.getString(1);
                                                        RightPanel.existingroom = true;
                                                        PreparedStatement roomprice = comm.prepareStatement("SELECT Price FROM buyhistory WHERE Buyer = "+"'"+RightPanel.Name+"'");
                                                        ResultSet getpricee = roomprice.executeQuery();
                                                        getpricee.next();
                                                        String pricee = getpricee.getString(1);
                                                        PreparedStatement nightsstate = comm.prepareStatement("SELECT Nights FROM buyhistory WHERE Buyer = "+"'"+RightPanel.Name+"'");
                                                        ResultSet getnights = nightsstate.executeQuery();
                                                        getnights.next();
                                                        String nights = getnights.getString(1);
                                                        RightPanel.ActiveRoom = new String[]{"Hotel name: "+author,
                                                                "Number of night: "+nights,
                                                                "price for "+nights+" night : "+Double.parseDouble(pricee)*Integer.parseInt(nights)};
                                                    }
                                                } catch (SQLException e) {
                                                    e.printStackTrace();
                                                }
                                            } else {
                                                Method.notification(Alert.AlertType.ERROR, "Failure", "Failure", "insufficient availability !");
                                            }
                                        }
                                    } else {
                                        Method.notification(Alert.AlertType.ERROR, "Failure", "Failure", "insufficient availability !");
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Method.notification(Alert.AlertType.ERROR, "Failure", "Failure", "NYou Have Rent a Room Already!");
                            }
                        });

                    } else if (i % 3 == 2) {
                        vBox3.getChildren().add(savedRoomsCompos.get(i));
                        HBox hbx = (HBox) savedRoomsCompos.get(i).getChildren().get(5);
                        Button btn = (Button) hbx.getChildren().get(0);
                        btn.setOnAction(event1 -> {
                            Method.notification(Alert.AlertType.INFORMATION, "Room's Author", "Author", "Author Of Room : " + hotelauthor);
                        });
                        Button btn1 = (Button) hbx.getChildren().get(1);
                        btn1.setOnAction(event1 -> {
                            Method.notification(Alert.AlertType.INFORMATION, "About Room", "Room", "Room's Capacity : " + roomcap + "Room's Price : " + price + "Room's facility : " + facility);
                        });
                        Button btn2 = (Button) hbx.getChildren().get(2);
                        btn2.setOnAction(event1 -> {
                            try {
                                if (SignInUp.enteredToApp) {
                                    PreparedStatement determinatiinstate = com.prepareStatement("SELECT Saver FROM savedplaces WHERE RoomID = " + "'" + id + "'");
                                    ResultSet getsave = determinatiinstate.executeQuery();
                                    if (!(getsave.next())) {
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setTitle("Save");
                                        alert.setHeaderText("Do You Want To Save This Place?");
                                        if (alert.showAndWait().get() == ButtonType.OK) {
                                            PreparedStatement setstate = com.prepareStatement("INSERT INTO savedplaces (Saver,RoomID) VALUES " + "('" + RightPanel.Name + "','" + id + "')");
                                            setstate.execute();
                                        }
                                    } else {
                                        Method.notification(Alert.AlertType.INFORMATION, "Error!",
                                                "Problem In Saving", "You Have Saved This Place Before");
                                    }
                                } else {
                                    Method.notification(Alert.AlertType.ERROR, "Error!",
                                            "Not Signed In", "First Please Log In");
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        });
                        Button btn3 = (Button) hbx.getChildren().get(3);
                        btn3.setOnAction(event1 -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Comments");
                            alert.setHeaderText("Comments");
                            try {
                                PreparedStatement getcomments = com.prepareStatement("SELECT Comments FROM rooms WHERE RoomID = " + "'" + id + "'");
                                ResultSet getcomment = getcomments.executeQuery();
                                getcomment.next();
                                String comments = getcomment.getString(1);
                                String all[] = comments.split(",");
                                String showing = "";
                                for (int j = 0; j < all.length; j++) {
                                    showing += all[j] + "\n";
                                }
                                alert.setContentText(showing);
                                ButtonType buttonTypeOne = new ButtonType("Put a Comment");
                                ButtonType buttonTypeCancel = new ButtonType("Back", ButtonBar.ButtonData.CANCEL_CLOSE);
                                alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == buttonTypeOne) {
                                    TextInputDialog dialog = new TextInputDialog("Puting Comment");
                                    dialog.setTitle("Comment");
                                    dialog.setHeaderText("PUTING COMMENT");
                                    dialog.setContentText("Please enter your comment :");
                                    Optional<String> resultx = dialog.showAndWait();
                                    if (resultx.isPresent()) {
                                        PreparedStatement commentstate = com.prepareStatement("SELECT Comments FROM rooms WHERE RoomID = " + "'" + id + "'");
                                        ResultSet getcom = commentstate.executeQuery();
                                        getcom.next();
                                        String ex = getcom.getString(1);
                                        if (!(ex.equals("null"))) {
                                            String newcomment = ex + " , " + dialog.getResult();
                                            PreparedStatement updatecomment = com.prepareStatement("UPDATE rooms SET Comments = " + "'" + newcomment + "'" + " WHERE RoomID = " + "'" + id + "'");
                                            updatecomment.executeUpdate();
                                        } else {
                                            PreparedStatement updatecomment = com.prepareStatement("UPDATE rooms SET Comments = " + "'" + dialog.getResult() + "'" + " WHERE RoomID = " + "'" + id + "'");
                                            updatecomment.executeUpdate();
                                        }
                                    }
                                } else {

                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        });
                        Button buybtn = (Button) savedRoomsCompos.get(i).getChildren().get(4);
                        buybtn.setOnAction(event3 -> {
                            Boolean z = RightPanel.existingroom;
                            if (!(z)) {
                                try {
                                    PreparedStatement balancestate = com.prepareStatement("SELECT Balance FROM passengers WHERE UserName = " + "'" + RightPanel.Name + "'");
                                    ResultSet getbalance = balancestate.executeQuery();
                                    getbalance.next();
                                    String bal = getbalance.getString(1);
                                    if (Double.parseDouble(bal) >= Double.parseDouble(price)) {
                                        List<String> choices = new ArrayList<>();
                                        choices.add("1");
                                        choices.add("2");
                                        choices.add("3");
                                        choices.add("4");
                                        choices.add("5");
                                        choices.add("6");
                                        choices.add("7");
                                        choices.add("8");
                                        choices.add("9");
                                        choices.add("10");
                                        ChoiceDialog<String> dialog = new ChoiceDialog<>("1", choices);
                                        dialog.setTitle("How Many Days");
                                        dialog.setHeaderText("How Many Days");
                                        dialog.setContentText("Choose :");
                                        Optional<String> result = dialog.showAndWait();
                                        if (result.isPresent()) {
                                            int choice = Integer.parseInt(dialog.getResult());
                                            double overallprice = choice * Double.parseDouble(price);
                                            if (Double.parseDouble(bal) >= overallprice) {
                                                Double newbalance = Double.parseDouble(bal) - overallprice;
                                                PreparedStatement setnewbalance = com.prepareStatement("UPDATE passengers SET Balance = " + "'" + String.valueOf(newbalance) + "'" + "WHERE UserName = " + "'" + RightPanel.Name + "'");
                                                setnewbalance.executeUpdate();
                                                //change nights
                                                PreparedStatement buyerstate = com.prepareStatement("INSERT INTO buyhistory (Buyer,RoomAuthor,RoomID,Price,Nights) VALUES" + "('" + RightPanel.Name + "','" + hotelauthor + "','" + id + "','" + price + "','" + String.valueOf(choice) + "')");
                                                buyerstate.execute();
                                                PreparedStatement hotelbalancestate = com.prepareStatement("SELECT Balance FROM hotel WHERE UserName = " + "'" + hotelauthor + "'");
                                                ResultSet hotelbalanceresult = hotelbalancestate.executeQuery();
                                                hotelbalanceresult.next();
                                                String hotelexbalance = hotelbalanceresult.getString(1);
                                                Double next = Double.parseDouble(hotelexbalance) + overallprice;
                                                PreparedStatement updatehotelbalance = com.prepareStatement("UPDATE hotel SET Balance = " + "'" + String.valueOf(next) + "'" + "WHERE UserName = " + "'" + hotelauthor + "'");
                                                updatehotelbalance.executeUpdate();
                                                try {
                                                    Connection comm = DriverManager.getConnection(DB_URL,USER,PASS);
                                                    PreparedStatement hotelauthorstate = comm.prepareStatement("SELECT RoomAuthor FROM buyhistory WHERE Buyer = "+"'"+RightPanel.Name+"'");
                                                    ResultSet situation = hotelauthorstate.executeQuery();
                                                    if(situation.next()){
                                                        String author = situation.getString(1);
                                                        RightPanel.existingroom = true;
                                                        PreparedStatement roomprice = comm.prepareStatement("SELECT Price FROM buyhistory WHERE Buyer = "+"'"+RightPanel.Name+"'");
                                                        ResultSet getpricee = roomprice.executeQuery();
                                                        getpricee.next();
                                                        String pricee = getpricee.getString(1);
                                                        PreparedStatement nightsstate = comm.prepareStatement("SELECT Nights FROM buyhistory WHERE Buyer = "+"'"+RightPanel.Name+"'");
                                                        ResultSet getnights = nightsstate.executeQuery();
                                                        getnights.next();
                                                        String nights = getnights.getString(1);
                                                        RightPanel.ActiveRoom = new String[]{"Hotel name: "+author,
                                                                "Number of night: "+nights,
                                                                "price for "+nights+" night : "+Double.parseDouble(pricee)*Integer.parseInt(nights)};
                                                    }
                                                } catch (SQLException e) {
                                                    e.printStackTrace();
                                                }
                                            } else {
                                                Method.notification(Alert.AlertType.ERROR, "Failure", "Failure", "insufficient availability !");
                                            }
                                        }
                                    } else {
                                        Method.notification(Alert.AlertType.ERROR, "Failure", "Failure", "insufficient availability !");
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Method.notification(Alert.AlertType.ERROR, "Failure", "Failure", "You Have Rent a Room Already!");
                            }
                        });
                    }
                    i++;
                }
//                com.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
