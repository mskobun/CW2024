package com.example.demo;

import javafx.scene.image.Image;

/**
 * An interface for creating {@link Image} instances.
 */
public interface AssetFactory {
    /**
     * Creates an {@link Image} instance for the given image name.
     *
     * @param imageName the name of the image to create, typically representing
     *                  a file name or resource path.
     * @return the {@link Image} object corresponding to the given name.
     */
    Image createImage(String imageName);
}
