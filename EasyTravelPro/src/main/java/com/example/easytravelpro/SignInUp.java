package com.example.easytravelpro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.sql.*;

public class SignInUp implements Initializable {

    static final String DB_URL = "jdbc:mysql://localhost:3306/easy_travel";
    static final String USER = "root";
    static final String PASS = "integral4560sini";

    @FXML
    private TextField usernameSu, usernameSi, fullName, location, phoneNumber, email;
    static ArrayList<TextField> textFields = new ArrayList<>();
    @FXML
    private PasswordField passwordSu, passwordSi;
    String usernameSuField;
    String fullNameField;
    String locationField;
    String phoneNumberField;
    String emailField;
    String passwordSuField;
    @FXML
    private ChoiceBox<String> userType;
    String[] userTypeList = {"I'm Passenger", "I'm Hotelier"};
    String currentUserType = "I'm Passenger";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textFields.add(usernameSu);
        textFields.add(fullName);
        textFields.add(location);
        textFields.add(phoneNumber);
        textFields.add(email);

        userType.getItems().addAll(userTypeList);
        userType.setOnAction(this::userChoice);
        userType.setValue("I'm Passenger");
    }


    // this is for sign in page

    public void userChoice(ActionEvent event) {
        currentUserType = userType.getValue();
        System.out.println("HEYYY");
    }

    static ArrayList<Passenger> passengers = new ArrayList<>();
    static int i = 0;

    public void signUp(ActionEvent event) throws IOException, SQLException {
        if (Method.searchEmptyField(textFields) && !(passwordSu.getText().equals(""))) {
            usernameSuField = usernameSu.getText();
            passwordSuField = passwordSu.getText();
            fullNameField = fullName.getText();
            locationField = location.getText();
            phoneNumberField = phoneNumber.getText();
            emailField = email.getText();
            if (currentUserType.equals(userTypeList[0])) {
                passengers.add(new Passenger(usernameSuField, passwordSuField, fullNameField, locationField, phoneNumberField, emailField,0));
                Connection con=DriverManager.getConnection(DB_URL,USER,PASS);
                PreparedStatement pst = con.prepareStatement("SELECT * FROM passengers WHERE UserName = " +"'"+ usernameSuField+"';");
                PreparedStatement pstq = con.prepareStatement("SELECT * FROM hotel WHERE UserName = " +"'"+ usernameSuField+"';");
                ResultSet rs = pst.executeQuery();
                ResultSet rsq = pstq.executeQuery();
                boolean x = rs.next();
                boolean y = rsq.next();
                if(!(x) && !(y)) {
                    PreparedStatement insertme = con.prepareStatement("INSERT INTO passengers (UserName,FullName,Location,PhoneNumber,Email,Password,Balance) VALUES" + "('" + usernameSuField + "','" + fullNameField + "','" + locationField + "','" + phoneNumberField + "','" + emailField + "','" + passwordSuField + "','" +String.valueOf(0)+"')");
                    insertme.execute();
                    con.close();
                    i++;
                    enteredToApp = true;
                    Method.notification(Alert.AlertType.INFORMATION, "Welcome", "Success Sign Up ", "welcome to our Application\nGood Luck ;)");
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Frame.fxml")));
                    Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    scene.setFill(Color.TRANSPARENT);
                    stage.setScene(scene);
                    stage.show();
                }
                if(x || y){
                    Method.notification(Alert.AlertType.ERROR, "Error!",
                            "Unavailable Username", "Please Choose Another Username");
                    con.close();
                }
            } else {
                Hotelier hotelier = new Hotelier(usernameSuField, passwordSuField, fullNameField, locationField, phoneNumberField, emailField);
                Connection con=DriverManager.getConnection(DB_URL,USER,PASS);
                PreparedStatement pst = con.prepareStatement("SELECT * FROM hotel WHERE UserName = " +"'"+ usernameSuField+"';");
                PreparedStatement hst = con.prepareStatement("SELECT * FROM passengers WHERE UserName = " +"'"+ usernameSuField+"';");
                ResultSet rs = pst.executeQuery();
                ResultSet rq = hst.executeQuery();
                boolean x = rs.next();
                boolean y = rq.next();
                if(!(x) && !(y)) {
                    PreparedStatement insertme = con.prepareStatement("INSERT INTO hotel (UserName,HotelName,Location,PhoneNumber,Email,Password) VALUES" + "('" + usernameSuField + "','" + fullNameField + "','" + locationField + "','" + phoneNumberField + "','" + emailField + "','" + passwordSuField + "')");
                    insertme.execute();
                    con.close();
                    i++;
                    enteredToApp = true;
                    Method.notification(Alert.AlertType.INFORMATION, "Welcome",
                            "Success Sign Up ", "welcome to our Application\nGood Luck ;)");
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Frame.fxml")));
                    Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    scene.setFill(Color.TRANSPARENT);
                    stage.setScene(scene);
                    stage.show();
                }
                if(x || y){
                    Method.notification(Alert.AlertType.ERROR, "Error!",
                            "Unavailable Username", "Please Choose Another Username");
                    con.close();
                }
            }
        } else
            Method.notification(Alert.AlertType.ERROR, "Error!", "Some fields are Empty", "Please fill all fields");
    }

    public void signIn(ActionEvent event) throws IOException, SQLException {
        Connection con=DriverManager.getConnection(DB_URL,USER,PASS);
        PreparedStatement pst = con.prepareStatement("SELECT * FROM hotel WHERE UserName = " +"'"+ usernameSi.getText()+"';");
        PreparedStatement hst = con.prepareStatement("SELECT * FROM passengers WHERE UserName = " +"'"+ usernameSi.getText()+"';");
        ResultSet rs = pst.executeQuery();
        ResultSet rq = hst.executeQuery();
        boolean hotelierbool = rs.next();
        boolean passengerbool = rq.next();
        boolean adminbool = false;
        if(usernameSi.getText().equals("admin") && passwordSi.getText().equals("admin")){
            System.out.println("admin has entered");
            adminbool = true;
        }
        System.out.println(rs);
        System.out.println(rq);
        if(hotelierbool){
            PreparedStatement hotelstate = con.prepareStatement("SELECT Password FROM hotel WHERE UserName = " +"'"+ usernameSi.getText()+"';");
            ResultSet hotelierpass = hotelstate.executeQuery();
            hotelierpass.next();
                if (passwordSi.getText().equals(hotelierpass.getString(1))) {
                    //Move to hotelier page
                    System.out.println("True Pass For Hotelier");
                }
                else{
                    Method.notification(Alert.AlertType.ERROR, "Error!", "Wrong UserName Or Password", "Please Re Write Your Account Information");
                }
        }
        if(passengerbool){
            PreparedStatement passengerstate = con.prepareStatement("SELECT Password FROM passengers WHERE UserName = " +"'"+ usernameSi.getText()+"';");
            ResultSet pss = passengerstate.executeQuery();
            pss.next();
            if(passwordSi.getText().equals(pss.getString(1))){
                //Move to passenger page
                System.out.println("True Pass For Passenger");
                enteredToApp = true;
                Method.notification(Alert.AlertType.INFORMATION, "Welcome", "Success Sign Up ", "welcome to our Application\nGood Luck ;)");
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Frame.fxml")));
                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);
                stage.setScene(scene);
                stage.show();
            }
            else{
                Method.notification(Alert.AlertType.ERROR, "Error!", "Wrong UserName Or Password", "Please Re Write Your Account Information");
            }
        }
        if(adminbool){
            //Move To Admin Page
            System.out.println("Hello Admin");
        }
        if((!(hotelierbool || passengerbool || adminbool))){
            Method.notification(Alert.AlertType.ERROR, "Error!", "Wrong UserName Or Password", "Please Re Write Your Account Information");
        }
        con.close();
    }


    //this is for design
    @FXML
    private ImageView beachPicture, roomPicture;
    @FXML
    private VBox signUpPage, signInPage;
    @FXML
    private Label alreadyLogged, newUser;
    @FXML
    private Button suBtn, siBtn;

    static boolean enteredToApp = false;

    public void signUpClicked() {
        Method.changePagesInLoginPage(signUpPage, beachPicture, signInPage, roomPicture, 700, 400, -400);
    }

    public void signInClicked() {
        Method.changePagesInLoginPage(signInPage, roomPicture, signUpPage, beachPicture, 700, -400, 400);
    }

    public void changeColorEntered() {
        suBtn.setStyle("-fx-background-color: #ffaf52;" +
                "-fx-background-radius: 50;");

        siBtn.setStyle("-fx-background-color: #ffaf52;" +
                "-fx-background-radius: 50;");
    }

    public void changeColorExited() {
        suBtn.setStyle("-fx-background-color: #FF9212;" +
                "-fx-background-radius: 50;");

        siBtn.setStyle("-fx-background-color: #FF9212;" +
                "-fx-background-radius: 50;");
    }

    public void changeColorPressed() {
        suBtn.setStyle("-fx-background-color: #e38600;" +
                "-fx-background-radius: 50;");

        siBtn.setStyle("-fx-background-color: #e38600;" +
                "-fx-background-radius: 50;");
    }

    public void changeColorReleased() {
        suBtn.setStyle("-fx-background-color: #ffaf52;" +
                "-fx-background-radius: 50;");

        siBtn.setStyle("-fx-background-color: #ffaf52;" +
                "-fx-background-radius: 50;");
    }

    public void changeColorEnter() {
        alreadyLogged.setStyle("-fx-text-fill: #3172f6;");
        newUser.setStyle("-fx-text-fill: #3172f6;");
    }

    public void changeColorExit() {
        alreadyLogged.setStyle("-fx-text-fill: #123d93;");
        newUser.setStyle("-fx-text-fill: #123d93;");
    }

    public void backButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Frame.fxml")));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
        textFields.clear();
    }
}
