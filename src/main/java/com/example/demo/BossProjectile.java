package com.example.demo;

/**
 * The {@code BossProjectile} class represents a projectile fired by the boss
 * enemy in the game. It extends the {@link Projectile} class and moves
 * horizontally across the screen.
 */
public class BossProjectile extends Projectile {
	
	private static final String IMAGE_NAME = "fireball.png";
	private static final int IMAGE_HEIGHT = 30;
	private static final int HORIZONTAL_VELOCITY = -15;
	private static final int INITIAL_X_POSITION = 950;

	/**
	 * Constructs a BossProjectile with the specified initial Y position.
	 *
	 * @param initialYPos The initial Y position of the projectile.
	 */
	public BossProjectile(double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos);
	}

	/**
	 * Updates the position of the BossProjectile by moving it horizontally.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Updates the actor in the game. At the moment, it only modifies the position of the projectile.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}
	
}
