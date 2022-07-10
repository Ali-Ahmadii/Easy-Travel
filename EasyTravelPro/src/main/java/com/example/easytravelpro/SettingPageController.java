package com.example.easytravelpro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.poi.hssf.record.PrecisionRecord;
import org.apache.poi.hssf.record.chart.DatRecord;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class SettingPageController {
    static final String DB_URL = "jdbc:mysql://localhost:3306/easy_travel";
    static final String USER = "root";
    static final String PASS = "integral4560sini";
    @FXML
    private Button darkMode, lightMode;


    public void setDarkMode(ActionEvent event) throws IOException, SQLException {
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        PreparedStatement premiumstate = con.prepareStatement("SELECT Premium FROM passengers WHERE UserName = " + "'" + RightPanel.Name + "'");
        ResultSet setpremium = premiumstate.executeQuery();
        setpremium.next();
        String x = setpremium.getString(1);
        if (x.equals("1")) {
            if (!(Main.isDark)) {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Frame.fxml")));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                scene.getStylesheets().add(Main.cssStyleDark);
                scene.setFill(Color.TRANSPARENT);
                Main.isDark = true;
            } else
                Method.notification(Alert.AlertType.ERROR, "Error!", "dark mode is on",
                        "it's already on dark mode you can set light mode");
        } else {
            Method.notification(Alert.AlertType.ERROR, "Error!", "Not VIP", "For This Action You Must Buy Premium Version");
        }
        con.close();
    }

    public void setLightMode(ActionEvent event) throws IOException {
        if (Main.isDark) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Frame.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            scene.getStylesheets().add(Main.cssStyleLight);
            scene.setFill(Color.TRANSPARENT);
            Main.isDark = false;
        } else
            Method.notification(Alert.AlertType.ERROR, "Error!", "light mode is on",
                    "it's already on light mode you can set dark mode");
    }
}
