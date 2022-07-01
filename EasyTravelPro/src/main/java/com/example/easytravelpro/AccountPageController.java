package com.example.easytravelpro;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;


public class AccountPageController implements Initializable {

    static final String DB_URL = "jdbc:mysql://localhost:3306/easy_travel";
    static final String USER = "root";
    static final String PASS = "integral4560sini";

    @FXML
    public Label fullNameL, usernameL, locationL, phoneNumberL, emailL;
    @FXML
    private Button showPass, setPro, changePro, delAcc, logOut, buyPre;

    static String fullname,username,location , phonenumber , email ,Balance;

//    @Overridev
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (SignInUp.enteredToApp) {
            fullNameL.setText(fullname);
            usernameL.setText(username);
            locationL.setText(location);
            phoneNumberL.setText(phonenumber);
            emailL.setText(email);
        }
    }
    @FXML
    void changepass() throws SQLException {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Change Password");
        dialog.setHeaderText("Password Changing");
        dialog.setContentText("Change Password");
        System.out.println(dialog.getResult());
        Optional <String> result = dialog.showAndWait();
        if(result.isPresent()){
            System.out.println(dialog.getResult());
            Connection con= DriverManager.getConnection(DB_URL,USER,PASS);
            //update users set num_points = 6000 where id = 2;
            PreparedStatement ss = con.prepareStatement("UPDATE passengers SET Password = "+"'"+dialog.getResult()+"'"+"WHERE UserName = '"+username+"'");
            ss.executeUpdate();
        }
    }
    @FXML
    void showpass() throws SQLException {
        Connection con= DriverManager.getConnection(DB_URL,USER,PASS);
        PreparedStatement cc = con.prepareStatement("SELECT Password FROM passengers WHERE UserName = '"+username +"'");
        ResultSet cs = cc.executeQuery();
        cs.next();
        String pass = cs.getString(1);
        Method.notification(Alert.AlertType.INFORMATION, "Password", "Your Password",pass);
    }
    @FXML
    void deletact(ActionEvent event) throws SQLException, IOException {
        Connection ccc = DriverManager.getConnection(DB_URL,USER,PASS);
        PreparedStatement deletstate = ccc.prepareStatement("DELETE FROM passengers WHERE UserName = '"+username+"'");
        deletstate.executeUpdate();
        SignInUp.enteredToApp = false;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Frame.fxml")));
        Stage stage = (Stage) ((javafx.scene.Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        SignInUp.enteredToApp = false;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Frame.fxml")));
        Stage stage = (Stage) ((javafx.scene.Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }
}