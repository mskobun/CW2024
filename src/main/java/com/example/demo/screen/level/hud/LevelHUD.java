package com.example.demo.screen.level.hud;

import com.example.demo.AssetFactory;
import com.example.demo.entities.HealthObservable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class LevelHUD {
    private static final double HEALTH_PROGRESS_BAR_WIDTH = 200;
    private final StackPane root;
    private WinOverlay winOverlay;
    private LoseOverlay loseOverlay;
    private final AssetFactory assetFactory;
    private HeartDisplay heartDisplay;
    private HealthProgressBar healthProgressBar;
    private PauseOverlay pauseOverlay;

    public LevelHUD(final StackPane root, final AssetFactory assetFactory) {
        this.root = root;
        this.assetFactory = assetFactory;
    }

    public void showHeartDisplay(final HealthObservable healthObservable) {
        heartDisplay = new HeartDisplay(healthObservable);
        Region view = heartDisplay.getView();
        StackPane.setAlignment(view, Pos.TOP_LEFT);
        StackPane.setMargin(view, new Insets(25, 0, 0, 5));
        root.getChildren().add(view);
    }

    public void hideHeartDisplay() {
        root.getChildren().remove(heartDisplay.getView());
        heartDisplay = null;
    }

    public void showWinOverlay(final Runnable onRestart, final Runnable onMainMenu) {
        winOverlay = new WinOverlay(assetFactory, onRestart, onMainMenu);
        root.getChildren().add(winOverlay.getView());
    }

    public void showLoseOverlay(final Runnable onRestart, final Runnable onMainMenu) {
        loseOverlay = new LoseOverlay(assetFactory, onRestart, onMainMenu);
        root.getChildren().add(loseOverlay.getView());
    }

    public void showHealthProgressBar(final HealthObservable healthObservable, final String labelText) {
        this.healthProgressBar = new HealthProgressBar(HEALTH_PROGRESS_BAR_WIDTH, healthObservable, labelText);
        Region view = this.healthProgressBar.getView();
        StackPane.setAlignment(view, Pos.BOTTOM_CENTER);
        StackPane.setMargin(view, new Insets(0, 0, 20, 0));
        root.getChildren().add(view);
    }

    public void hideHealthProgressBar() {
        root.getChildren().remove(this.healthProgressBar.getView());
        this.healthProgressBar = null;
    }

    public void showPauseOverlay(final Runnable onResume, final Runnable onMainMenu) {
        pauseOverlay = new PauseOverlay(onResume, onMainMenu);
        StackPane.setAlignment(pauseOverlay.getView(), Pos.CENTER);
        root.getChildren().add(pauseOverlay.getView());
    }

    public void hidePauseOverlay() {
        root.getChildren().remove(pauseOverlay.getView());
        pauseOverlay = null;
    }
}
