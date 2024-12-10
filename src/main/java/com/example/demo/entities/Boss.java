package com.example.demo.entities;

import com.example.demo.movement.BossMovementStrategy;
import javafx.scene.Group;
import javafx.scene.Node;

public class Boss extends FighterPlane {
	// TODO: Move to factory
	private static final double INITIAL_X_POSITION = 1000.0;
	private static final double INITIAL_Y_POSITION = 400;
	private static final double SHIELD_X_OFFSET = -150;
	private static final double SHIELD_Y_OFFSET = -50;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 0;
	private static final int HEALTH = 100;
	private static final double MAX_DELTA_WITH_SHIELD = 2.5;
	private boolean fireProjectileThisFrame;
	private final Probability fireProbability = new Probability(0.8);
	private final Probability shieldProbability = new Probability(0.04);
	private final ProbabilisticShield shield;
	private final ActorFactory actorFactory;

	public Boss(Group root, Node bossPlane, Node shieldView, ActorFactory actorFactory) {
		super(root, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		this.shield = new ProbabilisticShield(shieldView, SHIELD_X_OFFSET, SHIELD_Y_OFFSET, shieldProbability, MAX_DELTA_WITH_SHIELD);
		root.getChildren().addAll(bossPlane, shieldView);
		setMovementStrategy(new BossMovementStrategy());
		setClampBounds(true, true);
		this.actorFactory = actorFactory;
		fireProjectileThisFrame = false;
	}

	@Override
	public ActorType getActorType() {
		return ActorType.ENEMY_UNIT;
	}

	public void updateFireProjectile(double timeDelta) {
		fireProjectileThisFrame = fireProbability.evaluate(timeDelta);
	}
	@Override
	public void updateActorState(double timeDelta) {
		shield.updateShield(timeDelta);
		updateFireProjectile(timeDelta);
	}

	private boolean isShielded() {
		return shield.isActive();
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		return fireProjectileThisFrame ? actorFactory.createBossProjectile(getProjectileInitialPosition()) : null;
	}
	
	@Override
	public void takeDamage() {
		if (!isShielded()) {
			super.takeDamage();
		}
	}

	private double getProjectileInitialPosition() {
		return getView().getLayoutY() + getView().getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}
}
