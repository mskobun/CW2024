package com.example.demo.entities.shields;

import javafx.scene.Node;

/**
 * Represents a shield that can be shown or hidden on a graphical interface.
 * The shield is associated with a {@link Node} that defines its visual representation.
 */
public class Shield {
    private final Node view;
    private boolean active;

    /**
     * Creates a new Shield instance.
     *
     * @param view      The graphical node representing the shield.
     * @param xPosition The initial x-coordinate of the shield.
     * @param yPosition The initial y-coordinate of the shield.
     */
    public Shield(final Node view, final double xPosition, final double yPosition) {
        this.view = view;
        view.setLayoutX(xPosition);
        view.setLayoutY(yPosition);
        // Hide by default
        active = false;
        hideShield();
    }

    /**
     * Activates the shield and makes it visible.
     */
    public void showShield() {
        active = true;
        view.setVisible(true);
    }

    /**
     * Deactivates the shield and hides it.
     */
    public void hideShield() {
        active = false;
        view.setVisible(false);
    }

    /**
     * Checks if the shield is currently active (visible).
     *
     * @return {@code true} if the shield is active; {@code false} otherwise.
     */
    public boolean isActive() {
        return active;
    }
}