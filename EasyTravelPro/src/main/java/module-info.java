module com.example.easytravelpro {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.easytravelpro to javafx.fxml;
    exports com.example.easytravelpro;
}