package com.example.demo.entities;

import com.example.demo.controller.Main;
import com.example.demo.movement.MovementStrategy;
import com.example.demo.movement.NoMovementStrategy;
import com.example.demo.movement.PositionDelta;
import javafx.scene.Node;

public abstract class ActiveActor {
	private final Node view;
	private MovementStrategy movementStrategy;
	private boolean clampX;
	private boolean clampY;

	public ActiveActor(Node view, double initialXPos, double initialYPos) {
		this.view = view;
		this.view.setLayoutX(initialXPos);
		this.view.setLayoutY(initialYPos);
		this.movementStrategy = new NoMovementStrategy();
		this.clampY = false;
		this.clampX = false;
	}

	protected void setMovementStrategy(MovementStrategy movementStrategy) {
		this.movementStrategy = movementStrategy;
	}

	/**
	 * Set whether actor coordinates will be clamped to their max value, if it tries to
	 * move out of bounds.
	 * @param clampX clamp X coordinate
	 * @param clampY clamp Y coordinate
	 */
	protected void setClampBounds(boolean clampX, boolean clampY) {
		this.clampX = clampX;
		this.clampY = clampY;
	}

	public Node getView() {
		return view;
	}

	public abstract ActorType getActorType();

	private double clampDimensionDelta(double oldValue, double maxValue, double length, double delta) {
		double minDelta = -oldValue;
		double maxDelta = maxValue - length - oldValue;
		return Math.min(maxDelta, Math.max(minDelta, delta));
	}

	/**
	 * Ensure applying {@code delta} to actor's view is not going to
	 * cause it to go out of bounds.
	 * @param delta
	 * @return a {@link PositionDelta} of either the original movement, or maximum allowable movement
	 */
	protected PositionDelta clampDelta(PositionDelta delta) {
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

	private void updateMovement(double timeDelta) {
		PositionDelta delta = movementStrategy.getPositionDelta(timeDelta);
		PositionDelta clampedDelta = clampDelta(delta);
		moveHorizontally(clampedDelta.x());
		moveVertically(clampedDelta.y());
	}
	public void updateActor(double timeDelta) {
		updateMovement(timeDelta);
		updateActorState(timeDelta);
	}

	public void updateActorState(double timeDelta) {}

	private void moveHorizontally(double horizontalMove) {
		view.setTranslateX(view.getTranslateX() + horizontalMove);
	}

	private void moveVertically(double verticalMove) {
		view.setTranslateY(view.getTranslateY() + verticalMove);
	}
}
