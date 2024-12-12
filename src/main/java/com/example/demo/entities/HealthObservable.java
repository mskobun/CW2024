package com.example.demo.entities;

import javafx.beans.property.IntegerProperty;

/**
 * Interface representing an entity that has observable health properties.
 * Classes implementing this interface provide access to the current and maximum health values
 * through `IntegerProperty` objects, allowing for dynamic updates and bindings in a UI.
 */
public interface HealthObservable {
    /**
     * @return an {@link IntegerProperty} representing the current health
     */
    IntegerProperty healthProperty();
    /**
     * @return an {@link IntegerProperty} representing the max health
     */
    IntegerProperty maxHealthProperty();
}
