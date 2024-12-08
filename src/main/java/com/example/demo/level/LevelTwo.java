package com.example.demo.level;

import com.example.demo.ui.LevelView;
import com.example.demo.entities.Boss;

public class LevelTwo extends AbstractLevel {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final Boss boss;

	public LevelTwo(double screenHeight, double screenWidth, LevelNavigator levelNavigator) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, levelNavigator);
		boss = new Boss();
	}

	@Override
	protected void initializeFriendlyUnits() {
		addNode(getUser());
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
			addEnemyUnit(boss);
			addNode(boss.getShield());
		}
	}

	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getSceneManager().getUILayer(), PLAYER_INITIAL_HEALTH);
	}
}
