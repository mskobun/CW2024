package com.example.demo.entities;


/**
 * Interface representing an entity that can be damaged and destroyed.
 */
public interface Destructible {
    /**
     * Apply damage to the entity.
     * The exact effect of damage is determined by the implementing class.
     */
    void takeDamage();

    /**
     * Destroys the entity, typically triggering a death animation or removal from the game.
     * The exact destruction behavior is determined by the implementing class.
     */
    void destroy();
}
