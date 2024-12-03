package com.example.demo.level;

public class LevelFactory {
    private final LevelNavigator navigator;

   public LevelFactory(LevelNavigator navigator) {
       this.navigator = navigator;
   }
    public AbstractLevel createLevel(LevelType levelType, double screenHeight, double screenWidth) {
        switch (levelType) {
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
