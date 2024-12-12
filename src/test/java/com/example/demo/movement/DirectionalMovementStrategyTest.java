package com.example.demo.movement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DirectionalMovementStrategyTest {
    @Test
    void cancelsOppositeHorizontalMovement() {
        DirectionalMovementStrategy strategy = new DirectionalMovementStrategy(8);
        strategy.setMovingLeft(true);
        strategy.setMovingRight(true);
        assertEquals(0, strategy.getPositionDelta(1).x());
    }

    @Test
    void cancelsOppositeVerticalMovement() {
        DirectionalMovementStrategy strategy = new DirectionalMovementStrategy(8);
        strategy.setMovingUp(true);
        strategy.setMovingDown(true);
        assertEquals(0, strategy.getPositionDelta(1).y());
    }
}
