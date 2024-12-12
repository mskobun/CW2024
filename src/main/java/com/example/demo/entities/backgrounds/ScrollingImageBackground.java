package com.example.demo.entities.backgrounds;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of {@link Background} representing a repeating background, scrolling right to left at constant speed.
 * <p>
 * For best look, a seamless image should be used.
 */
public class ScrollingImageBackground extends Background {
    // XXX: This HAS to be negative due to the way repeating logic is implemented
    private final double SLIDE_VELOCITY = -300;
    private final Pane root;
    private final Image image;
    private final double screenHeight;
    private final double screenWidth;
    private final List<ImageView> imageViews;

    public ScrollingImageBackground(Image image, double screenHeight, double screenWidth) {
        // Use Pane as root since we need absolute positioning
        this.root = new Pane();
        setView(root);
        this.image = image;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.imageViews = new ArrayList<>();

        ImageView initialView = createImageView();
        initialView.setLayoutX(0);
        initialView.setLayoutY(0);

        imageViews.add(initialView);
        root.getChildren().add(initialView);
    }

    /**
     * Updates the background.
     * <p>
     * The strategy is:
     * 1. Move imageViews left based on velocity and timeDelta.
     * 2. If there is a hole on the right, add a new image to fill that hole.
     * 3. If some imageViews are completely past the left edge, remove them.
     * @param timeDelta time elapsed since last update, in seconds
     */
    @Override
    public void update(double timeDelta) {
        double nextMoveDistance = SLIDE_VELOCITY * timeDelta;
        maybeCreateNewImage(nextMoveDistance);
        updateImagesPosition(nextMoveDistance);
        removePastLeftEdge();
    }

    /**
     * If moving the last (and therefore rightmost) imageView is going to cause a hole to appear,
     * add a new imageView to fill the hole.
     * @param nextMoveDistance distance the image will travel this frame
     */
    private void maybeCreateNewImage(double nextMoveDistance) {
        ImageView lastImageView = imageViews.get(imageViews.size() - 1);
        if (imageEndingThisFrame(lastImageView, nextMoveDistance)) {
            ImageView newImageView = createImageView();
            newImageView.setLayoutX(lastImageView.getBoundsInParent().getMaxX());
            newImageView.setLayoutY(0);
            imageViews.add(newImageView);
            root.getChildren().add(newImageView);
        }
    }

    /**
     * Returns true if imageView's rightmost point will be past right edge, once next move is made.
     * @param nextMoveDistance distance the image will travel this frame
     * @return
     */
    private boolean imageEndingThisFrame(ImageView imageView, double nextMoveDistance) {
        double maxX = imageView.getBoundsInParent().getMaxX();
        return (maxX >= screenWidth && (maxX + nextMoveDistance) < screenWidth);
    }

    /**
     * Moves all imageViews by {@param moveDistance}
     * @param moveDistance Distance to move the images by
     */
    private void updateImagesPosition(double moveDistance) {
        imageViews.forEach(imageView ->
                imageView.setTranslateX(imageView.getTranslateX() + moveDistance)
                );
    }

    /**
     * Removes all imageViews that are fully past left edge.
     */
    private void removePastLeftEdge() {
        imageViews.removeIf(this::isFullyPastLeftEdge);
    }

    /**
     * @return True if rightmost point of imageView is past left edge.
     */
    private boolean isFullyPastLeftEdge(ImageView imageView) {
        double x = imageView.getLayoutX() + imageView.getTranslateX();
        return x < -imageView.getBoundsInParent().getWidth();
    }

    private ImageView createImageView() {
        ImageView view = new ImageView();
        view.setImage(image);
        view.setFitHeight(screenHeight);
        view.setFitWidth(screenWidth);
        return view;
    }
}