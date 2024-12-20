package com.example.demo.entities.planes;

import com.example.demo.entities.ActiveActorDestructible;

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