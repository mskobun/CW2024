module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.demo.level to javafx.fxml;
    opens com.example.demo.entities to javafx.fxml;
    opens com.example.demo.ui to javafx.fxml;

    exports com.example.demo.controller;
    exports com.example.demo.level;
    exports com.example.demo.entities;
    exports com.example.demo.ui;
}