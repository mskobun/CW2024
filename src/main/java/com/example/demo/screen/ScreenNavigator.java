package com.example.demo.screen;

public interface ScreenNavigator {
    /**
     * Navigate to a specific screen based on the provided {@link ScreenType}.
     *
     * @param screenType The type of the screen to navigate to.
     */
    void goToScreen(ScreenType screenType);
}
