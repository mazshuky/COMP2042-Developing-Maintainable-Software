package com.example.demo;

import javafx.scene.image.*;

/**
 * Abstract class representing an active actor in the game
 */
public abstract class ActiveActor extends ImageView {
	
	private static final String IMAGE_LOCATION = "/com/example/demo/images/";

	/**
	 * Constructs a new Activator
	 *
	 * @param imageName    the name of the image file
	 * @param imageHeight  the height of the image
	 * @param initialXPos  the initial x position
	 * @param initialYPos  the initial y position
	 */
	public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		this.setImage(new Image(getClass().getResource(IMAGE_LOCATION + imageName).toExternalForm()));
		this.setLayoutX(initialXPos);
		this.setLayoutY(initialYPos);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true);
	}

	/**
	 * Updates the position of the actor
	 */
	public abstract void updatePosition();

	/**
	 * Moves the actor horizontally
	 *
	 * @param horizontalMove   the distance to move horizontally
	 */
	protected void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	/**
	 * Moves the actor vertically
	 *
	 * @param verticalMove  the distance to move vertically
	 */
	protected void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}

}
