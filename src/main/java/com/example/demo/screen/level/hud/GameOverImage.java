package com.example.demo.screen.level.hud;

import com.example.demo.AssetFactory;
import com.example.demo.controller.Main;
import javafx.scene.image.ImageView;

public class GameOverImage extends ImageView {

    private static final String IMAGE_NAME = "gameover.png";

    public GameOverImage(AssetFactory assetFactory) {
        setImage(assetFactory.createImage(IMAGE_NAME));
        setFitHeight(Main.SCREEN_HEIGHT);
        setPreserveRatio(true);
    }

}
