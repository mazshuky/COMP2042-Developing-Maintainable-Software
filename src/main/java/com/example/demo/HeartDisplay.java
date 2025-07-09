package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import java.net.URL;

/**
 * The {@code HeartDisplay} class represents a display of hearts in the game
 * to represent lives or health.
 */
public class HeartDisplay {

	private static final String HEART_IMAGE_NAME = "/com/example/demo/images/heart.png";
	private static final int HEART_HEIGHT = 30;
	private static final int INDEX_OF_FIRST_ITEM = 0;

	private final HBox container;
	private final double containerXPosition;
	private final double containerYPosition;
	private final int numberOfHeartsToDisplay;

	/**
	 * Constructs a new {@code HeartDisplay} with specified coordinates and number of hearts.
	 *
	 * @param xPosition       the x position of the heart display
	 * @param yPosition       the y position of the heart display
	 * @param heartsToDisplay the number of hearts to initially display
	 */
	public HeartDisplay(double xPosition, double yPosition, int heartsToDisplay) {
		this.containerXPosition = xPosition;
		this.containerYPosition = yPosition;
		this.numberOfHeartsToDisplay = heartsToDisplay;
		this.container = new HBox();
		initializeContainer();
		initializeHearts();
	}

	/**
	 * Initializes the container's position for the heart display.
	 */
	private void initializeContainer() {
		container.setLayoutX(containerXPosition);
		container.setLayoutY(containerYPosition);
	}

	/**
	 * Initializes and adds heart images to the container based on the specified number.
	 * Throws an {@link IllegalArgumentException} if a heart image resource cannot be found.
	 */
	private void initializeHearts() {
		for (int i = 0; i < numberOfHeartsToDisplay; i++) {
			ImageView heart = createHeartImageView();
			if (heart != null) {
				container.getChildren().add(heart);
			} else {
				throw new IllegalArgumentException("Heart image not found: " + HEART_IMAGE_NAME);
			}
		}
	}

	/**
	 * Creates an {@link ImageView} for the heart image.
	 *
	 * @return the {@link ImageView} for the heart image or null if the image resource is not found
	 */
	private ImageView createHeartImageView() {
		URL resource = getClass().getResource(HEART_IMAGE_NAME);
		if (resource != null) {
			Image heartImage = new Image(resource.toExternalForm());
			ImageView heart = new ImageView(heartImage);
			heart.setFitHeight(HEART_HEIGHT);
			heart.setPreserveRatio(true);
			return heart;
		}
		return null;
	}

	/**
	 * Removes a heart if no hearts are left, nothing happens.
	 */
	public void removeHeart() {
		if (!container.getChildren().isEmpty()) {
			container.getChildren().remove(INDEX_OF_FIRST_ITEM);
		}
	}

	/**
	 * Updates the heart based on the current health.
	 *
	 * @param health The current health value.
	 */
	public void update(int health) {
		container.getChildren().clear();
		for (int i = 0; i < health; i++) {
			ImageView heart = createHeartImageView();
			if (heart != null) {
				container.getChildren().add(heart);
			}
		}
	}

	/**
	 * Retrieves the container that holds the heart images.
	 *
	 * @return the {@link HBox} container for the heart display
	 */
	public HBox getContainer() {
		return container;
	}
}
