module com.example.demo {
    requires java.desktop;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo.controller;
}