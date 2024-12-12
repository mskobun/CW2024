package com.example.demo.entities;

import javafx.scene.Node;

/**
 * Interface for entities that can be updated over time and provide a graphical representation.
 */
public interface UpdatableEntity {
    /**
     * Gets the view associated with this entity.
     *
     * @return the view as a {@link Node} representing the entity
     */
    Node getView();

    /**
     * Updates the entity based on the time delta.
     * This method is typically called every frame in a game loop.
     *
     * @param timeDelta the time passed since the last update, in seconds
     */
    void update(double timeDelta);
}
