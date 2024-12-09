package com.example.demo.entities;

public abstract class FighterPlane extends HealthObservableActor {

	public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos, health, health);
	}

	public abstract ActiveActorDestructible fireProjectile();
	
	@Override
	public void takeDamage() {
		healthProperty().set(getHealth() - 1);
		if (healthAtZero()) {
			this.destroy();
		}
	}

	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset;
	}

	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset;
	}

	private boolean healthAtZero() {
		return getHealth() == 0;
	}

	public int getHealth() {
		return healthProperty().get();
	}
		
}
