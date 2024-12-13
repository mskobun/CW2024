package com.example.demo.screen.level;

import com.example.demo.AssetFactory;
import com.example.demo.entities.backgrounds.Background;
import com.example.demo.entities.backgrounds.StaticImageBackground;
import com.example.demo.entities.planes.Boss;
import com.example.demo.screen.ScreenNavigator;
import com.example.demo.screen.ScreenType;

/**
 * The second level of the game.
 */
public class LevelTwo extends AbstractLevel {

    private static final String BACKGROUND_IMAGE_NAME = "background2.jpg";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static ScreenType NEXT_LEVEL = ScreenType.LEVEL_THREE;
    private final Boss boss;

    /**
     * Constructs the second level of the game.
     *
     * @param screenHeight    the height of the screen
     * @param screenWidth     the width of the screen
     * @param screenNavigator the {@link ScreenNavigator} used to manage screen transitions
     * @param assetFactory    the {@link AssetFactory} used to create game assets
     */
    public LevelTwo(
            final double screenHeight,
            final double screenWidth,
            final ScreenNavigator screenNavigator,
            final AssetFactory assetFactory
    ) {
        super(screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, screenNavigator, assetFactory);
        boss = getActorFactory().createBoss();
    }

    /**
     * Creates static background for this level.
     *
     * @return static background
     */
    @Override
    protected Background createBackground() {
        return new StaticImageBackground(getAssetFactory().createImage(BACKGROUND_IMAGE_NAME), getScreenHeight(), getScreenWidth());
    }

    /**
     * Initializes user plane.
     */
    @Override
    protected void initializeFriendlyUnits() {
        addActor(getUser());
    }

    /**
     * @see AbstractLevel#checkIfGameOver()
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (boss.isDestroyed()) {
            getLevelHUD().hideHealthProgressBar();
            goToScreen(NEXT_LEVEL);
        }
    }

    /**
     * Spawns the boss if there are no enemies on the screen.
     */
    @Override
    protected void spawnEnemyUnits(final double timeDelta) {
        if (getCurrentNumberOfEnemies() == 0) {
            spawnBoss();
        }
    }

    private void spawnBoss() {
        addActor(boss);
        getLevelHUD().showHealthProgressBar(boss, "BOSS");
    }
}
