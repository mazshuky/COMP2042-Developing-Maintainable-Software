package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import java.net.URL; //import this one

public class HeartDisplay {

	private static final String HEART_IMAGE_NAME = "/com/example/demo/images/heart.png";
	private static final int HEART_HEIGHT = 50;
	private static final int INDEX_OF_FIRST_ITEM = 0;
	private final HBox container; // Mark as final since it's initialized in the constructor
	private final double containerXPosition; // Mark as final
	private final double containerYPosition; // Mark as final
	private final int numberOfHeartsToDisplay; // Mark as final

	public HeartDisplay(double xPosition, double yPosition, int heartsToDisplay) {
		this.containerXPosition = xPosition;
		this.containerYPosition = yPosition;
		this.numberOfHeartsToDisplay = heartsToDisplay;
		this.container = new HBox(); // Initialize the container here
		initializeContainer();
		initializeHearts();
	}

	private void initializeContainer() {
		container.setLayoutX(containerXPosition);
		container.setLayoutY(containerYPosition);
	}

	private void initializeHearts() {
		for (int i = 0; i < numberOfHeartsToDisplay; i++) {
			// Get the resource and check for null
			URL resource = getClass().getResource(HEART_IMAGE_NAME);
			if (resource != null) {
				Image heartImage = new Image(resource.toExternalForm());
				ImageView heart = new ImageView(heartImage);

				heart.setFitHeight(HEART_HEIGHT);
				heart.setPreserveRatio(true);
				container.getChildren().add(heart);
			} else {
				// Throw an exception if the heart image is not found
				throw new IllegalArgumentException("Heart image not found: " + HEART_IMAGE_NAME);
			}
		}
	}

	public void removeHeart() {
		if (!container.getChildren().isEmpty()) {
			container.getChildren().remove(INDEX_OF_FIRST_ITEM);
		}
	}

	public HBox getContainer() {
		return container;
	}
}
