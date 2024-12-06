package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * ShieldImage class represents the shield image that appears on the screen.
 */
public class ShieldImage extends ImageView {
	
	private static final String IMAGE_NAME = "/com/example/demo/images/shield.png";
	private static final int SHIELD_SIZE = 50;

	/**
	 * Constructor for ShieldImage class.
	 * @param xPosition the x-coordinate of the shield
	 * @param yPosition the y-coordinate of the shield
	 */
	public ShieldImage(double xPosition, double yPosition) {
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
		System.out.println("ShieldImage initialized at position: (" + xPosition + ", " + yPosition + ")");
		var resource = getClass().getResource(IMAGE_NAME);
		if (resource != null) {
			System.out.println("Resource loaded from: " + resource.toExternalForm());
			this.setImage(new Image(resource.toExternalForm()));
		} else {
			System.out.println("Shield image resource not found.");
		}
		this.setFitHeight(SHIELD_SIZE);
		this.setFitWidth(SHIELD_SIZE);
	}

	/**
	 * Shows the shield on the screen.
	 */
	public void showShield() {
		System.out.println("Shield is now visible.");
		this.setVisible(true);
		this.toFront();
	}

	/*
	 * Hides the shield on the screen.
	 */
	public void hideShield() {
		this.setVisible(false);
	}

}
