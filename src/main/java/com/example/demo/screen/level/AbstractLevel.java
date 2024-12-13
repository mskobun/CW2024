package com.example.demo.screen.level;

import com.example.demo.AssetFactory;
import com.example.demo.controller.KeyAction;
import com.example.demo.entities.ActiveActorDestructible;
import com.example.demo.entities.ActorFactory;
import com.example.demo.entities.backgrounds.Background;
import com.example.demo.entities.planes.UserPlane;
import com.example.demo.screen.AbstractScreen;
import com.example.demo.screen.ScreenNavigator;
import com.example.demo.screen.ScreenType;
import com.example.demo.screen.level.hud.LevelHUD;
import com.example.demo.screen.level.manager.ActorManager;
import com.example.demo.screen.level.manager.LayerManager;

import java.util.Iterator;


/**
 * Abstract class representing a level in the game. It manages the initialization,
 * updates, and game state transitions such as pausing, winning, or losing the game.
 * Subclasses must implement level-specific logic for creating backgrounds, spawning
 * enemies, and handling the game over conditions.
 */
public abstract class AbstractLevel extends AbstractScreen {

    private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
    private final double screenHeight;
    private final double screenWidth;
    private final double enemyMaximumYPosition;

    private final ActorFactory actorFactory;
    private final LayerManager layerManager;
    private final ActorManager actorManager;
    private final UserPlane user;
    private final Background background;
    private final LevelHUD levelHUD;
    private int currentNumberOfEnemies;
    private boolean isPaused;
    private boolean isGameOver;

    /**
     * Constructs a new level.
     *
     * @param screenHeight        the height of the screen.
     * @param screenWidth         the width of the screen.
     * @param playerInitialHealth the initial health of the player.
     * @param screenNavigator     the {@link ScreenNavigator} used to manage screen transitions.
     * @param assetFactory        the {@link AssetFactory} used to create game assets.
     */
    public AbstractLevel(double screenHeight, double screenWidth, int playerInitialHealth, ScreenNavigator screenNavigator, AssetFactory assetFactory) {
        super(screenHeight, screenWidth, screenNavigator, assetFactory);
        this.layerManager = new LayerManager(getContentRoot());
        this.actorManager = new ActorManager();
        this.actorFactory = new ActorFactory(assetFactory, actorManager);
        actorManager.addListener(layerManager);
        this.user = actorFactory.createUserPlane(playerInitialHealth, getKeyInputHandler());
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
        this.levelHUD = instantiateLevelView();
        this.currentNumberOfEnemies = 0;
        this.isPaused = false;
        this.isGameOver = false;
        this.background = createBackground();
        initializePauseHandler();
        initializeLevel();
    }

    /**
     * @return the {@link Background} for this level.
     */
    protected abstract Background createBackground();

    /**
     * Initialize friendly units. Ran once on level initialization.
     */
    protected abstract void initializeFriendlyUnits();

    /**
     * Check if game is over and perform appropriate actions. Ran every frame.
     */
    protected abstract void checkIfGameOver();

    /**
     * Spawn enemy units. Ran every frame
     *
     * @param timeDelta time since last frame, in seconds.
     */
    protected abstract void spawnEnemyUnits(double timeDelta);

    protected LevelHUD instantiateLevelView() {
        return new LevelHUD(getLayerManager().getUILayer(), getAssetFactory());
    }

    private void initializePauseHandler() {
        getKeyInputHandler().addListener(KeyAction.TOGGLE_PAUSE, ((keyAction, active) -> {
            // Only care when the key is pressed
            if (active) {
                togglePaused();
            }
        }));
    }

    private void togglePaused() {
        if (isGameOver) {
            return;
        }

        if (!isPaused) {
            stopLoop();
            levelHUD.showPauseOverlay(this::togglePaused, this::goToMainMenu);
        } else {
            startLoop();
            levelHUD.hidePauseOverlay();
        }
        isPaused = !isPaused;
    }

    private void goToMainMenu() {
        goToScreen(ScreenType.MAIN_MENU);
    }

    /**
     * Restart the game by going to the first level.
     */
    protected void restartGame() {
        goToScreen(ScreenType.LEVEL_ONE);
    }

    private void initializeLevel() {
        initializeBackground();
        initializeFriendlyUnits();
        levelHUD.showHeartDisplay(user);
    }

    /**
     * Gets the {@link LayerManager} for managing the layers in the level.
     *
     * @return the {@link LayerManager} instance
     */
    protected LayerManager getLayerManager() {
        return layerManager;
    }

    /**
     * Gets the {@link ActorFactory} used to create game actors in the level.
     *
     * @return the {@link ActorFactory} instance
     */
    protected ActorFactory getActorFactory() {
        return actorFactory;
    }


    /**
     * Gets the {@link LevelHUD} for displaying the Heads-Up Display (HUD) in the level.
     *
     * @return the {@link LevelHUD} instance
     */
    protected LevelHUD getLevelHUD() {
        return levelHUD;
    }

    /**
     * Updates level state on every frame.
     *
     * @param timeDelta the time elapsed since the last update, in seconds.
     * @see AbstractScreen#updateScene(double)
     */
    @Override
    public void updateScene(final double timeDelta) {
        background.update(timeDelta);
        spawnEnemyUnits(timeDelta);
        updateActors(timeDelta);
        handleEnemyPenetration();
        updateKillCount();
        updateNumberOfEnemies();
        updateLevelView();
        checkIfGameOver();
    }

    private void initializeBackground() {
        layerManager.getBackgroundLayer().getChildren().addAll(background.getView());
    }

    /**
     * Adds an actor
     *
     * @param actor the {@link ActiveActorDestructible} to be added
     */
    protected void addActor(final ActiveActorDestructible actor) {
        actorManager.addActor(actor);
    }

    private void updateActors(final double timeDelta) {
        actorManager.updateActors(timeDelta);
    }

    private void handleEnemyPenetration() {
        for (Iterator<ActiveActorDestructible> it = actorManager.getEnemies(); it.hasNext(); ) {
            ActiveActorDestructible enemy = it.next();
            if (enemyHasPenetratedDefenses(enemy)) {
                user.takeDamage();
                enemy.destroy();
            }
        }
    }

    private void updateLevelView() {
    }

    private void updateKillCount() {
        for (int i = 0; i < currentNumberOfEnemies - actorManager.getNumberOfEnemies(); i++) {
            user.incrementKillCount();
        }
    }

    private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
        // TODO: Remove direct access of view
        return Math.abs(enemy.getView().getTranslateX()) > screenWidth;
    }

    /**
     * Triggers the game win state.
     * Stops the game loop and displays the win overlay with options to restart the game or return to the main menu.
     */
    protected void winGame() {
        stopLoop();
        isGameOver = true;
        levelHUD.showWinOverlay(this::restartGame, this::goToMainMenu);
    }

    /**
     * Triggers the game lose state.
     * Stops the game loop and displays the lose overlay with options to restart the game or return to the main menu.
     */
    protected void loseGame() {
        stopLoop();
        isGameOver = true;
        levelHUD.showLoseOverlay(this::restartGame, this::goToMainMenu);
    }


    /**
     * Gets the user's plane actor for the current level.
     *
     * @return the {@link UserPlane} representing the user's plane.
     */
    protected UserPlane getUser() {
        return user;
    }


    /**
     * Gets the maximum Y-coordinate an enemy can occupy before being considered off the screen.
     *
     * @return the maximum Y-coordinate for enemies.
     */
    protected double getEnemyMaximumYPosition() {
        return enemyMaximumYPosition;
    }

    /**
     * @return the width of the screen
     */
    protected double getScreenWidth() {
        return screenWidth;
    }

    /**
     * @return the height of the screen
     */
    protected double getScreenHeight() {
        return screenHeight;
    }


    /**
     * Checks whether the player's user plane has been destroyed.
     *
     * @return {@code true} if the user plane is destroyed; {@code false} otherwise.
     */
    protected boolean userIsDestroyed() {
        return user.isDestroyed();
    }


    /**
     * Retrieves the current number of enemies in the level.
     *
     * @return the current number of enemies
     */
    protected int getCurrentNumberOfEnemies() {
        return actorManager.getNumberOfEnemies();
    }

    private void updateNumberOfEnemies() {
        currentNumberOfEnemies = actorManager.getNumberOfEnemies();
    }
}
