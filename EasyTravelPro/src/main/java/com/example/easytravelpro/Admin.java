package com.example.easytravelpro;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class Admin implements Initializable {
    static final String DB_URL = "jdbc:mysql://localhost:3306/easy_travel";
    static final String USER = "root";
    static final String PASS = "integral4560sini";
    @FXML
    private TextArea textArea;
    static String user;
    static String which;

    static String current;
    static String textFillDark = "#e6e6e6";
    static String textFillLight = "#191919";
    static boolean isRainBow = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textArea.setOnKeyPressed(new EventHandler<>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                Timeline timelineR = new Timeline(new KeyFrame(Duration.seconds(0.3), e -> {
                    int a = (int) Method.random(0, 255);
                    int b = (int) Method.random(0, 255);
                    int c = (int) Method.random(0, 255);
                    textArea.setStyle("-fx-text-fill: rgb(" + a + "," + b + "," + c + ");");
                }));
                timelineR.setCycleCount(-1);
                if (Main.isDark) textArea.setStyle("-fx-text-fill: " + textFillDark + ";");
                else textArea.setStyle("-fx-text-fill: " + textFillLight + ";");

                if (keyEvent.getCode() == KeyCode.ALT) {
                    textArea.clear();
                }
                if (keyEvent.getCode() == KeyCode.ENTER) {

                    current = textArea.getText().trim().toLowerCase();

                    if (current.equals("show hoteliers info")) {
                        Connection con = null;
                        try {
                            con = DriverManager.getConnection(DB_URL, USER, PASS);
                            PreparedStatement hoteliersinfo = con.prepareStatement("SELECT UserName FROM hotel");
                            ResultSet gethoteliers = hoteliersinfo.executeQuery();
                            ArrayList<String> HotelierInfo = new ArrayList<>();
                            //clear list?? for hotelier and passengers
                            while (gethoteliers.next()) {
                                String hotels = gethoteliers.getString(1);
                                PreparedStatement allinfos = con.prepareStatement("SELECT HotelName FROM hotel WHERE UserName = " + "'" + hotels + "'");
                                ResultSet getinfos = allinfos.executeQuery();
                                getinfos.next();
                                String hotelname = getinfos.getString(1);
                                PreparedStatement passstate = con.prepareStatement("SELECT Password FROM hotel WHERE UserName = " + "'" + hotels + "'");
                                ResultSet getpass = passstate.executeQuery();
                                getpass.next();
                                String password = getpass.getString(1);
                                PreparedStatement roomstate = con.prepareStatement("SELECT RoomCollection FROM hotel WHERE UserName = " + "'" + hotels + "'");
                                ResultSet getrooms = roomstate.executeQuery();
                                getrooms.next();
                                String rooms = getrooms.getString(1);
                                String print = "Hotelier UserName = " + hotels + " ,Hotel Name : " + hotelname + " ,Password : " + password + " ,Rooms : " + rooms + "\n";
                                HotelierInfo.add(print);
                            }
                            con.close();
                            final int[] i = {0};
                            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
                                textArea.appendText(HotelierInfo.get(i[0]));
                                i[0]++;
                            }));
                            timeline.setCycleCount(HotelierInfo.size());
                            timeline.play();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else if (current.equals("show passengers info")) {
                        Connection con = null;
                        try {
                            con = DriverManager.getConnection(DB_URL, USER, PASS);
                            PreparedStatement usernamesstate = con.prepareStatement("SELECT UserName FROM passengers");
                            ResultSet getusers = usernamesstate.executeQuery();
                            ArrayList<String> PassengersInfo = new ArrayList<>();
                            while (getusers.next()) {
                                String users = getusers.getString(1);
                                PreparedStatement fullnamestate = con.prepareStatement("SELECT FullName FROM passengers WHERE UserName = " + "'" + users + "'");
                                ResultSet setname = fullnamestate.executeQuery();
                                setname.next();
                                String fullname = setname.getString(1);
                                PreparedStatement passwordstate = con.prepareStatement("SELECT Password FROM passengers WHERE UserName = " + "'" + users + "'");
                                ResultSet setpassword = passwordstate.executeQuery();
                                setpassword.next();
                                String password = setpassword.getString(1);

                                PreparedStatement balancestate = con.prepareStatement("SELECT Balance FROM passengers WHERE UserName = " + "'" + users + "'");
                                ResultSet setbalance = balancestate.executeQuery();
                                setbalance.next();
                                String balance = setbalance.getString(1);

                                PreparedStatement phonestate = con.prepareStatement("SELECT PhoneNumber FROM passengers WHERE UserName = " + "'" + users + "'");
                                ResultSet setphone = phonestate.executeQuery();
                                setphone.next();
                                String phonenumber = setphone.getString(1);

                                String print = "User Name : " + users + " ,Full Name : " + fullname + " ,Password : " + password + " ,Balance : " + balance + " ,Phone Number : " + phonenumber + "\n";
                                PassengersInfo.add(print);
                            }
                            con.close();
                            final int[] i = {0};
                            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
                                textArea.appendText(PassengersInfo.get(i[0]));
                                i[0]++;
                            }));
                            timeline.setCycleCount(PassengersInfo.size());
                            timeline.play();
                        } catch (SQLException t) {
                            t.printStackTrace();
                        }

                    } else if (current.equals("show balance")) {
                        textArea.clear();

                    } else if (current.startsWith("delete username")) {
                        String p = current.substring(16);
                        String user = p.replace("\n", "");
                        try {
                            if (EXISTANCE(user)) {
                                deletuser(user);
                                textArea.setText("Deleted Successfuly");
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        textArea.clear();

                    } else if (current.equals("export hoteliers information\n")) {
                        new hoteliersinformation().export();
                        textArea.setText("Exported Successfuly");
                    } else if (current.equals("export passengers information\n")) {
                        new passengersinformation().export();
                        textArea.setText("Exported Successfuly");
                    } else if (current.startsWith("set text fill")) {
                        String color = textArea.getText().substring(13).trim();
                        if (color.equals("rainbow")) {
                            timelineR.play();
                            isRainBow = true;
                        } else if (Main.isDark) {
                            textFillDark = color;
                            textArea.setStyle("-fx-text-fill: " + textFillDark + ";");
                        } else {
                            textFillLight = color;
                            textArea.setStyle("-fx-text-fill: " + textFillLight + ";");
                        }
                        textArea.clear();
                        textArea.setText("text fill successfully changed");
                    } else if (current.equals("back to login")) {
                        try {
                            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SignInUp.fxml")));
                            Stage stage = (Stage) ((javafx.scene.Node) keyEvent.getSource()).getScene().getWindow();
                            Scene scene = new Scene(root);

                            if (Main.isDark) scene.getStylesheets().add(Main.loginStyleDark);
                            else scene.getStylesheets().add(Main.loginStyleLight);

                            scene.setFill(Color.TRANSPARENT);
                            stage.setScene(scene);
                            Animation.Fade(root,400,800,0,1);
                            stage.show();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }

                    } else if (current.equals("set dark mode")) {
                        textArea.clear();
                        if (Main.isDark) {
                            textArea.setText("It's already on dark mode");
                        } else {
                            Main.isDark = true;
                            try {
                                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdminPage.fxml")));
                                Stage stage = (Stage) ((javafx.scene.Node) keyEvent.getSource()).getScene().getWindow();
                                Scene scene = new Scene(root);
                                scene.getStylesheets().add(Main.cssStyleDark);
                                scene.setFill(Color.TRANSPARENT);
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    } else if (current.equals("set light mode")) {
                        if (!(Main.isDark)) {
                            textArea.setText("It's already on light mode");
                        } else {
                            Main.isDark = false;
                            try {
                                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdminPage.fxml")));
                                Stage stage = (Stage) ((javafx.scene.Node) keyEvent.getSource()).getScene().getWindow();
                                Scene scene = new Scene(root);
                                scene.getStylesheets().add(Main.cssStyleLight);
                                scene.setFill(Color.TRANSPARENT);
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    } else if (current.equals("show command guide")) {
                        textArea.clear();
                        final int[] i = {1};
                        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.4), e -> {
                            switch (i[0]) {
                                case 1 -> textArea.appendText("<<show hoteliers info>> ->  you can see hoteliers information");
                                case 2 -> textArea.appendText("\n\n<<show passengers info>> ->  you can see passenger information");
                                case 3 -> textArea.appendText("\n\n<<show balance>> ->  you can see balance of admin (Not define yet)");
                                case 4 -> textArea.appendText("\n\n<<delete username>> ->  you can delete someone from application");
                                case 5 -> textArea.appendText("\n\n<<set text fill #color>> ->  you can change the color of text");
                                case 6 -> textArea.appendText("\n\n<<back to login>> ->  you will back to loginPage        ");
                                case 7 -> textArea.appendText("\n\n<<set light mode>> ->  you will change background to white");
                                case 8 -> textArea.appendText("\n\n<<set dark mode>> ->  you will change background to black");
                                case 9 -> textArea.appendText("\n\n<<close app>> ->  for exit from application        ");
                                case 10 -> textArea.appendText("\n\n<<stop rainbow>> ->  it will stop the rain bow       \n");
                            }
                            i[0]++;
                        }));
                        timeline.setCycleCount(10);
                        timeline.play();
                    } else if (current.equals("close app")) {
                    } else if (current.equals("stop rainbow")) {
                        if (isRainBow) {
                            textArea.setText("successfully stopped");
                            timelineR.stop();
                            isRainBow = false;
                        } else textArea.setText("rainbow isn't running");
                    } else if (textArea.getText().equals("\nInvalid Command :(")) {
                        textArea.clear();
                    } else if (textArea.getText().equals("\n")) {
                        textArea.clear();
                    } else {
                        textArea.setStyle("-fx-text-fill: red;");
                        textArea.clear();
                        textArea.setText("Invalid Command :(");
                    }
                }
            }
        });
    }

    Boolean EXISTANCE(String input) throws SQLException {
        Connection c = DriverManager.getConnection(DB_URL, USER, PASS);
        PreparedStatement existance = c.prepareStatement("SELECT UserName FROM passengers WHERE UserName =" + "'" + input + "'");
        ResultSet getresult = existance.executeQuery();
        Boolean z = getresult.next();
        c.close();
        return z;
    }

    void deletuser(String inp) throws SQLException {
        Connection c = DriverManager.getConnection(DB_URL, USER, PASS);
        PreparedStatement whichone = c.prepareStatement("DELETE FROM passengers WHERE UserName = '" + inp + "'");
        whichone.executeUpdate();
        c.close();
    }
}
