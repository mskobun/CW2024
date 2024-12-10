package com.example.demo.entities;

import com.example.demo.movement.DirectionalMovementStrategy;
import javafx.scene.Node;

public class UserPlane extends FighterPlane {
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int VERTICAL_VELOCITY = 160;
	private static final int PROJECTILE_X_POSITION = 161;
	private static final int PROJECTILE_Y_POSITION_OFFSET = 25;
	private int numberOfKills;
	private final ActorFactory actorFactory;
	private final DirectionalMovementStrategy directionalMovementStrategy;

	public UserPlane(Node view, int initialHealth, ActorFactory actorFactory) {
		super(view, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		this.actorFactory = actorFactory;
		directionalMovementStrategy = new DirectionalMovementStrategy(VERTICAL_VELOCITY);
		setMovementStrategy(directionalMovementStrategy);
		setClampBounds(true, true);
	}
	
	@Override
	public ActiveActorDestructible fireProjectile() {
		return actorFactory.createUserProjetile(PROJECTILE_X_POSITION, getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
	}

	@Override
	public ActorType getActorType() {
		return ActorType.FRIENDLY_UNIT;
	}

	public void moveUp() {
		directionalMovementStrategy.setMovingUp(true);
	}

	public void moveDown() {
		directionalMovementStrategy.setMovingDown(true);
	}

	public void stop() {
		// TODO: Stop function not granular enough, like in the original code. Fix later
		directionalMovementStrategy.setMovingUp(false);
		directionalMovementStrategy.setMovingDown(false);
	}

	public int getNumberOfKills() {
		return numberOfKills;
	}

	public void incrementKillCount() {
		numberOfKills++;
	}

}
