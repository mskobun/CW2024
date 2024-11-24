package com.example.demo.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import com.example.demo.LevelParent;
import java.io.PrintWriter;
import java.io.StringWriter;

public class Controller implements Observer {

	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.LevelOne";
	private final Stage stage;

	public Controller(Stage stage) {
		this.stage = stage;
	}

	public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException  {

			stage.show();
			goToLevel(LEVEL_ONE_CLASS_NAME);
	}

	private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			Class<?> myClass = Class.forName(className);
			Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
			LevelParent myLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());
			myLevel.addObserver(this);
			Scene scene = myLevel.initializeScene();
			stage.setScene(scene);
			// Weird workaround, but without these lines it crashes on KDE Wayland, similar to:
			// https://forum.snapcraft.io/t/gl-framebuffer-error-crash/27142/2
			stage.setWidth(stage.getWidth());
			stage.setHeight(stage.getHeight());
			myLevel.startGame();

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

	// TODO: If an error occurs, it will turn into endless alert spam since the loop is not stopped.
	// Find a way to stop the loop but also show a nice error window to the user.
	@Override
	public void update(Observable arg0, Object arg1) {
		try {
			goToLevel((String) arg1);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			fatalError(e);
		}
	}

}
