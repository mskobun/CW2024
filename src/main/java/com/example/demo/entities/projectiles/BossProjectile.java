package com.example.demo.entities.projectiles;

import com.example.demo.entities.ActorType;
import com.example.demo.movement.LinearMovementStrategy;
import javafx.scene.Node;

public class BossProjectile extends Projectile {
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
