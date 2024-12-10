package com.example.demo.ui;

import com.example.demo.AssetFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameOverImage extends ImageView {
	
	private static final String IMAGE_NAME = "gameover.png";

	public GameOverImage(double xPosition, double yPosition, AssetFactory assetFactory) {
		setImage(assetFactory.createImage(IMAGE_NAME));
		setLayoutX(xPosition);
		setLayoutY(yPosition);
	}

}
