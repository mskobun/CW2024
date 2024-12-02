package com.example.demo.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import com.example.demo.level.AbstractLevel;
import java.io.PrintWriter;
import java.io.StringWriter;

public class Controller implements LevelNavigator {

	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.level.LevelOne";
	private final Stage stage;

	public Controller(Stage stage) {
		this.stage = stage;
	}

	public void launchGame() {
			stage.show();
			goToLevel(LEVEL_ONE_CLASS_NAME);
	}

	@Override
	public void goToLevel(String className) {
		try {
			Class<?> myClass = Class.forName(className);
			Constructor<?> constructor = myClass.getConstructor(double.class, double.class, LevelNavigator.class);
			AbstractLevel myLevel = (AbstractLevel) constructor.newInstance(stage.getHeight(), stage.getWidth(), this);
			Scene scene = myLevel.initializeScene();
			stage.setScene(scene);
			// Weird workaround, but without these lines it crashes on KDE Wayland, similar to:
			// https://forum.snapcraft.io/t/gl-framebuffer-error-crash/27142/2
			stage.setWidth(stage.getWidth());
			stage.setHeight(stage.getHeight());
			myLevel.startGame();
		} catch (Throwable e) {
			fatalError(e);
		}
	}

	private String getThrowableStr(Throwable e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String exceptionString = sw.toString();

		if (e instanceof InvocationTargetException) {
			Throwable cause = ((InvocationTargetException) e).getCause();
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
