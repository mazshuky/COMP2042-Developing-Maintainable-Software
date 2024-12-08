package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;

/**
 * Represents a bomb object displaying on the screen and drops down.
 * The bomb has an image representation and plays a sound when dropped.
 */
public class Bomb extends ImageView {

    private static final String IMAGE_NAME = "/com/example/demo/images/bomb.png";
    private static final int BOMB_SIZE = 70;

    private AudioClip bombDropSound;

    /**
     * Constructor that initializes the bomb at a specific position on the screen.
     * @param xPosition  the initial x-coordinate of the bomb
     * @param yPosition  the initial y-coordinate of the bomb
     */
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

    /*
     * Initializes the sound effects for the bomb drop.
     */
    private void initializeSounds() {
        this.bombDropSound = SoundEffects.loadSound("/com/example/demo/sounds/bombdrop.wav", this.getClass());
    }

    /**
     * Moves the bomb down by 5 pixels and plays the drop sound.
     */
    public void moveDown() {
        this.setLayoutY(this.getLayoutY() + 5);
        playBombDropSound();
    }

    /**
     * Plays the bomb drop sound effect.
     */
    public void playBombDropSound() {
        if (bombDropSound != null) {
            bombDropSound.play();
        } else {
            System.out.println("Bomb drop sound is not initialized.");
        }
    }
}
