package com.example.demo.screen;

import com.example.demo.AssetFactory;
import com.example.demo.screen.level.LevelOne;
import com.example.demo.screen.level.LevelTwo;

public class ScreenFactory {
    private final ScreenNavigator navigator;
    private final AssetFactory assetFactory;

    public ScreenFactory(ScreenNavigator navigator, AssetFactory assetFactory) {
        this.navigator = navigator;
        this.assetFactory = assetFactory;
    }

    public AbstractScreen createScreen(ScreenType screenType, double screenHeight, double screenWidth) {
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
        }
        return null;
    }
}
