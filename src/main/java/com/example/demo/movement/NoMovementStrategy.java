package com.example.demo.movement;

/**
 * A movement strategy that results in no movement.
 * This strategy ensures that the actor remains stationary.
 */
public class NoMovementStrategy implements MovementStrategy {
    private static final PositionDelta NO_OP_MOVEMENT = new PositionDelta(0, 0);

    /**
     * @see MovementStrategy#getPositionDelta(double)
     */
    @Override
    public PositionDelta getPositionDelta(final double timeDelta) {
        return NO_OP_MOVEMENT;
    }
}
