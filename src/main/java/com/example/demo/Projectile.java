package com.example.demo;

/**
 * Abstract class representing a projectile in the game
 */
public abstract class Projectile extends ActiveActorDestructible {

	/**
	 * Constructor for a projectile
	 * @param imageName    the name of the image representing the projectile
	 * @param imageHeight  the height of the image
	 * @param initialXPos  the initial X position of the projectile
	 * @param initialYPos  the initial Y position of the projectile
	 */
	public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
	}

	/**
	 * Handles the damage taken by the projectile
	 * Destroys the projectile when it takes damage
	 */
	@Override
	public void takeDamage() {
		this.destroy();
	}

	/**
	 * Updates the position of the projectile
	 * Mehod to be implemented by subclasses
	 */
	@Override
	public abstract void updatePosition();

}
