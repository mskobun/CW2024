module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.demo.screen to javafx.fxml;
    opens com.example.demo.entities to javafx.fxml;

    exports com.example.demo.controller;
    exports com.example.demo.screen;
    exports com.example.demo.movement;
    exports com.example.demo.entities;
    exports com.example.demo.entities.backgrounds;
    exports com.example.demo.screen.level;
    opens com.example.demo.screen.level to javafx.fxml;
    exports com.example.demo.screen.level.manager;
    opens com.example.demo.screen.level.manager to javafx.fxml;
    exports com.example.demo;
    opens com.example.demo to javafx.fxml;
    opens com.example.demo.controller to javafx.fxml;
    exports com.example.demo.screen.level.hud;
    opens com.example.demo.screen.level.hud to javafx.fxml;
    exports com.example.demo.util;
    opens com.example.demo.util to javafx.fxml;
    exports com.example.demo.entities.projectiles;
    opens com.example.demo.entities.projectiles to javafx.fxml;
    exports com.example.demo.entities.planes;
    opens com.example.demo.entities.planes to javafx.fxml;
    exports com.example.demo.entities.shields;
    opens com.example.demo.entities.shields to javafx.fxml;
    exports com.example.demo.screen.level.hud.component;
    opens com.example.demo.screen.level.hud.component to javafx.fxml;
}