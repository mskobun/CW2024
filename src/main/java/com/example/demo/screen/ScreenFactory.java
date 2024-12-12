package com.example.demo.screen;

import com.example.demo.AssetFactory;
import com.example.demo.screen.level.EndlessModeLevel;
import com.example.demo.screen.level.LevelOne;
import com.example.demo.screen.level.LevelTwo;

/**
 * Factory responsible for creating different types of screens.
 * It uses a {@link ScreenType} to determine the appropriate screen to instantiate.
 */

public class ScreenFactory {
    private final ScreenNavigator navigator;
    private final AssetFactory assetFactory;

    /**
     * Constructs a new {@code ScreenFactory}.
     *
     * @param navigator    the {@code ScreenNavigator} used for navigating between screens
     * @param assetFactory the {@code AssetFactory} used to load assets for the screens
     */
    public ScreenFactory(final ScreenNavigator navigator, final AssetFactory assetFactory) {
        this.navigator = navigator;
        this.assetFactory = assetFactory;
    }

    /**
     * Creates and returns a new {@link AbstractScreen} based on the specified screen type.
     *
     * @param screenType    the {@code ScreenType} indicating which screen to create
     * @param screenHeight  the height of the screen
     * @param screenWidth   the width of the screen
     * @return the created {@code AbstractScreen} for the specified screen type
     */
    public AbstractScreen createScreen(
            ScreenType screenType,
            final double screenHeight,
            final double screenWidth
    ) {
        switch (screenType) {
            case MAIN_MENU -> {
                return new MainMenuScreen(screenHeight, screenWidth, navigator, assetFactory);
            }
            case LEVEL_ONE -> {
                return new LevelOne(screenHeight, screenWidth, navigator, assetFactory);
            }
            case LEVEL_TWO -> {
                return new LevelTwo(screenHeight, screenWidth, navigator, assetFactory);
            }
            case LEVEL_ENDLESS_MODE -> {
                return new EndlessModeLevel(screenHeight, screenWidth, navigator, assetFactory);
            }
        }
        return null;
    }
}
