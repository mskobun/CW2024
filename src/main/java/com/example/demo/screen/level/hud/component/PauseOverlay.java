package com.example.demo.screen.level.hud.component;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * Overlay shown when the game is paused.
 */
public class PauseOverlay implements HUDComponent {
    private final StackPane view;
    private final Runnable onResume;
    private final Runnable onMainMenu;

    /**
     * Creates a new {@code PauseOverlay} instance.
     *
     * @param onResume   the action to perform when the "Resume" button is clicked
     * @param onMainMenu the action to perform when the "Main Menu" button is clicked
     */
    public PauseOverlay(Runnable onResume, Runnable onMainMenu) {
        view = new StackPane();
        this.onResume = onResume;
        this.onMainMenu = onMainMenu;
        initializeView();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Region getView() {
        return view;
    }

    /**
     * Initializes the visual structure of the pause overlay, including its layout and buttons.
     */
    private void initializeView() {
        final double BUTTON_BOX_SPACING = 5;
        // Half-transparent black background
        view.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.5), CornerRadii.EMPTY, null)));

        VBox buttonBox = new VBox();
        buttonBox.setSpacing(BUTTON_BOX_SPACING);
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
