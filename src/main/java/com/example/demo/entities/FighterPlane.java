package com.example.demo.entities;

import javafx.scene.Node;

public abstract class FighterPlane extends HealthObservableActor {
	private ProjectileListener projectileListener;

	public FighterPlane(Node view, double initialXPos, double initialYPos, int health, ProjectileListener projectileListener) {
		super(view, initialXPos, initialYPos, health, health);
		this.projectileListener = projectileListener;
	}

	public void spawnProjectile(ActiveActorDestructible projectile) {
		projectileListener.spawnProjectile(projectile);
	}
	
	@Override
	public void takeDamage() {
		healthProperty().set(getHealth() - 1);
		if (healthAtZero()) {
			this.destroy();
		}
	}

	protected double getProjectileXPosition(double xPositionOffset) {
		return getView().getLayoutX() + getView().getTranslateX() + xPositionOffset;
	}

	protected double getProjectileYPosition(double yPositionOffset) {
		return getView().getLayoutY() + getView().getTranslateY() + yPositionOffset;
	}

	private boolean healthAtZero() {
		return getHealth() == 0;
	}

	public int getHealth() {
		return healthProperty().get();
	}
		
}
