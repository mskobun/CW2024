package com.example.demo.screen.level;

import com.example.demo.AssetFactory;
import com.example.demo.entities.ActiveActorDestructible;
import com.example.demo.entities.backgrounds.Background;
import com.example.demo.entities.backgrounds.ScrollingImageBackground;
import com.example.demo.screen.ScreenNavigator;
import com.example.demo.screen.ScreenType;

/**
 * Represents the endless mode level of the game where enemies spawn continuously,
 * with enemies spawning more frequently over time.
 */
public class EndlessModeLevel extends AbstractLevel {
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private final int MAX_ENEMIES_ON_SCREEN = 10;
    private final double BASE_SPAWN_INTERVAL = 1; // Base interval between spawns (in seconds)
    private final double MIN_SPAWN_INTERVAL = 0.5; // Minimum interval for faster spawning as time progresses
    private double totalElapsedTime;
    private double elapsedTimeSinceLastSpawn;

    /**
     * Constructs endless mode level.
     *
     * @param screenHeight    the height of the screen.
     * @param screenWidth     the width of the screen.
     * @param screenNavigator the {@link ScreenNavigator} used to manage screen transitions.
     * @param assetFactory    the {@link AssetFactory} used to create game assets.
     */
    public EndlessModeLevel(
            final double screenHeight,
            final double screenWidth,
            final ScreenNavigator screenNavigator,
            final AssetFactory assetFactory
    ) {
        super(screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, screenNavigator, assetFactory);
        this.totalElapsedTime = 0;
    }

    /**
     * @see AbstractLevel#createBackground()
     */
    @Override
    protected Background createBackground() {
        return new ScrollingImageBackground(getAssetFactory().createImage("sunsetBackground.png"), getScreenHeight(), getScreenWidth());
    }

    /**
     * @see AbstractLevel#initializeFriendlyUnits()
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
        if (getUser().isDestroyed()) {
            loseGame();
        }
    }

    /**
     * Spawns enemy units, with spawn interval increasing as time passes.
     *
     * @see AbstractLevel#spawnEnemyUnits(double)
     */
    @Override
    protected void spawnEnemyUnits(final double timeDelta) {
        increaseElapsedTime(timeDelta);
        // The more time passes, the lower spawnInterval becomes,
        // until it hits MIN_SPAWN_INTERVAL
        double spawnInterval = Math.max(BASE_SPAWN_INTERVAL - totalElapsedTime * 0.01, MIN_SPAWN_INTERVAL);

        if (shouldSpawnEnemy(spawnInterval)) {
            if (getCurrentNumberOfEnemies() < MAX_ENEMIES_ON_SCREEN) {
                spawnEnemy();
            }
        }
    }

    /**
     * Restarts the level.
     */
    @Override
    protected void restartGame() {
        goToScreen(ScreenType.LEVEL_ENDLESS_MODE);
    }

    /**
     * Spawns an {@link com.example.demo.entities.planes.EnemyPlane} at random Y position.
     */
    private void spawnEnemy() {
        double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
        ActiveActorDestructible newEnemy = getActorFactory().createEnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
        addActor(newEnemy);
    }

    private boolean shouldSpawnEnemy(final double spawnInterval) {
        if (elapsedTimeSinceLastSpawn >= spawnInterval) {
            resetElapsedSpawnTime();
            return true;
        }
        return false;
    }

    private void increaseElapsedTime(final double timeDelta) {
        elapsedTimeSinceLastSpawn += timeDelta;
        totalElapsedTime += timeDelta;
    }

    private void resetElapsedSpawnTime() {
        elapsedTimeSinceLastSpawn = 0;
    }
}