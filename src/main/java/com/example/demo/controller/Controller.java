package com.example.demo.controller;

import java.lang.reflect.InvocationTargetException;

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
import com.example.demo.screen.level.AbstractLevel;
import java.io.PrintWriter;
import java.io.StringWriter;

public class Controller implements ScreenNavigator {

	private final Stage stage;
	private final ScreenFactory screenFactory;
	public Controller(Stage stage) {
		this.stage = stage;
		final String ASSET_BASE_PATH = "/com/example/demo/images/";
		AssetFactory assetFactory = new CachedAssetFactory(ASSET_BASE_PATH);
		screenFactory = new ScreenFactory(this, assetFactory);
	}

	public void launchGame() {
			stage.show();
			goToScreen(ScreenType.LEVEL_ONE);
	}

	@Override
	public void goToScreen(ScreenType screenType) {
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
