package com.example.demo;

/**
 * Abstract class representing a destructible active actor in the game
 */
public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

	private boolean isDestroyed;

	/**
	 * Constructs a new ActiveActorDestructible
	 * @param imageName     the name of the image file
	 * @param imageHeight   the height of the image
	 * @param initialXPos   the initial x position
	 * @param initialYPos   the initial y position
	 */

	public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		isDestroyed = false;
	}

	/**
	 * Updates the position of the actor
	 */
	@Override
	public abstract void updatePosition();

	/**
	 * Updates the state of the actor
	 */
	public abstract void updateActor();

	/**
	 * Applies damage to the actor
	 */
	@Override
	public abstract void takeDamage();

	/**
	 * Destroys the actor
	 */
	@Override
	public void destroy() {
		setDestroyed(true);
	}

	/**
	 * Sets the destroyed state of the actor
	 *
	 * @param isDestroyed   the new destroyed state
	 */
	protected void setDestroyed(boolean isDestroyed) {
	 	this.isDestroyed = isDestroyed;
	}

	/**
	 * Checks if the actor is destroyed
	 *
	 * @return  true if the actor is destroyed, false otherwise
	 */
	public boolean isDestroyed() {
		return isDestroyed;
	}
	
}
