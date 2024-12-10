package com.example.demo.entities;

import javafx.scene.Node;

public abstract class ActiveActor {
	private final Node view;

	public ActiveActor(Node view, double initialXPos, double initialYPos) {
		this.view = view;
		this.view.setLayoutX(initialXPos);
		this.view.setLayoutY(initialYPos);
	}

	public Node getView() {
		return view;
	}

	public abstract ActorType getActorType();

	public abstract void updateActor(double timeDelta);

	protected void moveHorizontally(double horizontalMove) {
		view.setTranslateX(view.getTranslateX() + horizontalMove);
	}

	protected void moveVertically(double verticalMove) {
		view.setTranslateY(view.getTranslateY() + verticalMove);
	}

	protected double calculateMovement(double movementPerSecond, double timeDelta) {
		return movementPerSecond * timeDelta;
	}
}
