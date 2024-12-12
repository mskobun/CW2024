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
    private final double SLIDE_VELOCITY = -100;
    private final Pane root;
    private final Image image;
    private final double screenHeight;
    private final double screenWidth;
    private final List<ImageView> imageViews;

    /**
     * @param image backgound image
     * @param screenHeight the height of the screen to which the background should be scaled
     * @param screenWidth  the width of the screen to which the background should be scaled
     */
    public ScrollingImageBackground(final Image image,
                                    final double screenHeight,
                                    final double screenWidth) {
        // Use Pane as root since we need absolute positioning
        this.root = new Pane();
        setView(root);
        this.image = image;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        imageViews = new ArrayList<>();

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
     *
     * @param timeDelta time elapsed since last update, in seconds
     */
    @Override
    public void update(final double timeDelta) {
        double nextMoveDistance = SLIDE_VELOCITY * timeDelta;
        maybeCreateNewImage(nextMoveDistance);
        updateImagesPosition(nextMoveDistance);
        removePastLeftEdge();
    }

    /**
     * If moving the last (and therefore rightmost) imageView is going to cause a hole to appear,
     * add a new imageView to fill the hole.
     *
     * @param nextMoveDistance distance the image will travel this frame
     */
    private void maybeCreateNewImage(final double nextMoveDistance) {
        ImageView lastImageView = imageViews.getLast();
        if (imageEndingThisFrame(lastImageView, nextMoveDistance)) {
            ImageView newImageView = createImageView();
            newImageView.setLayoutX(lastImageView.getBoundsInParent().getMaxX());
            newImageView.setLayoutY(0);
            imageViews.add(newImageView);
            root.getChildren().add(newImageView);
        }
    }

    /**
     * @param imageView the {@code ImageView}
     * @param nextMoveDistance distance the image will travel this frame
     * @return true if rightmost point of imageView will be past right edge,
     * once next move is made.
     */
    private boolean imageEndingThisFrame(final ImageView imageView,
                                         final double nextMoveDistance) {
        double maxX = imageView.getBoundsInParent().getMaxX();
        return (maxX >= screenWidth && (maxX + nextMoveDistance) < screenWidth);
    }

    /**
     * Moves all imageViews by {@param moveDistance}.
     *
     * @param moveDistance Distance to move the images by
     */
    private void updateImagesPosition(final double moveDistance) {
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
     * @param imageView the {@code ImageView}
     * @return true if rightmost point of imageView is past left edge.
     */
    private boolean isFullyPastLeftEdge(final ImageView imageView) {
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