package com.example.demo.entities;

import com.example.demo.movement.LinearMovementStrategy;
import com.example.demo.util.Probability;
import javafx.scene.Node;

public class EnemyPlane extends FighterPlane {
	private static final int HORIZONTAL_VELOCITY = -120;
	private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 20.0;
	private static final int INITIAL_HEALTH = 1;
	private final Probability fireProbability = new Probability(0.2);
	private final ActorFactory actorFactory;

	public EnemyPlane(Node view, double initialXPos, double initialYPos, ActorFactory actorFactory, ProjectileListener projectileListener) {
		super(view, initialXPos, initialYPos, INITIAL_HEALTH, projectileListener);
		this.actorFactory = actorFactory;
		setMovementStrategy(new LinearMovementStrategy(HORIZONTAL_VELOCITY, 0));
	}

	@Override
	public ActorType getActorType() {
		return ActorType.ENEMY_UNIT;
	}


	public void fireProjectile() {
		double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
		double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
		spawnProjectile(actorFactory.createEnemyProjectile(projectileXPosition, projectileYPosition));
	}

	private void maybeFireProjectile(double timeDelta) {
		if (fireProbability.evaluate(timeDelta)) {
			fireProjectile();
		}
	}
	@Override
	public void updateActorState(double timeDelta) {
		maybeFireProjectile(timeDelta);
	}

}
