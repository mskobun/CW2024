package com.example.demo.screen.level.hud;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class PauseOverlay {
    private final VBox view;
    private final Runnable onResume;
    private final Runnable onMainMenu;

    public PauseOverlay(Runnable onResume, Runnable onMainMenu) {
        view = new VBox();
        this.onResume = onResume;
        this.onMainMenu = onMainMenu;
        initializeView();
    }

    public Region getView() {
        return view;
    }

    private void initializeView() {
        view.setSpacing(5);
        view.setAlignment(Pos.CENTER);

        Button resumeButton = new Button("Resume");
        resumeButton.setOnAction(e -> onResume.run());
        Button mainMenuButton = new Button("Main Menu");
        mainMenuButton.setOnAction(e -> onMainMenu.run());

        view.getChildren().addAll(resumeButton, mainMenuButton);
    }
}
