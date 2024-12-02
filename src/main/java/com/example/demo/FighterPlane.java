package com.example.demo;

/**
 * The FighterPlane class represents a type of destructible active actor with the ability to fire projectiles
 * and take damage. It serves as an abstract base for specific fighter plane implementations.
 */
public abstract class FighterPlane extends ActiveActorDestructible {

	private static final int DAMAGE_AMOUNT = 1;
	private int health;

	/**
	 * Constructs a FighterPlane with specified image and initial position.
	 *
	 * @param imageName   the name of the image representing the fighter plane
	 * @param imageHeight the height of the image
	 * @param initialXPos the initial X position of the fighter plane
	 * @param initialYPos the initial Y position of the fighter plane
	 * @param health      the initial health of the fighter plane
	 */
	public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
	}

	/**
	 * Fires a projectile from the fighter plane. The concrete implementation should define
	 * the specific behavior of the projectile.
	 *
	 * @return a new instance of ActiveActorDestructible representing the fired projectile
	 */
	public abstract ActiveActorDestructible fireProjectile();

	/**
	 * Decreases the health of the fighter plane by a predefined damage amount. If the health
	 * drops to zero or below, the plane is destroyed.
	 */
	@Override
	public void takeDamage() {
		decreaseHealth();
		if (health <= 0) {
			this.destroy();
		}
	}

	/**
	 * Decreases the health of the fighter plane by the constant DAMAGE_AMOUNT, ensuring that the health
	 * remains non-negative.
	 */
	protected void decreaseHealth() {
		this.health = Math.max(0, this.health - DAMAGE_AMOUNT);
	}

	/**
	 * Calculates and returns the projectile's initial position based on offsets
	 * from the fighter plane's current position.
	 *
	 * @param xPositionOffset the X offset for the projectile's position
	 * @param yPositionOffset the Y offset for the projectile's position
	 * @return an array containing the X and Y coordinates of the projectile's initial position
	 */
	protected double[] getProjectilePosition(double xPositionOffset, double yPositionOffset) {
		return new double[] {
				getLayoutX() + getTranslateX() + xPositionOffset,
				getLayoutY() + getTranslateY() + yPositionOffset
		};
	}

	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * Returns the current health of the fighter plane.
	 *
	 * @return the health of the fighter plane
	 */
	public int getHealth() {
		return health;
	}

		
}
