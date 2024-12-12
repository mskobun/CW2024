package com.example.demo.entities.projectiles;

import com.example.demo.entities.ActiveActor;
import com.example.demo.entities.ActorType;
import com.example.demo.movement.LinearMovementStrategy;
import javafx.scene.Node;

/**
 * Represents a projectile fired by the boss.
 */
public class BossProjectile extends Projectile {
    private static final int HORIZONTAL_VELOCITY = -300;
    private static final int INITIAL_X_POSITION = 950;

    /**
     * Constructs a {@code BossProjectile}.
     *
     * @param view        the visual representation of the projectile
     * @param initialYPos the initial Y position of the projectile
     */
    public BossProjectile(final Node view, final double initialYPos) {
        super(view, INITIAL_X_POSITION, initialYPos);
        setMovementStrategy(new LinearMovementStrategy(HORIZONTAL_VELOCITY, 0));
    }

    /**
     * @see ActiveActor#getActorType()
     */
    @Override
    public ActorType getActorType() {
        return ActorType.ENEMY_PROJECTILE;
    }
}