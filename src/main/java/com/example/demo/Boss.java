package com.example.demo;

import java.util.*;
import javafx.scene.media.AudioClip;

/**
 * Represents the boss enemy in the game.
 * The Boss has specific behaviors such as moving in pattern,
 * firing projectiles, and activating a shield.
 */
public class Boss extends FighterPlane {

	private static final String IMAGE_NAME = "bossplane.png";
	private static final double INITIAL_X_POSITION = 1120.0;
	private static final double INITIAL_Y_POSITION = 450;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 0.0;
	private static final double BOSS_FIRE_RATE = .04;
	private static final double BOSS_SHIELD_PROBABILITY = 0.05;
	private static final int IMAGE_HEIGHT = 170;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HEALTH = 10;
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
	private static final int ZERO = 0;
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
	private static final int Y_POSITION_UPPER_BOUND = -15;
	private static final int Y_POSITION_LOWER_BOUND = 475;
	private static final int MAX_FRAMES_WITH_SHIELD = 50;

	private final List<Integer> movePattern;
	private boolean isShielded;
	private int consecutiveMovesInSameDirection;
	private int indexOfCurrentMove;
	private int framesWithShieldActivated;

	private AudioClip fireballSound;

	/**
	 * Creates a new Boss object with the specified image, initial position, and health.
	 */
	public Boss() {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		movePattern = new ArrayList<>();
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		framesWithShieldActivated = 0;
		isShielded = false;
		initializeMovePattern();
		initializeSounds();
	}

	/**
	 * Initializes the sound effects for the Boss.
	 */
	private void initializeSounds() {
		this.fireballSound = SoundEffects.loadSound("/com/example/demo/sounds/fireball.wav", this.getClass());
	}

	/**
	 * Plays the specified sound effect.
	 * @param sound the AudioClip to be played
	 */
	private void playSound(AudioClip sound) {
		if (sound != null) {
			sound.play();
		}
	}

	/**
	 * Updates the position of the Boss by moving it vertically.
	 * If the Boss is out of bounds, it resets its position and move pattern.
	 */
	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		moveVertically(getNextMove());
		if (isOutOfBounds()) {
			setTranslateY(initialTranslateY);
			resetMovePattern();
		}
	}

	/**
	 * Updates the Boss by updating its position and shield status.
	 */
	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
	}

	/**
	 * Fires a projectile if the Boss is ready to fire.
	 * @return the Boss's projectile if it is fired, null otherwise
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		if (bossFiresInCurrentFrame()) {
			playFireballSound();
			return new BossProjectile(getProjectileInitialPosition());
		}
		return null;
	}

	/**
	 * Applies damage to the Boss if it is not shielded.
	 */
	@Override
	public void takeDamage() {
		if (!isShielded) {
			super.takeDamage();
		}
	}

	/**
	 * Initializes the move pattern for the Boss.
	 */
	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(ZERO);
		}
		Collections.shuffle(movePattern);
	}

	/**
	 * Updates the shield status of the Boss.
	 */
	private void updateShield() {
		if (isShielded) {
			framesWithShieldActivated++;
		} else if (shieldShouldBeActivated()) {
			activateShield();
		}
		if (shieldExhausted()) {
			deactivateShield();
		}
	}

	/**
	 * Returns the next move in the Boss's move pattern.
	 * @return the next move value
	 */
	private int getNextMove() {
		int currentMove = movePattern.get(indexOfCurrentMove);
		consecutiveMovesInSameDirection++;
		if (consecutiveMovesInSameDirection == MAX_FRAMES_WITH_SAME_MOVE) {
			resetMovePattern();
		}
		return currentMove;
	}

	/**
	 * Determines if the Boss should fire a projectile in the current frame.
	 * @return true if the Boss should fire, false otherwise
	 */
	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE;
	}

	/**
	 * Returns the initial position of the Boss's projectile.
	 * @return the y-coordinate of the projectile's initial position
	 */
	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + ((double) IMAGE_HEIGHT / 2) + PROJECTILE_Y_POSITION_OFFSET;
	}

	/**
	 * Determines if the Boss should activate its shield.
	 * @return true if the Boss should activate its shield, false otherwise
	 */
	private boolean shieldShouldBeActivated() {
		return Math.random() < BOSS_SHIELD_PROBABILITY;
	}

	/**
	 * Determines if the Boss's shield has been exhausted.
	 * @return true if the shield has been exhausted, false otherwise
	 */
	private boolean shieldExhausted() {
		return framesWithShieldActivated == MAX_FRAMES_WITH_SHIELD;
	}

	/**
	 * Activates the Boss's shield.
	 */
	private void activateShield() {
		isShielded = true;
	}

	/**
	 * Deactivates the Boss's shield.
	 */
	private void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
	}

	/**
	 * Determines if the Boss is out of bounds.
	 * @return true if the Boss is out of bounds, false otherwise
	 */
	private boolean isOutOfBounds() {
		double currentPosition = getLayoutY() + getTranslateY();
		return currentPosition < Y_POSITION_UPPER_BOUND || currentPosition > Y_POSITION_LOWER_BOUND;
	}

	/**
	 * Resets the Boss's move pattern.
	 */
	private void resetMovePattern() {
		Collections.shuffle(movePattern);
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove++;
		if (indexOfCurrentMove == movePattern.size()) {
			indexOfCurrentMove = 0;
		}
	}

	/**
	 * Checks if the Boss is shielded.
	 * @return true if the Boss is shielded, false otherwise
	 */
	public boolean isShielded() {
		return isShielded;
	}

	/**
	 * Plays the fireball sound effect.
	 */
	public void playFireballSound() {
		playSound(fireballSound);
	}

	/**
	 * Activates the Boss's shield, intended for JUnit testing only.
	 */
	public void activateShieldPublic() {
		activateShield();
	}

	/**
	 * Updates the shield status of the Boss, intended for JUnit testing only.
	 */
	public void updateShieldPublic() {
		updateShield();
	}

	/**
	 * Returns the fireball sound effect, intended for JUnit testing only.
	 *
	 * @return the AudioClip representing the fireball sound effect
	 */
	public AudioClip getFireballSound() {
		return fireballSound;
	}
}
