package com.example.demo.screen.level.hud;

import com.example.demo.AssetFactory;
import com.example.demo.entities.HealthObservable;
import com.example.demo.screen.level.hud.component.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;


/**
 * Manages the Heads-Up Display (HUD) components for the game level.
 * Provides methods to display or hide HUD elements.
 */
public class LevelHUD {
    private static final double HEALTH_PROGRESS_BAR_WIDTH = 200;
    private final StackPane root;
    private WinOverlay winOverlay;
    private LoseOverlay loseOverlay;
    private final AssetFactory assetFactory;
    private HeartDisplay heartDisplay;
    private HealthProgressBar healthProgressBar;
    private PauseOverlay pauseOverlay;

    /**
     * Constructs a new LevelHUD.
     *
     * @param root the root {@link StackPane} for the HUD
     * @param assetFactory the factory for creating assets
     */
    public LevelHUD(final StackPane root, final AssetFactory assetFactory) {
        this.root = root;
        this.assetFactory = assetFactory;
    }

    /**
     * Displays the heart display HUD showing source's health in heart icons.
     *
     * @param healthObservable the health source to observe
     */
    public void showHeartDisplay(final HealthObservable healthObservable) {
        heartDisplay = new HeartDisplay(healthObservable);
        addHUDComponent(heartDisplay, Pos.TOP_LEFT, new Insets(25, 0, 0, 5));
    }

    public void hideHeartDisplay() {
        if (heartDisplay != null) {
            removeHUDComponent(heartDisplay);
            heartDisplay = null;
        }
    }


    /**
     * Displays the win overlay HUD.
     *
     * @param onRestart callback for restarting the game
     * @param onMainMenu callback for returning to the main menu
     */
    public void showWinOverlay(final Runnable onRestart, final Runnable onMainMenu) {
        winOverlay = new WinOverlay(assetFactory, onRestart, onMainMenu);
        addHUDComponent(winOverlay, Pos.CENTER);
    }

    /**
     * Displays the lose overlay HUD.
     *
     * @param onRestart callback for restarting the game
     * @param onMainMenu callback for returning to the main menu
     */
    public void showLoseOverlay(final Runnable onRestart, final Runnable onMainMenu) {
        loseOverlay = new LoseOverlay(assetFactory, onRestart, onMainMenu);
        addHUDComponent(loseOverlay, Pos.CENTER);
    }

    /**
     * Displays the health progress bar HUD, showing source's health as a progress bar.
     *
     * @param healthObservable the health source to observe
     * @param labelText the label text to display alongside the bar.
     *                  If {@code null}, no text will be displayed.
     */
    public void showHealthProgressBar(final HealthObservable healthObservable, final String labelText) {
        final Insets MARGINS = new Insets(0, 0, 20, 0);

        healthProgressBar = new HealthProgressBar(HEALTH_PROGRESS_BAR_WIDTH, healthObservable, labelText);
        addHUDComponent(healthProgressBar, Pos.BOTTOM_CENTER, MARGINS);
    }

    /**
     * Hides the health progress bar HUD.
     */
    public void hideHealthProgressBar() {
        if (healthProgressBar != null) {
            removeHUDComponent(healthProgressBar);
            healthProgressBar = null;
        }
    }


    /**
     * Displays the pause overlay HUD.
     *
     * @param onResume callback for resuming the game
     * @param onMainMenu callback for returning to the main menu
     */
    public void showPauseOverlay(final Runnable onResume, final Runnable onMainMenu) {
        pauseOverlay = new PauseOverlay(onResume, onMainMenu);
        addHUDComponent(pauseOverlay, Pos.CENTER);
    }

    /**
     * Hides the pause overlay HUD.
     */
    public void hidePauseOverlay() {
        if (pauseOverlay != null) {
            removeHUDComponent(pauseOverlay);
            pauseOverlay = null;
        }
    }

    /**
     * Adds a new HUD component to the screen.
     * @param component component to be added
     * @param alignment component alignment in the root container
     * @param margins component margins in the root container
     */
    private void addHUDComponent(final HUDComponent component, final Pos alignment, final Insets margins) {
        StackPane.setAlignment(component.getView(), alignment);
        StackPane.setMargin(component.getView(), margins);
        root.getChildren().add(component.getView());
    }

    /**
     * Like {@link #addHUDComponent(HUDComponent, Pos, Insets)}, but margins are assumed to be zero.
     */
    private void addHUDComponent(final HUDComponent component, final Pos alignment) {
        final Insets ZERO_MARGINS = new Insets(0, 0, 0, 0);
        addHUDComponent(component, alignment, ZERO_MARGINS);
    }

    /**
     * Removes a HUD component from the screen.
     * @param component component to be removed
     */
    private void removeHUDComponent(final HUDComponent component) {
        root.getChildren().remove(component.getView());
    }
}
