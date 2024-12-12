package com.example.demo.entities.projectiles;

import com.example.demo.entities.ActiveActorDestructible;
import javafx.scene.Node;

/**
 * Abstract class representing a projectile in the game.
 */
public abstract class Projectile extends ActiveActorDestructible {

    /**
     * Constructs a Projectile with the specified visual representation and initial position.
     *
     * @param view        the visual representation of the projectile
     * @param initialXPos the initial X position of the projectile
     * @param initialYPos the initial Y position of the projectile
     */
    public Projectile(final Node view, final double initialXPos, final double initialYPos) {
        super(view, initialXPos, initialYPos);
    }

    /**
     * Takes damage and triggers the destruction of the projectile.
     */
    @Override
    public void takeDamage() {
        this.destroy();
    }
}
