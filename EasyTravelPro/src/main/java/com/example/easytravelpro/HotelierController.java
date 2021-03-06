package com.example.easytravelpro;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Scanner;

public class HotelierController implements Initializable {

    @FXML
    private TextField capacity, facility, price;
    @FXML
    private HBox roomBox, RateBox;
    @FXML
    private Label accountBalance;
    @FXML
    private Label hotelNameLabel;
    static String hotelName;
    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<AnchorPane> roomCompos = new ArrayList<>();
    static int k = 0;
    static boolean x = false;
    static final String DB_URL = "jdbc:mysql://localhost:3306/easy_travel";
    static final String USER = "root";
    static final String PASS = "integral4560sini";
    static String userhotel;
    static int compontntindex = -1;
    static String lastroomid;
    @FXML
    private ScrollPane roomscrollpane;
    public void choosePhotoForRoom(ActionEvent event) throws MalformedURLException, SQLException {
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose your photo");
        fc.setInitialDirectory(new File("C:\\Users\\Ali\\Desktop\\before revert\\New folder (3)\\Easy-Travel\\EasyTravelPro\\src\\main\\resources\\com\\example\\easytravelpro\\NewRooms"));
        fc.getExtensionFilters().clear();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image file","*.PNG"));
        File file = fc.showOpenDialog(null);
        if (file != null) {
            Connection lastconnection = DriverManager.getConnection(DB_URL,USER,PASS);
            PreparedStatement lastroomidstate = lastconnection.prepareStatement("UPDATE rooms SET PhotoAdress = "+"'"+file.toURL().toString()+"'"+" WHERE RoomID = "+"'"+lastroomid+"'");
            lastroomidstate.executeUpdate();
            lastconnection.close();
            AnchorPane lastComponent = (AnchorPane) roomBox.getChildren().get(compontntindex);
            ImageView iv = (ImageView) lastComponent.getChildren().get(0);
            iv.setImage(new Image(file.toURL().toString()));
        } else
            Method.notification(Alert.AlertType.ERROR,"","","");
    }
    @FXML
    void exportbuyinformation(){
        new buyinformation(userhotel).export();
    }
    @FXML void exportroomsinfo(){
        new roomsinformation(userhotel).export();
    }
    public void addRoom(ActionEvent event) throws IOException, SQLException {
         Connection con=DriverManager.getConnection(DB_URL,USER,PASS);
         PreparedStatement locationinfostate = con.prepareStatement("SELECT Location FROM hotel WHERE UserName = " +"'"+ userhotel+"';");
            ResultSet setlocationinfo = locationinfostate.executeQuery();
            setlocationinfo.next();
            String o = setlocationinfo.getString(1);
        PreparedStatement hotelnameinfo = con.prepareStatement("SELECT UserName FROM hotel WHERE UserName = " +"'"+ userhotel+"';");
        ResultSet sethotel = hotelnameinfo.executeQuery();
        sethotel.next();
        String oo = sethotel.getString(1);
        hotelName = oo;
        PreparedStatement exrooms = con.prepareStatement("SELECT * FROM rooms WHERE AuthorHotel = "+"'"+userhotel+"';");
        ResultSet roomsresult = exrooms.executeQuery();
        while(roomsresult.next()){
        }
        rooms.add(new Room(hotelName
                , o
                , Integer.parseInt(capacity.getText())
                , facility.getText(),
                Double.parseDouble(price.getText())));
        compontntindex++;
        File gg = new File("C:\\Users\\Ali\\Desktop\\before revert\\New folder (3)\\Easy-Travel\\EasyTravelPro\\src\\main\\java\\com\\example\\easytravelpro\\IDG.txt");
        Scanner scanaccountnumber = new Scanner(gg);
        String give = scanaccountnumber.nextLine();
        scanaccountnumber.close();
        FileWriter writenewone = new FileWriter(gg);
        int x = Integer.parseInt(give);
        int constant = x;
        lastroomid = String.valueOf(constant);
        x++;;
        String newaccount = String.valueOf(x);
        writenewone.write(newaccount);
        writenewone.close();
        scanaccountnumber.close();
        PreparedStatement roomstate = con.prepareStatement("INSERT INTO rooms (AuthorHotel,Price,Rate,Comments,Facilities,Capacity,PhotoAdress,RoomID) VALUES "+"('"+oo+"','"+price.getText()+"','"+null+"','"+null+"','"+facility.getText()+"','"+capacity.getText()+"','"+null+"','"+String.valueOf(constant)+"')");
        roomstate.execute();
        PreparedStatement getroomcount = con.prepareStatement("SELECT RoomsCount FROM hotel WHERE UserName = "+"'"+userhotel +"'");
        PreparedStatement getroomscollection = con.prepareStatement("SELECT RoomCollection FROM hotel WHERE UserName = "+"'"+userhotel +"'");
        ResultSet getroomcountnow = getroomcount.executeQuery();
        ResultSet getroomcollectionnow = getroomscollection.executeQuery();
        getroomcountnow.next();
        int ex = Integer.parseInt(getroomcountnow.getString(1));
        getroomcollectionnow.next();
        int excollection = Integer.parseInt(getroomcollectionnow.getString(1));
        int entered = Integer.parseInt(capacity.getText());
        int next = ex+entered;
        int now = excollection+1;
        PreparedStatement updatecount = con.prepareStatement("UPDATE hotel SET RoomsCount = "+"'" +String.valueOf(next)+"'" +"WHERE UserName = "+"'"+userhotel +"';");
        updatecount.executeUpdate();
        PreparedStatement updatecountcollection = con.prepareStatement("UPDATE hotel SET RoomCollection = "+"'" +String.valueOf(now) +"'"+"WHERE UserName = "+"'"+userhotel +"'");

        updatecountcollection.executeUpdate();
        roomCompos.add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("roomComponent.fxml"))));
        roomBox.getChildren().add(roomCompos.get(k));
        k++;
            con.close();
    }

    public void backToLoginPage(ActionEvent event) throws IOException {
        SignInUp.enteredToHotelierUI = false;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SignInUp.fxml")));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        com.example.easytravelpro.Animation.Fade(root, 400, 800, 0, 1);

        if (Main.isDark) scene.getStylesheets().add(Main.loginStyleDark);
        else scene.getStylesheets().add(Main.loginStyleLight);

        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        compontntindex = -1;
        try {
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement howmany = con.prepareStatement("SELECT RoomCollection FROM hotel WHERE UserName = " + "'" + userhotel + "'");
            ResultSet howmanyresult = howmany.executeQuery();
            howmanyresult.next();
            int h = Integer.parseInt(howmanyresult.getString(1));
            PreparedStatement balancestate = con.prepareStatement("SELECT Balance FROM hotel WHERE UserName = +"+"'"+userhotel+"'");
            ResultSet getbalance = balancestate.executeQuery();
            getbalance.next();
            String x = getbalance.getString(1);
            accountBalance.setText("Account Balance: $"+x);
            if (h != 0) {
                PreparedStatement hotellocation = con.prepareStatement("SELECT Location FROM hotel WHERE UserName = " + "'" + userhotel + "'");
                ResultSet locationset = hotellocation.executeQuery();
                locationset.next();
                String loc = locationset.getString(1);
                PreparedStatement roomid = con.prepareStatement("SELECT RoomID FROM rooms WHERE AuthorHotel = " + "'" + userhotel + "'");
                ResultSet setroomid = roomid.executeQuery();
                setroomid.next();
                String myid1 = String.valueOf(setroomid.getString(1));
                PreparedStatement roomcap1 = con.prepareStatement("SELECT Capacity FROM rooms WHERE RoomID = " + "'" + myid1 + "'");
                ResultSet setcap1 = roomcap1.executeQuery();
                setcap1.next();
                String mycapa1 = setcap1.getString(1);
                PreparedStatement roomfac1 = con.prepareStatement("SELECT Facilities FROM rooms WHERE RoomID = " + "'" + myid1 + "'");
                ResultSet setfac1 = roomfac1.executeQuery();
                setfac1.next();
                String faci1 = setfac1.getString(1);
                PreparedStatement roomprice1 = con.prepareStatement("SELECT Price FROM rooms WHERE RoomID = " + "'" + myid1 + "'");
                ResultSet setprice1 = roomprice1.executeQuery();
                setprice1.next();
                String pricee1 = setprice1.getString(1);
                rooms.add(new Room(hotelName, loc, Integer.parseInt(mycapa1), faci1, Double.parseDouble(pricee1)));
                roomCompos.add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("roomComponent.fxml"))));
                roomBox.getChildren().add(roomCompos.get(k));
                PreparedStatement phonestate = con.prepareStatement("SELECT PhoneNumber FROM hotel WHERE UserName = " + "'" + userhotel + "'");
                ResultSet getphone = phonestate.executeQuery();
                getphone.next();
                String phone = getphone.getString(1);

                HBox hBox = (HBox) roomCompos.get(k).getChildren().get(5);
                Button btn = (Button) hBox.getChildren().get(0);
                btn.setOnAction(event1 -> {
                    String n = userhotel;
                    Method.notification(Alert.AlertType.INFORMATION, "Room's Author", "Author", "Author OF Room : " + n + " Location : " + loc + " PhoneNumber : " + phone);
                });
                PreparedStatement facilitystate = con.prepareStatement("SELECT Facilities FROM rooms WHERE RoomID = " + "'" + myid1 + "'");
                ResultSet getfacility = facilitystate.executeQuery();
                getfacility.next();
                String fac = getfacility.getString(1);

                PreparedStatement capacitystate = con.prepareStatement("SELECT Capacity FROM rooms WHERE RoomID = " + "'" + myid1 + "'");
                ResultSet getcapacity = capacitystate.executeQuery();
                getcapacity.next();
                String capacit = getcapacity.getString(1);
                Button btninfo = (Button) hBox.getChildren().get(1);
                btninfo.setOnAction(event1 -> {
                    Method.notification(Alert.AlertType.INFORMATION, "Room's Info", "Info", "Room's Facilities : " + fac + " Room's Capacity : " + capacit);
                });
                PreparedStatement commentsstate = con.prepareStatement("SELECT Comments FROM rooms WHERE RoomID = " + "'" + myid1 + "'");
                ResultSet getcomments = commentsstate.executeQuery();
                getcomments.next();
                String comments = getcomments.getString(1);
                Button commentbtn = (Button) hBox.getChildren().get(3);
                commentbtn.setOnAction(eventx -> {
                    Method.notification(Alert.AlertType.INFORMATION, "Comments", "Comments", "Comments : " + comments);
                });
                k++;
                while (setroomid.next()) {
                    String myid = String.valueOf(setroomid.getString(1));
                    PreparedStatement roomcap = con.prepareStatement("SELECT Capacity FROM rooms WHERE RoomID = " + "'" + myid + "'");
                    ResultSet setcap = roomcap.executeQuery();
                    setcap.next();
                    String mycapa = setcap.getString(1);
                    PreparedStatement roomfac = con.prepareStatement("SELECT Facilities FROM rooms WHERE RoomID = " + "'" + myid + "'");
                    ResultSet setfac = roomfac.executeQuery();
                    setfac.next();
                    String faci = setfac.getString(1);
                    PreparedStatement roomprice = con.prepareStatement("SELECT Price FROM rooms WHERE RoomID = " + "'" + myid + "'");
                    ResultSet setprice = roomprice.executeQuery();
                    setprice.next();
                    String pricee = setprice.getString(1);
                    rooms.add(new Room(hotelName, loc, Integer.parseInt(mycapa), faci, Double.parseDouble(pricee)));
                    roomCompos.add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("roomComponent.fxml"))));
                    roomBox.getChildren().add(roomCompos.get(k));
                    HBox hBox1 = (HBox) roomCompos.get(k).getChildren().get(5);
                    Button btn1 = (Button) hBox1.getChildren().get(0);
                    btn1.setOnAction(event1 -> {
                        String m = userhotel;
                        Method.notification(Alert.AlertType.INFORMATION, "Room's Author", "Author", "Author OF Room : " + userhotel + " Location : " + loc + " PhoneNumber : " + phone);
                    });
                    PreparedStatement getfac = con.prepareStatement("SELECT Facilities FROM rooms WHERE RoomID = " + "'" + myid + "'");
                    PreparedStatement getcapa = con.prepareStatement("SELECT Capacity FROM rooms WHERE RoomID = " + "'" + myid + "'");
                    ResultSet getfacres = getfac.executeQuery();
                    ResultSet getcapres = getcapa.executeQuery();
                    getfacres.next();
                    String facstring = getfacres.getString(1);
                    getcapres.next();
                    String capstring = getcapres.getString(1);
                    Button infobtn = (Button) hBox1.getChildren().get(1);
                    infobtn.setOnAction(event2 -> {
                        Method.notification(Alert.AlertType.INFORMATION, "Room's Info", "Info", "Room's Facilities : " + facstring + " Room's Capacity : " + capstring);
                    });
                    PreparedStatement commentsstate1 = con.prepareStatement("SELECT Comments FROM rooms WHERE RoomID = " + "'" + myid + "'");
                    ResultSet getcomments1 = commentsstate1.executeQuery();
                    getcomments1.next();
                    String comments1 = getcomments1.getString(1);
                    Button commentbtn1 = (Button) hBox1.getChildren().get(3);
                    commentbtn1.setOnAction(eventy -> {
                        Method.notification(Alert.AlertType.INFORMATION, "Comments", "Comments", "Comments : " + comments1);
                    });
                    k++;
                }
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
            if (SignInUp.enteredToHotelierUI) {
                    hotelNameLabel.setText(hotelName + " is a Lovely Place");
            }
        if(x) {
            try {
                hotelName = Method.inputNotification();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else{

        }
    }
}