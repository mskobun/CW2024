package com.example.demo.entities.projectiles;

import com.example.demo.entities.ActiveActor;
import com.example.demo.entities.ActorType;
import com.example.demo.movement.LinearMovementStrategy;
import javafx.scene.Node;

/**
 * Represents a projectile fired by the enemy.
 */
public class EnemyProjectile extends Projectile {
    private static final int HORIZONTAL_VELOCITY = -300;

    /**
     * Constructs a {@code EnemyProjectile}.
     *
     * @param view        the visual representation of the projectile
     * @param initialXPos the initial X position of the projectile
     * @param initialYPos the initial Y position of the projectile
     */
    public EnemyProjectile(final Node view, final double initialXPos, final double initialYPos) {
        super(view, initialXPos, initialYPos);
        this.setMovementStrategy(new LinearMovementStrategy(HORIZONTAL_VELOCITY, 0));
    }

    /**
     * @see ActiveActor#getActorType()
     */
    @Override
    public ActorType getActorType() {
        return ActorType.ENEMY_PROJECTILE;
    }
}
