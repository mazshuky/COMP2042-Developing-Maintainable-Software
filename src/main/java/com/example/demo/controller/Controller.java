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
		try {
			if (currentLevel != null) {
				currentLevel.removePropertyChangeListener(this);
			}

			LevelParent myLevel = createLevelInstance(className);
			setupScene(myLevel);
			myLevel.startGame();
			currentLevel = myLevel;
		} catch (Exception e) {
			throw new CustomException("Error going to level: " + className, e);
		}
	}

	private LevelParent createLevelInstance(String className) {
		if (LEVEL_ONE_CLASS_NAME.equals(className)) {
			return new LevelOne(stage.getHeight(), stage.getWidth(), heartDisplay);
		}
		// Add other levels if necessary here
		throw new IllegalArgumentException("Unknown level class: " + className);
	}

	private void setupScene(LevelParent myLevel) {
		myLevel.addPropertyChangeListener(this);
		Scene scene = myLevel.initializeScene();
		stage.setScene(scene);
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if ("levelChange".equals(event.getPropertyName())) {
			try {
				goToLevel((String) event.getNewValue());
			} catch (CustomException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText(e.getClass().toString());
				alert.show();
			}
		}
	}
}
