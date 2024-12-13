package com.example.demo.entities.planes;

import com.example.demo.entities.ActorFactory;
import com.example.demo.entities.ActorType;
import com.example.demo.entities.projectiles.BombProjectile;
import com.example.demo.movement.LinearMovementStrategy;
import com.example.demo.util.Probability;
import javafx.scene.Node;

/**
 * Represents a bomber plane in the game.
 * <p>
 * This plane moves in a linear path and occasionally drops bomb projectiles.
 */
public class BomberPlane extends FighterPlane {
    private Probability fireProbability = new Probability(0.2);
    private static double HORIZONTAL_VELOCITY = -200;
    private static int HEALTH = 2;
    private ActorFactory actorFactory;

    /**
     * Constructs a new {@code BomberPlane}.
     *
     * @param view               The visual representation of the plane.
     * @param initialXPos        The initial x-coordinate of the plane.
     * @param initialYPos        The initial y-coordinate of the plane.
     * @param actorFactory       The factory used to create projectiles and other associated actors.
     * @param projectileListener The listener to handle projectile-related events.
     */
    public BomberPlane(
            final Node view,
            final double initialXPos,
            final double initialYPos,
            final ActorFactory actorFactory,
            final ProjectileListener projectileListener
    ) {
        super(view, initialXPos, initialYPos, HEALTH, projectileListener);
        this.actorFactory = actorFactory;
        setMovementStrategy(new LinearMovementStrategy(HORIZONTAL_VELOCITY, 0));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActorType getActorType() {
        return ActorType.ENEMY_UNIT;
    }

    private void fireProjectile() {
        BombProjectile projectile = actorFactory.createBombProjectile(
                getProjectileXPosition(0),
                getProjectileYPosition(50)
        );
        spawnProjectile(projectile);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateState(final double timeDelta) {
        if (fireProbability.evaluate(timeDelta)) {
            fireProjectile();
        }
    }

}
