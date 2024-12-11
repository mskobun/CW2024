package com.example.demo.screen.level;

import com.example.demo.AssetFactory;
import com.example.demo.entities.ActiveActorDestructible;
import com.example.demo.screen.ScreenNavigator;
import com.example.demo.screen.ScreenType;

public class LevelOne extends AbstractLevel {

    private static final String BACKGROUND_IMAGE_NAME = "background1.jpg";
    private static final ScreenType NEXT_LEVEL = ScreenType.LEVEL_TWO;
    private static final int TOTAL_ENEMIES = 5;
    private static final int KILLS_TO_ADVANCE = 10;
    private static final double ENEMY_SPAWN_PROBABILITY = .20;
    private static final int PLAYER_INITIAL_HEALTH = 5;

    public LevelOne(double screenHeight, double screenWidth, ScreenNavigator screenNavigator, AssetFactory assetFactory) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, screenNavigator, assetFactory);
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (userHasReachedKillTarget())
            goToScreen(NEXT_LEVEL);
    }

    @Override
    protected void initializeFriendlyUnits() {
        addActor(getUser());
    }

    @Override
    protected void spawnEnemyUnits() {
        int currentNumberOfEnemies = getCurrentNumberOfEnemies();
        for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
            if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
                double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
                ActiveActorDestructible newEnemy = getActorFactory().createEnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
                addActor(newEnemy);
            }
        }
    }

    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
    }
}
