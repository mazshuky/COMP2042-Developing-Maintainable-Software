package com.example.demo;

public abstract class FighterPlane extends ActiveActorDestructible {

	private static final int DAMAGE_AMOUNT = 1;
	private int health;

	public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
	}

	public abstract ActiveActorDestructible fireProjectile();

	@Override
	public void takeDamage() {
		decreaseHealth();
		if (health <= 0) {
			this.destroy();
		}
	}

	protected void decreaseHealth() {
		this.health = Math.max(0, this.health - DAMAGE_AMOUNT);
	}

	protected double[] getProjectilePosition(double xPositionOffset, double yPositionOffset) {
		return new double[] {
				getLayoutX() + getTranslateX() + xPositionOffset,
				getLayoutY() + getTranslateY() + yPositionOffset
		};
	}

	public int getHealth() {
		return health;
	}
		
}
