package com.example.demo.entities;

import com.example.demo.AssetFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageActorNode extends ImageView {
    public ImageActorNode(AssetFactory assetFactory, String imageName, double imageHeight) {
        super();
        Image image = assetFactory.createImage(imageName);
        setImage(image);
        setFitHeight(imageHeight);
        setPreserveRatio(true);
    }
}
