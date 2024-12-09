package com.example.demo.screen.level;

import com.example.demo.screen.ScreenNavigator;
import com.example.demo.screen.ScreenType;

public class LevelFactory {
    private final ScreenNavigator navigator;

   public LevelFactory(ScreenNavigator navigator) {
       this.navigator = navigator;
   }
    public AbstractLevel createLevel(ScreenType screenType, double screenHeight, double screenWidth) {
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
