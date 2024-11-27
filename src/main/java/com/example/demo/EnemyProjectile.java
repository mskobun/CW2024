package com.example.demo;

public class EnemyProjectile extends Projectile {
	
	private static final String IMAGE_NAME = "enemyFire.png";
	private static final int IMAGE_HEIGHT = 50;
	private static final int HORIZONTAL_VELOCITY = -200;

	public EnemyProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	@Override
	public void updatePosition(int timeDelta) {
		moveHorizontally(calculateMovement(HORIZONTAL_VELOCITY, timeDelta));
	}

	@Override
	public void updateActor(int timeDelta) {
		updatePosition(timeDelta);
	}


}
