package com.example.demo.screen.level.hud.component;

import javafx.scene.layout.Region;

/**
 * Represents a component of the Heads-Up Display (HUD) in the game.
 */
public interface HUDComponent {
    /**
     * Retrieves the visual representation of the component.
     *
     * @return the {@link Region} representing this component's view.
     */
    Region getView();
}