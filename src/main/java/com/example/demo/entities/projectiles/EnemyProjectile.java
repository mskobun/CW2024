package com.example.demo.entities.projectiles;

import com.example.demo.entities.ActorType;
import com.example.demo.movement.LinearMovementStrategy;
import javafx.scene.Node;

public class EnemyProjectile extends Projectile {
    private static final int HORIZONTAL_VELOCITY = -200;

    public EnemyProjectile(Node view, double initialXPos, double initialYPos) {
        super(view, initialXPos, initialYPos);
        this.setMovementStrategy(new LinearMovementStrategy(HORIZONTAL_VELOCITY, 0));
    }

    @Override
    public ActorType getActorType() {
        return ActorType.ENEMY_PROJECTILE;
    }
}
