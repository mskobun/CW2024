package com.example.demo.screen.level;

import java.util.*;

import com.example.demo.entities.ActiveActorDestructible;
import com.example.demo.screen.AbstractScreen;
import com.example.demo.screen.ScreenNavigator;
import com.example.demo.screen.level.manager.ActorManager;
import com.example.demo.screen.level.manager.LayerManager;
import com.example.demo.ui.LevelView;
import com.example.demo.entities.UserPlane;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.*;
import javafx.scene.input.*;

public abstract class AbstractLevel extends AbstractScreen {

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;

	private final LayerManager layerManager;
	private final ActorManager actorManager;
	private final UserPlane user;
	private final ImageView background;

	private int currentNumberOfEnemies;
	private LevelView levelView;

	public AbstractLevel(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth, ScreenNavigator screenNavigator) {
		super(screenHeight, screenWidth, screenNavigator);
		this.layerManager = new LayerManager(getContentRoot());
		this.actorManager = new ActorManager();
		actorManager.addListener(layerManager);
		this.user = new UserPlane(playerInitialHealth);
		this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));
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
		return new LevelView(getLayerManager().getUILayer());
	}

	private void initializeLevel() {
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay(user);
	}

	public LayerManager getLayerManager() {
		return layerManager;
	}

	public ActorManager getActorManager() {
		return actorManager;
	}

	@Override
	public void updateScene(int timeDelta) {
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
		background.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode kc = e.getCode();
				if (kc == KeyCode.UP) user.moveUp();
				if (kc == KeyCode.DOWN) user.moveDown();
				if (kc == KeyCode.SPACE) fireProjectile();
			}
		});
		background.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode kc = e.getCode();
				if (kc == KeyCode.UP || kc == KeyCode.DOWN) user.stop();
			}
		});
		layerManager.getBackgroundLayer().getChildren().addAll(background);
	}

	public void addActor(ActiveActorDestructible actor) {
		actorManager.addActor(actor);
	}

	public void addNode(Node node) {
		layerManager.getEntityLayer().getChildren().add(node);
	}

	private void fireProjectile() {
		ActiveActorDestructible projectile = user.fireProjectile();
		addActor(projectile);
	}

	private void updateActors(int timeDelta) {
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
		return Math.abs(enemy.getTranslateX()) > screenWidth;
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
