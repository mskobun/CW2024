package com.example.demo.screen.level;

import com.example.demo.AssetFactory;
import com.example.demo.entities.backgrounds.Background;
import com.example.demo.entities.backgrounds.StaticImageBackground;
import com.example.demo.entities.planes.Boss;
import com.example.demo.screen.ScreenNavigator;

public class LevelTwo extends AbstractLevel {

    private static final String BACKGROUND_IMAGE_NAME = "background2.jpg";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private final Boss boss;

    public LevelTwo(double screenHeight, double screenWidth, ScreenNavigator screenNavigator, AssetFactory assetFactory) {
        super(screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, screenNavigator, assetFactory);
        boss = getActorFactory().createBoss();
    }

    @Override
    protected Background createBackground() {
        return new StaticImageBackground(getAssetFactory().createImage(BACKGROUND_IMAGE_NAME), getScreenHeight(), getScreenWidth());
    }

    @Override
    protected void initializeFriendlyUnits() {
        addActor(getUser());
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (boss.isDestroyed()) {
            winGame();
        }
    }

    @Override
    protected void spawnEnemyUnits(double timeDelta) {
        if (getCurrentNumberOfEnemies() == 0) {
            spawnBoss();
        }
    }

    private void spawnBoss() {
        addActor(boss);
        getLevelView().showHealthProgressBar(boss, "BOSS");
    }
}
