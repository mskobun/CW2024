package com.example.demo.level;

import java.util.*;

import com.example.demo.entities.ActiveActorDestructible;
import com.example.demo.level.manager.ActorManager;
import com.example.demo.level.manager.SceneManager;
import com.example.demo.ui.LevelView;
import com.example.demo.entities.UserPlane;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.util.Duration;

public abstract class AbstractLevel {

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
	private static final int MILLISECOND_DELAY = 10;
	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;

	private final SceneManager sceneManager;
	private final ActorManager actorManager;
	private final Timeline timeline;
	private final UserPlane user;
	private final ImageView background;

	private LevelNavigator levelNavigator;
	private int currentNumberOfEnemies;
	private LevelView levelView;

	public AbstractLevel(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth, LevelNavigator levelNavigator) {
		this.sceneManager = new SceneManager(screenHeight, screenWidth);
		this.actorManager = new ActorManager();
		actorManager.addListener(sceneManager);
		this.timeline = new Timeline();
		this.user = new UserPlane(playerInitialHealth);
		this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = 0;
		this.levelNavigator = levelNavigator;
		initializeTimeline();
	}

	protected abstract void initializeFriendlyUnits();

	protected abstract void checkIfGameOver();

	protected abstract void spawnEnemyUnits();

	protected LevelView instantiateLevelView() {
		return new LevelView(getSceneManager().getUILayer());
	}

	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay(user);
		return sceneManager.getScene();
	}

	public void startGame() {
		background.requestFocus();
		timeline.play();
	}

	public void goToNextLevel(LevelType levelType) {
		timeline.stop();
		levelNavigator.goToLevel(levelType);
	}

	public SceneManager getSceneManager() {
		return sceneManager;
	}

	public ActorManager getActorManager() {
		return actorManager;
	}
	private void updateScene() {
		spawnEnemyUnits();
		updateActors();
		handleEnemyPenetration();
		updateKillCount();
		updateNumberOfEnemies();
		updateLevelView();
		checkIfGameOver();
	}

	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}

	private void stopTimeline() {
		timeline.stop();
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
		sceneManager.getBackgroundLayer().getChildren().addAll(background);
	}

	public void addActor(ActiveActorDestructible actor) {
		actorManager.addActor(actor);
	}

	public void addNode(Node node) {
		sceneManager.getEntityLayer().getChildren().add(node);
	}

	private void fireProjectile() {
		ActiveActorDestructible projectile = user.fireProjectile();
		addActor(projectile);
	}

	private void updateActors() {
		actorManager.updateActors(MILLISECOND_DELAY);
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
		timeline.stop();
		levelView.showWinImage();
	}

	protected void loseGame() {
		timeline.stop();
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
}
