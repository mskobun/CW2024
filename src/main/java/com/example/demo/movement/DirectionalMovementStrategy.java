package com.example.demo.movement;

public class DirectionalMovementStrategy implements MovementStrategy {
    private boolean movingUp;
    private boolean movingDown;
    private boolean movingRight;
    private boolean movingLeft;
    private double speed;

    public void setMovingUp(boolean movingUp) {
        this.movingUp = movingUp;
    }

    public void setMovingDown(boolean movingDown) {
        this.movingDown = movingDown;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public DirectionalMovementStrategy(double speed) {
        this.speed = speed;
    }

    @Override
    public PositionDelta getPositionDelta(double timeDelta) {
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
