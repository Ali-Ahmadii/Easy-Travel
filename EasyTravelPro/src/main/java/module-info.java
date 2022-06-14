module com.example.easytravelpro {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.easytravelpro to javafx.fxml;
    exports com.example.easytravelpro;
}