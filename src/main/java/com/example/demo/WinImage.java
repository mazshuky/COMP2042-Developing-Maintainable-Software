package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The WinImage class represents an image that is displayed when the player wins the game.
 * It extends the ImageView class from JavaFX.
 */
public class WinImage extends ImageView {

	private static final String IMAGE_NAME = "/com/example/demo/images/youwin.png";
	private static final int HEIGHT = 400;
	private static final int WIDTH = 500;

	/**
	 * Constructs a WinImage object with the specified x and y positions.
	 *
	 * @param xPosition the x position
	 * @param yPosition the y position
	 */
	public WinImage(double xPosition, double yPosition) {
		initializeImage();
		this.setVisible(false);
		this.setFitHeight(HEIGHT);
		this.setFitWidth(WIDTH);
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
	}

	/**
	 * Initializes the image by loading it from the specified resource path.
	 * If the resource is not found, an error message is printed to the standard error stream.
	 */
	private void initializeImage() {
		var resource = getClass().getResource(IMAGE_NAME);
		if (resource != null) {
			setImage(new Image(resource.toExternalForm()));
		} else {
			System.err.println("Resource not found: " + IMAGE_NAME);
		}
	}

	/**
	 * Makes the win image visible.
	 */
	public void showWinImage() {
		this.setVisible(true);
	}

}
