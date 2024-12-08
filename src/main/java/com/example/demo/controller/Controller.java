package com.example.demo.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.example.demo.*;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/*
 * Controller class that handles the game logic.
 */
public class Controller implements PropertyChangeListener {

	private final Stage stage;
	private final HeartDisplay heartDisplay;
	private LevelParent currentLevel;
	private boolean isPaused;

	/**
	 * Constructor for the Controller class.
	 *
	 * @param stage    the primary stage for the application
	 */
	public Controller(Stage stage) {
		this.stage = stage;
		this.heartDisplay = new HeartDisplay(stage.getHeight(), stage.getWidth(), 5);
		this.isPaused = false;
	}

	/**
	 * Launches the game and transitions to the first level.
	 *
	 * @param stage            the primary stage for the application
	 * @throws CustomException if there is an error launching the game
	 */
	public void launchGame(Stage stage) throws CustomException {
		try {
			stage.show();
			goToLevel(GameConstants.LEVEL_ONE);
		} catch (SecurityException | IllegalArgumentException e) {
			throw new CustomException("Error launching game", e);
		}
	}

	/**
	 * Transitions to the specified level.
	 *
	 * @param className        the name of the level class to transition to
	 * @throws CustomException if there is an error transitioning to the level
	 */
	private void goToLevel(String className) throws CustomException {
		if (currentLevel != null) {
			currentLevel.removePropertyChangeListener(this);
		}

		try {
			LevelParent myLevel = createLevelInstance(className);
			setupScene(myLevel);
			myLevel.startGame();
			currentLevel = myLevel;
		} catch (Exception e) {
			handleLevelTransitionException(className, e);
		}
	}

	/**
	 * Creates an instance of the specified level class.
	 *
	 * @param className the name of the level class
	 * @return an instance of the specified level class
	 */
	private LevelParent createLevelInstance(String className) {
		return switch (className) {
			case "com.example.demo.LevelOne" -> new LevelOne(stage.getHeight(), stage.getWidth(), heartDisplay, this);
			case "com.example.demo.LevelTwo" -> new LevelTwo(stage.getHeight(), stage.getWidth(), heartDisplay, this);
			case "com.example.demo.LevelThree" -> new LevelThree(stage.getHeight(), stage.getWidth(), heartDisplay, this);
			default -> throw new IllegalArgumentException("Unknown level class: " + className);
		};
	}

	/**
	 * Sets up the scene for the specified level.
	 *
	 * @param myLevel the level to set up the scene for
	 */
	private void setupScene(LevelParent myLevel) {
		myLevel.addPropertyChangeListener(this);
		Scene scene = myLevel.initializeScene();
		stage.setScene(scene);
	}

	/**
	 * Handles exceptions that occur during level transitions.
	 *
	 * @param className        the name of the level class
	 * @param e                the exception that occurred
	 * @throws CustomException if there is an error transitioning to the level
	 */
	private void handleLevelTransitionException(String className, Exception e) throws CustomException {
		throw new CustomException("Error going to level: " + className, e);
	}

	/**
	 * Handles property change events.
	 *
	 * @param event the property change event
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if ("levelChange".equals(event.getPropertyName())) {
			handleLevelChangeEvent(event);
		}
	}

	/**
	 * Handles level change events.
	 *
	 * @param event the property change event
	 */
	private void handleLevelChangeEvent(PropertyChangeEvent event) {
		String newLevel = (String) event.getNewValue();
		System.out.println("Handling level change event. Transitioning to: " + newLevel);
		try {
			goToLevel(newLevel);
		} catch (CustomException e) {
			showErrorAlert(newLevel, e);
		}
	}

	/**
	 * Shows an error alert for level transition errors.
	 *
	 * @param newLevel the name of the new level
	 * @param e        the exception that occurred
	 */
	private void showErrorAlert(String newLevel, CustomException e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setContentText("Error changing to level: " + newLevel + "\n" + e.getMessage());
		alert.show();
	}

	/**
	 * Pauses the game.
	 */
	public void pauseGame() {
		if (!isPaused && currentLevel != null) {
			currentLevel.pause();
			isPaused = true;
		}
	}

	/**
	 * Resumes the game.
	 */
	public void resumeGame() {
		if (isPaused) {
			currentLevel.resume();
			isPaused = false;
		}
	}

}
