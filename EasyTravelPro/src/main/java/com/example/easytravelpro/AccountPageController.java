package com.example.easytravelpro;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
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

    static String photopath = null;


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
        Optional <String> result = dialog.showAndWait();
        if(result.isPresent()){
            Connection con= DriverManager.getConnection(DB_URL,USER,PASS);
            PreparedStatement ss = con.prepareStatement("UPDATE passengers SET Password = "+"'"+dialog.getResult()+"'"+"WHERE UserName = '"+username+"'");
            ss.executeUpdate();
            con.close();
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
        con.close();
    }
    @FXML
    void deletact(ActionEvent event) throws SQLException, IOException {
        Connection ccc = DriverManager.getConnection(DB_URL,USER,PASS);
        PreparedStatement deletstate = ccc.prepareStatement("DELETE FROM passengers WHERE UserName = '"+username+"'");
        deletstate.executeUpdate();
        ccc.close();
        SignInUp.enteredToApp = false;
        PaymentPageController.entered = false;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Frame.fxml")));
        Stage stage = (Stage) ((javafx.scene.Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
        if (Main.isDark) scene.getStylesheets().add(Main.cssStyleDark);
        else scene.getStylesheets().add(Main.cssStyleLight);
        ccc.close();
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
        if (Main.isDark) scene.getStylesheets().add(Main.cssStyleDark);
        else scene.getStylesheets().add(Main.cssStyleLight);

    }
    public void chooseprofilephoto(ActionEvent event) throws MalformedURLException, SQLException {
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose your photo");
        fc.setInitialDirectory(new File("C:\\Users\\Ali\\Desktop\\before revert\\New folder (3)\\Easy-Travel\\EasyTravelPro\\src\\main\\resources\\com\\example\\easytravelpro\\ProfilePhotos\\"));
        fc.getExtensionFilters().clear();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image file","*.PNG"));
        File file = fc.showOpenDialog(null);
        if (file != null) {
            photopath = file.toURL().toString();
            Connection con = DriverManager.getConnection(DB_URL,USER,PASS);
            PreparedStatement photourlstate = con.prepareStatement("UPDATE passengers SET PhotoURL = "+"'"+photopath+"'"+" WHERE UserName = "+"'"+RightPanel.Name+"'");
            photourlstate.executeUpdate();
            con.close();
        } else
            Method.notification(Alert.AlertType.ERROR,"","","");
    }

    @FXML
    void buypremium(ActionEvent event) throws IOException, SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Buying Confirmation");
        alert.setHeaderText("Confirmation");
        alert.setContentText("Premium Version Costs $100");
        Connection lastc = DriverManager.getConnection(DB_URL,USER,PASS);
        Optional<ButtonType> result = alert.showAndWait();
        PreparedStatement getbal = lastc.prepareStatement("SELECT Balance FROM passengers WHERE UserName = "+"'"+username+"'");
        ResultSet setbal = getbal.executeQuery();
        setbal.next();
        String balance = setbal.getString(1);
        PreparedStatement premiumstate = lastc.prepareStatement("SELECT Premium FROM passengers WHERE UserName = "+"'"+username+"'");
        ResultSet setpremiumstate = premiumstate.executeQuery();
        setpremiumstate.next();
        String premium = setpremiumstate.getString(1);
        if (result.get() == ButtonType.OK){
            if(!(premium.equals("1"))) {
                if (Double.parseDouble(balance) >= 100) {
                    PreparedStatement setpremium = lastc.prepareStatement("UPDATE passengers SET Premium = '1'" +"WHERE UserName = "+"'"+username+"'");
                    setpremium.executeUpdate();
                    String mybalance = setbal.getString(1);
                    Double nextbalance = Double.parseDouble(mybalance) - 100;
                    PreparedStatement setbalance = lastc.prepareStatement("UPDATE passengers SET Balance = " + "'" + String.valueOf(nextbalance) + "'"+" WHERE UserName = "+"'"+username+"'");
                    setbalance.executeUpdate();
                    lastc.close();
                    Method.notification(Alert.AlertType.INFORMATION, "Succesful Operation!", "Congratulations", "Now You Are On Premium Version");

                }
                else{
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Error");
                    alert1.setHeaderText("Failure Operation");
                    alert1.setContentText("Ooops, Insuddicient Availability");
                    alert1.showAndWait();
                    lastc.close();
                }
            }
            else{
                Method.notification(Alert.AlertType.ERROR, "Error!", "Premium Already", "You Are On Premium Version!");
            }
        } else {

        }

    }
}