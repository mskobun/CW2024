package com.example.demo.util;

import com.example.demo.AssetFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * A specialized {@link ImageView} for displaying images with scaled dimensions.
 * Provides constructors to create an image view with either a fixed height
 * (while preserving the aspect ratio) or both fixed height and width.
 */
public class ScaledImageView extends ImageView {

    /**
     * Creates a ScaledImageView with a specified image and height, preserving the image's aspect ratio.
     *
     * @param assetFactory the {@link AssetFactory} used to create the {@link Image}
     * @param imageName    the name of the image resource to be loaded
     * @param imageHeight  the desired height of the image view
     */
    public ScaledImageView(
            final AssetFactory assetFactory,
            final String imageName,
            final double imageHeight
    ) {
        super();
        Image image = assetFactory.createImage(imageName);
        setImage(image);
        setFitHeight(imageHeight);
        setPreserveRatio(true);
    }

    /**
     * Creates a ScaledImageView with a specified image, height, and width.
     *
     * @param assetFactory the {@link AssetFactory} used to create the {@link Image}
     * @param imageName    the name of the image resource to be loaded
     * @param imageHeight  the desired height of the image view
     * @param imageWidth   the desired width of the image view
     */
    public ScaledImageView(
            final AssetFactory assetFactory,
            final String imageName,
            final double imageHeight,
            final double imageWidth
    ) {
        super();
        Image image = assetFactory.createImage(imageName);
        setImage(image);
        setFitHeight(imageHeight);
        setFitWidth(imageWidth);
    }
}
