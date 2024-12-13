package com.example.demo.screen.level;

import com.example.demo.AssetFactory;
import com.example.demo.entities.ActiveActorDestructible;
import com.example.demo.entities.backgrounds.Background;
import com.example.demo.entities.backgrounds.ScrollingImageBackground;
import com.example.demo.screen.ScreenNavigator;
import com.example.demo.util.Probability;

import java.util.HashMap;
import java.util.Map;

/**
 * The third level of the game.
 */
public class LevelThree extends AbstractLevel {
    private static int HEALTH = 5;
    private static int MAX_ENEMIES_ON_SCREEN = 5;
    private Probability ENEMY_SPAWN_PROBABILITY = new Probability(1);
    private static final int KILLS_TO_ADVANCE = 30;

    /**
     * Used to make a weighted random choice about which enemy to spawn.
     * Key: spawn function, Value: weight
     */
    private Map<Runnable, Double> enemyWeights;

    /**
     * Constructs a new level.
     *
     * @param screenHeight    the height of the screen.
     * @param screenWidth     the width of the screen.
     * @param screenNavigator the {@link ScreenNavigator} used to manage screen transitions.
     * @param assetFactory    the {@link AssetFactory} used to create game assets.
     */
    public LevelThree(final double screenHeight, final double screenWidth, final ScreenNavigator screenNavigator, final AssetFactory assetFactory) {
        super(screenHeight, screenWidth, 5, screenNavigator, assetFactory);
        initalizeEnemyWeights();
    }

    /**
     * Initializes spawn function->weight map
     */
    private void initalizeEnemyWeights() {
        enemyWeights = new HashMap<>();
        enemyWeights.put(this::spawnBomber, 1.0);
        enemyWeights.put(this::spawnFighter, 10.0);
        enemyWeights.put(this::spawnZigZag, 2.0);
    }

    /**
     * Creates scrolling background for this level.
     *
     * @return scrolling background
     */
    @Override
    protected Background createBackground() {
        return new ScrollingImageBackground(
                getAssetFactory().createImage("fadingBlueSkyBackground.png"),
                getScreenHeight(),
                getScreenWidth()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initializeFriendlyUnits() {
        addActor(getUser());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (getUser().getNumberOfKills() > KILLS_TO_ADVANCE) {
            winGame();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void spawnEnemyUnits(final double timeDelta) {
        int currentNumberOfEnemies = getCurrentNumberOfEnemies();
        for (int i = 0; i < MAX_ENEMIES_ON_SCREEN - currentNumberOfEnemies; i++) {
            if (ENEMY_SPAWN_PROBABILITY.evaluate(timeDelta)) {
                // Now that we know an enemy is going to be spawned,
                // we need to decide between fighter, bomber and zigzag,
                // use a weighted random algorithm.
                double totalWeight = enemyWeights.values().stream().mapToDouble(Double::doubleValue).sum();
                double randomValue = Math.random() * totalWeight;
                for (Map.Entry<Runnable, Double> entry : enemyWeights.entrySet()) {
                    randomValue -= entry.getValue();
                    if (randomValue <= 0) {
                        entry.getKey().run();
                    }
                }
            }
        }
    }

    private void spawnBomber() {
        // Since bombers drop bombs down, only spawn them in upper quarter of the screen
        double initialYPosition = Math.random() * (getEnemyMaximumYPosition() / 4);
        ActiveActorDestructible bomber = getActorFactory().createBomberPlane(getScreenWidth(), initialYPosition);
        addActor(bomber);
    }

    private void spawnZigZag() {
        double initialYPosition = Math.random() * (getEnemyMaximumYPosition());
        double amplitude = Math.random() * 200;
        // 3 to 5 seconds
        double frequency = 3 + Math.random() * 5;
        ActiveActorDestructible zigZagPlane = getActorFactory().createZigZagPlane(getScreenWidth(), initialYPosition, amplitude, frequency);
        addActor(zigZagPlane);
    }

    private void spawnFighter() {
        // Since bombers drop bombs down, only spawn them in upper quarter of the screen
        double initialYPosition = Math.random() * (getEnemyMaximumYPosition());
        ActiveActorDestructible fighter = getActorFactory().createEnemyPlane(getScreenWidth(), initialYPosition);
       addActor(fighter);
    }
}
