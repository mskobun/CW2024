package com.example.demo.ui;

import com.example.demo.AssetFactory;
import com.example.demo.controller.Main;
import com.example.demo.entities.HealthObservable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import java.util.Stack;

public class LevelView {
	private static final double HEALTH_PROGRESS_BAR_WIDTH = 200;
	private final StackPane root;
	private final WinImage winImage;
	private final GameOverImage gameOverImage;
	private final AssetFactory assetFactory;
	private HeartDisplay heartDisplay;
	private HealthProgressBar healthProgressBar;
	
	public LevelView(StackPane root, AssetFactory assetFactory) {
		this.root = root;
		this.assetFactory = assetFactory;
		this.winImage = new WinImage(assetFactory);
		this.gameOverImage = new GameOverImage(assetFactory);
	}
	
	public void showHeartDisplay(HealthObservable healthObservable) {
		heartDisplay  = new HeartDisplay(healthObservable);
		Region view = heartDisplay.getView();
		StackPane.setAlignment(view, Pos.TOP_LEFT);
		StackPane.setMargin(view, new Insets(25, 0, 0, 5));
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
		StackPane.setAlignment(view, Pos.BOTTOM_CENTER);
		StackPane.setMargin(view, new Insets(0, 0, 20, 0));
		root.getChildren().add(view);
	}

	public void hideHealthProgressBar() {
		root.getChildren().remove(this.healthProgressBar.getView());
		this.healthProgressBar = null;
	}
}
