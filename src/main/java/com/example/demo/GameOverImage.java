package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;

/**
 * The {@code GameOverImage} class extends {@link ImageView} and represents
 * a custom image view for displaying a "Game Over" image in a JavaFX application.
 * It provides constructors to set image position and dimensions.
 */
public class GameOverImage extends ImageView {

	/** The path to the "Game Over" image resource. */
	private static final String IMAGE_NAME = "/com/example/demo/images/gameover.png";

	/**
	 * Constructs a {@code GameOverImage} with specified position and dimensions.
	 *
	 * @param xPosition the x position of the image view
	 * @param yPosition the y position of the image view
	 * @param width     the width of the image
	 * @param height    the height of the image
	 */
	public GameOverImage(double xPosition, double yPosition, double width, double height) {
		setGameOverImage();
		setPosition(xPosition, yPosition);
		setDimensions(width, height);
	}

	/**
	 * Loads the "Game Over" image from resources and sets it to this image view.
	 * Logs an error if the image resource cannot be found.
	 */
	private void setGameOverImage() {
		URL resource = getClass().getResource(IMAGE_NAME);
		if (resource != null) {
			setImage(new Image(resource.toExternalForm()));
		} else {
			System.err.println("Game Over image not found: " + IMAGE_NAME);
		}
	}

	/**
	 * Sets the position of the image view.
	 *
	 * @param xPosition the x position of the image view
	 * @param yPosition the y position of the image view
	 */
	private void setPosition(double xPosition, double yPosition) {
		setLayoutX(xPosition);
		setLayoutY(yPosition);
	}

	/**
	 * Sets the dimensions (width and height) of the image view.
	 *
	 * @param width  the width of the image
	 * @param height the height of the image
	 */
	private void setDimensions(double width, double height) {
		setFitWidth(width);
		setFitHeight(height);
	}

}
