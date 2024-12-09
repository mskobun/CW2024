package com.example.demo.screen.level;

import com.example.demo.screen.ScreenNavigator;
import com.example.demo.ui.LevelView;
import com.example.demo.entities.Boss;

public class LevelTwo extends AbstractLevel {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final Boss boss;
	private LevelView levelView;

	public LevelTwo(double screenHeight, double screenWidth, ScreenNavigator screenNavigator) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, screenNavigator);
		boss = new Boss();
	}

	@Override
	protected void initializeFriendlyUnits() {
		addActor(getUser());
	}

	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (boss.isDestroyed()) {
			winGame();
		}
	}

	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			spawnBoss();
		}
	}

	private void spawnBoss() {
		addActor(boss);
		addNode(boss.getShield());
		levelView.showHealthProgressBar(boss, "BOSS");

	}
	@Override
	protected LevelView instantiateLevelView() {
		this.levelView = new LevelView(getLayerManager().getUILayer());
		return this.levelView;
	}
}
