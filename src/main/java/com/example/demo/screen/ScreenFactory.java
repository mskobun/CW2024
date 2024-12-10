package com.example.demo.screen;

import com.example.demo.screen.level.LevelOne;
import com.example.demo.screen.level.LevelTwo;

public class ScreenFactory {
    private final ScreenNavigator navigator;

   public ScreenFactory(ScreenNavigator navigator) {
       this.navigator = navigator;
   }
    public AbstractScreen createScreen(ScreenType screenType, double screenHeight, double screenWidth) {
        switch (screenType) {
            case LEVEL_ONE -> {
                return new LevelOne(screenHeight, screenWidth, navigator);
            }
            case LEVEL_TWO -> {
                return new LevelTwo(screenHeight, screenWidth, navigator);
            }
        }
        return null;
    }
}
