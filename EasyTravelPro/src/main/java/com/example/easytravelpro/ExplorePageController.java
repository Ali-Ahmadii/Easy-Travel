package com.example.easytravelpro;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class ExplorePageController implements Initializable {
    @FXML
    private Label villaLabel, tentLabel;
    @FXML
    private HBox roomBox;
    static ArrayList<Room> allRooms = new ArrayList<>();
    static ArrayList<AnchorPane> allRoomsCompos = new ArrayList<>();
    static int m = 0;
    int flag = 0;
    static final String DB_URL = "jdbc:mysql://localhost:3306/easy_travel";
    static final String USER = "root";
    static final String PASS = "integral4560sini";
    static String realuser;
    public void activePics() {
        if (flag == 0) {
            goRight();
        } else {
            goLeft();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection con = DriverManager.getConnection(DB_URL,USER,PASS);
            Connection com = DriverManager.getConnection(DB_URL,USER,PASS);
            PreparedStatement selectallrooms = con.prepareStatement("SELECT RoomID FROM rooms");
            ResultSet getids = selectallrooms.executeQuery();
            while(getids.next()){
                String id = getids.getString(1);
                PreparedStatement authorstate = con.prepareStatement("SELECT AuthorHotel FROM rooms WHERE RoomID = "+"'"+id+"'");
                ResultSet getauthor = authorstate.executeQuery();
                getauthor.next();
                String hotelauthor = getauthor.getString(1);
                PreparedStatement locationstate = com.prepareStatement("SELECT Location FROM hotel WHERE UserName = "+"'"+hotelauthor+"'");
                ResultSet getlocation = locationstate.executeQuery();
                getlocation.next();
                PreparedStatement capacitystate = con.prepareStatement("SELECT Capacity FROM rooms WHERE RoomID = "+"'"+id+"'");
                ResultSet getcap = capacitystate.executeQuery();
                getcap.next();
                String roomcap = getcap.getString(1);
                PreparedStatement facilitystate = con.prepareStatement("SELECT Facilities FROM rooms WHERE RoomID = "+"'"+id+"'");
                ResultSet getfacility= facilitystate.executeQuery();
                getfacility.next();
                String facility = getfacility.getString(1);
                PreparedStatement pricestate = con.prepareStatement("SELECT Price FROM rooms WHERE RoomID = "+"'"+id+"'");
                ResultSet getprice = pricestate.executeQuery();
                getprice.next();
                String price = getprice.getString(1);
                allRooms.add(new Room(hotelauthor, "", Integer.parseInt(roomcap), facility, Double.parseDouble(price)));
                allRoomsCompos.add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("AllRoomComponent.fxml"))));
                ImageView iv = (ImageView)allRoomsCompos.get(m).getChildren().get(0);
                PreparedStatement urlstate = con.prepareStatement("SELECT PhotoAdress FROM rooms WHERE RoomID = "+"'"+id+"'");
                ResultSet seturl = urlstate.executeQuery();
                seturl.next();
                String urlimg = seturl.getString(1);
                if(!(urlimg.equals("null"))) {
                    iv.setImage(new Image(urlimg));
                }
                roomBox.getChildren().add(allRoomsCompos.get(m));
                HBox hbx = (HBox) allRoomsCompos.get(m).getChildren().get(5);
                Button btn = (Button) hbx.getChildren().get(0);
                btn.setOnAction(event1 -> {
                    Method.notification(Alert.AlertType.INFORMATION, "Room's Author", "Author", "Author Of Room : "+hotelauthor);
                });
                Button btn2 = (Button)hbx.getChildren().get(1);
                btn2.setOnAction(event2 -> {
                    Method.notification(Alert.AlertType.INFORMATION, "About Room", "Room", "Room's Capacity : "+roomcap +"Room's Price : "+price+"Room's facility : "+facility);
                });
                Button savebtn = (Button) hbx.getChildren().get(2);
                savebtn.setOnAction(event1 -> {
                    try {
                        if(SignInUp.enteredToApp) {
                            PreparedStatement determinatiinstate = con.prepareStatement("SELECT Saver FROM savedplaces WHERE RoomID = " + "'" + id + "'");
                            ResultSet getsave = determinatiinstate.executeQuery();
                            Boolean state = true;
                            while (getsave.next()){
                                if(RightPanel.Name.equals(getsave.getString(1)))
                                    state = false;
                            }
                            if (state) {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Save");
                                alert.setHeaderText("Do You Want To Save This Place?");
                                if (alert.showAndWait().get() == ButtonType.OK) {
                                    PreparedStatement setstate = con.prepareStatement("INSERT INTO savedplaces (Saver,RoomID) VALUES " + "('" + realuser + "','" + id + "')");
                                    setstate.execute();
                                }
                            } else {
                                Method.notification(Alert.AlertType.INFORMATION, "Error!",
                                        "Problem In Saving", "You Have Saved This Place Before");
                            }
                        }
                        else{
                            Method.notification(Alert.AlertType.ERROR, "Error!",
                                    "Not Signed In", "First Please Log In");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
                Button btnComment = (Button)hbx.getChildren().get(3);
                btnComment.setOnAction(event2 -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Comments");
                    alert.setHeaderText("Comments");
                    try {
                        PreparedStatement getcomments = con.prepareStatement("SELECT Comments FROM rooms WHERE RoomID = "+"'"+id+"'");
                        ResultSet getcomment = getcomments.executeQuery();
                        getcomment.next();
                        String comments = getcomment.getString(1);
                        String all[]=comments.split(",");
                        String showing = "";
                        for(int i = 0;i<all.length;i++){
                            showing+=all[i]+"\n";
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
                            if (resultx.isPresent()){
                                PreparedStatement commentstate = con.prepareStatement("SELECT Comments FROM rooms WHERE RoomID = "+"'"+id+"'");
                                ResultSet getcom = commentstate.executeQuery();
                                getcom.next();
                                String ex = getcom.getString(1);
                                if(!(ex.equals("null"))) {
                                    String newcomment = ex + " , " + dialog.getResult();
                                    PreparedStatement updatecomment = con.prepareStatement("UPDATE rooms SET Comments = " + "'" + newcomment + "'" + " WHERE RoomID = " + "'" + id + "'");
                                    updatecomment.executeUpdate();
                                }
                                else{
                                    PreparedStatement updatecomment = con.prepareStatement("UPDATE rooms SET Comments = " + "'" + dialog.getResult() + "'" + " WHERE RoomID = " + "'" + id + "'");
                                    updatecomment.executeUpdate();
                                }
                            }
                        }
                        else{

                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });

                Button buybtn = (Button)allRoomsCompos.get(m).getChildren().get(4);
                buybtn.setOnAction(event3->{
                    if(SignInUp.enteredToApp){
                        Boolean z = RightPanel.existingroom;
                        if(!(z)){
                            try {
                                PreparedStatement balancestate = con.prepareStatement("SELECT Balance FROM passengers WHERE UserName = "+"'"+realuser+"'");
                                ResultSet getbalance = balancestate.executeQuery();
                                getbalance.next();
                                String bal = getbalance.getString(1);
                                if(Double.parseDouble(bal)>=Double.parseDouble(price)){
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
                                    if (result.isPresent()){
                                        int choice = Integer.parseInt(dialog.getResult());
                                        double overallprice = choice*Double.parseDouble(price);
                                        if(Double.parseDouble(bal)>=overallprice){
                                            Double newbalance = Double.parseDouble(bal)-overallprice;
                                             PreparedStatement setnewbalance = con.prepareStatement("UPDATE passengers SET Balance = " + "'" + String.valueOf(newbalance) + "'" + "WHERE UserName = " + "'" + realuser + "'");
                                             setnewbalance.executeUpdate();
                                             PreparedStatement buyerstate = con.prepareStatement("INSERT INTO buyhistory (Buyer,RoomAuthor,RoomID,Price,Nights) VALUES"+"('"+realuser+"','"+hotelauthor+"','"+id+"','"+price+"','"+String.valueOf(choice)+"')");
                                             buyerstate.execute();
                                             PreparedStatement hotelbalancestate = con.prepareStatement("SELECT Balance FROM hotel WHERE UserName = "+"'"+hotelauthor+"'");
                                             ResultSet hotelbalanceresult = hotelbalancestate.executeQuery();
                                             hotelbalanceresult.next();
                                             String hotelexbalance = hotelbalanceresult.getString(1);
                                             Double next = Double.parseDouble(hotelexbalance)+overallprice;
                                             PreparedStatement updatehotelbalance = con.prepareStatement("UPDATE hotel SET Balance = "+"'"+String.valueOf(next)+"'"+"WHERE UserName = "+"'"+hotelauthor+"'");
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
                                                            "price for "+nights+" night : "+Double.parseDouble(pricee.trim())*Integer.parseInt(nights.trim())};
                                                    RightPanel.tline.play();
                                                }
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        else{
                                            Method.notification(Alert.AlertType.ERROR,"Failure","Failure","insufficient availability !");
                                        }
                                    }
                                }
                                else{
                                    Method.notification(Alert.AlertType.ERROR,"Failure","Failure","insufficient availability !");
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            Method.notification(Alert.AlertType.ERROR,"Failure","Failure","You Have Rent a Room Already!");
                        }
                    }
                    else{
                        Method.notification(Alert.AlertType.ERROR,"ERROR","Error","First Please Login");
                    }
                });
                m++;
            }
////            com.close();
//            con.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int a = (int) Method.random(10, 15);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(a), e -> {
            activePics();
        }));
        timeline.setCycleCount(javafx.animation.Animation.INDEFINITE);
        timeline.play();
    }

    public void goRight() {
        if (flag == 0) {
            Method.changeMainPagesPhotos(villaLabel, tentLabel, 732);
            flag = 1;
        }
    }

    public void goLeft() {
        if (flag == 1) {
            Method.changeMainPagesPhotos(tentLabel, villaLabel, -732);
            flag = 0;
        }

    }
}
