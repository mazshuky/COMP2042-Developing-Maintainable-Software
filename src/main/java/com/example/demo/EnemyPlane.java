package com.example.demo;

import javafx.scene.media.AudioClip;

/**
 * The {@code EnemyPlane} class represents an enemy plane in the game.
 * It extends the {@link FighterPlane} class and includes functionality
 * for firing projectiles and playing sound effects.
 */
public class EnemyPlane extends FighterPlane {

	private static final String IMAGE_NAME = "enemyplane.png";
	private static final int IMAGE_HEIGHT = 100;
	private static final int HORIZONTAL_VELOCITY = -6;
	private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 25.0;
	private static final int INITIAL_HEALTH = 1;
	private static final double FIRE_RATE = .01;

	private AudioClip enemyDestroySound;
	private AudioClip fireSound;

	/**
	 * Constructs an {@code EnemyPlane} with the specified initial position.
	 *
	 * @param initialXPos the initial X position
	 * @param initialYPos the initial Y position
	 */
	public EnemyPlane(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
		initializeSounds();
	}

	/**
	 * Initializes the sound effects for the enemy plane.
	 */
	private void initializeSounds() {
		this.enemyDestroySound = SoundEffects.loadSound("/com/example/demo/sounds/enemydestroy.wav", this.getClass());
		this.fireSound = SoundEffects.loadSound("/com/example/demo/sounds/enemyfire.wav", this.getClass());
	}

	/**
	 * Plays the specified sound effect.
	 *
	 * @param sound The {@code AudioClip} to be played.
	 */
	private void playSound(AudioClip sound) {
		if (sound != null) {
			sound.play();
		}
	}

	/**
	 * Updates the position of the enemy plane by moving it horizontally.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Fires a projectile from the enemy plane with a certain probability.
	 *
	 * @return An {@code ActiveActorDestructible} representing the fired projectile,
	 *         or {@code null} if no projectile is fired.
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		if (Math.random() < FIRE_RATE) {
			double[] projectilePosition = getProjectilePosition(PROJECTILE_X_POSITION_OFFSET, PROJECTILE_Y_POSITION_OFFSET);
			double projectileXPosition = projectilePosition[0];
			double projectileYPosition = projectilePosition[1];
			playSound(fireSound);
			return new EnemyProjectile(projectileXPosition, projectileYPosition);
		}
		return null;
	}

	/**
	 * Updates the actor in the game. At the moment, it only modifies the position of the enemy plane.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

	/**
	 * Plays the sound effect for when the enemy plane is destroyed.
	 */
	public void playEnemyDestroySound() {
		playSound(enemyDestroySound);
	}

}
