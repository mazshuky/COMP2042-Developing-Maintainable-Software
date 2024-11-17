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


	// extract exception handling via encapsulation
	// launchGame method can be simplified by extracting goToLevel call into a method that handles initial setup

	/**
	public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException  {
		stage.show();
		goToLevel(LEVEL_ONE_CLASS_NAME);
	}
	*/

	// extract logic for creating an instance of LevelParent into a separate method
	// extract logic for setting the scene into a separate method
	// remove unnecessary addition and immediate removal of PropertyChangeListener
	/** private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> myClass = Class.forName(className);
		Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
		LevelParent myLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());

		// Add this controller as a PropertyChangeListener
		myLevel.addPropertyChangeListener(this);
		myLevel.removePropertyChangeListener(this);
		Scene scene = myLevel.initializeScene();
		stage.setScene(scene);
		myLevel.startGame();
	}
	 */

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
			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
					 | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText(e.getClass().toString());
				alert.show();
			}
		}
	}
