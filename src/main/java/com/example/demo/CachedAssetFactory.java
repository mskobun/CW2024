package com.example.demo;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;


/**
 * An {@link AssetFactory} implementation that caches {@link Image} objects to optimize resource loading.
 */
public class CachedAssetFactory implements AssetFactory {
    /**
     * A cache for storing previously loaded images, with the image name as the key.
     */
    private final Map<String, Image> imageCache = new HashMap<>();
    private final String imagePrefix;

    /**
     * Constructs a new {@code CachedAssetFactory} with the specified image prefix.
     *
     * @param imagePrefix the prefix to prepend to image names when locating resources.
     */
    public CachedAssetFactory(String imagePrefix) {
       this.imagePrefix = imagePrefix;
    }

    /**
     * Retrieves or creates an {@link Image} based on the given image name.
     *
     * <p>If the image has already been loaded previously, it is retrieved from the cache.
     * Otherwise, it is loaded from the specified resource path and added to the cache.</p>
     *
     * @param imageName the name of the image to create, typically representing a file name or resource path.
     * @return the {@link Image} object corresponding to the given name.
     */
    @Override
    public Image createImage(String imageName) {
        return imageCache.computeIfAbsent(imageName, this::loadImage);
    }

    private Image loadImage(String imageName) {
        return new Image(getClass().getResource(imagePrefix + imageName).toExternalForm());
    }
}
