package com.example.demo.movement;

public class DirectionalMovementStrategy implements MovementStrategy {
    private boolean movingUp;
    private boolean movingDown;
    private boolean movingRight;
    private boolean movingLeft;
    private double speed;

    public void setMovingUp(final boolean movingUp) {
        this.movingUp = movingUp;
    }

    public void setMovingDown(final boolean movingDown) {
        this.movingDown = movingDown;
    }

    public void setMovingRight(final boolean movingRight) {
        this.movingRight = movingRight;
    }

    public void setMovingLeft(final boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public void setSpeed(final double speed) {
        this.speed = speed;
    }

    /**
     * Constructs a {@code DirectionalMovementStrategy}.
     * @param speed movement speed, per second
     */
    public DirectionalMovementStrategy(final double speed) {
        this.speed = speed;
    }

    /**
     * @see MovementStrategy#getPositionDelta(double)
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
