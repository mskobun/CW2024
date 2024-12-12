package com.example.demo.movement;

import com.example.demo.entities.planes.Boss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Movement strategy for {@link Boss}.
 */
public class BossMovementStrategy implements MovementStrategy {
    private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
    private static final double ZERO = 0;
    private static final double VERTICAL_VELOCITY = 160;
    private static final double MAX_DELTA_WITH_SAME_MOVE = 0.5;
    private final List<Double> movePattern;
    private double sameDirectionMoveDelta;
    private int indexOfCurrentMove;

    /**
     * Constructs {@code BossMovementStrategy}.
     */
    public BossMovementStrategy() {
        movePattern = new ArrayList<>();
        sameDirectionMoveDelta = 0;
        indexOfCurrentMove = 0;
        initializeMovePattern();
    }

    private void initializeMovePattern() {
        for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
            movePattern.add(VERTICAL_VELOCITY);
            movePattern.add(-VERTICAL_VELOCITY);
            movePattern.add(ZERO);
        }
        Collections.shuffle(movePattern);
    }

    private double getNextMove(final double timeDelta) {
        double currentMove = movePattern.get(indexOfCurrentMove);
        sameDirectionMoveDelta += timeDelta;

        if (sameDirectionMoveDelta >= MAX_DELTA_WITH_SAME_MOVE) {
            Collections.shuffle(movePattern);
            sameDirectionMoveDelta = 0;
            indexOfCurrentMove++;
        }
        if (indexOfCurrentMove == movePattern.size()) {
            indexOfCurrentMove = 0;
        }
        return currentMove * timeDelta;
    }

    /**
     * @see MovementStrategy#getPositionDelta(double)
     */
    @Override
    public PositionDelta getPositionDelta(final double timeDelta) {
        return new PositionDelta(0, getNextMove(timeDelta));
    }
}
