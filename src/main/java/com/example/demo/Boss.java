package com.example.demo;

import java.util.*;

public class Boss extends FighterPlane {

	private static final String IMAGE_NAME = "bossplane.png";
	private static final double INITIAL_X_POSITION = 800.0;
	private static final double INITIAL_Y_POSITION = 400;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 0;
	private static final double BOSS_FIRE_PROBABILITY = 0.8;
	private static final double BOSS_SHIELD_PROBABILITY = .04;
	private static final int IMAGE_HEIGHT = 56;
	private static final int VERTICAL_VELOCITY = 160;
	private static final int HEALTH = 100;
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
	private static final int ZERO = 0;
	private static final int MAX_DELTA_WITH_SAME_MOVE = 500;
	private static final int Y_POSITION_UPPER_BOUND = 0;
	private static final int Y_POSITION_LOWER_BOUND = 695;
	private static final int MAX_DELTA_WITH_SHIELD = 2500;
	private final List<Integer> movePattern;
	private boolean isShielded;
	private int sameDirectionMoveDelta;
	private int indexOfCurrentMove;
	private int shieldActivatedDelta;
	private boolean fireProjectileThisFrame;

	public Boss() {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		movePattern = new ArrayList<>();
		sameDirectionMoveDelta = 0;
		indexOfCurrentMove = 0;
		shieldActivatedDelta = 0;
		isShielded = false;
		fireProjectileThisFrame = false;
		initializeMovePattern();
	}

	@Override
	public void updatePosition(int timeDelta) {
		double initialTranslateY = getTranslateY();
		moveVertically(calculateMovement(getNextMove(timeDelta), timeDelta));
		double currentPosition = getLayoutY() + getTranslateY();
		if (currentPosition < Y_POSITION_UPPER_BOUND || currentPosition > Y_POSITION_LOWER_BOUND) {
			setTranslateY(initialTranslateY);
		}
	}

	public void updateFireProjectile(int timeDelta) {
		fireProjectileThisFrame = evaluateProbability(BOSS_FIRE_PROBABILITY, timeDelta);
	}
	@Override
	public void updateActor(int timeDelta) {
		updatePosition(timeDelta);
		updateShield(timeDelta);
		updateFireProjectile(timeDelta);
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		return fireProjectileThisFrame ? new BossProjectile(getProjectileInitialPosition()) : null;
	}
	
	@Override
	public void takeDamage() {
		if (!isShielded) {
			super.takeDamage();
		}
	}

	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(ZERO);
		}
		Collections.shuffle(movePattern);
	}

	private void updateShield(int timeDelta) {
		if (isShielded) shieldActivatedDelta += timeDelta;
		else if (shieldShouldBeActivated(timeDelta)) activateShield();
		if (shieldExhausted()) deactivateShield();
	}

	private int getNextMove(int timeDelta) {
		int currentMove = movePattern.get(indexOfCurrentMove);
		sameDirectionMoveDelta += timeDelta;

		if (sameDirectionMoveDelta >= MAX_DELTA_WITH_SAME_MOVE) {
			Collections.shuffle(movePattern);
			sameDirectionMoveDelta = 0;
			indexOfCurrentMove++;
		}
		if (indexOfCurrentMove == movePattern.size()) {
			indexOfCurrentMove = 0;
		}
		return currentMove;
	}

	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}

	private boolean shieldShouldBeActivated(int timeDelta) {
		return evaluateProbability(BOSS_SHIELD_PROBABILITY, timeDelta);
	}

	private boolean shieldExhausted() {
		return shieldActivatedDelta >= MAX_DELTA_WITH_SHIELD;
	}

	private void activateShield() {
		isShielded = true;
	}

	private void deactivateShield() {
		isShielded = false;
		shieldActivatedDelta = 0;
	}

}
