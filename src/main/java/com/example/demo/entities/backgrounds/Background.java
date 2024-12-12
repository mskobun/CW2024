package com.example.demo.entities.backgrounds;

import com.example.demo.entities.UpdatableEntity;
import javafx.scene.Node;

/**
 * Abstract class representing background logic.
 * <p>
 * This class provides common functionality for background entities, including
 * the ability to retrieve and set the visual representation (view) of the background.
 * It also implements the {@link UpdatableEntity} interface, but the does not perform any updates in this class.
 * Subclasses can extend this class to provide specific background behavior and visuals.
 * </p>
 */
public abstract class Background implements UpdatableEntity {
    private Node view;

    /**
     * Returns the visual representation of this background.
     *
     * @return the Node representing the background's view
     */
    @Override
    public Node getView() {
        return view;
    }

    /**
     * Sets the visual representation of the background.
     * This method is protected and can be used by subclasses to set the background's view.
     *
     * @param view the Node representing the new view of the background
     */
    protected void setView(Node view) {
        this.view = view;
    }
}