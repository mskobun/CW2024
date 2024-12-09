package com.example.demo.controller;

import java.lang.reflect.InvocationTargetException;

import com.example.demo.screen.level.LevelFactory;
import com.example.demo.screen.ScreenNavigator;
import com.example.demo.screen.ScreenType;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import com.example.demo.screen.level.AbstractLevel;
import java.io.PrintWriter;
import java.io.StringWriter;

public class Controller implements ScreenNavigator {

	private final Stage stage;
	private final LevelFactory levelFactory;
	public Controller(Stage stage) {
		this.stage = stage;
		levelFactory = new LevelFactory(this);
	}

	public void launchGame() {
			stage.show();
			goToScreen(ScreenType.LEVEL_ONE);
	}

	@Override
	public void goToScreen(ScreenType screenType) {
		try {
			AbstractLevel myLevel = levelFactory.createLevel(screenType, Main.SCREEN_HEIGHT, Main.SCREEN_WIDTH);
			Scene scene = myLevel.getScene();
			stage.setScene(scene);
			// Weird workaround, but without these lines it crashes on KDE Wayland, similar to:
			// https://forum.snapcraft.io/t/gl-framebuffer-error-crash/27142/2
			stage.setWidth(stage.getWidth());
			stage.setHeight(stage.getHeight());
			myLevel.startLoop();
		} catch (Throwable e) {
			fatalError(e);
		}
	}

	private String getThrowableStr(Throwable e) {
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
	 * @param e the unrecoverable error
	 */
	private void fatalError(Throwable e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setHeaderText(e.getClass().toString());
		String exceptionString = getThrowableStr(e);
		alert.setContentText(exceptionString);
		alert.show();
		stage.close();
	}
}
