package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameOverImage extends ImageView {
	
	private static final String IMAGE_NAME = "/com/example/demo/images/gameover.png";

	public GameOverImage(double xPosition, double yPosition, double width, double height) {
		var resource = getClass().getResource(IMAGE_NAME);
		if (resource != null) {
			setImage(new Image(resource.toExternalForm()));
		}
		setLayoutX(xPosition);
		setLayoutY(yPosition);
		setFitWidth(width);
		setFitHeight(height);
	}

}
