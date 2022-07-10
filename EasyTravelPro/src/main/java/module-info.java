module com.example.easytravelpro {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.poi.ooxml;


    opens com.example.easytravelpro to javafx.fxml;
    exports com.example.easytravelpro;
}