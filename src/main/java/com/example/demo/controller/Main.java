package com.example.demo.controller;

import java.lang.reflect.InvocationTargetException;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public static final int SCREEN_WIDTH = 1300;
	public static final int SCREEN_HEIGHT = 750;
	private static final String TITLE = "Sky Battle";
	private Controller myController;

	@Override
	public void start(Stage stage) {
		stage.setTitle(TITLE);
		stage.setHeight(SCREEN_HEIGHT);
		stage.setWidth(SCREEN_WIDTH);
		stage.setFullScreen(true);
		myController = new Controller(stage);
		myController.launchGame();
	}

	public static void main(String[] args) {
		launch();
	}
}
