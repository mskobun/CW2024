package com.example.demo.entities;

import com.example.demo.controller.KeyAction;
import com.example.demo.controller.KeyInputHandler;
import com.example.demo.movement.DirectionalMovementStrategy;
import javafx.scene.Node;

public class UserPlane extends FighterPlane {
    private static final double INITIAL_X_POSITION = 5.0;
    private static final double INITIAL_Y_POSITION = 300.0;
    private static final int VERTICAL_VELOCITY = 160;
    private static final double PROJECTILE_X_POSITION_OFFSET = INITIAL_X_POSITION + 156;
    private static final double PROJECTILE_Y_POSITION_OFFSET = 25;
    private int numberOfKills;
    private final ActorFactory actorFactory;
    private final DirectionalMovementStrategy directionalMovementStrategy;

    public UserPlane(Node view, int initialHealth, ActorFactory actorFactory, KeyInputHandler keyInputHandler, ProjectileListener projectileListener) {
        super(view, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth, projectileListener);
        this.actorFactory = actorFactory;
        directionalMovementStrategy = new DirectionalMovementStrategy(VERTICAL_VELOCITY);
        setMovementStrategy(directionalMovementStrategy);
        registerKeyActions(keyInputHandler);
        setClampBounds(true, true);
    }

    private void fireProjectile() {
        ActiveActorDestructible projectile = actorFactory.createUserProjetile(
                getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET),
                getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET)
        );
        spawnProjectile(projectile);
    }

    @Override
    public ActorType getActorType() {
        return ActorType.FRIENDLY_UNIT;
    }

    private void registerKeyActions(KeyInputHandler keyInputHandler) {
        keyInputHandler.addListener(KeyAction.MOVE_UP, this::handleKeyAction);
        keyInputHandler.addListener(KeyAction.MOVE_DOWN, this::handleKeyAction);
        keyInputHandler.addListener(KeyAction.MOVE_RIGHT, this::handleKeyAction);
        keyInputHandler.addListener(KeyAction.MOVE_LEFT, this::handleKeyAction);
        keyInputHandler.addListener(KeyAction.FIRE_PROJECTILE, this::handleKeyAction);
    }

    private void handleKeyAction(KeyAction action, boolean active) {
        switch (action) {
            case MOVE_UP -> directionalMovementStrategy.setMovingUp(active);
            case MOVE_DOWN -> directionalMovementStrategy.setMovingDown(active);
            case MOVE_RIGHT -> directionalMovementStrategy.setMovingRight(active);
            case MOVE_LEFT -> directionalMovementStrategy.setMovingLeft(active);
            case FIRE_PROJECTILE -> {
                if (active)
                    fireProjectile();
            }
        }
    }

    public int getNumberOfKills() {
        return numberOfKills;
    }

    public void incrementKillCount() {
        numberOfKills++;
    }

}
