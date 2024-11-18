package com.example.demo.controller;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class that launches the game
 */
public class Main extends Application {

	private static final int SCREEN_WIDTH = 1300;
	private static final int SCREEN_HEIGHT = 750;
	private static final String TITLE = "Sky Battle";
	private Controller myController;

	/**
	 * Starts the JavaFX application
	 *
	 * @param stage               the primary stage for the application
	 * @throws CustomException    if there is an error launching the game
	 */
	@Override
	public void start(Stage stage) throws CustomException {
		try {
			stage.setTitle(TITLE);
			stage.setResizable(false);
			stage.setHeight(SCREEN_HEIGHT);
			stage.setWidth(SCREEN_WIDTH);
			myController = new Controller(stage);
			myController.launchGame(stage);

		} catch (SecurityException | IllegalArgumentException e) {
			throw new CustomException("Error launching game", e);
		}
	}

	/**
	 * Main method that launches the game
	 *
	 * @param args    the command line arguments
	 */
	public static void main(String[] args) {
		launch();
	}
}