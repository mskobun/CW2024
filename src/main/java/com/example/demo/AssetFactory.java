package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public interface AssetFactory {
    public Image createImage(String imageName);
}
