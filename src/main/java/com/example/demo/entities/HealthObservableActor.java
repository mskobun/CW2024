package com.example.demo.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;


/**
 * Abstract class representing an actor with health properties.
 * This class is responsible for managing both current and maximum health for an actor,
 * and exposing these health values as {@link IntegerProperty} for dynamic updates.
 */
public abstract class HealthObservableActor extends ActiveActorDestructible implements HealthObservable {

    private final IntegerProperty health;
    private final IntegerProperty maxHealth;


    /**
     * Constructs a {@codeHealthObservableActor}.
     *
     * @param view the visual representation of the actor
     * @param initialXPos the initial X position of the actor
     * @param initialYPos the initial Y position of the actor
     * @param initialHealth the initial health of the actor
     * @param maxHealth the maximum health of the actor
     */
    public HealthObservableActor(final Node view,
                                 final double initialXPos,
                                 final double initialYPos,
                                 final int initialHealth,
                                 final int maxHealth) {
        super(view, initialXPos, initialYPos);
        health = new SimpleIntegerProperty(initialHealth);
        this.maxHealth = new SimpleIntegerProperty(maxHealth);
    }

    /**
     * @see HealthObservable#healthProperty()
     */
    @Override
    public IntegerProperty healthProperty() {
        return health;
    }

    /**
     * @see HealthObservable#healthProperty()
     */
    @Override
    public IntegerProperty maxHealthProperty() {
        return maxHealth;
    }
}
