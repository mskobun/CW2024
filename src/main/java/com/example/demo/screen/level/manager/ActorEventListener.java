package com.example.demo.screen.level.manager;

import com.example.demo.entities.ActiveActor;

/**
 * A listener interface for receiving events related to actors being added or removed in the level.
 * <p>
 * Implementing classes can use this interface to respond to actor lifecycle events, such as when an actor
 * is added to or removed from the level.
 */
public interface ActorEventListener {
    /**
     * Invoked when an actor is added to the game.
     *
     * @param actor the {@link ActiveActor} that was added.
     */
    void actorAdded(ActiveActor actor);

    /**
     * Invoked when an actor is removed from the game.
     *
     * @param actor the {@link ActiveActor} that was removed.
     */
    void actorRemoved(ActiveActor actor);
}
