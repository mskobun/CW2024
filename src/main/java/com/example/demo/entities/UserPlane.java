package com.example.demo.entities;

import javafx.scene.Node;

public class UserPlane extends FighterPlane {

	private static final String IMAGE_NAME = "userplane.png";
	private static final double Y_UPPER_BOUND = 0;
	private static final double Y_LOWER_BOUND = 730.5;
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_HEIGHT = 39;
	private static final int VERTICAL_VELOCITY = 160;
	private static final int PROJECTILE_X_POSITION = 161;
	private static final int PROJECTILE_Y_POSITION_OFFSET = 25;
	private int velocityMultiplier;
	private int numberOfKills;
	private final ActorFactory actorFactory;

	public UserPlane(Node view, int initialHealth, ActorFactory actorFactory) {
		super(view, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		this.actorFactory = actorFactory;
		velocityMultiplier = 0;
	}
	
	private void updatePosition(double timeDelta) {
		if (isMoving()) {
			double initialTranslateY = getView().getTranslateY();
			int curVerticalVelocity = VERTICAL_VELOCITY * velocityMultiplier;
			this.moveVertically(calculateMovement(curVerticalVelocity, timeDelta));
			double newPosition = getView().getLayoutY() + getView().getTranslateY();
			if (newPosition < Y_UPPER_BOUND || newPosition > Y_LOWER_BOUND) {
				getView().setTranslateY(initialTranslateY);
			}
		}
	}
	
	@Override
	public void updateActor(double timeDelta) {
		updatePosition(timeDelta);
	}
	
	@Override
	public ActiveActorDestructible fireProjectile() {
		return actorFactory.createUserProjetile(PROJECTILE_X_POSITION, getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
	}

	@Override
	public ActorType getActorType() {
		return ActorType.FRIENDLY_UNIT;
	}

	private boolean isMoving() {
		return velocityMultiplier != 0;
	}

	public void moveUp() {
		velocityMultiplier = -1;
	}

	public void moveDown() {
		velocityMultiplier = 1;
	}

	public void stop() {
		velocityMultiplier = 0;
	}

	public int getNumberOfKills() {
		return numberOfKills;
	}

	public void incrementKillCount() {
		numberOfKills++;
	}

}
