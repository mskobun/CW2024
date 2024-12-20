package com.example.demo.controller;

import com.example.demo.AssetFactory;
import com.example.demo.CachedAssetFactory;
import com.example.demo.screen.AbstractScreen;
import com.example.demo.screen.ScreenFactory;
import com.example.demo.screen.ScreenNavigator;
import com.example.demo.screen.ScreenType;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;

/**
 * Manages the navigation between different screens of the game
 * and handles the game lifecycle.
 * Responsible for initializing the application, showing the main menu,
 * and switching between screens.
 */
public class Controller implements ScreenNavigator {

    private final Stage stage;
    private final ScreenFactory screenFactory;

    /**
     * Constructs a new instance with the provided stage.
     *
     * @param stage The primary stage for this application.
     */
    public Controller(final Stage stage) {
        this.stage = stage;
        final String ASSET_BASE_PATH = "/com/example/demo/images/";
        AssetFactory assetFactory = new CachedAssetFactory(ASSET_BASE_PATH);
        screenFactory = new ScreenFactory(this, assetFactory);
    }

    /**
     * Launches the game and opens the main menu.
     */
    public void launchGame() {
        stage.show();
        goToScreen(ScreenType.MAIN_MENU);
    }

    /**
     * @see ScreenNavigator#goToScreen(ScreenType)
     */
    @Override
    public void goToScreen(final ScreenType screenType) {
        try {
            AbstractScreen myScreen = screenFactory.createScreen(screenType, Main.SCREEN_HEIGHT, Main.SCREEN_WIDTH);
            Scene scene = myScreen.getScene();
            stage.setScene(scene);
            // Weird workaround, but without these lines it crashes on KDE Wayland, similar to:
            // https://forum.snapcraft.io/t/gl-framebuffer-error-crash/27142/2
            stage.setWidth(stage.getWidth());
            stage.setHeight(stage.getHeight());
            myScreen.startLoop();
        } catch (Throwable e) {
            fatalError(e);
        }
    }

    /**
     * Converts a {@link Throwable} into a string,
     * including the stack trace and the cause of the exception if applicable.
     *
     * @param e The throwable to convert.
     * @return A string representation of the stack trace and cause.
     */
    private String getThrowableStr(final Throwable e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String exceptionString = sw.toString();

        if (e instanceof InvocationTargetException) {
            Throwable cause = e.getCause();
            exceptionString += "Caused by:\n" + getThrowableStr(cause);
        }

        return exceptionString;
    }

    /**
     * Display an error window and exit the game in case any unrecoverable error occurs.
     *
     * @param e the unrecoverable error
     */
    private void fatalError(final Throwable e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText(e.getClass().toString());
        String exceptionString = getThrowableStr(e);
        alert.setContentText(exceptionString);
        alert.show();
        stage.close();
    }
}
