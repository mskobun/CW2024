package com.example.demo.entities;

/**
 * Interface for handling projectile-related events.
 */
public interface ProjectileListener {
    /**
     * Spawns a projectile with the specified actor.
     *
     * @param projectile the projectile to be spawned, represented as an {@link ActiveActorDestructible}.
     */
    void spawnProjectile(ActiveActorDestructible projectile);
}