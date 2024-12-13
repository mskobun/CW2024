package com.example.demo.screen.level.hud;

import com.example.demo.AssetFactory;


/**
 * Represents an overlay displayed when the player loses the game.
 * This overlay provides options to restart the game or return to the main menu.
 */
public class LoseOverlay extends GameEndOverlay {
    /**
     * Creates a new {@code LoseOverlay} instance.
     *
     * @param assetFactory an {@code AssetFactory} used to load the loss image.
     * @param onRestart a {@code Runnable} to execute when the restart button is clicked.
     * @param onMainMenu a {@code Runnable} to execute when the main menu button is clicked.
     */
    public LoseOverlay(
            final AssetFactory assetFactory,
            final Runnable onRestart,
            final Runnable onMainMenu
    ) {
        super(assetFactory.createImage("gameover.png"), onRestart, onMainMenu);
    }
}