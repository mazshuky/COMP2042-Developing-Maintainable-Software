package com.example.demo.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.example.demo.LevelParent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import com.example.demo.HeartDisplay;
import com.example.demo.LevelOne;
import com.example.demo.LevelTwo;

public class Controller implements PropertyChangeListener {

	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.LevelOne";
	private final Stage stage;
	private final HeartDisplay heartDisplay;
	private LevelParent currentLevel;

	public Controller(Stage stage) {
		this.stage = stage;
		this.heartDisplay = new HeartDisplay(stage.getHeight(), stage.getWidth(), 5);
	}

	public void launchGame(Stage stage) throws CustomException {
		try {
			stage.show();
			goToLevel(LEVEL_ONE_CLASS_NAME);
		} catch (SecurityException | IllegalArgumentException e) {
			throw new CustomException("Error launching game", e);
		}
	}

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

	private LevelParent createLevelInstance(String className) {
		switch (className) {
			case "com.example.demo.LevelOne":
				return new LevelOne(stage.getHeight(), stage.getWidth(), heartDisplay);
			case "com.example.demo.LevelTwo":
				return new LevelTwo(stage.getHeight(), stage.getWidth(), heartDisplay);
			// Add more levels as necessary
			default:
				throw new IllegalArgumentException("Unknown level class: " + className);
		}
	}

	private void setupScene(LevelParent myLevel) {
		myLevel.addPropertyChangeListener(this);
		Scene scene = myLevel.initializeScene();
		stage.setScene(scene);
	}

	private void handleLevelTransitionException(String className, Exception e) throws CustomException {
		throw new CustomException("Error going to level: " + className, e);
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if ("levelChange".equals(event.getPropertyName())) {
			handleLevelChangeEvent(event);
		}
	}

	private void handleLevelChangeEvent(PropertyChangeEvent event) {
		String newLevel = (String) event.getNewValue();
		try {
			goToLevel(newLevel);
		} catch (CustomException e) {
			showErrorAlert(newLevel, e);
		}
	}

	private void showErrorAlert(String newLevel, CustomException e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setContentText("Error changing to level: " + newLevel + "\n" + e.getMessage());
		alert.show();
	}

}
