package com.example.demo.movement;

/**
 * Represents a change in position in a two-dimensional space.
 * This class encapsulates the x and y deltas that can be applied
 * to an object's position during movement updates.
 */
public record PositionDelta(double x, double y) {
    public static double NO_MOVEMENT_IN_DIRECTION = 0;
}
