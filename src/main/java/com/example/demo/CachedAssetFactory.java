package com.example.demo;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class CachedAssetFactory implements AssetFactory {
    private final Map<String, Image> imageCache = new HashMap<>();
    private final String imagePrefix;

    public CachedAssetFactory(String imagePrefix) {
       this.imagePrefix = imagePrefix;
    }
    @Override
    public Image createImage(String imageName) {
        return imageCache.computeIfAbsent(imageName, this::loadImage);
    }

    private Image loadImage(String imageName) {
        return new Image(getClass().getResource(imagePrefix + imageName).toExternalForm());
    }
}
