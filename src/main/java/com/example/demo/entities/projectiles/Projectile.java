package com.example.demo.entities.projectiles;

import com.example.demo.entities.ActiveActorDestructible;
import javafx.scene.Node;

public abstract class Projectile extends ActiveActorDestructible {

    public Projectile(Node view, double initialXPos, double initialYPos) {
        super(view, initialXPos, initialYPos);
    }

    @Override
    public void takeDamage() {
        this.destroy();
    }
}
