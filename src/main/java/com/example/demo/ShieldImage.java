package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ShieldImage extends ImageView {
	
	private static final String IMAGE_NAME = "/com/example/demo/images/shield.png";
	private static final int SHIELD_SIZE = 50;
	
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

	public void showShield() {
		System.out.println("Shield is now visible.");
		this.setVisible(true);
		this.toFront();
	}
	
	public void hideShield() {
		this.setVisible(false);
	}

}
