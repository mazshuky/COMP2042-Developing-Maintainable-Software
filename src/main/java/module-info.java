module com.example.demo {
    requires java.desktop;
    requires java.logging;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo.controller;
}