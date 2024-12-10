package com.example.demo.entities;

import javafx.scene.Group;
import javafx.scene.Node;

import java.util.*;

public class Boss extends FighterPlane {
	// TODO: Move to factory
	private static final double INITIAL_X_POSITION = 1000.0;
	private static final double INITIAL_Y_POSITION = 400;
	private static final double SHIELD_X_OFFSET = -150;
	private static final double SHIELD_Y_OFFSET = -50;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 0;
	private static final int VERTICAL_VELOCITY = 160;
	private static final int HEALTH = 100;
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
	private static final int ZERO = 0;
	private static final double MAX_DELTA_WITH_SAME_MOVE = 0.5;
	private static final int Y_POSITION_UPPER_BOUND = 0;
	private static final int Y_POSITION_LOWER_BOUND = 695;
	private static final double MAX_DELTA_WITH_SHIELD = 2.5;
	private final List<Integer> movePattern;
	private double sameDirectionMoveDelta;
	private int indexOfCurrentMove;
	private boolean fireProjectileThisFrame;
	private final Probability fireProbability = new Probability(0.8);
	private final Probability shieldProbability = new Probability(0.04);
	private final ProbabilisticShield shield;
	private final ActorFactory actorFactory;

	public Boss(Group root, Node bossPlane, Node shieldView, ActorFactory actorFactory) {
		super(root, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		this.shield = new ProbabilisticShield(shieldView, SHIELD_X_OFFSET, SHIELD_Y_OFFSET, shieldProbability, MAX_DELTA_WITH_SHIELD);
		root.getChildren().addAll(bossPlane, shieldView);

		this.actorFactory = actorFactory;
		movePattern = new ArrayList<>();
		sameDirectionMoveDelta = 0;
		indexOfCurrentMove = 0;
		fireProjectileThisFrame = false;
		initializeMovePattern();
	}

	@Override
	public ActorType getActorType() {
		return ActorType.ENEMY_UNIT;
	}

	// TODO: refactor
	public void updatePosition(double timeDelta) {
		double initialTranslateY = getView().getTranslateY();
		moveVertically(calculateMovement(getNextMove(timeDelta), timeDelta));
		double currentPosition = getView().getLayoutY() + getView().getTranslateY();
		if (currentPosition < Y_POSITION_UPPER_BOUND || currentPosition > Y_POSITION_LOWER_BOUND) {
			getView().setTranslateY(initialTranslateY);
		}
	}

	public void updateFireProjectile(double timeDelta) {
		fireProjectileThisFrame = fireProbability.evaluate(timeDelta);
	}
	@Override
	public void updateActor(double timeDelta) {
		updatePosition(timeDelta);
		shield.updateShield(timeDelta);
		updateFireProjectile(timeDelta);
	}

	private boolean isShielded() {
		return shield.isActive();
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		return fireProjectileThisFrame ? actorFactory.createBossProjectile(getProjectileInitialPosition()) : null;
	}
	
	@Override
	public void takeDamage() {
		if (!isShielded()) {
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

	private int getNextMove(double timeDelta) {
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
		return getView().getLayoutY() + getView().getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}
}
