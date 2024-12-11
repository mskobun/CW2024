package com.example.demo.movement;

/**
 * A movement strategy for moving with a constant speed.
 */
public class LinearMovementStrategy implements MovementStrategy {
    private final double velocityX;
    private final double velocityY;

    /**
     * Creates new {@code LinearMovementStrategy}
     *
     * @param velocityX velocity on the X axis, per second.
     * @param velocityY velocity on the Y axis, per second.
     */
    public LinearMovementStrategy(double velocityX, double velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    @Override
    public PositionDelta getPositionDelta(double timeDelta) {
        return new PositionDelta(velocityX * timeDelta, velocityY * timeDelta);
    }
}
