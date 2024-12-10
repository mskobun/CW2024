package com.example.demo.ui;

import com.example.demo.AssetFactory;
import com.example.demo.entities.HealthObservable;
import javafx.scene.Group;
import javafx.scene.layout.Region;

public class LevelView {
	
	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 25;
	private static final double HEALTH_PROGRESS_BAR_X_POSITION = 450;
	private static final double HEALTH_PROGRESS_BAR_Y_POSITION = 610;
	private static final double HEALTH_PROGRESS_BAR_WIDTH = 200;
	private static final int WIN_IMAGE_X_POSITION = 355;
	private static final int WIN_IMAGE_Y_POSITION = 175;
	private static final int LOSS_SCREEN_X_POSITION = -160;
	private static final int LOSS_SCREEN_Y_POSISITION = -375;
	private final Group root;
	private final WinImage winImage;
	private final GameOverImage gameOverImage;
	private final AssetFactory assetFactory;
	private HeartDisplay heartDisplay;
	private HealthProgressBar healthProgressBar;
	
	public LevelView(Group root, AssetFactory assetFactory) {
		this.root = root;
		this.assetFactory = assetFactory;
		this.winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION, assetFactory);
		this.gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSISITION, assetFactory);
	}
	
	public void showHeartDisplay(HealthObservable healthObservable) {
		heartDisplay  = new HeartDisplay(healthObservable);
		Region view = heartDisplay.getView();
		view.setLayoutX(HEART_DISPLAY_X_POSITION);
		view.setLayoutY(HEART_DISPLAY_Y_POSITION);
		root.getChildren().add(view);
	}

	public void hideHeartDisplay() {
		root.getChildren().remove(heartDisplay.getView());
		heartDisplay = null;
	}

	public void showWinImage() {
		root.getChildren().add(winImage);
	}
	
	public void showGameOverImage() {
		root.getChildren().add(gameOverImage);
	}
	
	public void showHealthProgressBar(HealthObservable healthObservable, String labelText) {
		this.healthProgressBar = new HealthProgressBar(HEALTH_PROGRESS_BAR_WIDTH, healthObservable, labelText);
		Region view = this.healthProgressBar.getView();
		view.setLayoutX(HEALTH_PROGRESS_BAR_X_POSITION);
		view.setLayoutY(HEALTH_PROGRESS_BAR_Y_POSITION);
		root.getChildren().add(view);
	}

	public void hideHealthProgressBar() {
		root.getChildren().remove(this.healthProgressBar.getView());
		this.healthProgressBar = null;
	}
}
