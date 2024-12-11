package com.example.demo.screen.level.hud;

import com.example.demo.AssetFactory;
import javafx.scene.image.ImageView;

public class WinImage extends ImageView {

    private static final String IMAGE_NAME = "youwin.png";
    private static final int HEIGHT = 500;
    private static final int WIDTH = 600;

    public WinImage(AssetFactory assetFactory) {
        this.setImage(assetFactory.createImage(IMAGE_NAME));
        this.setFitHeight(HEIGHT);
        this.setFitWidth(WIDTH);
    }
}
