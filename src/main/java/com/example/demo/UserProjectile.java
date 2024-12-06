package com.example.demo;

/**
 * CLass represents a projectile fired by the user.
 */
public class UserProjectile extends Projectile {

	private static final String IMAGE_NAME = "userfire.png";
	private static final int IMAGE_HEIGHT = 30;
	private static final int HORIZONTAL_VELOCITY = 15;

	/**
	 * Constructor for the UserProjectile class.
	 * @param initialXPos the initial x position of the projectile
	 * @param initialYPos the initial y position of the projectile
	 */
	public UserProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos + 30, initialYPos + 60);
	}

	/**
	 * Updates the projectile's position by moving it horizontally.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Updates the actor's state. Currently, it only updates the position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}
	
}
