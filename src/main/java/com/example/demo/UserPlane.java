package com.example.demo;

import java.util.logging.Logger;
import javafx.scene.media.AudioClip;
import javafx.animation.Timeline;

/**
 * Represents the user plane in the game. The UserPlane handles its own movement,
 * interaction with enemies, projectiles, and the situation when the game wins or loses.
 */
public class UserPlane extends FighterPlane {

	private static final String IMAGE_NAME = "userplane.png";
	private static final double Y_UPPER_BOUND = -40;
	private static final double Y_LOWER_BOUND = 600.0;
	private static final double X_LEFT_BOUND = 0.0;
	private static final double X_RIGHT_BOUND = 800.0;
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_HEIGHT = 100;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HORIZONTAL_VELOCITY = 8;
	private static final int PROJECTILE_X_POSITION = 100;
	private static final int PROJECTILE_Y_POSITION_OFFSET = -20;
	private static final Logger logger = Logger.getLogger(UserPlane.class.getName());

	private int verticalVelocityMultiplier;
	private int horizontalVelocityMultiplier;
	private int numberOfKills;
	private final HeartDisplay heartDisplay;

	private AudioClip shootSound;
	private AudioClip gameOverSound;
	private AudioClip youWinSound;
	private AudioClip hitSound;

	private Timeline bombTimeline;

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
	 * Initializes the audio clips used for various actions in the game.
	 */
	private void initializeSounds() {
		this.verticalVelocityMultiplier = 0;
		this.horizontalVelocityMultiplier = 0;
		this.shootSound = SoundEffects.loadSound("/com/example/demo/sounds/usershoot.wav", this.getClass());
		this.gameOverSound = SoundEffects.loadSound("/com/example/demo/sounds/gameover.wav", this.getClass());
		this.youWinSound = SoundEffects.loadSound("/com/example/demo/sounds/youwin.wav", this.getClass());
		this.hitSound = SoundEffects.loadSound("/com/example/demo/sounds/userhit.wav", this.getClass());
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
			double initialTranslateX = getTranslateX();
			moveVertically(VERTICAL_VELOCITY * verticalVelocityMultiplier);
			moveHorizontally(HORIZONTAL_VELOCITY * horizontalVelocityMultiplier);
			if (isOutOfBounds()) {
				setTranslateY(initialTranslateY);
				setTranslateY(initialTranslateX);
			}
		}

	/**
	 * Checks whether the UserPlane is out of the vertical bounds.
	 *
	 * @return true if the plane's position is out of bounds, otherwise false.
	 */
	private boolean isOutOfBounds() {
		double newYPosition = getLayoutY() + getTranslateY();
		double newXPosition = getLayoutX() + getTranslateX();
		return newYPosition < Y_UPPER_BOUND || newYPosition > Y_LOWER_BOUND || newXPosition < X_LEFT_BOUND || newXPosition > X_RIGHT_BOUND;
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
		return verticalVelocityMultiplier != 0 || horizontalVelocityMultiplier != 0;
	}

	/**
	 * Initiates an upward movement for the UserPlane.
	 */
	public void moveUp() {
		verticalVelocityMultiplier = -1;
	}

	/**
	 * Initiates a downward movement for the UserPlane.
	 */
	public void moveDown() {
			verticalVelocityMultiplier = 1;
		}

		public void moveLeft() {
			horizontalVelocityMultiplier = -1;
		}

		public void moveRight() {
			horizontalVelocityMultiplier = 1;
		}

	/**
	 * Stops any movement of the UserPlane.
	 */
	public void stop() {
		verticalVelocityMultiplier = 0;
		horizontalVelocityMultiplier = 0;
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
			if (bombTimeline != null) {
				bombTimeline.stop();
			}
	}

	public void setBombTimeline(Timeline bombTimeline) {
		this.bombTimeline = bombTimeline;
	}

	/**
	 * Handles the event when the UserPlane is hit, playing a sound.
	 */
	public void handleHit() {
		playSound(hitSound);
	}

	/**
	 * Handles the event when the player wins, playing a sound and showing the win image.
	 *
	 * @param winImage Display the winImage object
	 */
	public void handleWin(WinImage winImage) {
		logger.info("You win! Playing win sound.");
		playSound(youWinSound);
		winImage.showWinImage();
	}

}
