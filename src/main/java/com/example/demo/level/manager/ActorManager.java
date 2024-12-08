package com.example.demo.level.manager;

import com.example.demo.entities.ActiveActor;
import com.example.demo.entities.ActiveActorDestructible;
import com.example.demo.entities.FighterPlane;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ActorManager {
    private final List<ActiveActorDestructible> friendlyUnits;
    private final List<ActiveActorDestructible> enemyUnits;
    private final List<ActiveActorDestructible> enemyProjectiles;
    private final List<ActiveActorDestructible> friendlyProjectiles;
    private final List<ActorEventListener> listeners;

    public ActorManager() {
        this.listeners = new ArrayList<>();
        this.friendlyUnits = new ArrayList<>();
        this.enemyUnits = new ArrayList<>();
        this.friendlyProjectiles = new ArrayList<>();
        this.enemyProjectiles = new ArrayList<>();
    }

    public void addListener(ActorEventListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ActorEventListener listener) {
        listeners.remove(listener);
    }

    private void notifyAddActor(ActiveActor actor) {
        listeners.forEach(listener -> listener.actorAdded(actor));
    }

    private void notifyRemoveActor(ActiveActor actor) {
        listeners.forEach(listener -> listener.actorRemoved(actor));
    }

    // FIXME: Replace with event-based alternative
    private void generateEnemyFire() {
        enemyUnits.forEach(enemy -> {
            // FIXME: unsafe cast to FighterPlane, like in the OG code. Not fixing, since
            // event-based alternative will replace this later anyway.
            ActiveActorDestructible projectile = ((FighterPlane) enemy).fireProjectile();
            if (projectile != null) {
                addActor(projectile);
            }
        });
    }

    private void handleCollisions(List<ActiveActorDestructible> actors1,
                                  List<ActiveActorDestructible> actors2) {
        for (ActiveActorDestructible actor : actors2) {
            for (ActiveActorDestructible otherActor : actors1) {
                if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
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

    private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
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
    public void updateActors(int timeDelta) {
        friendlyUnits.forEach(actor -> actor.updateActor(timeDelta));
        enemyUnits.forEach(actor -> actor.updateActor(timeDelta));
        enemyProjectiles.forEach(actor -> actor.updateActor(timeDelta));
        friendlyProjectiles.forEach(actor -> actor.updateActor(timeDelta));
        generateEnemyFire();
        handleAllCollisions();
        removeAllDestroyedActors();
    }

    public void addActor(ActiveActorDestructible actor) {
        switch (actor.getActorType()) {
            case ENEMY_UNIT -> enemyUnits.add(actor);
            case FRIENDLY_UNIT -> friendlyUnits.add(actor);
            case ENEMY_PROJECTILE -> enemyProjectiles.add(actor);
            case FRIENDLY_PROJECTILE -> friendlyProjectiles.add(actor);
        }
        notifyAddActor(actor);
    }

    public int getNumberOfEnemies() {
        return enemyUnits.size();
    }

    // FIXME: Not ideal ,but needed for handleEnemyPenetration of Level
    public Iterator<ActiveActorDestructible> getEnemies() {
        return Collections.unmodifiableList(enemyUnits).iterator();
    }
}
