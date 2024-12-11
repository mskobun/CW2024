package com.example.demo.entities;

import com.example.demo.movement.LinearMovementStrategy;
import javafx.scene.Node;

public class BossProjectile extends Projectile {

    // TODO: Move to factory
    private static final String IMAGE_NAME = "fireball.png";
    private static final int IMAGE_HEIGHT = 75;
    private static final int HORIZONTAL_VELOCITY = -300;
    private static final int INITIAL_X_POSITION = 950;

    public BossProjectile(Node view, double initialYPos) {
        super(view, INITIAL_X_POSITION, initialYPos);
        setMovementStrategy(new LinearMovementStrategy(HORIZONTAL_VELOCITY, 0));
    }

    @Override
    public ActorType getActorType() {
        return ActorType.ENEMY_PROJECTILE;
    }
}
