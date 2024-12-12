package com.example.demo.screen.level.hud;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Represents an overlay displayed at the end of the game, offering options
 * to restart the game or return to the main menu.
 */
public abstract class GameEndOverlay {
    private final Runnable onRestart;
    private final Runnable onMainMenu;
    private final VBox view;

    /**
     * Creates a new {@code GameEndOverlay} instance.
     *
     * @param statusImage an {@link Image} representing the game end status (e.g., win or loss).
     * @param onRestart a {@link Runnable} to execute when the restart button is clicked.
     * @param onMainMenu a {@link Runnable} to execute when the main menu button is clicked.
     */
    public GameEndOverlay(Image statusImage, Runnable onRestart, Runnable onMainMenu) {
        this.onRestart = onRestart;
        this.onMainMenu = onMainMenu;
        this.view = new VBox();
        initializeView(statusImage);
    }


    /**
     * @return a {@code Region} representing the overlay.
     */
    public Region getView() {
        return view;
    }

    /**
     * Initializes the visual elements of the overlay, including the status image
     * and buttons for user interaction.
     *
     * @param statusImage an {@code Image} to display in the overlay.
     */
    private void initializeView(Image statusImage) {
        view.setAlignment(Pos.CENTER);
        view.setSpacing(5);

        ImageView statusImageView = new ImageView(statusImage);
        statusImageView.setFitHeight(500);
        statusImageView.setPreserveRatio(true);

        Button restartButton = new Button("Restart");
        restartButton.setOnAction(e -> onRestart.run());
        Button mainMenuButton = new Button("Main Menu");
        mainMenuButton.setOnAction(e -> onMainMenu.run());

        view.getChildren().addAll(statusImageView, restartButton, mainMenuButton);
    }
}
