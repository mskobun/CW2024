package com.example.demo.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class HealthObservableActor extends ActiveActorDestructible implements HealthObservable{

    private final IntegerProperty health;
    private final IntegerProperty maxHealth;

    public HealthObservableActor(String imageName, int imageHeight, double initialXPos, double initialYPos, int initialHealth, int maxHealth) {
        super(imageName, imageHeight, initialXPos, initialYPos);
        this.health = new SimpleIntegerProperty(initialHealth);
        this.maxHealth = new SimpleIntegerProperty(maxHealth);
    }

    @Override
    public IntegerProperty healthProperty() {
        return health;
    }

    @Override
    public IntegerProperty maxHealthProperty() {
        return maxHealth;
    }
}
