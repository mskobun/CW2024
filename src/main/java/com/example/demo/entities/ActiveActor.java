package com.example.demo.entities;

import com.example.demo.controller.Main;
import com.example.demo.movement.MovementStrategy;
import com.example.demo.movement.NoMovementStrategy;
import com.example.demo.movement.PositionDelta;
import javafx.scene.Node;

/**
 * Abstract class representing an active actor in the game that can be updated over time.
 * It manages the actor's view, movement strategy, and clamping of coordinates within screen bounds.
 */
public abstract class ActiveActor implements UpdatableEntity {
    private final Node view;
    private MovementStrategy movementStrategy;
    private boolean clampX;
    private boolean clampY;

    /**
     * Constructs an ActiveActor with the given view and initial position.
     *
     * @param view the graphical representation of the actor
     * @param initialXPos the initial X position of the actor
     * @param initialYPos the initial Y position of the actor
     */
    public ActiveActor(final Node view, final double initialXPos, final double initialYPos) {
        this.view = view;
        this.view.setLayoutX(initialXPos);
        this.view.setLayoutY(initialYPos);
        this.movementStrategy = new NoMovementStrategy();
        this.clampY = false;
        this.clampX = false;
    }

    /**
     * Sets the movement strategy for the actor.
     *
     * @param movementStrategy the movement strategy
     */
    protected void setMovementStrategy(final MovementStrategy movementStrategy) {
        this.movementStrategy = movementStrategy;
    }

    /**
     * Set whether actor coordinates will be clamped to their max value, if it tries to
     * move out of bounds.
     *
     * @param clampX clamp X coordinate
     * @param clampY clamp Y coordinate
     */
    protected void setClampBounds(final boolean clampX, final boolean clampY) {
        this.clampX = clampX;
        this.clampY = clampY;
    }

    /**
     * @see UpdatableEntity#getView()
     */
    public Node getView() {
        return view;
    }

    /**
     * Gets the type of the actor.
     *
     * @return the actor type
     */
    public abstract ActorType getActorType();

    private double clampDimensionDelta(final double oldValue,
                                       final double maxValue,
                                       final double length,
                                       final double delta) {
        double minDelta = -oldValue;
        double maxDelta = maxValue - length - oldValue;
        return Math.min(maxDelta, Math.max(minDelta, delta));
    }

    /**
     * Ensure applying {@code delta} to actor's view is not going to
     * cause it to go out of bounds.
     *
     * @param delta
     * @return a {@link PositionDelta} of either the original movement, or maximum allowable movement
     */
    protected PositionDelta clampDelta(final PositionDelta delta) {
        double clampedDeltaX = delta.x();
        double clampedDeltaY = delta.y();
        if (clampX) {
            double oldX = view.getLayoutX() + view.getTranslateX();
            double width = view.getBoundsInParent().getWidth();
            clampedDeltaX = clampDimensionDelta(oldX, Main.SCREEN_WIDTH, width, delta.x());
        }

        if (clampY) {
            double oldY = view.getLayoutY() + view.getTranslateY();
            double height = view.getBoundsInParent().getHeight();
            clampedDeltaY = clampDimensionDelta(oldY, Main.SCREEN_HEIGHT, height, delta.y());
        }

        return new PositionDelta(clampedDeltaX, clampedDeltaY);
    }

    private void updateMovement(final double timeDelta) {
        PositionDelta delta = movementStrategy.getPositionDelta(timeDelta);
        PositionDelta clampedDelta = clampDelta(delta);
        moveHorizontally(clampedDelta.x());
        moveVertically(clampedDelta.y());
    }

    /**
     * Updates actor. This method can not be overriden, subclasses
     * should instead override {@link #updateState(double)}.
     * @param timeDelta the time passed since the last update, in seconds
     */
    public final void update(final double timeDelta) {
        updateMovement(timeDelta);
        updateState(timeDelta);
    }

    /**
     * Update actor's state.
     * @param timeDelta the time passed since the last update, in seconds
     */
    protected void updateState(final double timeDelta) {
    }

    private void moveHorizontally(final double horizontalMove) {
        view.setTranslateX(view.getTranslateX() + horizontalMove);
    }

    private void moveVertically(final double verticalMove) {
        view.setTranslateY(view.getTranslateY() + verticalMove);
    }
}
