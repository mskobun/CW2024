package com.example.demo.entities.backgrounds;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * An implementation of {@link Background} representing a static image background.
 * <p>
 * This class creates an {@link ImageView} with a specified image and adjusts its size to
 * fit the provided screen dimensions. The background remains static and does not change or
 * update over time.
 * </p>
 */
public class StaticImageBackground extends Background {
    /**
     * Constructs a {@link StaticImageBackground} with the given image and screen dimensions.
     * The image will be resized to fit the specified width and height of the screen.
     *
     * @param image        the image to be displayed as the background
     * @param screenHeight the height of the screen to which the background should be scaled
     * @param screenWidth  the width of the screen to which the background should be scaled
     */
    public StaticImageBackground(final Image image, final double screenHeight, final double screenWidth) {
        ImageView view = new ImageView(image);
        view.setFitHeight(screenHeight);
        view.setFitWidth(screenWidth);
        view.setLayoutX(0);
        view.setLayoutY(0);
        setView(view);
    }

    /**
     * Updates the background.
     * In this class, the background is static, so no update occurs.
     *
     * @param timeDelta the time elapsed since the last update
     */
    @Override
    public void update(final double timeDelta) {
        // Do nothing
    }
}
