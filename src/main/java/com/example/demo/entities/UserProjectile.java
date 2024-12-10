package com.example.demo.entities;

import javafx.scene.Node;

public class UserProjectile extends Projectile {

	private static final String IMAGE_NAME = "userfire.png";
	private static final int IMAGE_HEIGHT = 6;
	private static final int HORIZONTAL_VELOCITY = 300;

	public UserProjectile(Node view, double initialXPos, double initialYPos) {
		super(view, initialXPos, initialYPos);
	}

	@Override
	public ActorType getActorType() {
		return ActorType.FRIENDLY_PROJECTILE;
	}

	private void updatePosition(double timeDelta) {
		moveHorizontally(calculateMovement(HORIZONTAL_VELOCITY, timeDelta));
	}
	
	@Override
	public void updateActor(double timeDelta) {
		updatePosition(timeDelta);
	}
	
}
