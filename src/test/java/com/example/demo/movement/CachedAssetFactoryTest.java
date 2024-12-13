package com.example.demo.movement;

import com.example.demo.AssetFactory;
import com.example.demo.CachedAssetFactory;
import javafx.application.Platform;
import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;


public class CachedAssetFactoryTest {
    @BeforeAll
    public static void initJavaFX() {
        // Initialize JavaFX runtime
        Platform.startup(() -> {});
    }
    @Test
    void reusesTheSameImage() {
        AssetFactory assetFactory = new CachedAssetFactory("/");
        Image image1 = assetFactory.createImage("testImage.png");
        Image image2 = assetFactory.createImage("testImage.png");
        assertSame(image1, image2);
    }
}
