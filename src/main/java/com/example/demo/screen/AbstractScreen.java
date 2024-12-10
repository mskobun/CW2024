package com.example.demo.screen;

import com.example.demo.AssetFactory;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 * Sets up the scene, manages the game loop, navigates to a different screen.
 * <p>
 * Subclasses must implement the {@link #updateScene(double)} method to define screen-specific updates.
 * {@link #dispose()} must be called before disposing of the object, due to manually-managed resources.
 */
public abstract class AbstractScreen {
    private final Pane contentRoot;
    private final Scene scene;
    private final ScreenNavigator screenNavigator;
    private final ScreenLoop screenLoop;
    private final AssetFactory assetFactory;
    // Call updateScene every targetDelta seconds
    private static final double TARGET_DELTA = 0.016;

    /**
     * Gets the content root {@link Pane} for this screen. This is where all the content of the screen
     * should be added. The root pane is part of a letterboxed layout that ensures the aspect
     * ratio is maintained regardless of the window size.
     *
     * @return the content root pane for this screen.
     */
    protected Pane getContentRoot() {
        return contentRoot;
    }

    public Scene getScene() {
        return scene;
    }

    public AssetFactory getAssetFactory() {
        return assetFactory;
    }

    /**
     * Constructs a new {@code AbstractScreen}.
     * <p>
     * Initializes the screen with a letterboxed {@link Scene} and sets up the game loop
     * for periodic updates. A {@link ScreenNavigator} is required to enable navigation
     * to other screens.
     *
     * @param screenHeight    the height of the screen's visible content area.
     * @param screenWidth     the width of the screen's visible content area.
     * @param screenNavigator the {@link ScreenNavigator} to handle screen transitions.
     * @param assetFactory    the {@link AssetFactory}.
     */
    public AbstractScreen(double screenHeight, double screenWidth, ScreenNavigator screenNavigator, AssetFactory assetFactory) {
        this.screenNavigator = screenNavigator;
        this.assetFactory = assetFactory;
        contentRoot = new Pane();
        scene = LetterboxManager.letterboxScene(contentRoot, screenHeight, screenWidth);
        this.screenLoop = new ScreenLoop(this, TARGET_DELTA);
    }

    /**
     * Starts the update loop for this screen. The loop periodically updates the screen
     * by invoking {@link #updateScene(double)}.
     * If this function was called, {@link #dispose()} has to be called before
     * disposing of the class. Doing otherwise will lead to a memory leak.
     */
    public void startLoop() {
        screenLoop.start();
    }

    /**
     * Stops the update loop for this screen, pausing periodic updates.
     */
    public void stopLoop() {
        screenLoop.stop();
    }

    /**
     * Dispose of manually-managed resources.
     * This function has to be called before disposing of the object.
     */
    public void dispose() {
        stopLoop();
    }

    /**
     * Transitions to a different screen.
     *
     * @param screen the {@link ScreenType} representing the target screen.
     */
    public void goToScreen(ScreenType screen) {
        dispose();
        screenNavigator.goToScreen(screen);
    }

    /**
     * Updates the screen's state. This method is invoked periodically by the game loop.
     * Subclasses must override this method to define screen-specific behavior during
     * each update cycle.
     *
     * @param timeDelta the time elapsed since the last update, in seconds.
     */
    public abstract void updateScene(double timeDelta);
}