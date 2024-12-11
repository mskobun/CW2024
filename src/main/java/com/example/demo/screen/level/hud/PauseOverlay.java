package com.example.demo.screen.level.hud;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class PauseOverlay {
    private final StackPane view;
    private final Runnable onResume;
    private final Runnable onMainMenu;

    public PauseOverlay(Runnable onResume, Runnable onMainMenu) {
        view = new StackPane();
        this.onResume = onResume;
        this.onMainMenu = onMainMenu;
        initializeView();
    }

    public Region getView() {
        return view;
    }

    private void initializeView() {
        // Half-transparent black background
        view.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.5), CornerRadii.EMPTY, null)));

        VBox buttonBox = new VBox();
        buttonBox.setSpacing(5);
        buttonBox.setAlignment(Pos.CENTER);
        StackPane.setAlignment(buttonBox, Pos.CENTER);
        view.getChildren().add(buttonBox);

        Button resumeButton = new Button("Resume");
        resumeButton.setOnAction(e -> onResume.run());
        Button mainMenuButton = new Button("Main Menu");
        mainMenuButton.setOnAction(e -> onMainMenu.run());

        buttonBox.getChildren().addAll(resumeButton, mainMenuButton);
    }
}
