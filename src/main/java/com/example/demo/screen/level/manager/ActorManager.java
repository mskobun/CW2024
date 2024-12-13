package com.example.demo.screen.level.manager;

import com.example.demo.entities.ActiveActor;
import com.example.demo.entities.ActiveActorDestructible;
import com.example.demo.entities.planes.ProjectileListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


/**
 * Manages the actors in the game.
 * Handles collision detection, actor updates, and interactions between actors.
 * Implements the {@link ProjectileListener} interface to manage projectile spawning.
 */
public class ActorManager implements ProjectileListener {
    private final List<ActiveActorDestructible> friendlyUnits;
    private final List<ActiveActorDestructible> enemyUnits;
    private final List<ActiveActorDestructible> enemyProjectiles;
    private final List<ActiveActorDestructible> friendlyProjectiles;
    private final List<ActorEventListener> listeners;

    /**
     * Constructs a new {@code ActorManager} instance.
     */
    public ActorManager() {
        this.listeners = new ArrayList<>();
        this.friendlyUnits = new ArrayList<>();
        this.enemyUnits = new ArrayList<>();
        this.friendlyProjectiles = new ArrayList<>();
        this.enemyProjectiles = new ArrayList<>();
    }

    /**
     * Adds an {@link ActorEventListener} to be notified of actor events.
     *
     * @param listener the listener to add.
     */
    public void addListener(final ActorEventListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes an {@link ActorEventListener}.
     *
     * @param listener the listener to remove.
     */
    public void removeListener(final ActorEventListener listener) {
        listeners.remove(listener);
    }

    private void notifyAddActor(final ActiveActor actor) {
        listeners.forEach(listener -> listener.actorAdded(actor));
    }

    private void notifyRemoveActor(final ActiveActor actor) {
        listeners.forEach(listener -> listener.actorRemoved(actor));
    }

    private void handleCollisions(final List<ActiveActorDestructible> actors1,
                                  final List<ActiveActorDestructible> actors2) {
        for (ActiveActorDestructible actor : actors2) {
            for (ActiveActorDestructible otherActor : actors1) {
                if (actor.getView().getBoundsInParent().intersects(otherActor.getView().getBoundsInParent())) {
                    actor.takeDamage();
                    otherActor.takeDamage();
                }
            }
        }
    }

    private void handleAllCollisions() {
        handleCollisions(friendlyUnits, enemyUnits);
        handleCollisions(friendlyProjectiles, enemyUnits);
        handleCollisions(enemyProjectiles, friendlyUnits);
    }

    private void removeDestroyedActors(final List<ActiveActorDestructible> actors) {
        List<ActiveActorDestructible> destroyedActors = actors.stream().filter(ActiveActorDestructible::isDestroyed)
                .toList();
        actors.removeAll(destroyedActors);
        destroyedActors.forEach(this::notifyRemoveActor);
    }

    private void removeAllDestroyedActors() {
        removeDestroyedActors(friendlyUnits);
        removeDestroyedActors(friendlyProjectiles);
        removeDestroyedActors(enemyUnits);
        removeDestroyedActors(enemyProjectiles);
    }


    /**
     * Updates all actors in the game, processes collisions, and removes destroyed actors.
     *
     * @param timeDelta the time elapsed since the last update.
     */
    public void updateActors(final double timeDelta) {
        friendlyUnits.forEach(actor -> actor.update(timeDelta));
        enemyUnits.forEach(actor -> actor.update(timeDelta));
        enemyProjectiles.forEach(actor -> actor.update(timeDelta));
        friendlyProjectiles.forEach(actor -> actor.update(timeDelta));
        handleAllCollisions();
        removeAllDestroyedActors();
    }


    /**
     * Adds a destructible actor and notifies listeners.
     *
     * @param actor the actor to add.
     */
    public void addActor(final ActiveActorDestructible actor) {
        switch (actor.getActorType()) {
            case ENEMY_UNIT -> enemyUnits.add(actor);
            case FRIENDLY_UNIT -> friendlyUnits.add(actor);
            case ENEMY_PROJECTILE -> enemyProjectiles.add(actor);
            case FRIENDLY_PROJECTILE -> friendlyProjectiles.add(actor);
        }
        notifyAddActor(actor);
    }


    /**
     * Gets the number of enemy units currently in the game.
     *
     * @return the number of enemy units.
     */
    public int getNumberOfEnemies() {
        return enemyUnits.size();
    }

    // FIXME: Not ideal ,but needed for handleEnemyPenetration of Level
    public Iterator<ActiveActorDestructible> getEnemies() {
        return Collections.unmodifiableList(enemyUnits).iterator();
    }


    /**
     * Spawns a projectile.
     * Implements the {@link ProjectileListener#spawnProjectile} method.
     *
     * @param projectile the projectile to spawn.
     */
    @Override
    public void spawnProjectile(final ActiveActorDestructible projectile) {
        addActor(projectile);
    }
}
