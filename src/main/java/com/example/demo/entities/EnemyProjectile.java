package com.example.demo.entities;

import com.example.demo.AssetFactory;
import javafx.scene.Node;

public class EnemyProjectile extends Projectile {
	// TODO: Move to factory
	private static final String IMAGE_NAME = "enemyFire.png";
	private static final int IMAGE_HEIGHT = 32;
	private static final int HORIZONTAL_VELOCITY = -200;

	public EnemyProjectile(Node view, double initialXPos, double initialYPos) {
		super(view, initialXPos, initialYPos);
	}

	@Override
	public ActorType getActorType() {
		return ActorType.ENEMY_PROJECTILE;
	}

	@Override
	public void updateActor(double timeDelta) {
		moveHorizontally(calculateMovement(HORIZONTAL_VELOCITY, timeDelta));
	}
}
