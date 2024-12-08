package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;

public class Bomb extends ImageView {

    private static final String IMAGE_NAME = "/com/example/demo/images/bomb.png";
    private static final int BOMB_SIZE = 70;

    private AudioClip bombDropSound;

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
        this.setPreserveRatio(true);
        initializeSounds();
    }

    private void initializeSounds() {
        this.bombDropSound = SoundEffects.loadSound("/com/example/demo/sounds/bombdrop.wav", this.getClass());
    }

    public void moveDown() {
        this.setLayoutY(this.getLayoutY() + 5);
        playBombDropSound();
    }

    public void playBombDropSound() {
        if (bombDropSound != null) {
            bombDropSound.play();
        } else {
            System.out.println("Bomb drop sound is not initialized.");
        }
    }
}
