package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import java.net.URL;

/**
 * Class representing the heart display in the game
 */
public class HeartDisplay {

	private static final String HEART_IMAGE_NAME = "/com/example/demo/images/heart.png";
	private static final int HEART_HEIGHT = 50;
	private static final int INDEX_OF_FIRST_ITEM = 0;

	private final HBox container;
	private final double containerXPosition;
	private final double containerYPosition;
	private final int numberOfHeartsToDisplay;

	/**
	 * Constructs a new HeartDisplay
	 *
	 * @param xPosition        the x position of the heart display
	 * @param yPosition        the y position of the heart display
	 * @param heartsToDisplay  the number of hearts to display
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
	 * Initializes the container for the heart display
	 */
	private void initializeContainer() {
		container.setLayoutX(containerXPosition);
		container.setLayoutY(containerYPosition);
	}

	/**
	 * Initializes the hearts to display
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
	 * Creates an ImageView for the heart image
	 *
	 * @return   the ImageView for the heart image or null if image is not found
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
	 * Removes a heart from the heart display
	 */
	public void removeHeart() {
		if (!container.getChildren().isEmpty()) {
			container.getChildren().remove(INDEX_OF_FIRST_ITEM);
		}
	}

	/**
	 * Gets the container for the heart display
	 *
	 * @return   the container for the heart display
	 */
	public HBox getContainer() {
		return container;
	}
}
