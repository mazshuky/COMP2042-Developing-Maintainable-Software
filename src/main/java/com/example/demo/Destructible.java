package com.example.demo;

/**
 * The {@code Destructible} interface representing a destructible entity in the game.
 */
public interface Destructible {

	/**
	 * Inflicts damage to the destructible entity.
	 */
	void takeDamage();

	/**
	 * Destroys the destructible entity.
	 */
	void destroy();
	
}
