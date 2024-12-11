package com.example.demo.entities;

import com.example.demo.movement.LinearMovementStrategy;
import javafx.scene.Node;

public class UserProjectile extends Projectile {
    private static final int HORIZONTAL_VELOCITY = 300;

    public UserProjectile(Node view, double initialXPos, double initialYPos) {
        super(view, initialXPos, initialYPos);
        setMovementStrategy(new LinearMovementStrategy(HORIZONTAL_VELOCITY, 0));
    }

    @Override
    public ActorType getActorType() {
        return ActorType.FRIENDLY_PROJECTILE;
    }
}
