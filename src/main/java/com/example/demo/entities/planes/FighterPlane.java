package com.example.demo.entities.planes;

import com.example.demo.entities.ActiveActorDestructible;
import com.example.demo.entities.HealthObservableActor;
import javafx.scene.Node;

/**
 * Represents a fighter plane in the game.
 * <p>
 * Fighter planes are actors with health and the ability to spawn projectiles.
 * This abstract class provides the base functionality for handling health
 * changes, projectile spawning, and position calculations for projectiles.
 * </p>
 */
public abstract class FighterPlane extends HealthObservableActor {
    private final ProjectileListener projectileListener;

    /**
     * Constructs a new {@code FighterPlane}.
     *
     * @param view               The visual representation of the plane.
     * @param initialXPos        The initial x-coordinate of the plane.
     * @param initialYPos        The initial y-coordinate of the plane.
     * @param health             The initial health of the plane.
     * @param projectileListener The listener for spawning projectiles.
     */
    public FighterPlane(
            final Node view,
            final double initialXPos,
            final double initialYPos,
            final int health,
            final ProjectileListener projectileListener
    ) {
        super(view, initialXPos, initialYPos, health, health);
        this.projectileListener = projectileListener;
    }

    /**
     * Spawns a projectile
     *
     * @param projectile the projectile
     */
    public void spawnProjectile(final ActiveActorDestructible projectile) {
        projectileListener.spawnProjectile(projectile);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void takeDamage() {
        healthProperty().set(getHealth() - 1);
        if (healthAtZero()) {
            this.destroy();
        }
    }

    /**
     * Calculates the x-coordinate for spawning a projectile, based on plane position + offset
     *
     * @param xPositionOffset The offset to apply to the current x-coordinate.
     * @return The x-coordinate for the projectile.
     */
    protected double getProjectileXPosition(final double xPositionOffset) {
        return getView().getLayoutX() + getView().getTranslateX() + xPositionOffset;
    }

    /**
     * Calculates the y-coordinate for spawning a projectile, based on plane position + offset
     *
     * @param yPositionOffset The offset to apply to the current y-coordinate.
     * @return The y-coordinate for the projectile.
     */
    protected double getProjectileYPosition(final double yPositionOffset) {
        return getView().getLayoutY() + getView().getTranslateY() + yPositionOffset;
    }


    /**
     * Checks if the plane's health has reached zero.
     *
     * @return {@code true} if health is zero, otherwise {@code false}.
     */
    private boolean healthAtZero() {
        return getHealth() == 0;
    }


    /**
     * Retrieves the current health of the plane.
     *
     * @return The current health value.
     */
    protected int getHealth() {
        return healthProperty().get();
    }
}
