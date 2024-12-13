package com.example.demo.entities.planes;

import com.example.demo.entities.ActorFactory;
import com.example.demo.movement.MovementStrategy;
import com.example.demo.movement.ZigZagMovementStrategy;
import javafx.scene.Node;

/**
 * {@link EnemyPlane} that travels in a zig-zag pattern.
 */
public class ZigZagPlane extends EnemyPlane {

    /**
     * Constructs a new {@code ZigZagPlane}.
     *
     * @param view               The visual representation of the plane.
     * @param initialXPos        The initial x-coordinate of the plane.
     * @param initialYPos        The initial y-coordinate of the plane.
     * @param actorFactory       The factory used to create projectiles and other associated actors.
     * @param projectileListener The listener to handle projectile-related events.
     * @param amplitude          Amplitude (maximum displacement) of the zig zag movement.
     * @param frequency          How long does it take to do one full zigzag.
     */
    public ZigZagPlane(
            final Node view,
            final double initialXPos,
            final double initialYPos,
            final ActorFactory actorFactory,
            final ProjectileListener projectileListener,
            final double amplitude,
            final double frequency
    ) {
        super(view, initialXPos, initialYPos, actorFactory, projectileListener);
        MovementStrategy movementStrategy = new ZigZagMovementStrategy(-250, amplitude, frequency);
        setMovementStrategy(movementStrategy);
    }
}
