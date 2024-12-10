package com.example.demo.entities;

import javafx.scene.Node;

public class EnemyPlane extends FighterPlane {

	private static final String IMAGE_NAME = "enemyplane.png";
	private static final int IMAGE_HEIGHT = 54;
	private static final int HORIZONTAL_VELOCITY = -120;
	private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 20.0;
	private static final int INITIAL_HEALTH = 1;
	private boolean fireProjectileThisFrame = false;
	private final Probability fireProbability = new Probability(0.2);
	private final ActorFactory actorFactory;

	public EnemyPlane(Node view, double initialXPos, double initialYPos, ActorFactory actorFactory) {
		super(view, initialXPos, initialYPos, INITIAL_HEALTH);
		this.actorFactory = actorFactory;
	}

	@Override
	public ActorType getActorType() {
		return ActorType.ENEMY_UNIT;
	}

	private void updatePosition(double timeDelta) {
		moveHorizontally(calculateMovement(HORIZONTAL_VELOCITY, timeDelta));
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		if (fireProjectileThisFrame) {
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPostion = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			return actorFactory.createEnemyProjectile(projectileXPosition, projectileYPostion);
		}
		return null;
	}

	public void updateFireProjectile(double timeDelta) {
		fireProjectileThisFrame = fireProbability.evaluate(timeDelta);
	}
	@Override
	public void updateActor(double timeDelta) {
		updatePosition(timeDelta);
		updateFireProjectile(timeDelta);
	}

}
