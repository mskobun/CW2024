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

    private int currentNumberOfEnemies;
    private final LevelHUD levelHUD;
    private boolean isPaused;

    /**
     * Constructs a new level.
     *
     * @param screenHeight the height of the screen.
     * @param screenWidth the width of the screen.
     * @param playerInitialHealth the initial health of the player.
     * @param screenNavigator the {@link ScreenNavigator} used to manage screen transitions.
     * @param assetFactory the {@link AssetFactory} used to create game assets.
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

    public LayerManager getLayerManager() {
        return layerManager;
    }

    public ActorFactory getActorFactory() {
        return actorFactory;
    }

    public ActorManager getActorManager() {
        return actorManager;
    }

    protected LevelHUD getLevelView() {
        return levelHUD;
    }

    @Override
    public void updateScene(double timeDelta) {
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

    public void addActor(ActiveActorDestructible actor) {
        actorManager.addActor(actor);
    }

    private void updateActors(double timeDelta) {
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

    protected void winGame() {
        stopLoop();
        levelHUD.showWinOverlay(this::restartGame, this::goToMainMenu);
    }

    protected void loseGame() {
        stopLoop();
        levelHUD.showLoseOverlay(this::restartGame, this::goToMainMenu);
    }

    protected UserPlane getUser() {
        return user;
    }

    protected double getEnemyMaximumYPosition() {
        return enemyMaximumYPosition;
    }

    protected double getScreenWidth() {
        return screenWidth;
    }

    protected double getScreenHeight() {
        return screenHeight;
    }

    protected boolean userIsDestroyed() {
        return user.isDestroyed();
    }

    private void updateNumberOfEnemies() {
        currentNumberOfEnemies = actorManager.getNumberOfEnemies();
    }

    protected int getCurrentNumberOfEnemies() {
        return actorManager.getNumberOfEnemies();
    }
}
