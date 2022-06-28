package com.example.easytravelpro;

import javafx.scene.control.Alert;

public class TopPanel {

    public void search() {
        if (SignInUp.enteredToApp)
            System.out.println("this is result of your search");
        else
            Method.notification(Alert.AlertType.INFORMATION, "Account",
                    "You haven't logged in",
                    "to use our Application you must Sign up/in first");
    }

}
