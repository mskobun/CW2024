package com.example.demo.movement;

/**
 * A movement strategy that allows for directional movement (up, down, left, right)
 * with a specified speed. This strategy calculates position changes based on the
 * direction flags and speed over a given time interval.
 */
public class DirectionalMovementStrategy implements MovementStrategy {

    /**
     * Indicates whether the entity is moving up.
     */
    private boolean movingUp;

    /**
     * Indicates whether the entity is moving down.
     */
    private boolean movingDown;

    /**
     * Indicates whether the entity is moving to the right.
     */
    private boolean movingRight;

    /**
     * Indicates whether the entity is moving to the left.
     */
    private boolean movingLeft;

    /**
     * The speed of the movement in units per second.
     */
    private double speed;

    /**
     * Sets whether the entity is moving up.
     *
     * @param movingUp {@code true} if moving up, {@code false} otherwise
     */
    public void setMovingUp(final boolean movingUp) {
        this.movingUp = movingUp;
    }

    /**
     * Sets whether the entity is moving down.
     *
     * @param movingDown {@code true} if moving down, {@code false} otherwise
     */
    public void setMovingDown(final boolean movingDown) {
        this.movingDown = movingDown;
    }

    /**
     * Sets whether the entity is moving to the right.
     *
     * @param movingRight {@code true} if moving right, {@code false} otherwise
     */
    public void setMovingRight(final boolean movingRight) {
        this.movingRight = movingRight;
    }

    /**
     * Sets whether the entity is moving to the left.
     *
     * @param movingLeft {@code true} if moving left, {@code false} otherwise
     */
    public void setMovingLeft(final boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    /**
     * Sets the speed of movement.
     *
     * @param speed the speed in units per second
     */
    public void setSpeed(final double speed) {
        this.speed = speed;
    }

    /**
     * Constructs a {@code DirectionalMovementStrategy} with the specified movement speed.
     *
     * @param speed the speed of movement in units per second
     */
    public DirectionalMovementStrategy(final double speed) {
        this.speed = speed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PositionDelta getPositionDelta(final double timeDelta) {
        double xDelta = 0;
        double yDelta = 0;

        if (movingUp && !movingDown) {
            yDelta = timeDelta * -speed;
        }
        if (movingDown && !movingUp) {
            yDelta = timeDelta * speed;
        }
        if (movingRight && !movingLeft) {
            xDelta = timeDelta * speed;
        }
        if (movingLeft && !movingRight) {
            xDelta = timeDelta * -speed;
        }

        return new PositionDelta(xDelta, yDelta);
    }
}