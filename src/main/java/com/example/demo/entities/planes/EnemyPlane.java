package com.example.demo.entities.planes;

import com.example.demo.entities.ActorFactory;
import com.example.demo.entities.ActorType;
import com.example.demo.movement.LinearMovementStrategy;
import com.example.demo.util.Probability;
import javafx.scene.Node;


/**
 * Represents an enemy plane in the game.
 */
public class EnemyPlane extends FighterPlane {
    private static final int HORIZONTAL_VELOCITY = -250;
    private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
    private static final double PROJECTILE_Y_POSITION_OFFSET = 20.0;
    private static final int INITIAL_HEALTH = 1;
    private final Probability fireProbability = new Probability(0.3);
    private final ActorFactory actorFactory;

    /**
     * Constructs a new {@code EnemyPlane}.
     *
     * @param view               The visual representation of the plane.
     * @param initialXPos        The initial x-coordinate of the plane.
     * @param initialYPos        The initial y-coordinate of the plane.
     * @param actorFactory       The factory used to create projectiles and other associated actors.
     * @param projectileListener The listener to handle projectile-related events.
     */
    public EnemyPlane(
            final Node view,
            final double initialXPos,
            final double initialYPos,
            final ActorFactory actorFactory,
            final ProjectileListener projectileListener
    ) {
        super(view, initialXPos, initialYPos, INITIAL_HEALTH, projectileListener);
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
        double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
        double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
        spawnProjectile(actorFactory.createEnemyProjectile(projectileXPosition, projectileYPosition));
    }

    private void maybeFireProjectile(final double timeDelta) {
        if (fireProbability.evaluate(timeDelta)) {
            fireProjectile();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateState(final double timeDelta) {
        maybeFireProjectile(timeDelta);
    }

}
