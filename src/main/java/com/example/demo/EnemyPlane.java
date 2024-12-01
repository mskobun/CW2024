package com.example.demo;

public class EnemyPlane extends FighterPlane {

	private static final String IMAGE_NAME = "enemyplane.png";
	private static final int IMAGE_HEIGHT = 54;
	private static final int HORIZONTAL_VELOCITY = -120;
	private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 20.0;
	private static final int INITIAL_HEALTH = 1;
	private static final double FIRE_PROBABILITY = .2;
	private boolean fireProjectileThisFrame = false;

	public EnemyPlane(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
	}

	@Override
	public void updatePosition(int timeDelta) {
		moveHorizontally(calculateMovement(HORIZONTAL_VELOCITY, timeDelta));
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		if (fireProjectileThisFrame) {
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPostion = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			return new EnemyProjectile(projectileXPosition, projectileYPostion);
		}
		return null;
	}

	public void updateFireProjectile(int timeDelta) {
		fireProjectileThisFrame = evaluateProbability(FIRE_PROBABILITY, timeDelta);
	}
	@Override
	public void updateActor(int timeDelta) {
		updatePosition(timeDelta);
		updateFireProjectile(timeDelta);
	}

}
