package com.example.demo.entities;

import javafx.scene.Node;


/**
 * Represents an active actor that can be destroyed. This class extends {@link ActiveActor}
 * and implements the {@link Destructible} interface, providing functionality for marking
 * the actor as destroyed.
 */
public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

    private boolean isDestroyed;

    /**
     * Constructs an {@code ActiveActorDestructible} with the specified view and initial position.
     *
     * @param view the graphical representation of the actor
     * @param initialXPos the initial x-coordinate of the actor
     * @param initialYPos the initial y-coordinate of the actor
     */
    public ActiveActorDestructible(Node view, double initialXPos, double initialYPos) {
        super(view, initialXPos, initialYPos);
        isDestroyed = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {
        setDestroyed(true);
    }

    /**
     * Checks whether the actor has been destroyed.
     *
     * @return {@code true} if the actor is destroyed, {@code false} otherwise
     */
    public boolean isDestroyed() {
        return isDestroyed;
    }

    /**
     * Sets the destroyed status of the actor.
     *
     * @param isDestroyed the new destroyed status
     */
    protected void setDestroyed(boolean isDestroyed) {
        this.isDestroyed = isDestroyed;
    }
}
