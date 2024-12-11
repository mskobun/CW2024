package com.example.demo.screen.level;

import java.util.*;

import com.example.demo.AssetFactory;
import com.example.demo.entities.ActiveActorDestructible;
import com.example.demo.entities.ActorFactory;
import com.example.demo.screen.AbstractScreen;
import com.example.demo.screen.ScreenNavigator;
import com.example.demo.screen.level.manager.ActorManager;
import com.example.demo.screen.level.manager.LayerManager;
import com.example.demo.ui.LevelView;
import com.example.demo.entities.UserPlane;
import javafx.scene.image.*;

public abstract class AbstractLevel extends AbstractScreen {

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;

	private final ActorFactory actorFactory;
	private final LayerManager layerManager;
	private final ActorManager actorManager;
	private final UserPlane user;
	private final ImageView background;

	private int currentNumberOfEnemies;
	private LevelView levelView;

	public AbstractLevel(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth, ScreenNavigator screenNavigator, AssetFactory assetFactory) {
		super(screenHeight, screenWidth, screenNavigator, assetFactory);
		this.layerManager = new LayerManager(getContentRoot());
		this.actorManager = new ActorManager();
		this.actorFactory = new ActorFactory(assetFactory, actorManager);
		actorManager.addListener(layerManager);
		this.user = actorFactory.createUserPlane(playerInitialHealth, getKeyInputHandler());
		this.background = new ImageView(assetFactory.createImage(backgroundImageName));
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = 0;
		initializeLevel();
	}

	protected abstract void initializeFriendlyUnits();

	protected abstract void checkIfGameOver();

	protected abstract void spawnEnemyUnits();

	protected LevelView instantiateLevelView() {
		return new LevelView(getLayerManager().getUILayer(), getAssetFactory());
	}

	private void initializeLevel() {
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay(user);
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

	protected LevelView getLevelView() {
		return levelView;
	}

	@Override
	public void updateScene(double timeDelta) {
		spawnEnemyUnits();
		updateActors(timeDelta);
		handleEnemyPenetration();
		updateKillCount();
		updateNumberOfEnemies();
		updateLevelView();
		checkIfGameOver();
	}

	private void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		layerManager.getBackgroundLayer().getChildren().addAll(background);
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
		levelView.showWinImage();
	}

	protected void loseGame() {
		stopLoop();
		levelView.showGameOverImage();
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

	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = actorManager.getNumberOfEnemies();
	}

	protected int getCurrentNumberOfEnemies() {
		return actorManager.getNumberOfEnemies();
	}

	@Override
	public void startLoop() {
		super.startLoop();
		background.requestFocus();
	}
}
