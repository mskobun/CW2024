package com.example.demo.entities.projectiles;

import com.example.demo.entities.ActorType;
import com.example.demo.movement.LinearMovementStrategy;
import javafx.scene.Node;

/**
 * A projectile falling down from a {@link com.example.demo.entities.planes.BomberPlane}.
 */
public class BombProjectile extends Projectile {

    private static double VERTICAL_SPEED = 200;
    private static double HORIZONTAL_SPEED = 0;

    /**
     * Constructs a BombProjectile with the specified visual representation and initial position.
     *
     * @param view        the visual representation of the projectile
     * @param initialXPos the initial X position of the projectile
     * @param initialYPos the initial Y position of the projectile
     */
    public BombProjectile(Node view, double initialXPos, double initialYPos) {
        super(view, initialXPos, initialYPos);
        setMovementStrategy(new LinearMovementStrategy(HORIZONTAL_SPEED, VERTICAL_SPEED));
    }

    @Override
    public ActorType getActorType() {
        return ActorType.ENEMY_PROJECTILE;
    }
}
