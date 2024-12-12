package com.example.demo.entities;

import com.example.demo.AssetFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ScaledImageView extends ImageView {
    public ScaledImageView(AssetFactory assetFactory, String imageName, double imageHeight) {
        super();
        Image image = assetFactory.createImage(imageName);
        setImage(image);
        setFitHeight(imageHeight);
        setPreserveRatio(true);
    }

    public ScaledImageView(AssetFactory assetFactory, String imageName, double imageHeight, double imageWidth) {
        super();
        Image image = assetFactory.createImage(imageName);
        setImage(image);
        setFitHeight(imageHeight);
        setFitWidth(imageWidth);
    }

}
