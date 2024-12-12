package com.example.demo.screen;

/**
 * Interface that defines the contract for navigating between different screens in the game.
 */
public interface ScreenNavigator {
    /**
     * Navigate to a specific screen based on the provided {@link ScreenType}.
     *
     * @param screenType The type of the screen to navigate to.
     */
    void goToScreen(ScreenType screenType);
}
