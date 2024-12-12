package com.example.demo.screen.level;

import com.example.demo.AssetFactory;
import com.example.demo.entities.ActiveActorDestructible;
import com.example.demo.entities.backgrounds.Background;
import com.example.demo.entities.backgrounds.ScrollingImageBackground;
import com.example.demo.screen.ScreenNavigator;
import com.example.demo.screen.ScreenType;
import com.example.demo.util.Probability;


/**
 * The first level of the game.
 */
public class LevelOne extends AbstractLevel {

    private static final String BACKGROUND_IMAGE_NAME = "blueSkyBackground.png";
    private static final ScreenType NEXT_LEVEL = ScreenType.LEVEL_TWO;
    private static final int TOTAL_ENEMIES = 5;
    private static final int KILLS_TO_ADVANCE = 10;
    private static final Probability ENEMY_SPAWN_PROBABILITY = new Probability(1);
    private static final int PLAYER_INITIAL_HEALTH = 5;

    /**
     * Constructs the first level of the game.
     *
     * @param screenHeight    the height of the screen
     * @param screenWidth     the width of the screen
     * @param screenNavigator the {@link ScreenNavigator} used to manage screen transitions
     * @param assetFactory    the {@link AssetFactory} used to create game assets
     */
    public LevelOne(
            final double screenHeight,
            final double screenWidth,
            final ScreenNavigator screenNavigator,
            final AssetFactory assetFactory
    ) {
        super(screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, screenNavigator, assetFactory);
    }

    /**
     * Checks whether the game is over or if the player has reached the target kill count to advance to the next level.
     * <ul>
     * <li>If the player's plane is destroyed, the game is lost, and the lose screen is displayed.</li>
     * <li>If the player reaches the kill target, the level transitions to the next level.</li>
     * </ul>
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (userHasReachedKillTarget()) {
            goToScreen(NEXT_LEVEL);
        }
    }

    /**
     * Creates scrolling background for this level.
     *
     * @return scrolling background
     */
    @Override
    protected Background createBackground() {
        return new ScrollingImageBackground(getAssetFactory().createImage(BACKGROUND_IMAGE_NAME), getScreenHeight(), getScreenWidth());
    }

    /**
     * Initializes user plane.
     */
    @Override
    protected void initializeFriendlyUnits() {
        addActor(getUser());
    }

    /**
     * Spawns enemy units during the level.
     * <p>
     * Enemies are spawned at random vertical positions on the screen, up to the enemy maximum Y position. The number of
     * active enemies is kept below a defined limit.
     *
     * @param timeDelta the time elapsed since the last update, in seconds.
     */
    @Override
    protected void spawnEnemyUnits(final double timeDelta) {
        int currentNumberOfEnemies = getCurrentNumberOfEnemies();
        for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
            if (ENEMY_SPAWN_PROBABILITY.evaluate(timeDelta)) {
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
