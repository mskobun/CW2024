package com.example.demo.entities.planes;

import com.example.demo.entities.ActorFactory;
import com.example.demo.entities.ActorType;
import com.example.demo.entities.shields.ProbabilisticShield;
import com.example.demo.movement.BossMovementStrategy;
import com.example.demo.util.Probability;
import javafx.scene.Group;
import javafx.scene.Node;

public class Boss extends FighterPlane {
    private static final double INITIAL_X_POSITION = 1000.0;
    private static final double INITIAL_Y_POSITION = 400;
    private static final double SHIELD_X_OFFSET = -100;
    private static final double SHIELD_Y_OFFSET = -15;
    private static final double PROJECTILE_Y_POSITION_OFFSET = 0;
    private static final int HEALTH = 100;
    private static final double MAX_DELTA_WITH_SHIELD = 2.5;
    private final Probability fireProbability = new Probability(0.8);
    private final Probability shieldProbability = new Probability(0.04);
    private final ProbabilisticShield shield;
    private final ActorFactory actorFactory;

    public Boss(Group root, Node bossPlane, Node shieldView, ActorFactory actorFactory, ProjectileListener projectileListener) {
        super(root, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH, projectileListener);
        this.shield = new ProbabilisticShield(shieldView, SHIELD_X_OFFSET, SHIELD_Y_OFFSET, shieldProbability, MAX_DELTA_WITH_SHIELD);
        root.getChildren().addAll(bossPlane, shieldView);
        setMovementStrategy(new BossMovementStrategy());
        setClampBounds(true, true);
        this.actorFactory = actorFactory;
    }

    @Override
    public ActorType getActorType() {
        return ActorType.ENEMY_UNIT;
    }

    public void maybeFireProjectile(double timeDelta) {
        if (fireProbability.evaluate(timeDelta)) {
            fireProjectile();
        }
    }

    @Override
    public void updateState(double timeDelta) {
        shield.updateShield(timeDelta);
        maybeFireProjectile(timeDelta);
    }

    private boolean isShielded() {
        return shield.isActive();
    }

    private void fireProjectile() {
        spawnProjectile(actorFactory.createBossProjectile(getProjectileInitialPosition()));
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
