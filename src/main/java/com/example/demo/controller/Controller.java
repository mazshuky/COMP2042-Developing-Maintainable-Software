package com.example.demo.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
// import java.lang.reflect.Constructor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

// import javafx.scene.Scene;
import com.example.demo.LevelParent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
// import com.example.demo.LevelParent;

public class Controller implements PropertyChangeListener {

	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.LevelOne";
	private final Stage stage;

	public Controller(Stage stage) {
		this.stage = stage;
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
			LevelParent myLevel = createLevelInstance(className);
			setupScene(myLevel);
			myLevel.startGame();
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				 | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new CustomException("Error going to level: " + className, e);
		}
	}

	private LevelParent createLevelInstance(String className) throws ClassNotFoundException, NoSuchMethodException,
			InstantiationException, IllegalAccessException, InvocationTargetException {
		Class<?> myClass = Class.forName(className);
		Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
		return (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());
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