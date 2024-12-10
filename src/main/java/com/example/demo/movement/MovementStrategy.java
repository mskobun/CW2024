package com.example.demo.movement;

/**
 * Defines a strategy for determining the position changes of an actor
 * based on the elapsed time.
 */
public interface MovementStrategy {
    /**
     * Calculates the change in position for the given time delta.
     *
     * @param timeDelta Time elapsed since the last update, in seconds.
     * @return A {@link PositionDelta} object representing the change in x and y positions.
     */
    PositionDelta getPositionDelta(double timeDelta);
}
