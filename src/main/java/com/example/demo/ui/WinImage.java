package com.example.demo.ui;

import com.example.demo.AssetFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WinImage extends ImageView {
	
	private static final String IMAGE_NAME = "youwin.png";
	private static final int HEIGHT = 500;
	private static final int WIDTH = 600;
	
	public WinImage(double xPosition, double yPosition, AssetFactory assetFactory) {
		this.setImage(assetFactory.createImage(IMAGE_NAME));
		this.setVisible(false);
		this.setFitHeight(HEIGHT);
		this.setFitWidth(WIDTH);
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
	}
}
