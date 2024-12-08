package com.example.demo.entities;

public class UserProjectile extends Projectile {

	private static final String IMAGE_NAME = "userfire.png";
	private static final int IMAGE_HEIGHT = 6;
	private static final int HORIZONTAL_VELOCITY = 300;

	public UserProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	@Override
	public ActorType getActorType() {
		return ActorType.FRIENDLY_PROJECTILE;
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
