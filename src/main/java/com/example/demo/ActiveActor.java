package com.example.demo;

import javafx.scene.image.*;

public abstract class ActiveActor extends ImageView {
	
	private static final String IMAGE_LOCATION = "/com/example/demo/images/";

	public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		//this.setImage(new Image(IMAGE_LOCATION + imageName));
		this.setImage(new Image(getClass().getResource(IMAGE_LOCATION + imageName).toExternalForm()));
		this.setLayoutX(initialXPos);
		this.setLayoutY(initialYPos);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true);
	}

	public abstract void updatePosition(int timeDelta);

	protected void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	protected void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}

	protected double calculateMovement(double movementPerSecond, int timeDelta) {
		return movementPerSecond * ((double) timeDelta / 1000.0);
	}
	protected double calculateProbability(double probabilityPerSecond, int timeDelta) {
		return probabilityPerSecond * ((double) timeDelta / 1000.0);
	}

	protected boolean evaluateProbability(double probabilityPerSecond, int timeDelta) {
		double adjustedProbability = calculateProbability(probabilityPerSecond, timeDelta);
		return Math.random() < adjustedProbability;
	}
}
