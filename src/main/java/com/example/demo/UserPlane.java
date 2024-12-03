package com.example.demo;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.AudioClip;

/**
 * Represents a user's fighter plane in the game. The UserPlane handles its own movement,
 * interaction with enemies, projectiles, and the situation when the game is over.
 */
public class UserPlane extends FighterPlane {

	private static final String IMAGE_NAME = "userplane.png";
	private static final double Y_UPPER_BOUND = -40;
	private static final double Y_LOWER_BOUND = 600.0;
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_HEIGHT = 50;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int PROJECTILE_X_POSITION = 90;
	private static final int PROJECTILE_Y_POSITION_OFFSET = -20;
	private static final Logger logger = Logger.getLogger(UserPlane.class.getName());

	private int velocityMultiplier;
	private int numberOfKills;
	private final HeartDisplay heartDisplay;

	private AudioClip shootSound;
	private AudioClip explosionSound;
	private AudioClip gameOverSound;

	/**
	 * Constructs a UserPlane with specified initial health and heart display.
	 *
	 * @param initialHealth The initial health of the plane.
	 * @param heartDisplay The HeartDisplay object used to display health status.
	 */
public UserPlane(int initialHealth, HeartDisplay heartDisplay) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		this.heartDisplay = heartDisplay;
		initializeSounds();
	}

/**
 * Initializes the audio clips used for various actions like shooting and explosions.
 */
private void initializeSounds() {
		this.velocityMultiplier = 0;
		this.shootSound = loadSound("/com/example/demo/sounds/userplaneshoot.wav");
		this.explosionSound = loadSound("/com/example/demo/sounds/enemyplaneexplode.wav");
		this.gameOverSound = loadSound("/com/example/demo/sounds/gameover.wav");
	}

/**
 * Loads an audio clip from the specified path.
 *
 * @param path The path to the sound file.
 * @return An AudioClip object if successfully loaded, or null if an error occurs.
 */
private AudioClip loadSound(String path) {
		try {
			var resource = getClass().getResource(path);
			if (resource != null) {
				return new AudioClip(resource.toExternalForm());
			} else {
				logger.warning("Sound file not found: " + path);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Failed to load sound: " + path, e);
		}
		return null;
	}

/**
 * Updates the position of the UserPlane based on its velocity.
 * Prevents the plane from moving out of predefined bounds.
 */
@Override
public void updatePosition() {
		if (!isMoving()) {
			return;
		}
		double initialTranslateY = getTranslateY();
		moveVertically(VERTICAL_VELOCITY * velocityMultiplier);
		if (isOutOfBounds()) {
			setTranslateY(initialTranslateY);
		}
	}

/**
 * Checks whether the UserPlane is out of the vertical bounds.
 *
 * @return true if the plane's position is out of bounds, otherwise false.
 */
private boolean isOutOfBounds() {
		double newPosition = getLayoutY() + getTranslateY();
		return newPosition < Y_UPPER_BOUND || newPosition > Y_LOWER_BOUND;
	}

/**
 * Updates the actor in the game's context. Currently, it only updates the position of the plane.
 */
@Override
public void updateActor() {
		updatePosition();
	}

/**
 * Fires a projectile from the UserPlane.
 *
 * @return A new UserProjectile instance with the initialized position.
 */
@Override
public ActiveActorDestructible fireProjectile() {
		playSound(shootSound);
		double[] projectilePosition = getProjectilePosition(PROJECTILE_X_POSITION, PROJECTILE_Y_POSITION_OFFSET);
		return new UserProjectile(projectilePosition[0], projectilePosition[1]);
	}

/**
 * Plays the sound effect associated with an explosion.
 */
public void playExplosionSound() {
		playSound(explosionSound);
	}

/**
 * Plays a specified sound effect if it is not null.
 *
 * @param sound The AudioClip to be played.
 */
private void playSound(AudioClip sound) {
		if (sound != null) {
			sound.play();
		}
	}

/**
 * Determines if the UserPlane is currently moving.
 *
 * @return true if the plane's velocity multiplier is not zero, otherwise false.
 */
private boolean isMoving() {
		return velocityMultiplier != 0;
	}

/**
 * Initiates an upward movement for the UserPlane.
 */
public void moveUp() {
		velocityMultiplier = -1;
	}

/**
 * Initiates a downward movement for the UserPlane.
 */
public void moveDown() {
		velocityMultiplier = 1;
	}

/**
 * Stops any movement of the UserPlane.
 */
public void stop() {
		velocityMultiplier = 0;
	}

/**
 * Retrieves the current number of kills made by the UserPlane.
 *
 * @return The count of kills.
 */
public int getNumberOfKills() {
		return numberOfKills;
	}

/**
 * Increments the count of enemies killed by the UserPlane.
 */
public void incrementKillCount() {
		numberOfKills++;
		System.out.println("Incremented kill count. Current number of kills: " + numberOfKills);
	}

	/**
	 * Sets the health of the UserPlane and updates the heart display.
	 *
	 * @param health The new health value.
	 */
	@Override
	public void setHealth(int health) {
		super.setHealth(health);
		heartDisplay.update(health);
	}

/**
 * Handles the event when the game is over, playing a sound and logging the event.
 */
public void handleGameOver() {
		logger.info("Game over. Playing game over sound.");
		playSound(gameOverSound);
	}

}
