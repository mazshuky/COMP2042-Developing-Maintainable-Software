package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bomb extends ImageView {

    private static final String IMAGE_NAME = "/com/example/demo/images/bomb.png";
    private static final int BOMB_SIZE = 70;

    public Bomb(double xPosition, double yPosition) {
        this.setLayoutX(xPosition);
        this.setLayoutY(yPosition);
        var resource = getClass().getResource(IMAGE_NAME);
        if (resource != null) {
            this.setImage(new Image(resource.toExternalForm()));
        } else {
            System.out.println("Bomb image resource not found.");
        }
        this.setFitHeight(BOMB_SIZE);
        this.setFitWidth(BOMB_SIZE);
    }

    public void moveDown() {
        this.setLayoutY(this.getLayoutY() + 5);
    }
}