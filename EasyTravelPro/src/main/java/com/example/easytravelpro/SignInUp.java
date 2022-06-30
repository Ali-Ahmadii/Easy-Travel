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

public class SignInUp implements Initializable {

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
    }

    static ArrayList<Passenger> passengers = new ArrayList<>();
    static int i = 0;
    static ArrayList<Hotelier> hoteliers = new ArrayList<>();
    static int j = 0;

    public void signUp(ActionEvent event) throws IOException {
        if (Method.searchEmptyField(textFields) && !(passwordSu.getText().equals(""))) {
            usernameSuField = usernameSu.getText();
            passwordSuField = passwordSu.getText();
            fullNameField = fullName.getText();
            locationField = location.getText();
            phoneNumberField = phoneNumber.getText();
            emailField = email.getText();

            Parent root;

            if (currentUserType.equals(userTypeList[0])) {
                passengers.add(new Passenger(usernameSuField, passwordSuField,
                        fullNameField, locationField, phoneNumberField, emailField, 0));
                i++;
                enteredToApp = true;
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Frame.fxml")));

            } else {
                hoteliers.add(new Hotelier(usernameSuField, passwordSuField,
                        fullNameField, locationField, phoneNumberField, emailField));
                j++;
                enteredToHotelierUI = true;
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Hotelier.fxml")));
            }

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.show();

            Method.notification(Alert.AlertType.INFORMATION, "Welcome",
                    "Success Sign Up ", "welcome to our Application\nGood Luck ;)");

        } else
            Method.notification(Alert.AlertType.ERROR, "Error!",
                    "Some fields are Empty", "Please fill all fields");
    }

    public void signIn(ActionEvent event) throws IOException {

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
    static boolean enteredToHotelierUI = true;

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
