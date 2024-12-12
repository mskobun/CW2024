package com.example.demo.screen.level.hud;

import com.example.demo.AssetFactory;


/**
 * Represents an overlay displayed when the player wins the game.
 * This overlay provides options to restart the game or return to the main menu.
 */
public class WinOverlay extends GameEndOverlay {
    /**
     * Creates a new {@code WinOverlay} instance.
     *
     * @param assetFactory an {@link AssetFactory} used to load the win image.
     * @param onRestart a {@link Runnable} to execute when the restart button is clicked.
     * @param onMainMenu a {@link Runnable} to execute when the main menu button is clicked.
     */
    public WinOverlay(AssetFactory assetFactory, Runnable onRestart, Runnable onMainMenu) {
        super(assetFactory.createImage("youwin.png"), onRestart, onMainMenu);
    }
}
