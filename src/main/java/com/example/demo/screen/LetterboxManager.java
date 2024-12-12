package com.example.demo.screen;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;

/**
 * Manages the letterboxing for a JavaFX {@link Scene}.
 * Ensures that the content of the scene maintains a specified aspect ratio by dynamically
 * adjusting its scaling and padding when the scene's dimensions change.
 * <p>
 * The code has been adapted from:<a href="https://stackoverflow.com/questions/16606162/javafx-fullscreen-resizing-elements-based-upon-screen-size">this StackOverflow answer</a>.
 */
public class LetterboxManager implements ChangeListener<Number> {
    private final Scene scene;
    private final double ratio;
    private final double initHeight;
    private final double initWidth;
    private final Pane contentPane;
    private static final String LETTERBOX_STYLE = "-fx-background-color: black;";

    /**
     * Constructs a {@code LetterboxManager} instance.
     *
     * @param scene         the {@link Scene} to be managed.
     * @param ratio         the aspect ratio (width/height) to maintain.
     * @param initHeight    the initial height of the content area.
     * @param initWidth     the initial width of the content area.
     * @param letterboxPane the {@link StackPane} serving as the content container with letterbox styling.
     */
    public LetterboxManager(
            final Scene scene,
            final double ratio,
            final double initHeight,
            final double initWidth,
            final StackPane letterboxPane
    ) {
        this.scene = scene;
        this.ratio = ratio;
        this.initHeight = initHeight;
        this.initWidth = initWidth;
        this.contentPane = letterboxPane;
    }

    /**
     * Creates a new {@link Scene} configured for letterboxing.
     *
     * @param root       the root {@link Pane} of the content to be displayed.
     * @param initHeight the initial height of the content area.
     * @param initWidth  the initial width of the content area.
     * @return a {@link Scene} with letterboxing applied.
     */
    public static Scene letterboxScene(final Pane root, final double initHeight, final double initWidth) {
        root.setMaxHeight(initHeight);
        root.setMaxWidth(initWidth);
        // Clip root so out of bounds nodes won't be rendered on letterbox edges
        root.setClip(new Rectangle(initWidth, initHeight));

        StackPane letterboxPane = new StackPane(root);
        letterboxPane.setStyle(LETTERBOX_STYLE);

        Scene scene = new Scene(new Group(letterboxPane));

        double ratio = initWidth / initHeight;
        LetterboxManager letterboxManager = new LetterboxManager(scene, ratio, initHeight, initWidth, letterboxPane);
        scene.widthProperty().addListener(letterboxManager);
        scene.heightProperty().addListener(letterboxManager);

        return scene;
    }

    /**
     * Updates the scale of the content based on the current dimensions of the {@link Scene}
     * while maintaining the specified aspect ratio.
     */
    private void updateScale() {
        double newWidth = scene.getWidth();
        double newHeight = scene.getHeight();
        double scaleFactor =
                newWidth / newHeight > ratio
                        ? newHeight / initHeight
                        : newWidth / initWidth;

        if (scaleFactor != 0) {
            Scale scale = new Scale(scaleFactor, scaleFactor);
            scale.setPivotX(0);
            scale.setPivotY(0);
            scene.getRoot().getTransforms().setAll(scale);
            contentPane.setPrefWidth(newWidth / scaleFactor);
            contentPane.setPrefHeight(newHeight / scaleFactor);
        }
    }

    /**
     * Handles changes in the scene's dimensions by invoking {@link #updateScale()}.
     * The parameters are unused.
     *
     * @param observableValue the observable value being monitored.
     * @param oldValue        the previous value.
     * @param newValue        the new value.
     */
    @Override
    public void changed(final ObservableValue<? extends Number> observableValue, final Number oldValue, final Number newValue) {
        updateScale();
    }
}
