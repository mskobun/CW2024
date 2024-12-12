package com.example.demo.controller;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Entry point for the Sky Battle game.
 * This class sets up the window, and creates {@link Controller}.
 */
public final class Main extends Application {
    public static final int SCREEN_WIDTH = 1300;
    public static final int SCREEN_HEIGHT = 750;
    private static final String TITLE = "Sky Battle";

    /**
     * @see Application#start(Stage)
     */
    @Override
    public void start(final Stage stage) {
        stage.setTitle(TITLE);
        stage.setHeight(SCREEN_HEIGHT);
        stage.setWidth(SCREEN_WIDTH);
        stage.setFullScreen(true);
        Controller myController = new Controller(stage);
        myController.launchGame();
    }

    /**
     * Entry function for the Sky Battle game.
     * @param args
     */
    public static void main(final String[] args) {
        launch();
    }
}