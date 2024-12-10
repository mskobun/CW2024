package com.example.demo.screen.level;

import com.example.demo.AssetFactory;
import com.example.demo.screen.ScreenNavigator;
import com.example.demo.ui.LevelView;
import com.example.demo.entities.Boss;

public class LevelTwo extends AbstractLevel {

	private static final String BACKGROUND_IMAGE_NAME = "background2.jpg";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final Boss boss;

	public LevelTwo(double screenHeight, double screenWidth, ScreenNavigator screenNavigator, AssetFactory assetFactory) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, screenNavigator, assetFactory);
		boss = getActorFactory().createBoss();
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
		getLevelView().showHealthProgressBar(boss, "BOSS");
	}
}
