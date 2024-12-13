package com.example.demo.movement;

/**
 * A movement strategy that creates a zigzag pattern by combining
 * constant linear movement in one direction with oscillating movement in the other.
 */
public class ZigZagMovementStrategy implements MovementStrategy {
    private final double linearSpeed;
    private final double zigzagAmplitude;
    private final double zigzagFrequency;
    private double cumulativeTime;
    private double previousY;

    /**
     * Constructs a ZigZagMovementStrategy.
     *
     * @param linearSpeed The speed of linear movement (e.g., right/left).
     * @param zigzagAmplitude The maximum displacement in the oscillating direction.
     * @param zigzagFrequency The frequency of oscillation in cycles per second.
     */
    public ZigZagMovementStrategy(
            final double linearSpeed,
            final double zigzagAmplitude,
            final double zigzagFrequency
    ) {
        this.linearSpeed = linearSpeed;
        this.zigzagAmplitude = zigzagAmplitude;
        this.zigzagFrequency = zigzagFrequency;
        this.cumulativeTime = 0.0;
        this.previousY = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PositionDelta getPositionDelta(final double timeDelta) {
        // Update cumulative time
        cumulativeTime += timeDelta;

        // Linear movement (e.g., horizontal movement)
        double deltaX = linearSpeed * timeDelta;

        // Oscillating movement (vertical movement)
        double currentY = zigzagAmplitude * Math.sin(2 * Math.PI * (cumulativeTime / zigzagFrequency));
        double deltaY = currentY - previousY;
        previousY = currentY;

        return new PositionDelta(deltaX, deltaY);
    }
}