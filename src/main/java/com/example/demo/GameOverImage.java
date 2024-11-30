package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;

public class GameOverImage extends ImageView {
	
	private static final String IMAGE_NAME = "/com/example/demo/images/gameover.png";

	public GameOverImage(double xPosition, double yPosition, double width, double height) {
		setGameOverImage();
		setPosition(xPosition, yPosition);
		setDimensions(width, height);
	}

	private void setGameOverImage() {
		URL resource = getClass().getResource(IMAGE_NAME);
		if (resource != null) {
			setImage(new Image(resource.toExternalForm()));
		} else {
			System.err.println("Game Over image not found: " + IMAGE_NAME);
		}
	}

	private void setPosition(double xPosition, double yPosition) {
		setLayoutX(xPosition);
		setLayoutY(yPosition);
	}

	private void setDimensions(double width, double height) {
		setFitWidth(width);
		setFitHeight(height);
	}

}
