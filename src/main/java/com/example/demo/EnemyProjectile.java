package com.example.demo;

/**
 * Represents a projectile fired by an enemy in the game.
 * This class extends the Projectile class and defines specific properties
 * and behaviors for enemy projectiles.
 */
public class EnemyProjectile extends Projectile {
	
	private static final String IMAGE_NAME = "enemyFire.png";
	private static final int IMAGE_HEIGHT = 60;
	private static final int HORIZONTAL_VELOCITY = -10;

	/**
	 * Constructs an EnemyProjectile with the specified initial X and Y positions.
	 *
	 * @param initialXPos the initial X position
	 * @param initialYPos the initial Y position
	 */
	public EnemyProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	/**
	 * Updates the position of the EnemyProjectile by moving it horizontally.
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

	/**
	 * Gets the current X position of the EnemyProjectile.
	 *
	 * @return the current X position
	 */
	public double getXPos() {
		return super.getXPos();
	}

	/**
	 * Gets the current Y position of the EnemyProjectile.
	 *
	 * @return the current Y position
	 */
	public double getYPos() {
		return super.getYPos();
	}

}
